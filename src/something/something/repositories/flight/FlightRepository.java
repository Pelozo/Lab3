package something.something.repositories.flight;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import something.something.model.client.Client;
import something.something.model.flight.Flight;
import something.something.model.plane.Plane;
import something.something.repositories.client.ClientRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO
public class FlightRepository implements FlightRepositoryContract {

    //nombre del archivo para guardar
    private static final String FILENAME = "flights.json";
    //lista de vuelos
    private List<Flight> flights;
    //guardar instancia del singleton
    private static FlightRepository instance = null;

    //en el constructor de cargan los datos del archivo
    //TODO sacar el load y llamarlo aparte?
    private FlightRepository() throws IOException {
        flights = new ArrayList<>();
        load();
    }

    public static FlightRepository getInstance() throws IOException {
        if(instance == null){
            instance = new FlightRepository();
        }
        return instance;
    }

    private void load() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Plane.class, new PlaneAdapter());
        Gson gson = builder.create();
        File flightsFile = new File(FILENAME);
        //si el archivo existe se leen los registros
        if(flightsFile.exists()){
            JsonReader reader = new JsonReader(new FileReader(FILENAME));
            Type type = new TypeToken<List<Flight>>(){}.getType();
            flights = gson.fromJson(reader, type);
            reader.close();
        }else{//sino se crea un archivo vacio
            FileWriter fw = new FileWriter(FILENAME);
            gson.toJson(flights, fw);
            fw.close();
        }

    }

    @Override
    public Boolean add(Flight flight) {
        if(!flights.contains(flight)){
            flights.add(flight);
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(String id) {
        for(Flight f: flights){
            if(f.getID().equals(id)){
                flights.remove(f);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Flight> getAll() {
        return flights;
    }

    @Override
    public Flight get(String id) {
        for(Flight f: flights){
            if(f.getID().equals(id)){
                return f;
            }
        }
        return null;
    }

    @Override
    public Boolean exists(Flight flight) {
        return flights.contains(flight);
    }

    @Override
    public void commit() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Plane.class, new PlaneAdapter<Plane>());
        Gson gson = builder.create();
        FileWriter fw = new FileWriter(FILENAME);
        gson.toJson(flights, fw);
        fw.close();
    }
}
