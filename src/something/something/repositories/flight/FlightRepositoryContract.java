package something.something.repositories.flight;

import something.something.model.flight.Flight;
import something.something.repositories.Repository;

import java.util.Date;
import java.util.List;

public interface FlightRepositoryContract extends Repository<Flight> {

    List<Flight> getAllByUsername(String username);
    List<Flight> getAllByDate(Date date);
}
