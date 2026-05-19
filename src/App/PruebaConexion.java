package App;

import conexion.ConexionBD;
import java.sql.Connection;

/*
Clase principal encargada de probar la conexion con la base de datos.
Ejecuta una conexion de prueba y muestra si fue exitosa en caso contrario, el error lanzado.
*/

public class PruebaConexion {
    
/*
Metodo que intenta establecer la conexion a la base de datos y mostrarsu status.
*/
    
    public static void main(String[] args) {
        try (Connection connection = ConexionBD.connect()) {
            System.out.println("Connecting to the database");
            
            // verificación de conexión establecida.
            if (connection != null) {
                System.out.println("SUCCESS: Successful connection to the database");
            }
            
        } catch (Exception e) {
            // Mostramos información detallada del error encontrado.
            e.printStackTrace();
        }
    }
}
