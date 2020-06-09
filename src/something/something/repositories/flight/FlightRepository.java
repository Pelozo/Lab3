package something.something.repositories.flight;

import something.something.model.client.Client;
import something.something.model.flight.Flight;
import something.something.repositories.client.ClientRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;


//TODO
public class FlightRepository implements FlightRepositoryContract {

    //nombre del archivo para guardar
    private static final String FILENAME = "flights.json";

    //guardar instancia del singleton
    private static FlightRepository instance = null;

    @Override
    public Boolean add(Flight value) {
        return null;
    }

    @Override
    public Boolean remove(String id) {
        return null;
    }

    @Override
    public List<Flight> getAll() {
        return null;
    }

    @Override
    public Flight get(String id) {
        return null;
    }

    @Override
    public Boolean exists(Flight value) {
        return null;
    }

    @Override
    public void commit() throws IOException {

    }
}
