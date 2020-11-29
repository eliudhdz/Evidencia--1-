package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

    private String database;
    private Connection connection;
    private Statement statement;

    public BaseDatos(String db) throws ClassNotFoundException, SQLException {
        this.database = db;
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        this.statement = connection.createStatement();
    }

    public Connection getConnection() {
        return connection;
    }

    public List<Usuario> getUsuarioByName(String nombre, String password) throws SQLException {
        ResultSet rs = this.statement.executeQuery("select * from usuario where upper(nombre)='" + nombre.toUpperCase() + "' and password='" + password.toUpperCase() + "'");
        List<Usuario> usuario = new ArrayList();
        while (rs.next()) {
            Usuario temp = new Usuario();
            temp.setIdUsuario(rs.getInt("id_usuario"));
            temp.setIdUsuario(rs.getInt("nombre"));
            temp.setIdUsuario(rs.getInt("password"));
            temp.setIdUsuario(rs.getInt("rol"));
            usuario.add(temp);
        }
        return usuario;
    }

    public boolean addCustomer(Usuario usuario) throws SQLException {
        String sql = "insert into Customer(FirstName, LastName, Company, Address, "
                + "City, State, Country, PostalCode, Phone, Fax, Email, SupportRepId) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        /*prepStmt.setString(1, customer.getFirstName());
        prepStmt.setString(2, customer.getLastName());
        prepStmt.setString(3, customer.getCompany());
        prepStmt.setString(4, customer.getAdd00s00000
        prepStmt.setString(5, customer.getCity());
        prepStmt.setString(6, customer.getState());
        prepStmt.setString(7, customer.getCountry());
        prepStmt.setString(8, customer.getPostalCode());
        prepStmt.setString(9, customer.getPhone
        prepStmt.setString(10, customer.getFax());
        prepStmt.setString(11, customer.getEmail());
        prepStmt.setInt(12, customer.getSupportRepId());*/
        return prepStmt.execute();}



    public void doctores() throws SQLException{
        String sql = "SELECT id,nombre,especialidad from doctores";
        try (Statement stmt= this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                System.out.println(rs.getInt("id")+"|"+
                        rs.getString( "nombre")+"|"+
                        rs.getString("especialidad"));

            }
        }catch (SQLException e){
            System.out.println(e.getMessage( ));
        }



    }


    public boolean añadirdoctor(String nombre,String especialidad)throws SQLException {
        String sql = "insert into doctores (nombre,especialidad)"+" values (?,?)";
        PreparedStatement preparedStmt = this.connection.prepareStatement(sql);
        preparedStmt.setString(  1,nombre);
        preparedStmt.setString(  2,especialidad);
        return preparedStmt.execute();

    }

    public boolean añadirpaciente (String nombre_paciente,String apellido,String no_seguro_social)throws SQLException {
        String sql = "insert into paciente (nombre_paciente,apellido,no_seguro_social)"+" values (?,?,?)";
        PreparedStatement preparedStmt = this.connection.prepareStatement(sql);
        preparedStmt.setString(  1,nombre_paciente);
        preparedStmt.setString(  2,apellido);
        preparedStmt.setString(  3,no_seguro_social);
        return preparedStmt.execute();

    }

    public boolean agendarcita(Integer id_horario,Integer id_doctor,Integer id_paciente)throws SQLException {
        String sql = "insert into agendarCitas(id_horario,id_doctor,id_paciente)"+" values (?,?,?)";
        PreparedStatement preparedStmt = this.connection.prepareStatement(sql);
        preparedStmt.setInt( 1,id_horario);
        preparedStmt.setInt( 2,id_doctor);
        preparedStmt.setInt( 3,id_paciente);
        return preparedStmt.execute();
    }

    public boolean AñadirHorario(String Fecha,String Hora)throws SQLException {
        String sql = "insert into horario(Fecha,Hora,id_paciente)"+" values (?,?)";
        PreparedStatement preparedStmt = this.connection.prepareStatement(sql);
        preparedStmt.setString( 1,Fecha);
        preparedStmt.setString( 2,Hora);
        return preparedStmt.execute();

    }


    public void pacientes() throws SQLException{
        String sql = "SELECT id_paciente,nombre_paciente,apellido,no_seguro_social from paciente";
        try (Statement stmt= this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                System.out.println (rs.getInt("id_paciente")+
                        rs.getString( "nombre_paciente")+
                        rs.getString("apellido")+
                        rs.getString("no_seguro_social")
                );

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public void horarios() throws SQLException{
        String sql = "SELECT Fecha,Hora from Horario";
        try (Statement stmt= this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                System.out.println(rs.getString( "Fecha")+"|"+
                        rs.getString("Hora")

                );

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }



    }

    public void citadoctores() throws SQLException {
        String sql = "SELECT aC.id_cita AS citas, doctores.nombre AS doctores, paciente.nombre_paciente AS pacientes, horario.Fecha AS HFECHAS, horario.Hora AS Hhorario\n" +
                "FROM agendarCitas aC\n" +
                "INNER JOIN doctores ON doctores.id = aC.id_doctor\n" +
                "INNER JOIN paciente ON paciente.id_paciente =aC.id_paciente\n" +
                "INNER JOIN horario ON horario.id_horario = aC.id_horario";

        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("citas") +"|"+
                        rs.getString("doctores") + "|"+
                        rs.getString("pacientes") +"|"+
                        rs.getString("HFECHAS") +"|"+
                        rs.getString("Hhorario")

                );

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void citas() throws SQLException {
        String sql = "SELECT id_cita,id_horario,id_doctor,id_paciente from agendarCitas";
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id_cita")+"|" +
                        rs.getInt("id_horario") +"|"+
                        rs.getInt("id_doctor") +"|"+
                        rs.getInt("id_paciente")
                );

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}








    //public int deleteCustomer(Usuario usuario) throws SQLException {
      //  String sql = "delete from Customer where CustomerId = ? ";
        //PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        /* prepStmt.setInt(1, customer.getCustomerId());*/
        //prepStmt.execute();
        //return prepStmt.getUpdateCount();
    //}

    //public int updateCustomer(Usuario usuario) throws SQLException {
        //String sql = "update customer set FirstName=?, LastName=?, Company=?, Address=?, City=?, "
              //  + "State=?, Country=?, PostalCode=?, Phone=?, Fax=?, Email=?, SupportRepId=?"
            //    + "where CustomerId = ?";
        //PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        /*prepStmt.setString(1, customer.getFirstName());
        prepStmt.setString(2, customer.getLastName());
        prepStmt.setString(3, customer.getCompany());
        prepStmt.setString(4, customer.getAddress());
        prepStmt.setString(5, customer.getCity());
        prepStmt.setString(6, customer.getState());
        prepStmt.setString(7, customer.getCountry());
        prepStmt.setString(8, customer.getPostalCode());
        prepStmt.setString(9, customer.getPhone());
        prepStmt.setString(10, customer.getFax());
        prepStmt.setString(11, customer.getEmail());
        prepStmt.setInt(12, customer.getSupportRepId());
        prepStmt.setInt(13, customer.getCustomerId());*/
        //prepStmt.execute();
        //return prepStmt.getUpdateCount();
    //}

