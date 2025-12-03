
import modelo.cliente;
import servicio.calculadoraTarifas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leo
 */
public class testTarifas {
    public static void main(String[] args){
        System.out.println("=".repeat(60));
        System.out.println("        PRUEBAS DE CALCULADORA DE TARIFAS");
        System.out.println("=".repeat(60));
        
        double precioBase = 20.00;
        
        // Caso 1: Adulto normal, día normal
        System.out.println("\nCASO 1: Adulto normal, dia normal");
        cliente adulto = new cliente("Juan Perez", 30, false);
        System.out.println(calculadoraTarifas.obtenerDesglose(precioBase, adulto, false, false, 1));
        
        // Caso 2: Niño, día normal
        System.out.println("\nCASO 2: Nino (10 anios), dia normal");
        cliente nino = new cliente("Maria Lopez", 10, false);
        System.out.println(calculadoraTarifas.obtenerDesglose(precioBase, nino, false, false, 1));
        
        // Caso 3: Estudiante, miércoles
        System.out.println("\nCASO 3: Estudiante adulto, miercoles");
        cliente estudiante = new cliente("Carlos Ruiz", 22, true);
        System.out.println(calculadoraTarifas.obtenerDesglose(precioBase, estudiante, true, false, 1));
        
        // Caso 4: Niño estudiante, miércoles (descuento triple)
        System.out.println("\nCASO 4: Nino estudiante, miercoles (DESCUENTO MAXIMO)");
        cliente ninoEstudiante = new cliente("Ana Torres", 11, true);
        System.out.println(calculadoraTarifas.obtenerDesglose(precioBase, ninoEstudiante, true, false, 2));
        
        // Caso 5: Adulto mayor, miércoles
        System.out.println("\nCASO 5: Adulto mayor, miercoles");
        cliente adultoMayor = new cliente("Pedro Gomez", 65, false);
        System.out.println(calculadoraTarifas.obtenerDesglose(precioBase, adultoMayor, true, false, 1));
        
        // Caso 6: Adulto con estreno (recargo)
        System.out.println("\nCASO 6: Adulto normal, ESTRENO (con recargo)");
        System.out.println(calculadoraTarifas.obtenerDesglose(precioBase, adulto, false, true, 1));
    }
}
