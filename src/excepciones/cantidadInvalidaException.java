/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author leo
 */
public class cantidadInvalidaException extends Exception{
    private int cantidadIngresada;
    
    //Constructor con mensaje
    public cantidadInvalidaException(String mensaje){
        super(mensaje);
    }
    
    //Constructor con mensaje + cantidad que causo error
    public cantidadInvalidaException(String mensaje, int cantidadIngresada){
        super(mensaje);
        this.cantidadIngresada = cantidadIngresada;
    }
    
    //Obtener cantidad que causo la excepcion
    public int getCantidadIngresada(){
        return cantidadIngresada;
    }
}
