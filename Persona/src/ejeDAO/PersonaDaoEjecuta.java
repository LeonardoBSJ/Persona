package ejeDAO;

import Dao.PersonaDao;
import Dao.PersonaDaoImp;
import java.util.Scanner;

public class PersonaDaoEjecuta {

    public static void main(String[] args) {
        int op;
        Scanner leer = new Scanner(System.in);
        PersonaDao pp = new PersonaDaoImp();
        do {
            System.out.println("\tQue quiere realizar:");
            System.out.println("\t1. agregardatos (Crear nuevo archivo) ");
            System.out.println("\t2. consultartodo");
            System.out.println("\t3. Borrar id");
            System.out.println("\t4. Buscar por id");
            System.out.println("\t5. Actualizar");
            System.out.println("\t6. borrarTodo");
            System.out.println("\tingrese el numero que quiere realizar");
            op = leer.nextInt();
            switch (op) {

                case 1: {
                    System.out.println("\tPrimero crea un nuevo archivo donde se va a agregar  los datos: ");
                    System.out.println("\tDespues Escriba los datos en orden : id, nombre, edad :  ");
                    System.out.println("\tPara terminar de introducir datos solo escriba (salir):  ");

                    pp.agregar();
                    break;
                }

                case 2: {
                    System.out.println("\tEscriba el nombre del archivo a consultar");

                    pp.consultartodo();
                    break;
                }
                case 3: {
                    System.out.println("\tEscriba el nombre del archivo a Borrar por id");

                    pp.BorrarId();
                    break;
                }

                case 4: {
                    System.out.print("\tIngrese el nombre del archivo donde desea buscar: ");

                    pp.BuscarId();
                    break;
                }
                case 5: {
                    System.out.print("\tEscriba el nombre del archivo ");

                    pp.Actualizar();
                    break;
                }

                case 6: {
                    System.out.println("\tEscriba el nombre del archivo para eliminar todo: ");
                    pp.BorrarTodo();
                    break;
                }
                default:
                    System.out.println("");

                    break;
            }

            try {
                Thread.sleep(1200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (op <= 6);
        leer.close();
        System.out.println("finalizo sus operaciones");
    }

}
