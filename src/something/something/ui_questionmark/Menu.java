package something.something.ui_questionmark;

import something.something.model.client.Client;
import something.something.model.flight.Flight;
import something.something.model.plane.*;
import something.something.repositories.client.ClientRepository;
import something.something.repositories.flight.FlightRepository;
import something.something.repositories.plane.PlaneRepository;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class Menu {


    private static void systemPause() {
        System.out.println("Presione una tecla para continuar...");
        new java.util.Scanner(System.in).nextLine();
    }

    private Menu() {
    }

    //imprime en pantalla la lista de opciones
    private static void print(List<?> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + 1 + ". " + options.get(i).toString());
        }
        System.out.println("0. Volver");
    }

    //imprime en pantalla la lista de opciones y espera por una opcion válida
    private static int printAndWaitAnswer(List<?> options) {

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
    public static void startMenu(ClientRepository clientRepository, FlightRepository flightRepository, PlaneRepository planeRepository) {


        System.out.print("- AeroTaxi -\n¡BIENVENIDO! Vola con AeroTaxi...\n");

        int selectedOption;

        do {
            selectedOption = printAndWaitAnswer(Arrays.asList(
                    "Registrarse",
                    "Iniciar sesión",
                    "Modo Admin"));
            switch (selectedOption) {
                case 1:
                    registerClient(clientRepository);
                    break;
                case 2:
                    Client loggedClient = loginClient(clientRepository);
                    if (loggedClient != null) {
                        hireCancelFlightMenu(loggedClient, flightRepository, clientRepository, planeRepository);
                    } else {
                        systemPause();
                    }
                    break;
                case 3:
                    menuAdmin(flightRepository,clientRepository);
                    break;
                default:
                    break;
            }
        } while (selectedOption != 0);
    }

    //Menu Admin
    public static void menuAdmin(FlightRepository flightRepository,ClientRepository clientRepository){
        int selectedOption;

        do {
            selectedOption = printAndWaitAnswer(Arrays.asList(
                    "Mostrar vuelos por fecha",
                    "Mostrar todos los clientes"));
            switch (selectedOption) {
                case 1:
                    printFlightsByDate(flightRepository);
                    break;
                case 2:
                    printAllClients(clientRepository);
                    break;
                default:
                    break;
            }
        } while (selectedOption != 0);
    }

     /*
    El sistema realiza algunas salidas con información sobre vuelos y clientes. Se
    solicita generar un método para listar todos los vuelos programados en una fecha dada y
    generar otro método para listar todos los clientes indicando por cada uno:
    ● Todos los datos personales.
    ● La categoría del mejor avión utilizado ( Gold, Silver o Bronze ).
    ● Total gastado de todos sus vuelos.
    */

    private static void printFlightsByDate(FlightRepository flightRepository) {

        System.out.println("Ingrese fecha");
        Date date = Menu.askForDate();

        List<Flight> flights = flightRepository.getAllByDate(date);
        if (flights.isEmpty()) {
            System.out.println("No hubo vuelos ese día");
        } else {
            for (Flight flight : flights) {
                System.out.println("Vuelo id: " + flight.getID());
                System.out.println("Fecha: " + flight.getDate());
                System.out.println("Recorrido: " + flight.getOrigin() + " - " + flight.getDestiny());
                System.out.println("Avión id: " + flight.getPlane());
                System.out.println("Reservado por cliente: " + flight.getClientUsername());
                System.out.println("Acompañantes: " + flight.getCompanions());
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    private static void printAllClients(ClientRepository clientRepository) {

        List<Client> clients = clientRepository.getAll();

        if (clients.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            for (Client client : clients) {
                System.out.println("Usuario: " + client.getUsername());
                System.out.println("Nombre: " + client.getFirstName());
                System.out.println("Apellido: " + client.getLastName());
                System.out.println("DNI: " + client.getDni());
                System.out.println("Edad: " + client.getAge());
                System.out.println("Total gastado: " + client.getTotalSpent());
                System.out.println("Mejor vuelo contratado: " + client.getBestPlaneUsed());
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    //Menu de inicio de sesión
    private static Client loginClient(ClientRepository clientRepository) {
        String username;
        String password;
        //corroborar si ya existe el cliente y mostrar nuevo menu de opciones para contratar un vuelo
        System.out.println("Iniciar sesión:");
        Scanner read1 = new Scanner(System.in);
        System.out.print("Nombre de Usuario: ");
        username = read1.next();
        System.out.print("Contraseña: ");
        password = read1.next();
        //recupera usuario del repositorio
        Client loggedClient = clientRepository.get(username);
        if (loggedClient == null) {
            //el usuario no existe
            System.out.println("El usuario ingresado no existe.");
        } else if (!loggedClient.getPassword().equals(password)) {
            //el usuario existe pero la contraseña es incorrecta
            System.out.println("La contraseña ingresada es incorrecta");
        } else {
            //login correcto
            return loggedClient;
        }
        return null;
    }

    //menu para seleccionar origen
    private static Flight.City selectOrigin() {

        //recupero la lista de ciudades de Flight.City
        List<Flight.City> cities = Arrays.asList(Flight.City.values());

        //muestro la lista y devuelvo la seleccionada por el usuario
        int selectedOrigin = printAndWaitAnswer(cities);
        if (selectedOrigin == 0)
            return null;
        return cities.get(selectedOrigin - 1);

    }

    //menu para seleccionar destino
    private static Flight.City selectDestiny(Flight.City origin) {

        //recupero la lista de ciudades de Flight.City
        List<Flight.City> cities = new ArrayList<>(Arrays.asList(Flight.City.values()));

        //elimino el destino
        cities.remove(origin);

        //muestro la lista y devuelvo la seleccionada por el usuario
        int selectedDestiny = printAndWaitAnswer(cities);
        if (selectedDestiny == 0) {
            return null;
        }
        return cities.get(selectedDestiny - 1);

    }

    //menu para registrar usuario
    private static void registerClient(ClientRepository clientRepository) {
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
            if (clientRepository.exists(username)) {
                System.out.println("El nombre de usuario ya existe, por favor elija otro.");
            }
        } while (clientRepository.exists(username));

        System.out.print("Contraseña: ");
        password = read.next();
        Client client = new Client(username, password, firstName, lastName, DNI, age);
        //guardar el cliente nuevo en un archivo
        clientRepository.add(client);

        try {
            clientRepository.commit();
            System.out.println("Cliente registrado con exito!");
        } catch (IOException e) {
            System.out.println("No se pudo escribir el archivo de clientes...");
            e.printStackTrace();
        }
    }

    private static boolean confirm() {
        boolean confirm = false;
        switch (printAndWaitAnswer(Arrays.asList(
                "Sí",
                "No"))) {
            case 1:
                confirm = true;
                break;

            case 2:
                confirm = false;
                break;
        }
        return confirm;
    }

    //menu para contratar o cancelar un vuelo
    private static void hireCancelFlightMenu(Client client, FlightRepository flightRepository, ClientRepository clientRepository, PlaneRepository planeRepository) {

        int selectedOption = -1;

        do {
            selectedOption = printAndWaitAnswer(Arrays.asList(
                    "Contratar vuelo",
                    "Cancelar vuelo"));

            switch (selectedOption) {
                case 1://contratar vuelo
                    hireFlight(client, flightRepository, planeRepository, clientRepository);
                    break;
                case 2://cancelar vuelo
                    cancelFlight(client, flightRepository, clientRepository, planeRepository);
                    break;
                default:
                    break;
            }

        } while (selectedOption != 0);


    }

    private static void hireFlight(Client client, FlightRepository flightRepository, PlaneRepository planeRepository, ClientRepository clientRepository) {
        //pide la fecha del vuelo
        Date date = askForDateAndTime("- Ingrese la fecha en la que desea viajar, ");
        //selecciona origin y destiny
        System.out.println("- Seleccione la ciudad de Origen del vuelo: ");
        Flight.City origin = selectOrigin();
        if (origin == null) {
            return;
        }
        System.out.println("Origen seleccionado: " + origin);
        System.out.println("- Seleccione la ciudad de Destino del vuelo: ");
        Flight.City destiny = selectDestiny(origin);
        if (destiny == null) {
            return;
        }
        System.out.println("- Ingrese la cantidad de acompañantes: ");
        Scanner read = new Scanner(System.in);
        int companions;
        while (!read.hasNextInt()) read.next(); //para que acepte solo números
        companions = read.nextInt();

        //lista de aviones que pueden llevar esos acompañantes
        List<Plane> availablePlanes = planeRepository.getAvailablePlanes(companions + 1);
        //lista de vuelos en esa fecha
        List<Flight> unavailableFlights = flightRepository.getAllByDate(date);
        //Le restamos a la lista de aviones que pueden llevar acompañantes todos los aviones que ya están resevados en esa fecha
        for (Flight f : unavailableFlights) {
            availablePlanes.remove(planeRepository.get(f.getPlane()));
        }
        if (availablePlanes.isEmpty()) {
            System.out.println("No hay aviones disponibles con las caracteristicas necesitarias en esa fecha");
            return;
        }
        System.out.println("- Seleccione el avión: ");
        //mostramos la lista
        List<String> options = new ArrayList<>();
        for (Plane p : availablePlanes) {
            options.add("Tipo: " + p.getType() +
                    ", Capacidad: " + p.getPassengerCapacity()
                    + ", Velocidad: " + p.getMaxSpeed() +
                    ((p instanceof Catering) ? " (Catering)" : "" + "") +
                    ((p instanceof GoldPlane && ((GoldPlane) p).getHasWifi()) ? " (Wifi)" : "")
            );
        }
        int selectedPlane = printAndWaitAnswer(options);
        if (selectedPlane == 0) {
            return;
        }
        //seleccionamos el avion
        Plane plane = availablePlanes.get(selectedPlane - 1);
        //Creamos el vuelo
        Flight flight = new Flight(date, origin, destiny, plane.getId());
        //Agregamos a los pasajeros
        flight.addPassagers(client, companions);
        //Mostramos el costo total
        System.out.println("Costo total de vuelo: " + flight.getTotalCost());
        //Pedimos confirmación
        System.out.println("¿Desea confirmar el vuelo?");
        if (confirm()) {
            flightRepository.add(flight);

            try {
                flightRepository.commit();
                client.setTotalSpent(client.getTotalSpent() + (float) flight.getTotalCost());
                client.setBestPlaneUsed(plane.getType());
                clientRepository.replace(client);
                clientRepository.commit();
                System.out.println("El vuelo fue reservado");
                systemPause();
            } catch (IOException e) {
                System.out.println("No se pudo reservar el vuelvo");
                e.printStackTrace();
            }
        }
    }

    //Menu para cancelar vuelos
    private static void cancelFlight(Client client, FlightRepository flightRepository, ClientRepository clientRepository, PlaneRepository planeRepository) {

        //recuperamos todos los vuelos del usuario
        List<Flight> flightForClient = flightRepository.getAllByUsername(client.getUsername());

        //borramos todos los que ya hayan pasado
        flightForClient.removeIf(flight -> flight.getDate().before(new Date()));

        //si no tiene vuelos muestro mensaje y salgo
        if (flightForClient.isEmpty()) {
            System.out.println("No tiene vuelos para cancelar");
            systemPause();
            //si tiene vuelos para cancelar los mostramos y le damos la opcion de seleccionar uno
        } else {
            System.out.println("Seleccione un vuelo para cancelarlo. Vuelos reservados: ");
            List<String> cancelOptions = new ArrayList<>();
            for (Flight f : flightForClient) {
                cancelOptions.add(f.getDate().toString() + " | " + f.getOrigin() + " - " + f.getDestiny());
            }
            int selectedOption = printAndWaitAnswer(cancelOptions);
            //borramos el vuelo seleccionado
            if (selectedOption != 0) {
                //nos fijamos que el vuelo a cancelar no esté dentro de las próximas 24hs
                Calendar currentPlus24 = Calendar.getInstance();
                currentPlus24.setTime(new Date());
                currentPlus24.add(Calendar.HOUR_OF_DAY, 24);

                Date flightDate = flightForClient.get(selectedOption - 1).getDate();

                if (flightDate.before(currentPlus24.getTime())) {
                    System.out.println("No se puede cancelar un vuelo con menos de 24hs de anticipación");
                    systemPause();
                } else {
                    flightRepository.remove(flightForClient.get(selectedOption - 1).getID());
                    try {
                        flightRepository.commit();
                        //resta del total gastado
                        client.setTotalSpent(client.getTotalSpent() - (float) flightForClient.get(selectedOption - 1).getTotalCost());
                        //si borramos un vuelo puede ser que sea el mejor vuelo del usuario,
                        // asi que vamos a tener que ver todos sus vuelos anteriores para ver el mejor (o null si este cancelado fue el único)
                        client.setBestPlaneUsed(null);
                        List<Flight> allFlightForClient = flightRepository.getAllByUsername(client.getUsername());
                        for (Flight flight : allFlightForClient) {
                            Plane plane = planeRepository.get(flight.getPlane());
                            client.setBestPlaneUsed(plane.getType());
                        }

                        clientRepository.replace(client);
                        clientRepository.commit();

                        System.out.println("Vuelo cancelado");
                        systemPause();
                    } catch (IOException e) {
                        System.out.println("No se pudo cancelar el vuelo");
                        e.printStackTrace();
                        systemPause();
                    }
                }
            }
        }
    }


    //this method smh
    public static Date askForDateAndTime(String header) {

        Date date = null;
        Date currentTime = new Date();
        do {
            System.out.println(header + "la fecha tiene que estar en formato dd/mm/yyyy HH:mm");
            System.out.println("Por ejemplo: 02/01/2017 22:00");
            Scanner sc = new Scanner(System.in);

            String input = sc.nextLine();
            SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            try {
                date = parser.parse(input);
                String formattedDate = parser.format(date);

                //se fija que la fecha no esté en el pasado.
                if (date.before(currentTime)) {
                    System.out.println("Fecha ya pasada");
                    date = null;
                } else {
                    System.out.println("Fecha seleccionada: " + formattedDate);
                }
            } catch (ParseException e) {
                System.out.println("Fecha seleccionada inválida");
            }

        } while (date == null);
        return date;
    }

    public static Date askForDate() {

        Date date = null;
        do {
            System.out.println("La fecha tiene que estar en formato dd/mm/yyyy");
            System.out.println("Por ejemplo: 02/01/2017");

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");

            try {
                date = parser.parse(input);
                String formattedDate = parser.format(date);
                System.out.println("Fecha seleccionada: " + formattedDate);
            } catch (ParseException e) {
                System.out.println("Fecha seleccionada inválida");
            }

        } while (date == null);

        return date;
    }

}
