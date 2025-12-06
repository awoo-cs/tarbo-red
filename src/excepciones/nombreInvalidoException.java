/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author leo
 */
public class nombreInvalidoException extends Exception{
    private String nombreIngresado;
    
    //Constructor con mensaje
    public nombreInvalidoException(String mensaje){
        super(mensaje);
    }
    
    //Constructor con mensaje + nombre que causo error
    public nombreInvalidoException(String mensaje, String nombreIngresado){
        super(mensaje);
        this.nombreIngresado = nombreIngresado;
    }
    
    //Nombre que causa excepcion
    public String getNombreIngresado(){
        return nombreIngresado;
    }
}
