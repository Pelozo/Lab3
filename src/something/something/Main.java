package something.something;

import com.google.gson.Gson;
import something.something.model.flight.Flight;
import something.something.model.plane.BronzePlane;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.model.client.Client;
import something.something.repositories.client.ClientRepository;
import something.something.repositories.flight.FlightRepository;
import something.something.model.plane.SilverPlane;
import something.something.repositories.plane.PlaneRepository;
import something.something.ui_questionmark.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Test.run();

        ClientRepository clientRepository;
        try {
            clientRepository = ClientRepository.getInstance();
        } catch (IOException e) {
            System.out.println("No se puedo leear el archivo de clientes ni generar uno nuevo. Saliendo...");
            e.printStackTrace();
            return;
        }

        FlightRepository flightRepository;
        try {
            flightRepository = FlightRepository.getInstance();
        } catch (IOException e) {
            System.out.println("No se puedo leear el archivo de vuelos ni generar uno nuevo. Saliendo...");
            e.printStackTrace();
            return;
        }


        PlaneRepository planeRepository;
        try {
            planeRepository = PlaneRepository.getInstance();
        } catch (IOException e) {
            System.out.println("No se pudo acceder al archivo de aviones ni crear uno nuevo. Saliendo...");
            e.printStackTrace();
            return;
        }

        //si no hay aviones se agregan algunos.
        if(planeRepository.getAll().isEmpty()) {
            planeRepository.add(new BronzePlane(20, 150, 2, 200, Plane.Propulsion.REACTION));
            planeRepository.add(new SilverPlane(30, 200, 3, 450, Plane.Propulsion.PROPELLER));
            planeRepository.add(new GoldPlane(50, 250, 4, 600, Plane.Propulsion.PISTON, true));
            try {
                planeRepository.commit();
            } catch (IOException e) {
                System.out.println("No se pudo guardar aviones nuevos. Saliendo...");
                e.printStackTrace();
                return;
            }
        }

        //printFlightsByDate(flightRepository);
        printAllClients(clientRepository);
        System.out.println("----\n\n");
        printFlight(flightRepository);


//
//
//
//        //creo un avión con capacidad 4
//        Plane plane = new GoldPlane(50,150,4, 600, Plane.Propulsion.PISTON, false);
//
//        //creo un vuelo
//        Flight flight = null;
//        try{
//            flight = new Flight(new Date(), Flight.City.BSAS, Flight.City.MONTEVIDEO, plane);
//        }
//        catch (Flight.OriginDestinyException OriginDestinyException){
//            System.out.println("El origen y el destino seleccionados son iguales.");
//        }
//
//        //creo un cliente
//        Client c = new Client("pelozo", "12345","Leo", "Pelozo", "asd", 26);
//
//        //agrego pasajero con 6 acompañantes (deberia tirar error)
//        try {
//            flight.addPassagers(c, 6);
//        } catch (Flight.MaxCapacityException e) {
//            System.out.println("El vuelo acepta hasta " + e.getMaxCapacity() + " pasajeros");
//        }
//
//        //agrego pasajero con 2 acompañantes
//        try {
//            flight.addPassagers(c, 2);
//        } catch (Flight.MaxCapacityException e) {
//            System.out.println("El vuelo acepta hasta " + e.getMaxCapacity() + " pasajeros");
//            e.printStackTrace();
//        }
//
////        System.out.println(flight.calculateTotalCost());
//
//        Gson gson = new Gson();
//        String json = gson.toJson(flight, Flight.class);
//
//        System.out.println(json);
//        c.addFlightCost(flight.calculateTotalCost());
//        System.out.println("Costo total en vuelos del cliente: "+c.getTotalSpent());
        //creo diferentes tipos de Plane
//        Plane plane1=new BronzePlane(36,150F,3,400,Plane.Propulsion.REACTION);
//        Plane plane2=new SilverPlane(45,150F,5,500,Plane.Propulsion.PROPELLER);
//        Plane plane3=new GoldPlane(41,150F,5,600,Plane.Propulsion.PISTON,true);
//        c.setBestPlaneUsed(plane1);
//        c.setBestPlaneUsed(plane2);
//        c.setBestPlaneUsed(plane3);
//        c.setBestPlaneUsed(plane1);
//        c.setBestPlaneUsed(plane2);
//        System.out.println("El mejor avión usado por el cliente fue: "+c.getBestPlaneUsed());


            Menu.startMenu(clientRepository,flightRepository, planeRepository);


    }
        /*
        El sistema realiza algunas salidas con información sobre vuelos y clientes. Se
        solicita generar un método para listar todos los vuelos programados en una fecha dada y
        generar otro método para listar todos los clientes indicando por cada uno:
        ● Todos los datos personales.
        ● La categoría del mejor avión utilizado ( Gold, Silver o Bronze ).
        ● Total gastado de todos sus vuelos.
         */
       private static void printFlightsByDate(FlightRepository flightRepository){

           System.out.println("Ingrese fecha");
           Date date = Menu.askForDate();

           List<Flight> flights = flightRepository.getAllByDate(date);
           if(flights.isEmpty()){
               System.out.println("No hubo vuelos ese día");
           }else{
               for(Flight flight: flights){
                   System.out.println("Vuelo id: " + flight.getID());
                   System.out.println("Fecha: " + flight.getDate());
                   System.out.println("Recorrido: " + flight.getOrigin() + " - " + flight.getDestiny());
                   System.out.println("Avión id: " + flight.getPlane());
                   System.out.println("Reservado por cliente: " + flight.getClientUsername());
                   System.out.println("Acompañantes: " + flight.getCompanions());
               }
           }
       }
       private static void printAllClients(ClientRepository clientRepository){

           List<Client> clients = clientRepository.getAll();

           if(clients.isEmpty()){
               System.out.println("No hay clientes registrados");
           }else{
               for(Client client: clients){
                   System.out.println("Usuario: " + client.getUsername());
                   System.out.println("Nombre: " + client.getFirstName());
                   System.out.println("Apellido: " + client.getLastName());
                   System.out.println("DNI: " + client.getDni());
                   System.out.println("Edad: " + client.getAge());
                   System.out.println("Total gastado: " + client.getTotalSpent());
                   System.out.println("Mejor vuelo contratado: " + client.getBestPlaneUsed());
               }
           }
       }

    private static void printFlight(FlightRepository flightRepository){


        List<Flight> flights = flightRepository.getAll();
        if(flights.isEmpty()){
            System.out.println("No hubo vuelos ese día");
        }else{
            for(Flight flight: flights){
                System.out.println(flight);
                System.out.println("Vuelo id: " + flight.getID());
                System.out.println("Fecha: " + flight.getDate());
                System.out.println("Recorrido: " + flight.getOrigin() + " - " + flight.getDestiny());
                System.out.println("Avión id: " + flight.getPlane());
                System.out.println("Reservado por cliente: " + flight.getClientUsername());
                System.out.println("Acompañantes: " + flight.getCompanions());
            }
        }
    }

}
