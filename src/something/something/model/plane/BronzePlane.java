package something.something.model.plane;

public class BronzePlane extends Plane {
    public BronzePlane(int fuelCapacity, float costPerKm, int passengerCapacity, int maxSpeed, Propulsion propulsion) throws InvalidCostException {
        super(fuelCapacity, costPerKm, passengerCapacity, maxSpeed, propulsion);
    }

    @Override
    public String toString(){
        return "BronzePlane{"+
                super.toString() +
                "}";
    }
}
