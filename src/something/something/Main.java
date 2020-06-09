package something.something;

import com.google.gson.Gson;
import something.something.model.flight.Flight;
import something.something.model.plane.BronzePlane;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.model.client.Client;
import something.something.model.plane.SilverPlane;

import java.util.Date;

public class Main {

    public static void main(String[] args){

        //Test.testClientRepo();



        //creo un avión con capacidad 4
        Plane plane = new GoldPlane(50,150,4, 600, Plane.Propulsion.PISTON, false);

        //creo un vuelo
        Flight flight = new Flight(new Date(), Flight.City.BSAS, Flight.City.MONTEVIDEO, plane);

        //creo un cliente
        Client c = new Client("pelozo", "12345","Leo", "Pelozo", "asd", 26);

        //agrego pasajero con 6 acompañantes (deberia tirar error)
        try {
            flight.addPassagers(c, 6);
        } catch (Flight.MaxCapacityException e) {
            System.out.println("El vuelo acepta hasta " + e.getMaxCapacity() + " pasajeros");
        }

        //agrego pasajero con 2 acompañantes
        try {
            flight.addPassagers(c, 2);
        } catch (Flight.MaxCapacityException e) {
            System.out.println("El vuelo acepta hasta " + e.getMaxCapacity() + " pasajeros");
            e.printStackTrace();
        }




        //System.out.println(flight);

        Gson gson = new Gson();
        String json = gson.toJson(flight, Flight.class);

        System.out.println(json);
        c.addFlightCost();
        System.out.println("Costo total en vuelos del cliente: "+c.getTotalSpent());
        //creo diferentes tipos de Plane
        Plane plane1=new BronzePlane(36,150F,3,400,Plane.Propulsion.REACTION);
        Plane plane2=new SilverPlane(45,150F,5,500,Plane.Propulsion.PROPELLER);
        Plane plane3=new GoldPlane(41,150F,5,600,Plane.Propulsion.PISTON,true);
        c.setBestPlaneUsed(plane1);
        c.setBestPlaneUsed(plane2);
        c.setBestPlaneUsed(plane3);
        c.setBestPlaneUsed(plane1);
        c.setBestPlaneUsed(plane2);
        System.out.println("El mejor avión usado por el cliente fue: "+c.getBestPlaneUsed());


        /*
        switch (new Menu(Arrays.asList(
                "Registrarse",
                "Iniciar sesión")).printAndWaitAnswer()){
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
        */


        //Date date = Menu.askForDate("Ingrese fecha de vuelo");
        //System.out.println(date);


    }


}
