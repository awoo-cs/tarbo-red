
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.cliente;
import modelo.pelicula;
import modelo.reserva;
import servicio.calculadoraTarifas;
import servicio.gestorAforo;
import servicio.gestorCartelera;
import servicio.gestorReservas;
import utilidades.validador;

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
    private static gestorAforo gestorAforo = new gestorAforo();
    
    public static void main(String[] args){
        try{
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        }catch (Exception e){
            
        }
        
        
        mostrarBienvenida();
        
        gestorAforo.inicializarAforo(gestorCartelera.obtenerPeliculas());
        
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
    private static int leerOpcion() {
        try {
            int opcion = sc.nextInt();
            sc.nextLine(); 
            return opcion;
        } catch (java.util.InputMismatchException e) {
            sc.nextLine(); 
            return -1; 
        }
    }
    
    //Placeholder para visualizar la cartelera
    private static void verCartelera() {
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("          CARTELERA DE PELICULAS");
        System.out.println("  ==========================================");

        System.out.println("\n  PELICULAS DISPONIBLES:");
        System.out.println("  " + "=".repeat(90) + "\n");

        ArrayList<pelicula> peliculas = gestorCartelera.obtenerPeliculas();

        for (int i = 0; i < peliculas.size(); i++) {
            pelicula p = peliculas.get(i);
            System.out.println("  [" + (i + 1) + "] " + p.getTitulo());
            System.out.println("      Genero: " + p.getGenero());
            System.out.println("      Duracion: " + p.getDuracion() + " minutos");
            System.out.println("      Precio base: S/ " + String.format("%.2f", p.getPrecioBase()));
            System.out.println("      Horarios disponibles:");

            // Mostrar horarios con disponibilidad
            for (String horario : p.getHorarios()) {
                int disponibles = gestorAforo.getAsientosDisponibles(p.getTitulo(), horario);
                String estado = gestorAforo.getEstadoAforo(p.getTitulo(), horario);
                System.out.printf("        - %s : %s\n", horario, estado);
            }
            System.out.println();
        }

        System.out.println("  " + "=".repeat(90));
        System.out.println("\n  Nota: Capacidad maxima por funcion: " + gestorAforo.CAPACIDAD_MAXIMA + " asientos");
        System.out.println("  Los precios mostrados son base. Se aplicaran descuentos segun perfil.\n");
    }
    
    //Placeholder para hacer reserva
    private static void hacerReserva() {
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("              NUEVA RESERVA");
        System.out.println("  ==========================================\n");

        try {
            gestorCartelera.mostrarCarteleraCompacta();

            int indicePelicula = leerEnteroValidado(
                "  Seleccione el numero de pelicula (0 para cancelar): ",
                0,
                gestorCartelera.getCantidadPeliculas()
            );

            if (indicePelicula == 0) {
                System.out.println("\n  [!] Reserva cancelada.\n");
                return;
            }

            pelicula peliculaSeleccionada = gestorCartelera.obtenerPeliculaPorIndice(indicePelicula - 1);

            System.out.println();
            System.out.println("  HORARIOS DISPONIBLES PARA: " + peliculaSeleccionada.getTitulo());
            System.out.println("  " + "-".repeat(70));

            String[] horarios = peliculaSeleccionada.getHorarios();
            for (int i = 0; i < horarios.length; i++) {
                String estado = gestorAforo.getEstadoAforo(peliculaSeleccionada.getTitulo(), horarios[i]);
                System.out.printf("  [%d] %s - %s\n", (i + 1), horarios[i], estado);
            }
            System.out.println("  " + "-".repeat(70));

            int indiceHorario = leerEnteroValidado(
                "\n  Seleccione el numero de horario: ",
                1,
                horarios.length
            );

            String horarioSeleccionado = horarios[indiceHorario - 1];

            int disponibles = gestorAforo.getAsientosDisponibles(
                peliculaSeleccionada.getTitulo(), 
                horarioSeleccionado
            );

            System.out.println("\n  Asientos disponibles para esta funcion: " + disponibles);

            if (disponibles == 0) {
                System.out.println("\n  [X] Lo sentimos, esta funcion esta LLENA.");
                System.out.println("  Por favor seleccione otro horario.\n");
                return;
            }

            int maxBoletos = Math.min(10, disponibles);
            int cantidad = leerEnteroValidado(
                "  Cantidad de boletos (1-" + maxBoletos + "): ", 
                1, 
                maxBoletos
            );

            if (!gestorAforo.hayDisponibilidad(peliculaSeleccionada.getTitulo(), horarioSeleccionado, cantidad)) {
                System.out.println("\n  [X] Lo sentimos, no hay suficientes asientos disponibles.");
                System.out.println("  Asientos disponibles: " + disponibles);
                System.out.println("  Boletos solicitados: " + cantidad + "\n");
                return;
            }

            System.out.println("\n  " + "-".repeat(50));
            System.out.println("  DATOS DEL CLIENTE");
            System.out.println("  " + "-".repeat(50));

            String nombre = leerNombreValidado("  Nombre completo: ");
            int edad = leerEdadValidada("  Edad: ");
            boolean esEstudiante = leerRespuestaSN("  Es estudiante? (S/N): ");

            boolean esMiercoles = leerRespuestaSN("  La funcion es en miercoles? (S/N): ");
            boolean esEstreno = leerRespuestaSN("  Es una pelicula en estreno? (S/N): ");

            cliente cliente = new cliente(nombre, edad, esEstudiante);

            double precioBase = peliculaSeleccionada.getPrecioBase();
            double precioTotal = calculadoraTarifas.calcularPrecio(
                precioBase, cliente, esMiercoles, esEstreno, cantidad
            );

            System.out.println(calculadoraTarifas.obtenerDesglose(
                precioBase, cliente, esMiercoles, esEstreno, cantidad
            ));

            boolean confirmar = leerRespuestaSN("  ¿Confirmar reserva? (S/N): ");

            if (!confirmar) {
                System.out.println("\n  [!] Reserva cancelada.\n");
                return;
            }

            boolean aforoRegistrado = gestorAforo.registrarReserva(
                peliculaSeleccionada.getTitulo(),
                horarioSeleccionado,
                cantidad
            );

            if (!aforoRegistrado) {
                System.out.println("\n  [X] Error: No se pudo registrar la reserva en el aforo.");
                System.out.println("  Por favor intente nuevamente.\n");
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
            System.out.println("  Lo necesitara para consultar o cancelar su reserva.");

            int disponiblesAhora = gestorAforo.getAsientosDisponibles(
                peliculaSeleccionada.getTitulo(), 
                horarioSeleccionado
            );
            System.out.println("\n  Asientos restantes para esta funcion: " + disponiblesAhora + "\n");

        } catch (Exception e) {
            System.out.println("\n  [X] Error inesperado: " + e.getMessage());
            System.out.println("  Por favor intente nuevamente.\n");
        }
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
    
    //Metodos aux
    //Leer un numero entero validado
    private static int leerEnteroValidado(String mensaje, int min, int max){
        while(true){
            try{
                System.out.println(mensaje);
                int valor = sc.nextInt();
                sc.nextLine();
                
                if(valor >= min && valor <= max){
                    return valor;
                }else{
                    System.out.println("\n [X] Error: Ingrese un numero entre " + min + " y " + max + ".\n");
                }
            }catch(java.util.InputMismatchException e){
                sc.nextLine();
                System.out.println("\n [X] Error: Debe ingresar un numero valido.\n");
            }
        }
    }
    
    //Lee una respuesta S/N validada
    private static boolean leerRespuestaSN(String mensaje){
        while(true){
            System.out.println(mensaje);
            String respuesta = sc.nextLine().trim().toUpperCase();
            
            if(validador.validarRespuestaSN(respuesta)){
                return validador.convertirRespuestaSN(respuesta);
            }else{
                System.out.println("\n [X] Error: Responda con S(Si) o N(No).\n");
            }
        }
    }
    
    //Lee un nombre validado
    private static String leerNombreValidado(String mensaje){
        while(true){
            System.out.println(mensaje);
            String nombre = sc.nextLine().trim();
            
            if(validador.validarNombre(nombre)){
                return nombre;
            }else{
                System.out.println("\n [X] Error: " + validador.getMensajeErrorNombre(nombre) + "\n");
            }
        }
    }
    
    //Lee una edad validada
    private static int leerEdadValidada(String mensaje){
        while(true){
            try{
                System.out.println(mensaje);
                int edad = sc.nextInt();
                sc.nextLine();
                
                if(validador.validarEdad(edad)){
                    return edad;
                }else{
                    System.out.println("\n [X] Error: " + validador.getMensajeErrorEdad(edad) + "\n");
                }
            }catch (java.util.InputMismatchException e){
                sc.nextLine();
                System.out.println("\n [X] Error: La edad debe ser un numero.\n");
            }
        }
    }
}
