package something.something.model.flight;
import something.something.model.client.Client;
import something.something.model.plane.BronzePlane;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.model.plane.SilverPlane;
import something.something.repositories.plane.PlaneRepository;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Flight implements Serializable {

    public enum City{
        BSAS("Buenos Aires"),
        CORDOBA("Córdoba"),
        SANTIAGO("Santiago"),
        MONTEVIDEO("Montevideo");
        private final String name;
        City(String name) {
            this.name = name;
        }

        @Override
        public String toString(){
            return this.name;
        }
    }

    private final Map<String, Map<String, Integer>> distances2 = Collections.unmodifiableMap(new HashMap<String, Map<String, Integer>>() {{
        put("Buenos Aires",
                Collections.unmodifiableMap(new HashMap<String, Integer>() {{
                    put("Córdoba", 695);
                    put("Santiago", 1400);
                    put("Montevideo", 950);
                }}));
        put("Córdoba",
                Collections.unmodifiableMap(new HashMap<String, Integer>() {{
                    put("Montevideo", 1190);
                    put("Santiago", 1050);
                }}));
        put("Santiago",
                Collections.unmodifiableMap(new HashMap<String, Integer>() {{
                    put("Montevideo", 2100);
                }}));
    }});


    private String ID = UUID.randomUUID().toString();
    private Date date;
    private City origin;
    private City destiny;
    private String plane;
    //para no tener que guardar todos los datos de los pasajeros cuando guardemos esto en un archivo, guardamos solamente el nombre de usuario y la cantidad de acompañantes
    private String clientUsername;
    private Integer companions;
    private float planeTariff;
    private float planeCostPerKm;

    public Flight(Date date, City origin, City destiny, String plane){
        setOrigin(origin);
        setDestiny(destiny);
        this.date = date;
        setPlane(plane);
    }

    public void addPassagers(Client client, int companions){
        this.clientUsername = client.getUsername();
        this.companions = companions;
    }

    public void removePassagers() {
        clientUsername = null;
        companions = null;
    }


    public int calculateKilometres(City origin, City destiny){
        try {
            return  distances2.get(origin.name).get(destiny.name);
        }catch (NullPointerException e){
            return distances2.get(destiny.name).get(origin.name);
        }
    }

    public float getPlaneTariff(){
        return planeTariff;
    }

    public double getTotalCost(){
        return (calculateKilometres(origin, destiny) * planeCostPerKm) +
                ((1 + companions) * 3500) +
                getPlaneTariff();
    }

    public String getID() {
        return ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public City getOrigin() {
        return origin;
    }

    public void setOrigin(City origin){
            this.origin = origin;
    }

    public City getDestiny() {
        return destiny;
    }

    public void setDestiny(City destiny) {
        this.destiny = destiny;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String planeId){
        PlaneRepository planeRepository;
        try {
            planeRepository = PlaneRepository.getInstance();
        } catch (IOException e) {
            System.out.println("No se pudo acceder al archivo Planes ni crear uno nuevo. Saliendo...");
            e.printStackTrace();
            return;
        }
        Plane p = planeRepository.get(planeId);
        plane = planeId;
        planeTariff = p.getType().getCost();
        planeCostPerKm = p.getCostPerKm();
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public Integer getCompanions() {
        return companions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return ID.equals(flight.ID) &&
                Objects.equals(date, flight.date) &&
                origin == flight.origin &&
                destiny == flight.destiny &&
                Objects.equals(plane, flight.plane) &&
                Objects.equals(clientUsername, flight.clientUsername) &&
                Objects.equals(companions, flight.companions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "ID='" + ID + '\'' +
                ", date=" + date +
                ", origin=" + origin +
                ", destiny=" + destiny +
                ", plane='" + plane + '\'' +
                ", clientUsername='" + clientUsername + '\'' +
                ", companions=" + companions +
                ", planeTariff=" + planeTariff +
                ", planeCostPerKm=" + planeCostPerKm +
                '}';
    }
}
