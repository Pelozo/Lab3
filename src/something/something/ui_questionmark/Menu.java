package something.something.ui_questionmark;

import something.something.model.client.Client;
import something.something.model.flight.Flight;
import something.something.model.plane.BronzePlane;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.model.plane.SilverPlane;
import something.something.repositories.client.ClientRepository;
import something.something.repositories.flight.FlightRepository;
import something.something.repositories.plane.PlaneRepository;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Menu {


    public static void systemPause() {
        System.out.println("Presione una tecla para continuar...");
        new java.util.Scanner(System.in).nextLine();
    }

    public Menu() {
    }

    public static void print(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + 1 + ". " + options.get(i));
        }
        System.out.println("0. Volver");
    }

    public static int printAndWaitAnswer(List<String> options) {

        final String ERROR = "La opcion ingresada no es válida. Debe ingresar un número entre 0 y " + options.size();

        int menuOption = -1;
        Scanner sc = new Scanner(System.in);

        do {
            print(options);

            if (sc.hasNextInt()) {
                menuOption = sc.nextInt();
                if (menuOption < 0 || menuOption > options.size()) {
                    System.out.println(ERROR);
                } else {
                    return menuOption;
                }
            } else {
                System.out.println(ERROR);
                sc.next();
            }
        } while (true);
    }

    //menu inicial
    public static void startMenu() {

        ClientRepository clients = null;
        try {
            clients = ClientRepository.getInstance();
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de clientes y no se pudo crear uno nuevo.\n Saliendo... ");
            e.printStackTrace();
            return;
        }

        System.out.print("- AeroTaxi -\n¡BIENVENIDO! Vola con AeroTaxi...\n");


        switch (printAndWaitAnswer(Arrays.asList(
                "Registrarse",
                "Iniciar sesión"))) {
            case 1:
                //registro un cliente nuevo y muestro nuevo menu de opciones para contratar un vuelo
                System.out.println("Fase de Registro:");
                String firstName;
                String lastName;
                String DNI;
                int age;
                String username;
                String password;

                Scanner read = new Scanner(System.in);
                System.out.print("Nombre: ");
                firstName = read.next();
                System.out.print("Apellido: ");
                lastName = read.next();
                System.out.print("DNI: ");
                DNI = read.next();
                System.out.print("Edad: ");
                age = read.nextInt();
                do {
                    System.out.print("Nombre de Usuario: ");
                    username = read.next();
                    if (clients.exists(username)) {
                        System.out.println("El nombre de usuario ya existe, por favor elija otro.");
                    }
                } while (clients.exists(username));

                System.out.print("Contraseña: ");
                password = read.next();
                Client client = new Client(username, password, firstName, lastName, DNI, age);
                //guardar el cliente nuevo en un archivo or something
                clients.add(client);

                try {
                    clients.commit();
                    System.out.println("Cliente registrado con exito!\n" + client);
                } catch (IOException e) {
                    System.out.println("No se pudo escribir el archivo de clientes...");
                    e.printStackTrace();
                    return;
                }

                systemPause();
                //TODO no hacer recursivo, acomodar
                startMenu();
                break;
            case 2:
                //corroborar si ya existe el cliente y mostrar nuevo menu de opciones para contratar un vuelo
                System.out.println("Iniciar sesión:");
                Scanner read1 = new Scanner(System.in);
                System.out.print("Nombre de Usuario: ");
                username = read1.next();
                System.out.print("Contraseña: ");
                password = read1.next();
                //recupera usuario del repositorio
                Client loggedClient = clients.get(username);
                if (loggedClient == null) {
                    //TODO el usuario no existe
                    System.out.println("El usuario ingresado no existe.");
                } else if (!loggedClient.getPassword().equals(password)) {
                    //TODO el usuario existe pero la contraseña es incorrecta
                    //TODO volver a pedir datos incorrectos?
                    System.out.println("La contraseña ingresada es incorrecta");
                } else {
                    //login correcto
                    hireCancelFlightMenu(loggedClient);
                }
                break;
            default:
                break;
        }
    }

    //menu para seleccionar origen
    public static Flight.City selectOrigin() {
        Flight.City origin = null;
        switch (printAndWaitAnswer(Arrays.asList(
                "Buenos Aires",
                "Córdoba",
                "Montevideo"))) {
            case 1:
                origin = Flight.City.BSAS;
                break;
            case 2:
                origin = Flight.City.CORDOBA;
                break;
            case 3:
                origin = Flight.City.MONTEVIDEO;
                break;
            default:
                break;
        }
        return origin;
    }

    //menu para seleccionar destino
    public static Flight.City selectDestiny(Flight.City origin) {
        Flight.City destiny = null;
        if (origin == Flight.City.BSAS) {
            switch (printAndWaitAnswer(Arrays.asList(
                    "Córdoba",
                    "Santiago",
                    "Montevideo"))) {
                case 1:
                    destiny = Flight.City.CORDOBA;
                    break;
                case 2:
                    destiny = Flight.City.SANTIAGO;
                    break;
                case 3:
                    destiny = Flight.City.MONTEVIDEO;
                    break;
                default:
                    break;
            }
        } else {
            if (origin == Flight.City.CORDOBA) {
                switch (printAndWaitAnswer(Arrays.asList(
                        "Montevideo",
                        "Santiago"))) {
                    case 1:
                        destiny = Flight.City.MONTEVIDEO;
                        break;
                    case 2:
                        destiny = Flight.City.SANTIAGO;
                        break;
                }
            } else {
                if (origin == Flight.City.MONTEVIDEO) {
                    switch (printAndWaitAnswer(Arrays.asList(
                            "Santiago"))) {
                        case 1:
                            destiny = Flight.City.SANTIAGO;
                            break;
                    }
                }
            }
        }

        return destiny;
    }


    public static boolean confirm (){
        boolean confirm= false;
        switch (printAndWaitAnswer(Arrays.asList(
                "Sí",
                "No")))
        {
            case 1:
                confirm=true;
                break;

            case 2:
                confirm=false;
                break;
        }
        return confirm;
    }

    //menu para contratar o cancelar un vuelo
    public static void hireCancelFlightMenu(Client client) {


        //aviones disponibles
        PlaneRepository planeRepository;
        try {
            planeRepository = PlaneRepository.getInstance();
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de vuelos y no se pudo crear uno nuevo.\n Saliendo... ");
            e.printStackTrace();
            return;
        }


        //Vuelos
        FlightRepository flightRepository;
        try {
            flightRepository = FlightRepository.getInstance();
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de vuelos y no se pudo crear uno nuevo.\n Saliendo... ");
            e.printStackTrace();
            return;
        }

        switch (printAndWaitAnswer(Arrays.asList(
                "Contratar vuelo",
                "Cancelar vuelo"))) {
            case 1:
                //pide la fecha del vuelo
                Date date = askForDate("- Ingrese la fecha en la que desea viajar, ");
                //selecciona origin y destiny
                System.out.println("- Seleccione la ciudad de Origen del vuelo: ");
                Flight.City origin = selectOrigin();
                System.out.println("Origen seleccionado: " + origin);
                System.out.println("- Seleccione la ciudad de Destino del vuelo: ");
                Flight.City destiny = selectDestiny(origin);
                System.out.println("- Ingrese la cantidad de acompañantes: ");
                Scanner read = new Scanner(System.in);
                int companions;
                companions = read.nextInt();
                System.out.println("- Seleccione el avión: ");

                //lista de aviones que pueden llevar esos acompañantes
                List<Plane> availablePlanes = planeRepository.getAvailablePlanes(companions + 1);
                //lista de vuelos en esa fecha
                List<Flight> unavailableFlights = flightRepository.getAllByDate(date);
                //Le restamos a la lista de aviones que pueden llevar acompañantes todos los aviones que ya están resevados en esa fecha
                for(Flight f: unavailableFlights){
                    availablePlanes.remove(planeRepository.get(f.getPlane()));
                }
                //mostramos la lista
                List<String> options = new ArrayList<>();
                for(Plane p: availablePlanes){
                    options.add("Tipo: " + p.getType() + ", Capacidad: " + p.getPassengerCapacity() + ", Velocidad: " + p.getMaxSpeed());
                }
            
                //seleccionamos el avion
                Plane plane = availablePlanes.get(printAndWaitAnswer(options) - 1);
                //Creamos el vuelo
                Flight flight = new Flight(date, origin, destiny, plane.getId());
                //Agregamos a los pasajeros
                flight.addPassagers(client, companions);
                //Mostramos el costo total
                System.out.println("Costo total de vuelo: " + flight.calculateTotalCost());
                //Pedimos confirmación
                System.out.println("¿Desea confirmar el vuelo?");
                if (confirm()){
                   flightRepository.add(flight);
                    try {
                        flightRepository.commit();
                    } catch (IOException e) {
                        System.out.println("No se pudo reservar el vuelvo");
                        e.printStackTrace();
                    }
                } else
                    startMenu(); ///TODO si no confirma el vuelo, vuelve al menú principal
                break;
            case 2://cancelar vuelo
                //mostramos todos los vuelos del usuario
                List<Flight> flightForClient = flightRepository.getAllByUsername(client.getUsername());
                if(flightForClient.isEmpty()){
                    System.out.println("No tiene vuelos para cancelar");
                }else{
                    List<String> cancelOptions = new ArrayList<>();
                    for(Flight f : flightForClient){
                        cancelOptions.add(f.getDate().toString() + " | " + f.getOrigin() + " - " + f.getDestiny());
                    }
                    int selectedFlight = printAndWaitAnswer(cancelOptions);
                    //borramos el vuelo seleccionado
                    flightRepository.remove(flightForClient.get(selectedFlight - 1).getID());
                    try {
                        flightRepository.commit();
                        System.out.println("Vuelo cancelado");
                    } catch (IOException e) {
                        System.out.println("No se pudo cancelar el vuelo");
                        e.printStackTrace();
                        return;
                    }
                }
                break;
            default:
                break;
        }
    }

    //this method smh
    public static Date askForDate(String header) {
        System.out.print(header + "la fecha tiene que estar en formato dd/mm/yyyy: ");
        Scanner sc = new Scanner(System.in);

        String ingresado = sc.next();
        SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = parser.parse(ingresado);
            String formattedDate = parser.format(date);
            System.out.println("Fecha seleccionada: " + formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
