package something.something.ui_questionmark;

import something.something.model.client.Client;
import something.something.model.flight.Flight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public void systemPause() {
        System.out.println("Presione una tecla para continuar...");
        new java.util.Scanner(System.in).nextLine();
    }

    private List<String> options;

    public Menu(List<String> options) {
        this.options = options;
    }

    public void print() {
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + 1 + ". " + options.get(i));
        }
        System.out.println("0. Volver");
    }

    public int printAndWaitAnswer() {

        final String ERROR = "La opcion ingresada no es válida. Debe ingresar un número entre 0 y " + options.size();

        int menuOption = -1;
        Scanner sc = new Scanner(System.in);

        do {
            print();

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
    public void startMenu() {
        System.out.print("- AeroTaxi -\n¡BIENVENIDO! Vola con AeroTaxi...\n");

        switch (new Menu(Arrays.asList(
                "Registrarse",
                "Iniciar sesión")).printAndWaitAnswer()) {
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
                System.out.print("Nombre de Usuario: ");
                username = read.next();
                System.out.print("Contraseña: ");
                password = read.next();
                Client client = new Client(username, password, firstName, lastName, DNI, age);
                //guardar el cliente nuevo en un archivo or something
                System.out.println("Cliente registrado con exito!\n" + client);
                systemPause();
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
                //if(el usuario existe)
                hireCancelFlightMenu();
                break;
            default:
                break;
        }
    }

    //menu para seleccionar origen
    public Flight.City selectOrigin() {
        Flight.City origin = null;
        switch (new Menu(Arrays.asList(
                "Buenos Aires",
                "Córdoba",
                "Montevideo")).printAndWaitAnswer()) {
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
    public Flight.City selectDestiny() {
        Flight.City destiny = null;
        switch (new Menu(Arrays.asList(
                "Córdoba",
                "Santiago",
                "Montevideo")).printAndWaitAnswer()) {
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
        return destiny;
    }

    //menu para contratar o cancelar un vuelo
    public void hireCancelFlightMenu() {
        switch (new Menu(Arrays.asList(
                "Contratar vuelo",
                "Cancelar vuelo")).printAndWaitAnswer()) {
            case 1:
                //pide la fecha del vuelo
                Date date = askForDate("- Ingrese la fecha en la que desea viajar, ");
                //selecciona origin y destiny
                System.out.println("- Seleccione la ciudad de Origen del vuelo: ");
                Flight.City origin = selectOrigin();
                System.out.println("Origen seleccionado: " + origin);
                System.out.println("- Seleccione la ciudad de Destino del vuelo: ");
                Flight.City destiny = selectDestiny();
                //verificar q destino y origen sean distintos
                System.out.println("- Ingrese la cantidad de acompañantes: ");

                break;
            case 2:
                break;
            default:
                break;
        }
    }

    //Este metodo es una mierda, y un claro ejemplo de porque existe un backend y un frontend, y pedir un menu y validaciones en una aplicación de consola es sadismo.
    public static Date askForDate(String header) {
        System.out.print(header + "la fecha tiene que estar en formato dd/mm/yyyy, (ingrese 0 para volver atras): ");
        Scanner sc = new Scanner(System.in);

        String ingresado = sc.next();

        if (ingresado.equals("0"))
            return null;

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
