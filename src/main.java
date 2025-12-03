
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leo
 */
public class main {
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args){
        mostrarBienvenida();
        
        boolean salir = false;
        
        while (!salir) {            
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    verCartelera();
                    break;
                case 2:
                    hacerReserva();
                    break;
                case 3:
                    verMisReservas();
                    break;
                case 4:
                    buscarReserva();
                    break;
                case 5:
                    cancelarReserva();
                    break;
                case 6:
                    verEstadisticas();
                    break;
                case 0:
                    salir = true;
                    mostrarDespedida();
                    break;
                default:
                    System.out.println("\n Opcion invalida. Por favor intente nuevamente.");
            }
            
            if(!salir){
                pausar();
            }
        }
        
        sc.close();
    }
    
    //Metodos
    //Mensaje de bienvenida
    private static void mostrarBienvenida(){
        limpiarConsola();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║                    TARBO-RED v1.0                          ║");
        System.out.println("║         Sistema de Gestion de Reservas de Cine            ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        pausar();
    }
    
    //Menu principal
    private static void mostrarMenuPrincipal(){
        limpiarConsola();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                      MENU PRINCIPAL                        ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Ver Cartelera de Peliculas                             ║");
        System.out.println("║  2. Hacer una Reserva                                      ║");
        System.out.println("║  3. Ver Mis Reservas                                       ║");
        System.out.println("║  4. Buscar Reserva                                         ║");
        System.out.println("║  5. Cancelar Reserva                                       ║");
        System.out.println("║  6. Ver Estadisticas                                       ║");
        System.out.println("║  0. Salir del Sistema                                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.print("\n➤ Seleccione una opcion: ");
    }
    
    //Leer opcion validada
    private static int leerOpcion(){
        try{
            return sc.nextInt();
        }catch (Exception e){
            sc.nextLine();
            return -1;
        }finally{
            sc.nextLine();
        }
    }
    
    //Placeholder para visualizar la cartelera
    private static void verCartelera(){
        limpiarConsola();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  CARTELERA DE PELICULAS                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n⏳ Funcion en desarrollo...");
    }
    
    //Placeholder para hacer reserva
    private static void hacerReserva(){
        limpiarConsola();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                     NUEVA RESERVA                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n⏳ Funcion en desarrollo...");
    }
    
    //Placeholder para ver reservas
    private static void verMisReservas(){
        limpiarConsola();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    MIS RESERVAS                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n⏳ Funcion en desarrollo...");
    }
    
    //Placeholder para buscar reserva
    private static void buscarReserva(){
        limpiarConsola();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   BUSCAR RESERVA                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n⏳ Funcion en desarrollo...");
    }
    
    //Placeholder para cancelar reserva
    private static void cancelarReserva() {
        limpiarConsola();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  CANCELAR RESERVA                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n⏳ Funcion en desarrollo...");
    }
    
    //Placeholder para estadisticas
    private static void verEstadisticas() {
        limpiarConsola();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ESTADÍSTICAS                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n⏳ Funcion en desarrollo...");
    }
    
    //Mensaje de despedida
    private static void mostrarDespedida() {
        limpiarConsola();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║         ¡Gracias por usar TARBO-RED!                      ║");
        System.out.println("║         Esperamos verlo pronto.                            ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    //Interrumpe la ejecucion hasta accion del usuario
    private static void pausar(){
        System.out.println("\n[Presione ENTER para continuar...]");
        sc.nextLine();
    }
    
    //CLS
    private static void limpiarConsola(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
}
