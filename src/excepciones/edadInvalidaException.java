/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author leo
 */
public class edadInvalidaException extends Exception{
    private int edadIngresada;
    
    //Constructor con mensaje
    public edadInvalidaException(String mensaje) {
        super(mensaje);
    }
    
    //Constructor con mensaje + edad que causo el error
    public edadInvalidaException(String mensaje, int edadIngresada){
        super(mensaje);
        this.edadIngresada = edadIngresada;
    }
    
    //Obtener edad que causa excepcion
    public int getEdadIngresada(){
        return edadIngresada;
    }
}
