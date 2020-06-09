package something.something.model.flight;

//import com.sun.istack.internal.NotNull;
import something.something.model.client.Client;
import something.something.model.plane.BronzePlane;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.model.plane.SilverPlane;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public static class OriginDestinyException extends Exception{
        private City origin;
        public OriginDestinyException(City origin){
            this.origin = origin;
        }

        public City getOrigin(){
            return origin;
        }
    }

    private UUID ID = UUID.randomUUID();
    private Date date;
    private City origin;
    private City destiny;
    private Plane plane;
    //para no tener que guardar todos los datos de los pasajeros cuando guardemos esto en un archivo, guardamos solamente el nombre de usuario y la cantidad de acompañantes
    private String clientUsername;
    private Integer companions;

    public Flight(Date date, City origin, City destiny, Plane plane) throws OriginDestinyException{
        //TODO verificar que origin/destiny sean distintos. Lo mismo en los setters
        this.date = date;
        if(origin.equals(destiny))
            throw new OriginDestinyException(origin);
        else{
            this.origin = origin;
            this.destiny = destiny;
        }
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

    public int calculateKilometres(City origin,City destiny){
        int kilometres = 0;
        if(origin == City.BSAS){
            if(destiny == City.CORDOBA)
                kilometres = 695;
            else{
                if(destiny == City.SANTIAGO)
                    kilometres = 1400;
                else{
                    if(destiny == City.MONTEVIDEO)
                        kilometres = 950;
                }
            }
        }else{
            if(origin == City.CORDOBA){
                if(destiny == City.MONTEVIDEO)
                    kilometres = 1190;
                else{
                    if(destiny == City.SANTIAGO)
                        kilometres = 1050;
                }
            }
            else{
                if(origin == City.MONTEVIDEO)
                    kilometres = 2100;
            }
        }
        return kilometres;
    }

    public int planeTariff(){
        int tariff = 0;
        if(plane instanceof BronzePlane)
            tariff = 3000;
        else{
            if(plane instanceof SilverPlane)
                tariff = 4000;
            else
                tariff = 6000;
        }
        return tariff;
    }

    public double calculateTotalCost(){
        double total = (calculateKilometres(origin,destiny)*plane.getCostPerKm())+(companions*3500)+planeTariff();
        return total;
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
