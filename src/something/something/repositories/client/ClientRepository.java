package something.something.repositories.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import something.something.model.client.Client;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepository implements ClientRepositoryContract {

    //nombre del archivo para guardar
    private static final String FILENAME = "clients.json";
    //lista de clientes
    Map<String, Client> clients;
    //guardar instancia del singleton
    private static ClientRepository instance = null;

    //excepción a tirar si el cliente ya existe
    public static class ClientAlreadyRegistered extends Exception{}

    //en el constructor de cargan los datos del archivo
    //TODO sacar el load y llamarlo aparte?
    private ClientRepository() throws IOException {
        clients = new HashMap<>();
        load();
    }

    public static ClientRepository getInstance() throws IOException {
        if(instance == null){
            instance = new ClientRepository();
        }
        return instance;
    }

    private void load() throws IOException {
        Gson gson = new Gson();
        File clientFile = new File(FILENAME);
        //si el archivo existe se leen los registros
        if(clientFile.exists()){
            JsonReader reader = new JsonReader(new FileReader(FILENAME));
            Type type = new TypeToken<Map<String, Client>>(){}.getType();
            clients = gson.fromJson(reader, type);
            reader.close();
        }else{//sino se crea un archivo vacio
            FileWriter fw = new FileWriter(FILENAME);
            gson.toJson(clients, fw);
            fw.close();
        }
    }


    @Override
    public Boolean add(Client client) {
        //si el cliente no existe ya
        if(!clients.containsKey(client.getUsername())){
            //lo agrega y devueelve true
            clients.put(client.getUsername(), client);
            return true;
        }
        //sino devuelve false
        return false;
    }


    @Override
    public Boolean remove(String username) {
        return clients.remove(username) != null;
    }

    @Override
    public void replace(Client client){
        clients.replace(client.getUsername(), client);
    }

    @Override
    public List<Client> getAll() {
        return new ArrayList<Client>(clients.values());
    }


    @Override
    public Client get(String username) {
        return clients.get(username);
    }

    @Override
    public Boolean exists(Client client) {
        return clients.containsKey(client.getUsername());
    }

    public Boolean exists(String username){
        return exists(new Client(username));
    }

    @Override
    public void commit() throws IOException {
        FileWriter fw = new FileWriter(FILENAME);
        new Gson().toJson(clients, fw);
        fw.close();
    }


}
