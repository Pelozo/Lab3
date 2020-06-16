package something.something.model.plane;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Plane implements Serializable {

    public enum Propulsion{
        REACTION,
        PROPELLER,
        PISTON
    }


    public static class InvalidCostException extends RuntimeException{}
    private String id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return id.equals(plane.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", fuelCapacity=" + fuelCapacity +
                ", costPerKm=" + costPerKm +
                ", passengerCapacity=" + passengerCapacity +
                ", maxSpeed=" + maxSpeed +
                ", propulsion=" + propulsion +
                '}';
    }

    public String getType(){
        if(this instanceof GoldPlane){
            return "Gold";
        }else if(this instanceof SilverPlane){
            return "Silver";
        }else if(this instanceof BronzePlane){
            return "Bronze";
        }
        return "asd";
    }
}
