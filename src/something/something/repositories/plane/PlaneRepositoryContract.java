package something.something.repositories.plane;

import something.something.model.plane.Plane;
import something.something.repositories.Repository;

import java.util.List;

public interface  PlaneRepositoryContract extends Repository<Plane> {

    List<Plane> getAvailablePlanes(int maxCapacity);
}
