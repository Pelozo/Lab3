package something.something;

import something.something.model.client.Client;
import something.something.repositories.client.ClientRepository;

import java.io.IOException;


// a falta de Junit...
public class Test {

    public static void testClientRepo(){
        System.out.println("testeando repo de clientes");

        ClientRepository repository = null;

        //intenta leer o crear archivo
        try {
            repository = ClientRepository.getInstance();
        } catch (IOException e) {
            System.out.println("No se pudo recuperar el archivo de clientes ni escribir uno nuevo");
            e.printStackTrace();
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

}
