package something.something.model.client;

import something.something.model.flight.Flight;
import something.something.model.plane.BronzePlane;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.model.plane.SilverPlane;

import java.io.Serializable;
import java.util.Objects;


public class Client implements Serializable {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String dni;
    private int age;
    private Plane bestPlaneUsed; //TODO
    private float totalSpent; //TODO


    public Client(String username) {
        this.username = username;
    }

    public Client(String username, String password, String firstName, String lastName, String dni, int age) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return username.equals(client.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dni='" + dni + '\'' +
                ", age=" + age +
                '}';
    }

    public void setTotalSpent(float totalSpent){
        this.totalSpent=totalSpent;
    }

    public float getTotalSpent (){
        return totalSpent;
    }

    public float flightCost (){
        return 5300;
    }

    public void addFlightCost(){ //añade el costo de un vuelo al gasto total (pasar por parámetro el método de Flight)
        totalSpent+=flightCost();
    }

    public void setBestPlaneUsed (Plane aux){ //Le paso por parámetro Flight.getPlane()
        if (bestPlaneUsed == null) {
            bestPlaneUsed=aux;
        } else {
            if (!(bestPlaneUsed instanceof GoldPlane)){
                if (bestPlaneUsed instanceof BronzePlane && aux instanceof SilverPlane)
                    bestPlaneUsed=aux;
                else
                    if (bestPlaneUsed instanceof SilverPlane && aux instanceof GoldPlane)
                        bestPlaneUsed=aux;
            }
        }
    }

    public Plane getBestPlaneUsed(){
        return bestPlaneUsed;
    }

}
