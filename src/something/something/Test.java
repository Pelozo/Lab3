package something.something;

import something.something.model.client.Client;
import something.something.model.flight.Flight;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.repositories.client.ClientRepository;
import something.something.repositories.flight.FlightRepository;

import java.io.IOException;
import java.util.Date;


// a falta de Junit...
public class Test {

    public static void testClientRepo(){
        System.out.println("Testeando repo de clientes");

        ClientRepository repository;

        //intenta leer o crear archivo
        try {
            repository = ClientRepository.getInstance();
            System.out.println("Instanciar repo: Ok");
        } catch (IOException e) {
            System.out.println("No se pudo recuperar el archivo de clientes ni escribir uno nuevo");
            e.printStackTrace();
            return;
        }

        //deberia agregar solamente al primero, porque todos tienen el mismo nombre de usuario
        Client c = new Client("pelozo", "12345","Leo", "Pelozo", "asd", 26);
        repository.add(c);
        repository.add(new Client("pelozo", "12h345","Lseo", "Pelozvo", "assd", 25));
        repository.add(new Client("pelozo", "12mmm345","Leo", "Pelozo", "asd", 33));
        repository.add(new Client("pelozo", "123ffert45","Leo", "Pelozo", "asd", 55));

        System.out.println("Solo un usuario: " + (repository.getAll().size() == 1));
        //intenta guardar en archivo
        try {
            repository.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Recuperar cliente: " + (repository.get("pelozo").equals(c)));

        //reemplazar usuario
        Client nuevo = new Client("pelozo", "abcdef","unNombre", "unApellido", "999", 99);
        repository.replace(nuevo);
        System.out.println("Reemplazar usuario: " + (repository.get("pelozo").getFirstName().equals("unNombre")));

        //cambiar usuario
        repository.get("pelozo").setPassword("666");
        System.out.println("Cambiar usuario: " + (repository.get("pelozo").getPassword().equals("666")));

        try {
            repository.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public static void testFlightRepo(){
        System.out.println("Testeando repo de clientes");


        //creo un avión con capacidad 4
        Plane plane = new GoldPlane(50,150,4, 600, Plane.Propulsion.PISTON, false);

        //creo un vuelo
        Flight flight = null;
        try {
            flight = new Flight(new Date(), Flight.City.BSAS, Flight.City.MONTEVIDEO, plane);
        } catch (Flight.OriginDestinyException e) {
            e.printStackTrace();
        }

        //creo un cliente
        Client c = new Client("pelozo", "12345","Leo", "Pelozo", "asd", 26);

        //agrego pasajero con 2 acompañantes
        try {
            flight.addPassagers(c, 2);
        } catch (Flight.MaxCapacityException e) {
            System.out.println("El vuelo acepta hasta " + e.getMaxCapacity() + " pasajeros");
            e.printStackTrace();
        }

        FlightRepository repo;
        try {
            repo = FlightRepository.getInstance();
            System.out.println("Instanciar repo: Ok");
        } catch (IOException e) {
            System.out.println("No se pudo recuperar el archivo de clientes ni escribir uno nuevo");
            e.printStackTrace();
            return;
        }

        repo.add(flight);
        System.out.println(repo.getAll());

        try {
            repo.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void testFlight(){
        //creo un avión con capacidad 4
        Plane plane = new GoldPlane(50,150,4, 600, Plane.Propulsion.PISTON, false);

        //creo un vuelo
        Flight flight = null;
        try {
            flight = new Flight(new Date(), Flight.City.BSAS, Flight.City.MONTEVIDEO, plane);
        } catch (Flight.OriginDestinyException e) {
            e.printStackTrace();
        }

        //creo un cliente
        Client c = new Client("pelozo", "12345","Leo", "Pelozo", "asd", 26);

        //agrego pasajero con 6 acompañantes (deberia tirar error)
        try {
            flight.addPassagers(c, 6);
        } catch (Flight.MaxCapacityException e) {
            System.out.println("Maximno de pasajeros: Ok");
        }

        //agrego pasajero con 2 acompañantes
        try {
            flight.addPassagers(c, 2);
            System.out.println("Agregar pasajeros: Ok");
        } catch (Flight.MaxCapacityException e) {
            e.printStackTrace();
        }

        //TODO calcular costo

    }
}
