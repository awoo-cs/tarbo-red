/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author leo
 */
public class cliente {
    //Atribs
    private String nombre;
    private int edad;
    private boolean esEstudiante;

    public cliente(String nombre, int edad, boolean esEstudiante) {
        this.nombre = nombre;
        this.edad = edad;
        this.esEstudiante = esEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isEsEstudiante() {
        return esEstudiante;
    }

    public void setEsEstudiante(boolean esEstudiante) {
        this.esEstudiante = esEstudiante;
    }
    
    //Metodos
    //El cliente es o no un ninio (menor de 12)
    public boolean esNino(){
        return edad < 12;
    }
    
    //El cliente es un adulto mayor o no (60 o mas)
    public boolean esAdultoMayor(){
        return edad >= 60;
    }
    
    //Obtiene tipo de cliente para visualizar luego
    public String getTipoCliente(){
        if (esNino()) {
            return "Nino";
        }else if(esAdultoMayor()){
            return "Adulto Mayor";
        }else if(esEstudiante){
            return "Estudiante";
        }else{
            return "Adulto General";
        }
    }

    //Salida del cliente
    public String toString(){
        return String.format(
                "Nombre: %s\n" + 
                "Edad: %d anios\n" + 
                "Tipo: %s",
                nombre, edad, getTipoCliente()
        );
    }
    
    //Salida compacta para mostrar en reservas
    public String toStringCompacto(){
        return String.format("%s (%d anios - %s)", nombre, edad, getTipoCliente());
    }
}
