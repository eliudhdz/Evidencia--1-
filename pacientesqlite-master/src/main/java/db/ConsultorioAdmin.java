package db;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class ConsultorioAdmin {

    static final Logger logger = LogManager.getLogger();

    /**
     *
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void main (String[] args) throws ClassNotFoundException, SQLException {

        int seleccion;
        String user = "usuario";
        String password = "password";
        BaseDatos persist = new BaseDatos("consultorio.db");
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("bienvenido al sistema de un centro hospitalario");
            System.out.println("Ingrese su usuario y contraseña para iniciar");
            System.out.println("Usuario:");
            user = scanner.nextLine();
            System.out.println("Contraseña:");
            password = scanner.nextLine();
            List<Usuario> usuario = persist.getUsuarioByName(user, password);
            if (!usuario.isEmpty()) {
                while (true) {
                    System.out.println("(1) Dar de alta doctores.");
                    System.out.println("(2) Dar de alta pacientes.");
                    System.out.println("(3) Lista de doctores");
                    System.out.println("(4) Lista de pacientes.");
                    System.out.println("(5) Crear una cita");
                    System.out.println("(6) Para ver una lista de las citas existentes");
                    System.out.println("(7) Para ver los horarios existentes");
                    System.out.println("(8) Para crear un horario nuevo");
                    System.out.println("(9) Relacionar una cita con un doctor y un paciente");
                    System.out.println("(0) Salir");
                    System.out.println("\nPor favor ingrese una opción: ");
                    // Fin de Menu
                    // Try Anidado
                    try {
                        // Asigna token Integer parseado
                        seleccion = scanner.nextInt();
                        switch (seleccion) {
                            case 0:
                                System.out.println("Saliendo..");
                                logger.info("Saliendo...");
                                return;
                            case 1:
                                Scanner n = new Scanner(System.in);
                                Scanner e= new Scanner(System.in);
                                System.out.println("Dame el nombre del nuevo doctor");
                                String nombre=n.next();
                                System.out.println("Escribe el nombre de la especialidad del doctor");
                                String especialidad =e.next();
                                persist.añadirdoctor(nombre,especialidad);

                                break;
                            case 2:
                                Scanner nuevo = new Scanner(System.in);
                                Scanner a= new Scanner(System.in);
                                Scanner Seg= new Scanner(System.in);
                                System.out.println("Dame el nombre del nuevo paciente");
                                String nombre_paciente=nuevo.next();
                                System.out.println("Dame el apellido del paciente");
                                String apellido =a.next();
                                System.out.println("numero de seguro social");
                                String no_seguro_social =Seg.next();
                                persist.añadirpaciente(nombre_paciente,apellido,no_seguro_social);

                                break;
                            case 3:persist.doctores();

                                break;
                            case 4:persist.pacientes();
                                break;
                            case 5:
                                Scanner horario = new Scanner(System.in);
                                Scanner doc= new Scanner(System.in);
                                Scanner paci= new Scanner(System.in);

                                System.out.println("Porfavor escribe el indicador de horario");
                                int id_horario=horario.nextInt();
                                System.out.println("Porfavor selecciona al doctor");
                                int id_doctor =doc.nextInt();
                                System.out.println("Porfavor escribe el id del paciente");
                                int id_paciente =paci.nextInt();
                                persist.agendarcita(id_horario,id_doctor,id_paciente);

                                break;
                            case 6:persist.citas();
                                break;
                            case 7:persist.horarios();
                                break;
                            case 8:
                                Scanner F = new Scanner(System.in);
                                Scanner H= new Scanner(System.in);
                                System.out.println("Escribe la fecha del horario");
                                String Fecha=F.next();
                                System.out.println("Escribe la hora ");
                                String Hora =H.next();
                                persist.AñadirHorario(Fecha,Hora);
                                break;
                            case 9:persist.citadoctores();
                                break;
                            default:
                                System.err.println("Opción inválida.");
                                logger.error("Opción inválida: {}", seleccion);
                                break;
                        }

                    } catch (Exception ex) {
                        logger.error("{}: {}", ex.getClass(), ex.getMessage());
                        System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
                        scanner.next();
                    }
                }
            } else {
                System.out.println("No tiene autorización");
            }
        } catch (Exception ex) {
            logger.error("{}: {}", ex.getClass(), ex.getMessage());
            System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
        } finally {
            persist.getConnection().close();
        }
    }
}
