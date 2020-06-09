package something.something.model.plane;

public class SilverPlane extends Plane implements Catering {
    public SilverPlane(int fuelCapacity, float costPerKm, int passengerCapacity, int maxSpeed, Propulsion propulsion) throws InvalidCostException {
        super(fuelCapacity, costPerKm, passengerCapacity, maxSpeed, propulsion);
    }
}
