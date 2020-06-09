package something.something.model.plane;

public abstract class Plane{

    public enum Propulsion{ //i'd be great to know how the fuck this is translated but I ain't a plane engineer
        REACTION,
        PROPELLER,
        PISTON
    }

    public static class InvalidCostException extends RuntimeException{

    }


    private int fuelCapacity;
    private float costPerKm;
    private int passengerCapacity;
    private int maxSpeed;
    private Propulsion propulsion;

    public Plane(int fuelCapacity, float costPerKm, int passengerCapacity, int maxSpeed, Propulsion propulsion) throws InvalidCostException {
        this.fuelCapacity = fuelCapacity;
        this.passengerCapacity = passengerCapacity;
        this.maxSpeed = maxSpeed;
        this.propulsion = propulsion;
        setCostPerKm(costPerKm);
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public float getCostPerKm() {
        return costPerKm;
    }

    public void setCostPerKm(float costPerKm) throws InvalidCostException {
        if(costPerKm < 150 || costPerKm > 300){
            throw new InvalidCostException();
        }
        this.costPerKm = costPerKm;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Propulsion getPropulsion() {
        return propulsion;
    }

    public void setPropulsion(Propulsion propulsion) {
        this.propulsion = propulsion;
    }


    @Override
    public String toString() {
        return "Plane{" +
                "fuelCapacity=" + fuelCapacity +
                ", costPerKm=" + costPerKm +
                ", passengerCapacity=" + passengerCapacity +
                ", maxSpeed=" + maxSpeed +
                ", propulsion=" + propulsion +
                '}';
    }
}
