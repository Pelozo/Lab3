package something.something.repositories;

import something.something.model.client.Client;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface Repository<T extends Serializable> {

    Boolean add(T value);
    Boolean remove(String id);
    List<Client> getAll();
    T get(String id);
    Boolean exists(T value);
    void commit() throws IOException;

}
