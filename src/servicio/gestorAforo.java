/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import java.util.HashMap;
import java.util.List;
import modelo.pelicula;

/**
 *
 * @author leo
 */
public class gestorAforo {
    //Capacidad maxima x funcion para sala estandar
    public static final int CAPACIDAD_MAXIMA = 50;
    
    //HM: clave = "pelicula - horario", valor = asientos ocupados
    private HashMap<String, Integer> aforoPorFuncion;
    
    public gestorAforo(){
        this.aforoPorFuncion = new HashMap<>();
    }
    
    //Inicializar aforo para peliculas y horarios
    public void inicializarAforo(List<pelicula> peliculas){
        aforoPorFuncion.clear();
        
        for(pelicula pelicula : peliculas){
            for(String horario : pelicula.getHorarios()){
                String clave = generarClave(pelicula.getTitulo(), horario);
                aforoPorFuncion.put(clave, 0);
            }
        }
    }
    
    //Genera clave unica para identificar una funcion
    private String generarClave(String tituloPelicula, String horario){
        return tituloPelicula + "-" + horario;
    }
    
    //Verificar si hay disponibilidad para x cantidad de boletos
    public boolean hayDisponibilidad(String tituloPelicula, String horario, int cantidad){
        String clave = generarClave(tituloPelicula, horario);
        
        if(!aforoPorFuncion.containsKey(clave)){
            aforoPorFuncion.put(clave, 0);
        }
        
        int ocupados = aforoPorFuncion.get(clave);
        int disponibles = CAPACIDAD_MAXIMA - ocupados;
        
        return disponibles >= cantidad;
    }
    
    //Registrar reserva, rellenando asientos
    public boolean registrarReserva(String tituloPelicula, String horario, int cantidad){
        String clave = generarClave(tituloPelicula, horario);
        
        if(!hayDisponibilidad(tituloPelicula, horario, cantidad)){
            return false;
        }
        
        int ocupadosActuales = aforoPorFuncion.getOrDefault(clave, 0);
        aforoPorFuncion.put(clave, ocupadosActuales + cantidad);
        
        return true;
    }
    
    //Liberar asientos al cancelar una reserva
    public void liberarReserva(String tituloPelicula, String horario, int cantidad){
        String clave = generarClave(tituloPelicula, horario);
        
        if(aforoPorFuncion.containsKey(clave)){
            int ocupadosActuales = aforoPorFuncion.get(clave);
            int nuevosOcupados = Math.max(0, ocupadosActuales - cantidad);
            aforoPorFuncion.put(clave, nuevosOcupados);
        }
    }
    
    //Obtener la cantidad de asientos disponibles para una funcion
    public int getAsientosDisponibles(String tituloPelicula, String horario){
        String clave = generarClave(tituloPelicula, horario);
        int ocupados = aforoPorFuncion.getOrDefault(clave, 0);
        return CAPACIDAD_MAXIMA - ocupados;
    }
    
    //Obtener la cantidad de asientos ocupados para una funcion
    public int getAsientosOcupados(String tituloPelicula, String horario){
        String clave = generarClave(tituloPelicula, horario);
        return aforoPorFuncion.getOrDefault(clave, 0);
    }
    
    //Calcular porcetanje de ocupacion de x funcion
    public double getPorcentajeOcupacion(String tituloPelicula, String horario){
        int ocupados = getAsientosOcupados(tituloPelicula, horario);
        return (ocupados * 100.0) / CAPACIDAD_MAXIMA;
    }
    
    //Verificar si una funcion esta llena
    public boolean estaLlena(String tituloPelicula, String horario){
        return getAsientosDisponibles(tituloPelicula, horario) == 0;
    }
    
    //Obtener un mensaje descriptivo del estado del aforo
    public String getEstadoAforo(String tituloPelicula, String horario){
        int disponibles = getAsientosDisponibles(tituloPelicula, horario);
        int ocupados = getAsientosOcupados(tituloPelicula, horario);
        double porcentaje = getPorcentajeOcupacion(tituloPelicula, horario);
        
        String estado;
        if(disponibles == 0){
            estado = "LLENO";
        }else if(disponibles <= 5){
            estado = "ULTIMAS ENTRADAS";
        }else if(porcentaje >= 70){
            estado = "ALTA DEMANDA";
        }else if(porcentaje >= 40){
            estado = "DISPONIBLE";
        }else{
            estado = "AMPLIA DISPONIBILIDAD";
        }
        
        return String.format("%s (%d/%d asientos", estado, ocupados, CAPACIDAD_MAXIMA);
    }
    
    //Reinicia el aforo de todas las funciones (util)
    public void reiniciarAforo(){
        for(String clave : aforoPorFuncion.keySet()){
            aforoPorFuncion.put(clave, 0);
        }
    }
    
    //Obtener el total de asientos vendidos en todo el cine
    public int getTotalAsientosVendidos(){
        int total = 0;
        for(int ocupados : aforoPorFuncion.values()){
            total += ocupados;
        }
        return total;
    }
    
    //Obtener la funcion mas popular
    public String getFuncionMasPopular(){
        String funcionMasPopular = "Ninguna";
        int maxOcupados = 0;
        
        for(String clave : aforoPorFuncion.keySet()){
            int ocupados = aforoPorFuncion.get(clave);
            if(ocupados > maxOcupados){
                maxOcupados = ocupados;
                funcionMasPopular = clave.replace("-", " - ");
            }
        }
        
        return funcionMasPopular + " (" + maxOcupados + " asientos)";
    }
    
    //Calcular promedio de ocupacion de todas las funciones
    public double getOcupacionPromedio(){
        if(aforoPorFuncion.isEmpty()){
            return 0;
        }
        
        int totalOcupados = getTotalAsientosVendidos();
        int totalFunciones = aforoPorFuncion.size();
        int capacidadTotal = totalFunciones * CAPACIDAD_MAXIMA;
        
        return (totalOcupados * 100.0) / capacidadTotal;
    }
    
    //Generar reporte detallado del aforo de todas las funciones
    public void mostrarReporteAforo() {
        System.out.println("\n  REPORTE DE AFORO POR FUNCION");
        System.out.println("  " + "=".repeat(80));
        System.out.println("  PELICULA - HORARIO                           | OCUPADOS | DISPONIBLES | %");
        System.out.println("  " + "=".repeat(80));
        
        for (String clave : aforoPorFuncion.keySet()) {
            String[] partes = clave.split("-");
            String titulo = partes[0];
            String horario = partes[1];
            
            int ocupados = getAsientosOcupados(titulo, horario);
            int disponibles = getAsientosDisponibles(titulo, horario);
            double porcentaje = getPorcentajeOcupacion(titulo, horario);
            
            String claveFormateada = String.format("%-44s", titulo + " - " + horario);
            
            System.out.printf("  %s | %-8d | %-11d | %.1f%%\n",
                claveFormateada, ocupados, disponibles, porcentaje);
        }
        
        System.out.println("  " + "=".repeat(80));
        System.out.printf("  Ocupacion promedio: %.1f%%\n", getOcupacionPromedio());
        System.out.println("  Funcion mas popular: " + getFuncionMasPopular());
        System.out.println();
    }
}
