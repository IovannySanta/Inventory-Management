package util;

public class Validador {
    
    private Validador(){
    }
    
    public static boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
    
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
    
    public static boolean esEnteroNoNegativo(String texto) {
        return esEntero(texto) && Integer.parseInt(texto) >= 0;
    }
    
    public static boolean esDecimalNoNegativo(String texto) {
        return esDecimal(texto) && Double.parseDouble(texto) >= 0;
    }
}
