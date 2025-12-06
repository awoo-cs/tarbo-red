/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

/**
 *
 * @author leo
 */
public class validador {
    //Validaciones
    //Validar edad
    public static boolean validarEdad(int edad){
        return edad >= 0 && edad <= 120;
    }
    
    //Validar solo letras y espacios / evitar campos vacios
    public static boolean validarNombre(String nombre){
        if(nombre == null || nombre.trim().isEmpty()){
            return false;
        }
        
        String nombreLimpio = nombre.trim();
        return nombreLimpio.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && nombreLimpio.length() >= 3;
    }
    
    //Validar cantidad de boletos 1-10
    public static boolean validarCantidad(int cantidad){
        return cantidad >= 1 && cantidad <= 10;
    }
    
    //Validar opciones del menu
    public static boolean validarOpcionMenu(int opcion, int min, int max){
        return opcion >= min && opcion <= max;
    }
    
    //Validar que un indice se encuentre dentro del rango de x lista
    public static boolean validarIndice(int indice, int tamanioLista){
        return indice >= 1 && indice <= tamanioLista;
    }
    
    //Validar respuesta (S/N)
    public static boolean validarRespuestaSN(String respuesta){
        if(respuesta == null || respuesta.trim().isEmpty()){
            return false;
        }
        
        String respuestaLimpia = respuesta.trim().toUpperCase();
        return respuestaLimpia.equals("S") || respuestaLimpia.equals("N");
    }
    
    //Convierte una respuesta S/N a boolean
    public static boolean convertirRespuestaSN(String respuesta){
        return respuesta.trim().toUpperCase().equals("S");
    }
    
    //Mensaje de error para edad invalida
    public static String getMensajeErrorEdad(int edad){
        if(edad < 0){
            return "La edad no puede ser negativa.";
        }else if(edad > 120){
            return "La edad ingresada es demasiado alta. (Max. 120 anios)";
        }
        return "Edad invalida.";
    }
    
    //Mensaje de error para nombre invalido
    public static String getMensajeErrorNombre(String nombre){
        if(nombre == null || nombre.trim().isEmpty()){
            return "El nomnbre no puede estar vacio.";
        }else if(nombre.trim().length() < 3){
            return "El nombre debe tener al menos 3 caracteres";
        }else{
            return "El nombre solo puede contener letras y espacios.";
        }
    }
    
    //Mensaje de error para cantidad invalida
    public static String getMensajeErrorCantidad(int cantidad){
        if(cantidad < 1){
            return "Debe reservar al menos 1 boleto.";
        }else if(cantidad > 10){
            return "No se pueden reservar mas de 10 boletos por transaccion.";
        }
        return "Cantidad invalida.";
    }
}

