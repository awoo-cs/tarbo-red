/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import modelo.cliente;

/**
 *
 * @author leo
 */
public class calculadoraTarifas {
    //Atribs constantes
    public static final double DESC_NINO = 0.50;
    public static final double DESC_ESTUDIANTE = 0.20;
    public static final double DESC_ADULTO_MAYOR = 0.30;
    public static final double DESC_MIERCOLES = 0.15;
    public static final double RECARGO_ESTRENO = 0.10;
    
    //Calcular precio final aplicando descuentos segun corresponda
    public static double calcularPrecio(double precioBase, cliente cliente, boolean esMiercoles, boolean esEstreno, int cantidadBoletos){
        double precioConDescuentos = precioBase;
        
        //Aplicar desc por edad/tipo cliente
        if (cliente.esNino()) {
            precioConDescuentos -= precioBase * DESC_NINO;
        }else if(cliente.esAdultoMayor()){
            precioConDescuentos -= precioBase * DESC_ADULTO_MAYOR;
        }
        
        //Aplicar desc de estudiante (acumulable)
        if (cliente.isEsEstudiante() && !cliente.esNino()) {
            //No se aplica desc si ya posee el desc de nino
            precioConDescuentos -= precioBase * DESC_ESTUDIANTE;
        }
        
        //Aplicar descuento de miercoles (acumulable)
        if (esMiercoles) {
            precioConDescuentos -= precioBase * DESC_MIERCOLES;
        }
        
        //Aplicar recargo por estreno
        if (esEstreno) {
            precioConDescuentos += precioBase * RECARGO_ESTRENO;
        }
        
        //Verificar precio negativo
        if (precioConDescuentos < 0) {
            precioConDescuentos = 0;
        }
        
        //Multiplicar x cant de boletos
        return precioConDescuentos * cantidadBoletos;
    }
    
    //Detalla desc aplicados
    public static String obtenerDesglose(double precioBase, cliente cliente, boolean esMiercoles, boolean esEstreno, int cantidadBoletos){
        StringBuilder desglose = new StringBuilder();
        
        desglose.append("\n  DESGLOSE DE PRECIO:\n");
        desglose.append("  " + "=".repeat(50) + "\n");
        desglose.append(String.format("  Precio base:                S/ %.2f x %d\n", 
                                     precioBase, cantidadBoletos));
        desglose.append(String.format("  Subtotal:                   S/ %.2f\n", 
                                     precioBase * cantidadBoletos));
        desglose.append("  " + "-".repeat(50) + "\n");
        
        double totalDescuentos = 0;
        double totalRecargos = 0;
        
        //Descuento x edad
        if (cliente.esNino()) {
            double descuento = precioBase * DESC_NINO * cantidadBoletos;
            desglose.append(String.format("  Descuento nino (50%%):      - S/ %.2f\n", descuento));
            totalDescuentos += descuento;
        }else if(cliente.esAdultoMayor()){
            double descuento = precioBase * DESC_ADULTO_MAYOR * cantidadBoletos;
            desglose.append(String.format("  Descuento adulto mayor (30%%): - S/ %.2f\n", descuento));
            totalDescuentos += descuento;
        }
        
        //Descuento x estudiante
        if(cliente.isEsEstudiante() && !cliente.esNino()){
            double descuento = precioBase * DESC_ESTUDIANTE * cantidadBoletos;
            desglose.append(String.format("  Descuento estudiante (20%%): - S/ %.2f\n", descuento));
            totalDescuentos += descuento;
        }
        
        //Descuento x miercoles
        if (esMiercoles) {
            double descuento = precioBase * DESC_MIERCOLES * cantidadBoletos;
            desglose.append(String.format("  Descuento miercoles (15%%): - S/ %.2f\n", descuento));
            totalDescuentos += descuento;
        }
        
        //Recargo por estreno
        if (esEstreno) {
            double recargo = precioBase * RECARGO_ESTRENO * cantidadBoletos;
            desglose.append(String.format("  Recargo estreno (10%%):     + S/ %.2f\n", recargo));
            totalRecargos += recargo;
        }
        
        desglose.append("  " + "-".repeat(50) + "\n");
        
        if (totalDescuentos > 0) {
            desglose.append(String.format("  Total descuentos:           - S/ %.2f\n", totalDescuentos));
        }
        if (totalRecargos > 0) {
            desglose.append(String.format("  Total recargos:             + S/ %.2f\n", totalRecargos));
        }
        
        double precioFinal = calcularPrecio(precioBase, cliente, esMiercoles, esEstreno, cantidadBoletos);
        double ahorro = (precioBase * cantidadBoletos) + totalRecargos - totalDescuentos;
        
        desglose.append("  " + "=".repeat(50) + "\n");
        desglose.append(String.format("  PRECIO FINAL:               S/ %.2f\n", precioFinal));
        
        if(ahorro > 0.01){
            desglose.append(String.format("  Usted ahorra:               S/ %.2f\n", ahorro));
        }
        
        desglose.append("  " + "=".repeat(50) + "\n");
        
        return desglose.toString();
    }
    
    //Porcentaje total del descuento aplicado
    public static double calcularPorcentajeDescuento(cliente cliente, boolean esMiercoles){
        double porcentaje = 0;
        
        if (cliente.esNino()) {
            porcentaje += DESC_NINO * 100;
        }else if(cliente.esAdultoMayor()){
            porcentaje += DESC_ADULTO_MAYOR * 100;
        }
        
        if (cliente.isEsEstudiante() && !cliente.esNino()) {
            porcentaje += DESC_ESTUDIANTE * 100;
        }
        
        if(esMiercoles){
            porcentaje += DESC_MIERCOLES * 100;
        }
        
        return porcentaje;
    }
    
    //Mensaje descriptivo de los desc aplicables
    public static String obtenerDescuentosAplicables(cliente cliente, boolean esMiercoles){
        StringBuilder mensaje = new StringBuilder("  Descuentos aplicables: ");
        
        boolean hayDescuentos = false;
        
        if(cliente.esNino()){
            mensaje.append("Nino (50%)");
            hayDescuentos = true;
        }else if(cliente.esAdultoMayor()){
            mensaje.append("Adulto Mayor (30%)");
            hayDescuentos = true;
        }
        
        if(cliente.isEsEstudiante() && !cliente.esNino()){
            if(hayDescuentos) mensaje.append(" + ");
            mensaje.append("Estudiante (20%)");
            hayDescuentos = true;
        }
        
        if(esMiercoles){
            if (hayDescuentos) {
                mensaje.append(" + ");
                mensaje.append("Miercoles (15%)");
                hayDescuentos = true;
            }
        }
        
        if(!hayDescuentos){
            mensaje.append("Ninguno");
        }
        
        return mensaje.toString();
    }
}
