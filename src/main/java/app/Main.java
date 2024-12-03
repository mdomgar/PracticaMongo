package app;

import dao.ProgramaDAOMongoDB;
import modelo.Audiencia;
import modelo.Colaborador;
import modelo.Horario;
import modelo.Programa;
import utils.MongoDBConnection;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int opcion = 99;
        MongoDBConnection mongoDBConnection = new MongoDBConnection();
        ProgramaDAOMongoDB programaDAOMongoDB = new ProgramaDAOMongoDB(mongoDBConnection);

        while (opcion!=0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introduce una opción: " +
                    "\n1. Crear un nuevo programa." +
                    "\n2. Listar todos los programas" +
                    "\n3. Consultar un programa por id" +
                    "\n4. Consultar un programa por nombre" +
                    "\n5. Actualizar un programa" +
                    "\n6. Eliminar un programa" +
                    "\n7. Listar programas de una categoría específica" +
                    "\n8. Obtener el programa con mayor audiencia en una fecha concreta" +
                    "\n9. Calcular la audiencia media de un programa en un rango de fechas" +
                    "\n0. Salir");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    try {
                        System.out.println("Introduce el nombre del programa");
                        String nombrePrograma = sc.nextLine();

                        System.out.println();
                        System.out.println("Introduce la categoria del programa");
                        String categoriaPrograma = sc.nextLine();

                        System.out.println();
                        System.out.println("Introduce el día del programa que ha sido emitido");
                        int diaPrograma = sc.nextInt();
                        sc.nextLine();

                        System.out.println();
                        System.out.println("Introduce la hora del programa que fue emitido (00:00)");
                        String horaPrograma = sc.nextLine();

                        Horario horario = new Horario(diaPrograma, horaPrograma);

                        System.out.println();
                        System.out.println("¿Cuantas emisiones vas a introducir?");
                        int a = sc.nextInt();
                        sc.nextLine();

                        List<Audiencia> audienciaList = new ArrayList<>();
                        for (int x = 0; x < a; x++) {
                            System.out.println("Introduce la fecha del programa emitido (yyyy-MM-dd)");
                            String fechaPrograma = sc.nextLine();
                            System.out.println();
                            System.out.println("Introduce el número de espectadores que tuvo el programa");
                            int espectadoresPrograma = sc.nextInt();
                            sc.nextLine();
                            audienciaList.add(new Audiencia(fechaPrograma, espectadoresPrograma));
                        }

                        System.out.println();
                        System.out.println("¿Cuantos colaboradores hubieron?");
                        a = sc.nextInt();
                        sc.nextLine();

                        List<Colaborador> colaboradorList = new ArrayList<>();
                        for (int x = 0; x < a; x++) {
                            System.out.println();
                            System.out.println("Introduce el nombre del colaborador");
                            String nombreColaborador = sc.nextLine();
                            System.out.println();
                            System.out.println("Introduce el rol del colaborador");
                            String rolColaborador = sc.nextLine();
                            colaboradorList.add(new Colaborador(nombreColaborador, rolColaborador));
                        }

                        programaDAOMongoDB.insertar(new Programa(nombrePrograma, categoriaPrograma, horario, audienciaList, colaboradorList));
                    } catch (Exception e) {
                        System.err.println("No se ha podido realizar la conexión con MongoDB" + e);
                    }
                    break;

                case 2:
                    try {
                        System.out.println(programaDAOMongoDB.buscarTodos());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Introduce una id de un programa");
                        String idPrograma = sc.nextLine();
                        System.out.println(programaDAOMongoDB.buscarPorId(idPrograma));
                    } catch (Exception e) {
                        System.err.println("La id introducida con coincide con ningún programa");
                    }
                    break;

                case 4:
                    try {
                        System.out.println("Introduce el nombre de un programa");
                        String nombrePrograma = sc.nextLine();
                        System.out.println();
                        System.out.println(programaDAOMongoDB.buscarPorNombre(nombrePrograma));
                    } catch (Exception e) {
                        System.err.println("El nombre introducido con coincide con ningún programa");
                    }
                    break;

                case 5:
                    try {
                        System.out.println("Introduce una id del programa que quieres actualizar");
                        String idPrograma = sc.nextLine();

                        System.out.println("Introduce el nombre del programa");
                        String nombrePrograma = sc.nextLine();

                        System.out.println();
                        System.out.println("Introduce la categoria del programa");
                        String categoriaPrograma = sc.nextLine();

                        System.out.println();
                        System.out.println("Introduce el día del programa que ha sido emitido");
                        int diaPrograma = sc.nextInt();
                        sc.nextLine();

                        System.out.println();
                        System.out.println("Introduce la hora del programa que fue emitido (00:00)");
                        String horaPrograma = sc.nextLine();

                        Horario horario = new Horario(diaPrograma, horaPrograma);

                        System.out.println();
                        System.out.println("¿Cuantas emisiones vas a introducir?");
                        int a = sc.nextInt();
                        sc.nextLine();

                        List<Audiencia> audienciaList = new ArrayList<>();
                        for (int x = 0; x < a; x++) {
                            System.out.println("Introduce la fecha del programa emitido (yyyy-MM-dd)");
                            String fechaPrograma = sc.nextLine();
                            System.out.println();
                            System.out.println("Introduce el número de espectadores que tuvo el programa");
                            int espectadoresPrograma = sc.nextInt();
                            sc.nextLine();
                            audienciaList.add(new Audiencia(fechaPrograma, espectadoresPrograma));
                        }

                        System.out.println();
                        System.out.println("¿Cuantos colaboradores hubieron?");
                        a = sc.nextInt();
                        sc.nextLine();

                        List<Colaborador> colaboradorList = new ArrayList<>();
                        for (int x = 0; x < a; x++) {
                            System.out.println();
                            System.out.println("Introduce el nombre del colaborador");
                            String nombreColaborador = sc.nextLine();
                            System.out.println();
                            System.out.println("Introduce el rol del colaborador");
                            String rolColaborador = sc.nextLine();
                            colaboradorList.add(new Colaborador(nombreColaborador, rolColaborador));
                        }
                        programaDAOMongoDB.actualizarById(new Programa(nombrePrograma, categoriaPrograma, horario, audienciaList, colaboradorList),idPrograma);
                    } catch (Exception e) {
                        System.err.println("La id introducida no coincide con ningún programa");
                    }
                    break;

                case 6:
                    try {
                        System.out.println("Introduce la id del programa que quieres eliminar");
                        String idPrograma = sc.nextLine();
                        programaDAOMongoDB.eliminar(idPrograma);
                    } catch (Exception e){
                        System.err.println("La id introducida no coincide con ningún programa");
                    }
                    break;

                case 7:
                    try {
                        System.out.println("Introduce una categoria para buscar programas");
                        String categoriaPrograma = sc.nextLine();
                        System.out.println(programaDAOMongoDB.buscarPorCategoria(categoriaPrograma));
                    } catch (Exception e){
                        System.err.println("La categoria introducida no coincide con ningún programa");
                    }
                    break;

                case 8:
                    try {
                        System.out.println("Introduce una fecha concreta (yyyy-MM-dd)");
                        String fechaPrograma = sc.nextLine();
                        System.out.println(programaDAOMongoDB.programaConMayorAudiencia(fechaPrograma));
                    } catch (Exception e){
                        System.err.println("La fecha introducida no coincide con ningún programa o es errónea");
                    }
                    break;

                case 9:
                    try {
                        System.out.println("Introduce una id de un programa");
                        String idPrograma = sc.nextLine();
                        System.out.println("Introduce la fecha Inicio (yyyy-MM-dd)");
                        String fechaInicio = sc.nextLine();
                        System.out.println("Introduce la fecha Fin (yyyy-MM-dd)");
                        String fechaFin = sc.nextLine();
                        System.out.println(programaDAOMongoDB.calcularAudienciaMedia(idPrograma,fechaInicio,fechaFin));
                    } catch (Exception e){
                        System.err.println("Los parametros introducidos son erroneos o con formato equivocado");
                    }
                    break;

                case 0:
                    break;
            }
        }
    }
}
