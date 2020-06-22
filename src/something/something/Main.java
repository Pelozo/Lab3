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
        if (planeRepository.getAll().isEmpty()) {
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

        Menu.startMenu(clientRepository, flightRepository, planeRepository);

    }
}
