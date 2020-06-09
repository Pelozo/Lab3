package something.something.repositories.client;

import something.something.model.client.Client;
import something.something.repositories.Repository;

public interface ClientRepositoryContract extends Repository<Client> {
    void replace(Client client);
}
