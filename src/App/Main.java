package App;

import vista.FrmInventario;

/*Clase principal del sistema, encargada de iniciar la aplicación*/

public class Main {
    
    /* Método principal que ejecuta la aplicación*/
    public static void main(String[] args) {
        try {
            new FrmInventario().setVisible(true);
            System.out.println("Ventana iniciada.");
        } catch (Exception errorAplicacion) {
            errorAplicacion.printStackTrace();
        }
    }
}
