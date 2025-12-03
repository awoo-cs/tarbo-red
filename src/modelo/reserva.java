/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author leo
 */
public class reserva {
    //Atribs
    private String codigo;
    private cliente cliente;
    private pelicula pelicula;
    private String horario;
    private int cantidadBoletos;
    private double precioTotal;
    private LocalDateTime fechaReserva;

    public reserva(String codigo, cliente cliente, pelicula pelicula, String horario, int cantidadBoletos, double precioTotal) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.pelicula = pelicula;
        this.horario = horario;
        this.cantidadBoletos = cantidadBoletos;
        this.precioTotal = precioTotal;
        this.fechaReserva = LocalDateTime.now();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getCantidadBoletos() {
        return cantidadBoletos;
    }

    public void setCantidadBoletos(int cantidadBoletos) {
        this.cantidadBoletos = cantidadBoletos;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    
    //Format fecha de reserva
    public String getFechaFormateada(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fechaReserva.format(formatter);
    }
    
    //Precio x boleto
    public double getPrecioPorBoleto(){
        return precioTotal/cantidadBoletos;
    }
    
    //Salida completa de la reserva
    public String toString(){
        return String.format(
           "╔════════════════════════════════════════════════════════════╗\n" +
            "║              COMPROBANTE DE RESERVA                        ║\n" +
            "╠════════════════════════════════════════════════════════════╣\n" +
            "║ Código: %-50s ║\n" +
            "║ Fecha: %-51s ║\n" +
            "╠════════════════════════════════════════════════════════════╣\n" +
            "║ Cliente: %-49s ║\n" +
            "║ Edad: %-52d ║\n" +
            "║ Tipo: %-52s ║\n" +
            "╠════════════════════════════════════════════════════════════╣\n" +
            "║ Película: %-48s ║\n" +
            "║ Horario: %-49s ║\n" +
            "║ Cantidad: %-48d ║\n" +
            "╠════════════════════════════════════════════════════════════╣\n" +
            "║ Precio por boleto: S/ %-35.2f ║\n" +
            "║ TOTAL A PAGAR: S/ %-39.2f ║\n" +
            "╚════════════════════════════════════════════════════════════╝",
                codigo,
                getFechaFormateada(),
                cliente.getNombre(),
                cliente.getEdad(),
                cliente.getTipoCliente(),
                pelicula.getTitulo(),
                horario,
                cantidadBoletos,
                getPrecioPorBoleto(),
                precioTotal
        );
    }
    
    //Visual para listas de reservas
    public String toStringCompacto(){
     return String.format("%-10s | %-20s | %-25s | %s | %d boletos | S/ %.2f", 
             codigo,
             cliente.getNombre(),
             pelicula.getTitulo(),
             horario,
             cantidadBoletos,
             precioTotal
        );
    }
    
    //Identificador de funcion
    public String getClaveFuncion(){
        return pelicula.getTitulo() + "-" + horario;
    }
}
