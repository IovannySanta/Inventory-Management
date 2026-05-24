package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
Clase encargada de gestionar la conexion con la base de datos MySQL en conjunto con la información establecida.
*/

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_management?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD =  "";
    
    // Establece una conexión con la base de datos
    public static Connection connect() throws SQLException {
        try {
            // Carga de driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Retorno de conexión establecida
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException errorDriver) {
            // Lanza exepción si el driver no fue encontrado.
            throw new SQLException("Driver MySQL no fue encontrado.", errorDriver);
        }
    }
}
