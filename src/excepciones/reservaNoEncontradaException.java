/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author leo
 */
public class reservaNoEncontradaException extends Exception{
    private String codigoBuscado;
    
    //Constructor con mensaje
    public reservaNoEncontradaException(String mensaje){
        super(mensaje);
    }
    
    //Constructor con mensaje + codigo buscado
    public reservaNoEncontradaException(String mensaje, String codigoBuscado){
        super(mensaje);
        this.codigoBuscado = codigoBuscado;
    }
    
    //Obtener codigo buscado
    public String getCodigoBuscado(){
        return codigoBuscado;
    }
}
