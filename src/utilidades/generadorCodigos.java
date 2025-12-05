/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

/**
 *
 * @author leo
 */
public class generadorCodigos {
    private static int contador = 0;
    
    //Genera cod unico de reserva
    public static String generarCodigoReserva(){
        contador++;
        return String.format("RES%03d", contador);
    }
    
    //Reiniciar contador
    public static void reiniciarContador(){
        contador = 0;
    }
    
    //Obtener valor del contador
    public static int getContador(){
        return contador;
    }
}
