/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leo
 */
import modelo.pelicula;
import servicio.gestorAforo;
import servicio.gestorCartelera;


public class testAforo {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("             PRUEBA DE SISTEMA DE CONTROL DE AFORO");
        System.out.println("=".repeat(80));
        
        gestorCartelera cartelera = new gestorCartelera();
        gestorAforo aforo = new gestorAforo();
        
        aforo.inicializarAforo(cartelera.obtenerPeliculas());
        
        System.out.println("\n1. ESTADO INICIAL (todo vacio):");
        aforo.mostrarReporteAforo();
        
        System.out.println("\n2. SIMULANDO RESERVAS...\n");
        
        aforo.registrarReserva("Oppenheimer", "14:00", 30);
        System.out.println("  - Reservados 30 boletos para Oppenheimer 14:00");
        
        aforo.registrarReserva("Barbie", "13:00", 45);
        System.out.println("  - Reservados 45 boletos para Barbie 13:00");
        
        aforo.registrarReserva("Dune: Parte Dos", "15:00", 50);
        System.out.println("  - Reservados 50 boletos para Dune 15:00 (LLENO)");
        
        aforo.registrarReserva("Kung Fu Panda 4", "12:00", 10);
        System.out.println("  - Reservados 10 boletos para Kung Fu Panda 12:00");
        
        System.out.println("\n3. ESTADO DESPUES DE RESERVAS:");
        aforo.mostrarReporteAforo();
        
        System.out.println("\n4. PRUEBA DE VERIFICACION:");
        System.out.println("  - Hay 10 asientos para Oppenheimer 14:00? " + 
            aforo.hayDisponibilidad("Oppenheimer", "14:00", 10));
        System.out.println("  - Hay 30 asientos para Oppenheimer 14:00? " + 
            aforo.hayDisponibilidad("Oppenheimer", "14:00", 30));
        System.out.println("  - Hay 1 asiento para Dune 15:00 (lleno)? " + 
            aforo.hayDisponibilidad("Dune: Parte Dos", "15:00", 1));
        
        System.out.println("\n5. LIBERANDO 20 ASIENTOS DE BARBIE 13:00...");
        aforo.liberarReserva("Barbie", "13:00", 20);
        
        System.out.println("\n6. ESTADO FINAL:");
        aforo.mostrarReporteAforo();
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                    FIN DE PRUEBAS");
        System.out.println("=".repeat(80));
    }
}
