package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leo
 */
public class pelicula {
    //Atribs
    private String titulo;
    private String genero;
    private int duracion;
    private String[] horarios;
    private double precioBase;

    public pelicula(String titulo, String genero, int duracion, String[] horarios, double precioBase) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.horarios = horarios;
        this.precioBase = precioBase;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String[] getHorarios() {
        return horarios;
    }

    public void setHorarios(String[] horarios) {
        this.horarios = horarios;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }
    
    //Metodos
    //Horarios formateados
    public String getHorariosFormateados(){
        return String.join(", ", horarios);
    }
    
    //Verifica un horario especifico para X pelicula
    public boolean tieneHorario(String horario){
        for (String h : horarios){
            if(h.equalsIgnoreCase(horario)){
                return true;
            }
        }
        return false;
    }
    
    //Salida formateada en CLI
    @Override
    public String toString(){
        return String.format(
                "Titulo: %s\n" + 
                "Genero: %s\n" + 
                "Duracion: %s\n" +
                "Horarios: %s\n" + 
                "Precio: S/ %.2f",
                titulo, genero, duracion, getHorariosFormateados(), precioBase);
    }
    
    //Salida compacta para listas
    public String toStringCompacto(){
        return String.format("%-30s | %-12s | %d min | S/ %.2f", 
                titulo, genero, duracion, precioBase);
    }
    
}
