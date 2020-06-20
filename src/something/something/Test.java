package something.something;

import something.something.model.client.Client;
import something.something.model.flight.Flight;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.model.plane.SilverPlane;
import something.something.repositories.client.ClientRepository;
import something.something.repositories.flight.FlightRepository;
import something.something.repositories.plane.PlaneRepository;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;


// a falta de Junit...
public class Test {

    public static void run(){
        testClientRepo();
        System.out.println("---");
        testFlightRepo(getPlaneRepo());
        System.out.println("---");
        testFlight(getPlaneRepo());
    }

    public static void testClientRepo(){
        System.out.println("Testeando repo de clientes");

        ClientRepository repository;

        //intenta leer o crear archivo
        try {
            repository = ClientRepository.getInstance();
            System.out.println("Instanciar repo: Ok");
        } catch (IOException e) {
            System.out.println("Instanciar repo: !!!");
            e.printStackTrace();
            return;
        }

        //deberia agregar solamente al primero, porque todos tienen el mismo nombre de usuario
        final String USER = UUID.randomUUID().toString();
        Client c = new Client(USER, "12345","Leo", "Pelozo", "asd", 26);
        repository.add(c);
        repository.add(new Client(USER, "12h345","Lseo", "Pelozvo", "assd", 25));
        repository.add(new Client(USER, "12mmm345","Leo33", "Pelggdsdozo", "as33d", 33));

        int u = 0;
        for(Client cli: repository.getAll()){
            if(cli.getUsername().equals(USER)) u++;
        }
        System.out.println("Solo un usuario: " + (u == 1?"Ok":"!!!"));

        //intenta guardar en archivo
        try {
            repository.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Recuperar cliente: " + ((repository.get(USER).equals(c))?"Ok":"!!!"));

        //reemplazar usuario
        Client nuevo = new Client(USER, "abcdef","unNombre", "unApellido", "999", 99);
        repository.replace(nuevo);
        System.out.println("Reemplazar usuario: " + ((repository.get(USER).getFirstName().equals("unNombre"))?"Ok":"!!!"));

        //cambiar usuario
        repository.get(USER).setPassword("666");
        System.out.println("Cambiar usuario: " + ((repository.get(USER).getPassword().equals("666"))?"Ok":"!!!"));

        try {
            repository.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void testFlightRepo(PlaneRepository planeRepository){
        System.out.println("Testeando repo de clientes");


        //creo un avión con capacidad 4
        Plane plane = new GoldPlane(50,150,4, 600, Plane.Propulsion.PISTON, false);
        planeRepository.add(plane);
        //creo un vuelo
        Flight flight = new Flight(new Date(), Flight.City.BSAS, Flight.City.MONTEVIDEO, plane.getId());

        //creo un cliente
        Client c = new Client("pelozo", "12345","Leo", "Pelozo", "asd", 26);

        flight.addPassagers(c, 2);

        //get repo
        FlightRepository repo;
        try {
            repo = FlightRepository.getInstance();
            System.out.println("Instanciar repo: Ok");
        } catch (IOException e) {
            System.out.println("Instanciar repo: !!!");
            e.printStackTrace();
            return;
        }

        //add flight
        repo.add(flight);

        //check if added
        if(repo.exists(flight)){
            System.out.println("Agregar Flight: Ok");
        }else{
            System.out.println("Agregar Flight: !!!");
        }

        //save changes
        try {
            repo.commit();
            System.out.println("Guardar cambios: Ok");
        } catch (IOException e) {
            System.out.println("Guardar cambios: !!!");
            e.printStackTrace();
        }

        //remove flight
        repo.remove(flight.getID());
        System.out.println("Borrar Flight: " + (!repo.exists(flight)?"Ok":"!!!"));


    }

    public static void testFlight(PlaneRepository planeRepository){
        System.out.println("Testeando vuelos");
        //creo un avión con capacidad 4
        Plane plane = new GoldPlane(50,150,4, 600, Plane.Propulsion.PISTON, false);
        planeRepository.add(plane);
        //creo un vuelo
        Flight flight = new Flight(new Date(), Flight.City.BSAS, Flight.City.MONTEVIDEO, plane.getId());

        //creo un cliente
        Client c = new Client("pelozo", "12345","Leo", "Pelozo", "asd", 26);

        //agrego pasajero con 2 acompañantes
        flight.addPassagers(c, 2);

        //calcular kilometros
        boolean calculateCost =
                flight.calculateKilometres(Flight.City.BSAS, Flight.City.CORDOBA) == 695 &&
                flight.calculateKilometres(Flight.City.CORDOBA, Flight.City.BSAS) == 695 &&
                flight.calculateKilometres(Flight.City.BSAS, Flight.City.SANTIAGO) == 1400 &&
                flight.calculateKilometres(Flight.City.SANTIAGO, Flight.City.BSAS) == 1400 &&
                flight.calculateKilometres(Flight.City.BSAS, Flight.City.MONTEVIDEO) == 950 &&
                flight.calculateKilometres(Flight.City.MONTEVIDEO, Flight.City.BSAS) == 950 &&
                flight.calculateKilometres(Flight.City.CORDOBA, Flight.City.MONTEVIDEO) == 1190 &&
                flight.calculateKilometres(Flight.City.MONTEVIDEO, Flight.City.CORDOBA) == 1190 &&
                flight.calculateKilometres(Flight.City.CORDOBA , Flight.City.SANTIAGO) == 1050 &&
                flight.calculateKilometres(Flight.City.SANTIAGO, Flight.City.CORDOBA) == 1050 &&
                flight.calculateKilometres(Flight.City.MONTEVIDEO , Flight.City.SANTIAGO) == 2100 &&
                flight.calculateKilometres(Flight.City.SANTIAGO, Flight.City.MONTEVIDEO) == 2100;

        System.out.println("Calcular kilometros: " + (calculateCost?"ok":"!!!"));


        //calculate total cost
        //(Cantidad de kms * Costo del km) + (cantidad de pasajeros * 3500) + (Tarifa del tipo de avión)

        boolean test1 = flight.getTotalCost() == (950 * 150) + (3 * 3500) + 6000;

        Plane silverPlane = new SilverPlane(50,152,15, 600, Plane.Propulsion.PISTON);
        planeRepository.add(silverPlane);
        Flight flight2 = new Flight(new Date(), Flight.City.SANTIAGO, Flight.City.MONTEVIDEO, silverPlane.getId());
        flight2.addPassagers(new Client("asd"), 14);

        boolean test2 = flight2.getTotalCost() == (2100 * 152) + (15 * 3500) + 4000;

        System.out.println("Calcular costo total: " + ((test1 && test2)?"ok":"!!!"));

    }

    private static PlaneRepository getPlaneRepo(){
        PlaneRepository planeRepository;
        try {
            planeRepository = PlaneRepository.getInstance();
        } catch (IOException e) {
            System.out.println("No se pudo acceder al archivo Planes ni crear uno nuevo. Saliendo...");
            e.printStackTrace();
            return null;
        }
        return planeRepository;
    }
}
