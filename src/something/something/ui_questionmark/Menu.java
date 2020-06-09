package something.something.ui_questionmark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private List<String> options;

    public Menu(List<String> options) {
        this.options = options;
    }


    public void print(){
        for(int i = 0; i < options.size(); i++){
            System.out.println(i + 1  + ". " + options.get(i));
        }
        System.out.println("0. Volver");
    }

    public int printAndWaitAnswer(){

        final String ERROR = "La opcion ingresada no es válida. Debe ingresar un número entre 0 y " + options.size();

        int menuOption = -1;
        Scanner sc = new Scanner(System.in);
        do {
            print();

            if (sc.hasNextInt()) {
                menuOption = sc.nextInt();
                if(menuOption < 0 || menuOption > options.size()){
                    System.out.println(ERROR);
                }else{
                    return menuOption;
                }
            } else {
                System.out.println(ERROR);
                sc.next();
            }
        }while (true);
    }

    //Este metodo es una mierda, y un claro ejemplo de porque existe un backend y un frontend, y pedir un menu y validaciones en una aplicación de consola es sadismo.
    public static Date askForDate(String header){
        System.out.println(header);
        System.out.println("La fecha tiene que estar en formato dd/mm/yyyy");
        System.out.println("O ingrese 0 para volver");
        Scanner sc = new Scanner(System.in);

        String ingresado = sc.next();

        if(ingresado.equals("0"))
            return null;

        SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = parser.parse(ingresado);
            String formattedDate = parser.format(date);
            System.out.println(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
