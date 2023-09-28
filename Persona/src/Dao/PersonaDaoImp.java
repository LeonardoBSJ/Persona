package Dao;

import java.io.*;
import persona.Persona;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PersonaDaoImp implements PersonaDao {

    Scanner entrada = new Scanner(System.in);

    @Override
    public void agregar() {
        Persona registro;
        int id;
        String nombre;
        int edad;

        String archivo = entrada.nextLine();
        try {
            FileOutputStream file = new FileOutputStream(archivo);
            ObjectOutputStream escritor = new ObjectOutputStream(file);

            while (entrada.hasNext()) {

                String entradastr = entrada.next();
                if (entradastr.equalsIgnoreCase("salir")) {
                    break;
                }
                id = Integer.parseInt(entradastr);
                nombre = entrada.next();
                edad = entrada.nextInt();
                if (id > 0) {
                    registro = new Persona(id, nombre, edad);
                    escritor.writeObject(registro);

                } else {
                    System.out.println("el numero de id debe ser (>0)");

                }
            }

            System.out.println("datos guardados en " + archivo);

        } catch (IOException e) {
            System.out.println("Error al escribir el archivo " + e.getMessage());

        } catch (NoSuchElementException e) {
            System.err.println("entrada invalidad intenta de nuevo" + e.getLocalizedMessage());
            entrada.nextLine();
        }

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("");
        }
        System.out.println(" ");
    }

    @Override
    public void consultartodo() {
        String archi = entrada.nextLine();

        try {
            FileInputStream file = new FileInputStream(archi);
            ObjectInputStream Obj = new ObjectInputStream(file);
            boolean archivoleido = false;
            while (!archivoleido) {
                try {

                    Persona or = (Persona) Obj.readObject();
                    if (or != null) {
                        System.out.println(" El id es: " + or.getId() + " Nombre: " + or.getNombre() + " edad: " + or.getEdad());
                        System.out.println("");
                    } else {
                        archivoleido = true;

                    }

                } catch (ClassNotFoundException e) {
                    System.out.println("datos no encontrados en archivo: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("fin al leer el archivo: ");

        }
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    @Override
    public void BorrarId() {
        String Original = entrada.nextLine();

        File archivo = new File(Original);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        System.out.print("Ingrese el Id que desea borrar: ");
        int idBorrar = entrada.nextInt();
        entrada.nextLine();

        System.out.print("Ingrese el nombre del nuevo archivo (para hacer una copia con las modificaciones del archivo original): ");
        String nuevoAr = entrada.nextLine();

        try {
            FileInputStream file = new FileInputStream(Original);
            ObjectInputStream Obj = new ObjectInputStream(file);
            FileOutputStream leer = new FileOutputStream(nuevoAr);
            ObjectOutputStream escrito = new ObjectOutputStream(leer);

            boolean encont = false;
            while (true) {
                try {
                    Persona persona = (Persona) Obj.readObject();
                    if (persona != null && persona.getId() == idBorrar) {
                        encont = true;
                    } else if (persona != null) {
                        escrito.writeObject(persona);
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Datos no encontrados en el archivo: " + e.getMessage());
                } catch (EOFException e) {

                    break;
                }
            }

            if (encont) {
                archivo.delete();
                System.out.println("Datos con Id " + idBorrar + " Sea  eliminado.");
            } else {
                System.out.println("Registro con Id " + idBorrar + " No existe ");
            }
        } catch (IOException e) {
            System.err.println("Error al borrar el registro: " + e.getMessage());
        }
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    @Override
    public void BuscarId() {

        String archivo = entrada.nextLine();

        System.out.print("Ingrese el Id que buscara: ");
        int idBuscar = entrada.nextInt();

        try ( ObjectInputStream lector = new ObjectInputStream(new FileInputStream(archivo))) {
            boolean encont = false;

            while (true) {
                try {
                    Persona persona = (Persona) lector.readObject();
                    if (persona != null && persona.getId() == idBuscar) {
                        System.out.println("Registro a sido encontrado:");
                        System.out.println("      Id:      " + "       Nombre:        " + "      Edad:     ");
                        System.out.println(persona.getId() + "   " + persona.getNombre() + "   " + persona.getEdad());
                        encont = true;
                        break;
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Datos no encontrada en el archivo: " + e.getMessage());
                } catch (EOFException e) {

                    break;
                }
            }

            if (!encont) {
                System.out.println("Registro con ID " + idBuscar + " no encontrado.");
            }
        } catch (IOException e) {
            System.err.println("Error al buscar el registro por ID: " + e.getMessage());
        }
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(" ");
    }

    @Override
    public void Actualizar() {

        String archivo = entrada.nextLine();

        System.out.println("Ingrese el Id que desea actualizar: ");
        int idActual = entrada.nextInt();

        entrada.nextLine();

        try ( ObjectInputStream lector = new ObjectInputStream(new FileInputStream(archivo));  ObjectOutputStream escritorTemp = new ObjectOutputStream(new FileOutputStream("Renombrado" + archivo))) {

            boolean encontrado = false;

            while (true) {
                try {
                    Persona persona = (Persona) lector.readObject();
                    if (persona != null) {
                        if (persona.getId() == idActual) {
                            encontrado = true;

                            System.out.print("Ingrese el nuevo nombre: ");
                            String nuevoNombre = entrada.nextLine();
                            System.out.print("Ingrese la nueva edad: ");
                            int nuevaEdad = entrada.nextInt();

                            Persona personaActualizada = new Persona(idActual, nuevoNombre, nuevaEdad);

                            escritorTemp.writeObject(personaActualizada);
                        } else {
                            escritorTemp.writeObject(persona);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("datos no encontrada en el archivo: " + e.getMessage());
                } catch (EOFException e) {
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Registro con ID " + idActual + " no encontrado.");
            } else {
                File Original = new File(archivo);
                File Temporal = new File(archivo);
                Temporal.renameTo(Original);
                System.out.println("Archivo actualizado.");
                System.out.println("como: Renombrado" + archivo);
                System.out.println("");
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el registro: " + e.getMessage());
        }
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    @Override
    public void BorrarTodo() {

        String archivo = entrada.nextLine();

        File archivoAElimina = new File(archivo);

        if (archivoAElimina.exists()) {
            archivoAElimina.delete();
            System.out.println("Todos los registros del archivo " + archivo + " han sido eliminados.");
        } else {
            System.out.println("El archivo " + archivo + " no existe.");
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println(" ");
        }
        System.out.println(" ");
    }

}
