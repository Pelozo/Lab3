package something.something.model.plane;

public class GoldPlane extends Plane implements Catering{

    private Boolean hasWifi;

    public GoldPlane(int fuelCapacity, float costPerKm, int passengerCapacity, int maxSpeed, Propulsion propulsion, Boolean hasWifi) throws InvalidCostException {
        super(fuelCapacity, costPerKm, passengerCapacity, maxSpeed, propulsion);
        this.hasWifi = hasWifi;
    }

    public Boolean getHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(Boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    @Override
    public String toString() {
        return "GoldPlane{" +
                super.toString() +
                ", hasWifi=" + hasWifi +
                '}';
    }
}
