
import java.io.PrintStream;
import java.util.Scanner;
import modelo.cliente;
import modelo.pelicula;
import modelo.reserva;
import servicio.calculadoraTarifas;
import servicio.gestorCartelera;
import servicio.gestorReservas;

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
    private static gestorCartelera gestorCartelera = new gestorCartelera();
    private static gestorReservas gestorReservas = new gestorReservas();
    
    public static void main(String[] args){
        try{
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        }catch (Exception e){
            
        }
        
        
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
        System.out.println("\n\n");
        System.out.println("  ================================================");
        System.out.println("                                                  ");
        System.out.println("                  TARBO-RED v1.0                  ");
        System.out.println("       Sistema de Gestion de Reservas de Cine    ");
        System.out.println("                                                  ");
        System.out.println("  ================================================");
        System.out.println("\n");
        pausar();
    }
    
    //Menu principal
    private static void mostrarMenuPrincipal(){
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("             MENU PRINCIPAL");
        System.out.println("  ==========================================\n");
        System.out.println("     [1] Ver Cartelera de Peliculas");
        System.out.println("     [2] Hacer una Reserva");
        System.out.println("     [3] Ver Mis Reservas");
        System.out.println("     [4] Buscar Reserva");
        System.out.println("     [5] Cancelar Reserva");
        System.out.println("     [6] Ver Estadisticas");
        System.out.println("     [0] Salir del Sistema\n");
        System.out.println("  ==========================================");
        System.out.print("\n  Opcion: ");
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
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("          CARTELERA DE PELICULAS");
        System.out.println("  ==========================================\n");
        
        gestorCartelera.mostrarCartelera();
        
        System.out.println("\n Nota: Los precios mostrados son base.");
        System.out.println("    Se aplicaran descuentos segun el perfil del cliente.");
    }
    
    //Placeholder para hacer reserva
    private static void hacerReserva(){
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("              NUEVA RESERVA");
        System.out.println("  ==========================================\n");
        
        gestorCartelera.mostrarCarteleraCompacta();
        
        System.out.println("    Selecciones el numero de pelicula (0 para cancelar): ");
        int indicePelicula = leerOpcion();
        
        if(indicePelicula == 0){
            System.out.println("\n  [!] Reserva cancelada.\n");
        }
        
        if(!gestorCartelera.indiceValido(indicePelicula)){
            System.out.println("\n  [X] Pelicula invalida.\n");
            return;
        }
        
        pelicula peliculaSeleccionada = gestorCartelera.obtenerPeliculaPorIndice(indicePelicula - 1);
        
        System.out.println();
        gestorCartelera.mostrarHorarios(peliculaSeleccionada);
        System.out.println("\n Seleccione el numero de horario: ");
        int indiceHorario = leerOpcion();
        
        String[] horarios = peliculaSeleccionada.getHorarios();
        if(indiceHorario < 1 || indiceHorario > horarios.length){
            System.out.println("\n [X] Horario invalido.\n");
            return;
        }
        
        String horarioSeleccionado = horarios[indiceHorario - 1];
        
        System.out.println("\n  " + "-".repeat(50));
        System.out.println("  DATOS DEL CLIENTE");
        System.out.println("  " + "-".repeat(50));
        
        System.out.println(" Nombre completo ");
        String nombre = sc.nextLine().trim();
        
        if(nombre.isEmpty()){
            System.out.println("\n [X] El nombre no puede estar vacio.\n");
            return;
        }
        
        System.out.println(" Edad: ");
        int edad = leerOpcion();
        
        if(edad < 0 || edad > 120){
            System.out.println("\n [X] Edad invalida.\n");
            return;
        }
        
        System.out.println(" Es estudiante? (S/N)");
        String respuestaEstudiante = sc.nextLine().trim().toUpperCase();
        boolean esEstudiante = respuestaEstudiante.equals("S");
        
        System.out.println(" Cantidad de boletos: ");
        int cantidad = leerOpcion();
        
        if(cantidad < 1 || cantidad > 10){
            System.out.println("\n [X] Cantidad invalida (1-10 boletos).\n");
            return;
        }
        
        System.out.print("  La funcion es en miercoles? (S/N): ");
        String respuestaMiercoles = sc.nextLine().trim().toUpperCase();
        boolean esMiercoles = respuestaMiercoles.equals("S");

        System.out.print("  Es una pelicula en estreno? (S/N): ");
        String respuestaEstreno = sc.nextLine().trim().toUpperCase();
        boolean esEstreno = respuestaEstreno.equals("S");

        cliente cliente = new cliente(nombre, edad, esEstudiante);

        double precioBase = peliculaSeleccionada.getPrecioBase();
        double precioTotal = calculadoraTarifas.calcularPrecio(
            precioBase, cliente, esMiercoles, esEstreno, cantidad
        );

        System.out.println(calculadoraTarifas.obtenerDesglose(
            precioBase, cliente, esMiercoles, esEstreno, cantidad
        ));

        System.out.print("  Confirmar reserva? (S/N): ");
        String confirmacion = sc.nextLine().trim().toUpperCase();

        if (!confirmacion.equals("S")) {
            System.out.println("\n  [!] Reserva cancelada.\n");
            return;
        }

        reserva nuevaReserva = gestorReservas.crearReserva(
            cliente,
            peliculaSeleccionada,
            horarioSeleccionado,
            cantidad,
            precioTotal
        );

        limpiarConsola();
        System.out.println("\n  [✓] RESERVA REALIZADA CON EXITO!\n");
        System.out.println(nuevaReserva.toString());
        System.out.println("\n  Guarde su codigo de reserva: " + nuevaReserva.getCodigo());
        System.out.println("  Lo necesitara para consultar o cancelar su reserva.\n");
    }
    
    //Placeholder para ver reservas
    private static void verMisReservas(){
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("               MIS RESERVAS");
        System.out.println("  ==========================================\n");
        
        gestorReservas.listarReservas();
        
        if(gestorReservas.hayReservas()){
            System.out.println("  Resumen:");
        System.out.println("  - Total de reservas: " + gestorReservas.getCantidadReservas());
        System.out.println("  - Total de boletos: " + gestorReservas.getTotalBoletosVendidos());
        System.out.println("  - Ingresos totales: S/ " + 
            String.format("%.2f", gestorReservas.getIngresoTotal()));
        System.out.println();
        }
    }
    
    //Placeholder para buscar reserva
    private static void buscarReserva(){
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("              BUSCAR RESERVA");
        System.out.println("  ==========================================\n");
        System.out.println("  [!] Funcion en desarrollo...\n");
    }
    
    //Placeholder para cancelar reserva
    private static void cancelarReserva() {
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("             CANCELAR RESERVA");
        System.out.println("  ==========================================\n");
        System.out.println("  [!] Funcion en desarrollo...\n");
    }
    
    //Placeholder para estadisticas
    private static void verEstadisticas() {
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("              ESTADISTICAS");
        System.out.println("  ==========================================\n");
        System.out.println("  [!] Funcion en desarrollo...\n");
    }
    
    //Mensaje de despedida
    private static void mostrarDespedida() {
        limpiarConsola();
        System.out.println("\n\n");
        System.out.println("  ================================================");
        System.out.println("                                                  ");
        System.out.println("           Gracias por usar TARBO-RED!           ");
        System.out.println("            Esperamos verlo pronto.              ");
        System.out.println("                                                  ");
        System.out.println("  ================================================");
        System.out.println("\n");
    }
    
    //Interrumpe la ejecucion hasta accion del usuario
    private static void pausar(){
        System.out.println("  [Presione ENTER para continuar...]");
        sc.nextLine();
    }
    
    //CLS
    private static void limpiarConsola() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
