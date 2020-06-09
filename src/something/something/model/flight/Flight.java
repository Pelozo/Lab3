package something.something.model.flight;
import something.something.model.client.Client;
import something.something.model.plane.Plane;

import java.io.Serializable;
import java.util.Date;
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
    }

    public static class MaxCapacityException extends Exception{
        private int maxCapacity;
        public MaxCapacityException(int maxCapacity){
            this.maxCapacity = maxCapacity;
        }

        public int getMaxCapacity() {
            return maxCapacity;
        }
    }

    private Date date;
    private City origin;
    private City destiny;
    private Plane plane;
    //para no tener que guardar todos los datos de los pasajeros cuando guardemos esto en un archivo, guardamos solamente el nombre de usuario y la cantidad de acompañantes
    private String clientUsername;
    private Integer companions;

    public Flight(Date date, City origin, City destiny, Plane plane){
        //TODO verificar que origin/destiny sean distintos. Lo mismo en los setters
        this.date = date;
        this.origin = origin;
        this.destiny = destiny;
        this.plane = plane;
    }

    public void addPassagers(Client client, int companions) throws MaxCapacityException{
        //Verifica si al avión le da la capacidad
        if(1 + companions > plane.getPassengerCapacity()){
            throw new MaxCapacityException(plane.getPassengerCapacity());
        }
        this.clientUsername = client.getUsername();
        this.companions = companions;
    }

    public void removePassagers() {
        clientUsername = null;
        companions = null;
    }



    @Override
    public String toString() {
        return "Flight{" +
                "date=" + date +
                ", origin=" + origin +
                ", destiny=" + destiny +
                ", plane=" + plane +
                ", clientUsername=" + clientUsername +
                ", companions=" + companions +
                '}';
    }
}
