package util;

/*
Clase encargada de validar datos ingresados por el usuario en los formularios del sistema
*/

public class Validador {
    
    // Constructor para evitar instancias de la clase
    private Validador(){
    }
    
    // Verifica si el texto esta vacio o contiene solo espacios
    public static boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
    
    //  Verifica si el texto puede convertirse en un número entero
    public static boolean esEntero(String texto) {
        if (estaVacio(texto)) {
            return false;
        }
        
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException errorFormato) {
            return false;
        }
    }
    
    // Verifica si un texto puede convertirse en un número decimal
    public static boolean esDecimal(String texto) {
        if (estaVacio(texto)) {
            return false;
        }
        
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException errorFormato) {
            return false;
        }
    }
    
    // Verifica que el número entero sea mayor o igual a cero
    public static boolean esEnteroNoNegativo(String texto) {
        return esEntero(texto) && Integer.parseInt(texto) >= 0;
    }
    
    // Verifica que el número decimal sea mayor o igual a cero
    public static boolean esDecimalNoNegativo(String texto) {
        return esDecimal(texto) && Double.parseDouble(texto) >= 0;
    }
}
