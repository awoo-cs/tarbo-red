/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import java.util.ArrayList;
import modelo.cliente;
import modelo.pelicula;
import modelo.reserva;
import utilidades.generadorCodigos;

/**
 *
 * @author leo
 */
public class gestorReservas {
    private ArrayList<reserva> reservas;

    public gestorReservas() {
        this.reservas = new ArrayList<>();
    }
    
    //Metodos
    //Crear una reserva
    public reserva crearReserva(cliente cliente, pelicula pelicula, String horario, int cantidadBoletos, double precioTotal){
        String codigo = generadorCodigos.generarCodigoReserva();
        
        reserva nuevaReserva = new reserva(
                codigo,
                cliente,
                pelicula,
                horario,
                cantidadBoletos,
                precioTotal
        );
        
        reservas.add(nuevaReserva);
        
        return nuevaReserva;
    }
    
    //Listar reservas
    public void listarReservas(){
        if(reservas.isEmpty()){
            System.out.println("\n No hay reservas registradas en el sistema.");
            return;
        }
        
        System.out.println("\n  TOTAL DE RESERVAS: " + reservas.size());
        System.out.println("  " + "=".repeat(100));
        System.out.println("  CODIGO   | CLIENTE              | PELICULA                  | HORARIO | BOLETOS | TOTAL");
        System.out.println("  " + "=".repeat(100));
        
        for (reserva r : reservas) {
            System.out.printf("  %-8s | %-20s | %-25s | %-7s | %-7d | S/ %.2f\n",
                r.getCodigo(),
                truncar(r.getCliente().getNombre(), 20),
                truncar(r.getPelicula().getTitulo(), 25),
                r.getHorario(),
                r.getCantidadBoletos(),
                r.getPrecioTotal()
            );
        }
        
        System.out.println("  " + "=".repeat(100) + "\n");
    }
    
    //Buscar reserva
    public reserva buscarReservaPorCodigo(String codigo){
        for(reserva r : reservas){
            if(r.getCodigo().equalsIgnoreCase(codigo)){
                return r;
            }
        }
        return null;
    }
    
    //Buscar reservas x nombre
    public ArrayList<reserva> buscarReservasPorNombre(String nombre){
        ArrayList<reserva> encontradas = new ArrayList<>();
        
        for(reserva r : reservas){
            if(r.getCliente().getNombre().toLowerCase().contains(nombre.toLowerCase())){
                encontradas.add(r);
            }
        }
        
        return encontradas;
    }
    
    //Cancela una reserva
    public boolean cancelarReserva(String codigo){
        reserva reserva = buscarReservaPorCodigo(codigo);
        
        if(reserva != null){
            reservas.remove(reserva);
            return true;
        }
        
        return false;
    }
    
    //Listar reservas
    public ArrayList<reserva> obtenerReservas(){
        return reservas;
    }
    
    //Nro total de reservas
    public int getCantidadReservas(){
        return reservas.size();
    }
    
    //Verificar reserva existente
    public boolean hayReservas(){
        return !reservas.isEmpty();
    }
    
    //Total de boletos bendidos
    public int getTotalBoletosVendidos(){
        int total = 0;
        for(reserva r : reservas){
            total += r.getCantidadBoletos();
        }
        return total;
    }
    
    //Calculo total de ingreso de reservas
    public double getIngresoTotal(){
        double total = 0;
        for (reserva r : reservas){
            total += r.getPrecioTotal();
        }
        return total;
    }
    
    //Obtener reservas de x pelicula
    public ArrayList<reserva> obtenerReservasPorPelicula(String tituloPelicula){
        ArrayList<reserva> reservasPelicula = new ArrayList<>();
        
        for (reserva r : reservas){
            if(r.getPelicula().getTitulo().equalsIgnoreCase(tituloPelicula)){
                reservasPelicula.add(r);
            }
        }
        
        return reservasPelicula;
    }
    
    //Truncar strings largos
    private String truncar(String texto, int maxLength){
        if(texto.length() <= maxLength){
            return texto;
        }
        return texto.substring(0, maxLength - 3) + "...";
    }
}
