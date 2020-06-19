package something.something.repositories.plane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.model.plane.SilverPlane;
import something.something.repositories.PlaneAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class PlaneRepository implements PlaneRepositoryContract{


    //nombre del archivo para guardar
    private static final String FILENAME = "planes.json";
    //lista de vuelos
    private List<Plane> planes;
    //guardar instancia del singleton
    private static PlaneRepository instance = null;

    //en el constructor de cargan los datos del archivo
    //TODO sacar el load y llamarlo aparte?
    private PlaneRepository() throws IOException {
        planes = new ArrayList<>();
        load();
    }

    public static PlaneRepository getInstance() throws IOException {
        if(instance == null){
            instance = new PlaneRepository();
        }
        return instance;
    }

    private void load() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Plane.class, new PlaneAdapter<Plane>());
        Gson gson = builder.create();
        File flightsFile = new File(FILENAME);
        //si el archivo existe se leen los registros
        if(flightsFile.exists()){
            JsonReader reader = new JsonReader(new FileReader(FILENAME));
            Type type = new TypeToken<List<Plane>>(){}.getType();
            planes = gson.fromJson(reader, type);
            reader.close();
        }else{//sino se crea un archivo vacio
            FileWriter fw = new FileWriter(FILENAME);
            gson.toJson(planes, fw);
            fw.close();
        }

    }




    @Override
    public Boolean add(Plane plane) {
        if(!planes.contains(plane)){
            planes.add(plane);
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(String id) {
        for(Plane f: planes){
            if(f.getId().equals(id)){
                planes.remove(f);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Plane> getAll() {
        return planes;
    }

    @Override
    public Plane get(String id) {
        for(Plane p: planes){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }

    @Override
    public Boolean exists(Plane plane) {
        return planes.contains(plane);
    }

    @Override
    public void commit() throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plane>>(){}.getType();
        FileWriter fw = new FileWriter(FILENAME);
        gson.toJson(planes, type, fw);
        fw.close();
    }

    @Override
    public List<Plane> getAvailablePlanes(int maxCapacity) {
        List<Plane> available = new ArrayList<>();
        for(Plane p: planes){
            if(p.getPassengerCapacity() >= maxCapacity){
                available.add(p);
            }
        }
        return available;
    }
}
