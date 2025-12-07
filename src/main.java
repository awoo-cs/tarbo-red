
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static void buscarReserva() {
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("              BUSCAR RESERVA");
        System.out.println("  ==========================================\n");

        if (!gestorReservas.hayReservas()) {
            System.out.println("  [!] No hay reservas registradas en el sistema.\n");
            return;
        }

        System.out.println("  Opciones de busqueda:");
        System.out.println("  [1] Buscar por codigo de reserva");
        System.out.println("  [2] Buscar por nombre del cliente");
        System.out.println("  [0] Cancelar\n");

        int opcion = leerEnteroValidado("  Seleccione una opcion: ", 0, 2);

        if (opcion == 0) {
            System.out.println("\n  [!] Busqueda cancelada.\n");
            return;
        }

        try {
            if (opcion == 1) {
                System.out.print("\n  Ingrese el codigo de reserva (ej: RES001): ");
                String codigo = sc.nextLine().trim().toUpperCase();

                if (codigo.isEmpty()) {
                    System.out.println("\n  [X] El codigo no puede estar vacio.\n");
                    return;
                }

                reserva reserva = gestorReservas.buscarReservaPorCodigo(codigo);

                if (reserva == null) {
                    System.out.println("\n  [X] No se encontro ninguna reserva con el codigo: " + codigo);
                    System.out.println("  Verifique el codigo e intente nuevamente.\n");
                } else {
                    System.out.println("\n  [✓] RESERVA ENCONTRADA:\n");
                    System.out.println(reserva.toString());

                    int disponibles = gestorAforo.getAsientosDisponibles(
                        reserva.getPelicula().getTitulo(),
                        reserva.getHorario()
                    );
                    System.out.println("\n  Estado actual de la funcion:");
                    System.out.println("  " + gestorAforo.getEstadoAforo(
                        reserva.getPelicula().getTitulo(),
                        reserva.getHorario()
                    ));
                    System.out.println();
                }

            } else if (opcion == 2) {
                System.out.print("\n  Ingrese el nombre (o parte del nombre) del cliente: ");
                String nombre = sc.nextLine().trim();

                if (nombre.isEmpty()) {
                    System.out.println("\n  [X] El nombre no puede estar vacio.\n");
                    return;
                }

                ArrayList<reserva> reservasEncontradas = gestorReservas.buscarReservasPorNombre(nombre);

                if (reservasEncontradas.isEmpty()) {
                    System.out.println("\n  [X] No se encontraron reservas para: " + nombre);
                    System.out.println("  Intente con otro nombre o parte del nombre.\n");
                } else {
                    System.out.println("\n  [✓] SE ENCONTRARON " + reservasEncontradas.size() + " RESERVA(S):\n");
                    System.out.println("  " + "=".repeat(100));
                    System.out.println("  CODIGO   | CLIENTE              | PELICULA                  | HORARIO | BOLETOS | TOTAL");
                    System.out.println("  " + "=".repeat(100));

                    for (reserva r : reservasEncontradas) {
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

                    boolean verDetalles = leerRespuestaSN("  Desea ver los detalles de alguna reserva? (S/N): ");

                    if (verDetalles) {
                        System.out.print("  Ingrese el codigo de la reserva: ");
                        String codigoDetalle = sc.nextLine().trim().toUpperCase();

                        reserva reservaDetalle = gestorReservas.buscarReservaPorCodigo(codigoDetalle);

                        if (reservaDetalle != null) {
                            System.out.println("\n");
                            System.out.println(reservaDetalle.toString());
                            System.out.println();
                        } else {
                            System.out.println("\n  [X] Codigo no encontrado.\n");
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("\n  [X] Error al buscar reserva: " + e.getMessage());
            System.out.println("  Por favor intente nuevamente.\n");
        }
    }
    
    //Placeholder para cancelar reserva
    private static void cancelarReserva() {
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("            CANCELAR RESERVA");
        System.out.println("  ==========================================\n");

        if (!gestorReservas.hayReservas()) {
            System.out.println("  [!] No hay reservas registradas en el sistema.\n");
            return;
        }

        System.out.println("  Para cancelar una reserva necesita su codigo.");
        System.out.println("  Si no lo recuerda, use la opcion 'Buscar Reserva' primero.\n");

        try {
            System.out.print("  Ingrese el codigo de reserva (o 0 para cancelar): ");
            String codigo = sc.nextLine().trim().toUpperCase();

            if (codigo.equals("0")) {
                System.out.println("\n  [!] Operacion cancelada.\n");
                return;
            }

            if (codigo.isEmpty()) {
                System.out.println("\n  [X] El codigo no puede estar vacio.\n");
                return;
            }

            reserva reserva = gestorReservas.buscarReservaPorCodigo(codigo);

            if (reserva == null) {
                System.out.println("\n  [X] No se encontro ninguna reserva con el codigo: " + codigo);
                System.out.println("  Verifique el codigo e intente nuevamente.\n");
                return;
            }

            System.out.println("\n  RESERVA ENCONTRADA:\n");
            System.out.println("  " + "=".repeat(70));
            System.out.println("  Codigo: " + reserva.getCodigo());
            System.out.println("  Cliente: " + reserva.getCliente().getNombre());
            System.out.println("  Pelicula: " + reserva.getPelicula().getTitulo());
            System.out.println("  Horario: " + reserva.getHorario());
            System.out.println("  Cantidad de boletos: " + reserva.getCantidadBoletos());
            System.out.println("  Total pagado: S/ " + String.format("%.2f", reserva.getPrecioTotal()));
            System.out.println("  Fecha de reserva: " + reserva.getFechaFormateada());
            System.out.println("  " + "=".repeat(70) + "\n");

            System.out.println("  [!] ADVERTENCIA:");
            System.out.println("  Esta accion es IRREVERSIBLE. La reserva sera eliminada del sistema.");
            System.out.println("  Los asientos seran liberados para otros clientes.\n");

            boolean confirmar = leerRespuestaSN("  Esta seguro que desea CANCELAR esta reserva? (S/N): ");

            if (!confirmar) {
                System.out.println("\n  [!] Cancelacion abortada. La reserva se mantiene activa.\n");
                return;
            }

            System.out.print("\n  Por favor confirme nuevamente escribiendo 'CONFIRMAR': ");
            String confirmacionFinal = sc.nextLine().trim().toUpperCase();

            if (!confirmacionFinal.equals("CONFIRMAR")) {
                System.out.println("\n  [!] Cancelacion abortada. La reserva se mantiene activa.\n");
                return;
            }

            String nombreCliente = reserva.getCliente().getNombre();
            String tituloPelicula = reserva.getPelicula().getTitulo();
            String horario = reserva.getHorario();
            int cantidadBoletos = reserva.getCantidadBoletos();
            double montoDevolver = reserva.getPrecioTotal();

            gestorAforo.liberarReserva(
                reserva.getPelicula().getTitulo(),
                reserva.getHorario(),
                reserva.getCantidadBoletos()
            );

            boolean cancelado = gestorReservas.cancelarReserva(codigo);

            if (cancelado) {
                limpiarConsola();
                System.out.println("\n");
                System.out.println("  ================================================================");
                System.out.println("                  RESERVA CANCELADA EXITOSAMENTE");
                System.out.println("  ================================================================\n");
                System.out.println("  Codigo de reserva: " + codigo);
                System.out.println("  Cliente: " + nombreCliente);
                System.out.println("  Pelicula: " + tituloPelicula);
                System.out.println("  Horario: " + horario);
                System.out.println("  Boletos liberados: " + cantidadBoletos);
                System.out.println("  Monto a devolver: S/ " + String.format("%.2f", montoDevolver));
                System.out.println("\n  Los asientos han sido liberados y estan disponibles nuevamente.");

                int disponiblesAhora = gestorAforo.getAsientosDisponibles(tituloPelicula, horario);
                System.out.println("  Asientos disponibles ahora para esta funcion: " + disponiblesAhora);
                System.out.println("\n  Gracias por utilizar nuestro sistema.");
                System.out.println("  ================================================================\n");

            } else {
                System.out.println("\n  [X] Error inesperado al cancelar la reserva.");
                System.out.println("  Por favor intente nuevamente o contacte al administrador.\n");
            }

        } catch (Exception e) {
            System.out.println("\n  [X] Error al cancelar reserva: " + e.getMessage());
            System.out.println("  Por favor intente nuevamente.\n");
        }
    }
    
    //Placeholder para estadisticas
    private static void verEstadisticas() {
        limpiarConsola();
        System.out.println("\n");
        System.out.println("  ==========================================");
        System.out.println("              ESTADISTICAS DEL SISTEMA");
        System.out.println("  ==========================================\n");

        if (!gestorReservas.hayReservas()) {
            System.out.println("  [!] No hay suficientes datos para generar estadisticas.");
            System.out.println("  Realice algunas reservas primero.\n");
            return;
        }

        System.out.println("  " + "=".repeat(70));
        System.out.println("  ESTADISTICAS GENERALES");
        System.out.println("  " + "=".repeat(70));

        int totalReservas = gestorReservas.getCantidadReservas();
        int totalBoletos = gestorReservas.getTotalBoletosVendidos();
        double ingresoTotal = gestorReservas.getIngresoTotal();
        double promedioBoletosReserva = (double) totalBoletos / totalReservas;
        double promedioIngresoReserva = ingresoTotal / totalReservas;

        System.out.println("\n  Total de reservas realizadas:     " + totalReservas);
        System.out.println("  Total de boletos vendidos:        " + totalBoletos);
        System.out.println("  Ingresos totales recaudados:      S/ " + String.format("%.2f", ingresoTotal));
        System.out.println("  Promedio boletos por reserva:     " + String.format("%.1f", promedioBoletosReserva));
        System.out.println("  Promedio de ingreso por reserva:  S/ " + String.format("%.2f", promedioIngresoReserva));

        System.out.println("\n  " + "=".repeat(70));
        System.out.println("  PELICULAS MAS POPULARES");
        System.out.println("  " + "=".repeat(70) + "\n");

        HashMap<String, Integer> reservasPorPelicula = new HashMap<>();
        HashMap<String, Double> ingresosPorPelicula = new HashMap<>();

        for (reserva r : gestorReservas.obtenerReservas()) {
            String titulo = r.getPelicula().getTitulo();

            reservasPorPelicula.put(titulo, 
                reservasPorPelicula.getOrDefault(titulo, 0) + 1);

            ingresosPorPelicula.put(titulo,
                ingresosPorPelicula.getOrDefault(titulo, 0.0) + r.getPrecioTotal());
        }

        String peliculaMasReservada = "";
        int maxReservas = 0;

        for (String titulo : reservasPorPelicula.keySet()) {
            int cantReservas = reservasPorPelicula.get(titulo);
            if (cantReservas > maxReservas) {
                maxReservas = cantReservas;
                peliculaMasReservada = titulo;
            }
        }

        System.out.println("  PELICULA                          | RESERVAS | BOLETOS | INGRESOS");
        System.out.println("  " + "-".repeat(70));

        for (String titulo : reservasPorPelicula.keySet()) {
            int cantReservas = reservasPorPelicula.get(titulo);
            double ingresos = ingresosPorPelicula.get(titulo);

            int boletosTotal = 0;
            for (reserva r : gestorReservas.obtenerReservas()) {
                if (r.getPelicula().getTitulo().equals(titulo)) {
                    boletosTotal += r.getCantidadBoletos();
                }
            }

            String marcador = titulo.equals(peliculaMasReservada) ? " ★" : "";
            System.out.printf("  %-33s | %-8d | %-7d | S/ %.2f%s\n",
                truncar(titulo, 33), cantReservas, boletosTotal, ingresos, marcador);
        }

        System.out.println("\n  ★ = Pelicula mas reservada");

        System.out.println("\n  " + "=".repeat(70));
        System.out.println("  ESTADISTICAS DE AFORO");
        System.out.println("  " + "=".repeat(70) + "\n");

        int totalAsientosVendidos = gestorAforo.getTotalAsientosVendidos();
        double ocupacionPromedio = gestorAforo.getOcupacionPromedio();
        String funcionMasPopular = gestorAforo.getFuncionMasPopular();

        System.out.println("  Total de asientos vendidos:       " + totalAsientosVendidos);
        System.out.println("  Ocupacion promedio general:        " + String.format("%.1f%%", ocupacionPromedio));
        System.out.println("  Funcion mas popular:               " + funcionMasPopular);

        // ===== GRÁFICO DE OCUPACIÓN =====
        System.out.println("\n  " + "=".repeat(70));
        System.out.println("  GRAFICO DE OCUPACION");
        System.out.println("  " + "=".repeat(70) + "\n");

        mostrarGraficoOcupacion();

        // ===== TIPOS DE CLIENTES =====
        System.out.println("\n  " + "=".repeat(70));
        System.out.println("  DISTRIBUCION DE CLIENTES");
        System.out.println("  " + "=".repeat(70) + "\n");

        int ninos = 0;
        int estudiantes = 0;
        int adultosMayores = 0;
        int adultos = 0;

        for (reserva r : gestorReservas.obtenerReservas()) {
            cliente c = r.getCliente();
            if (c.esNino()) {
                ninos++;
            } else if (c.esAdultoMayor()) {
                adultosMayores++;
            } else if (c.isEsEstudiante()) {
                estudiantes++;
            } else {
                adultos++;
            }
        }

        int totalClientes = totalReservas;

        System.out.println("  Ninos (< 12 años):           " + ninos + 
            " (" + String.format("%.1f%%", (ninos * 100.0 / totalClientes)) + ")");
        System.out.println("  Estudiantes:                 " + estudiantes + 
            " (" + String.format("%.1f%%", (estudiantes * 100.0 / totalClientes)) + ")");
        System.out.println("  Adultos mayores (>= 60):     " + adultosMayores + 
            " (" + String.format("%.1f%%", (adultosMayores * 100.0 / totalClientes)) + ")");
        System.out.println("  Adultos generales:           " + adultos + 
            " (" + String.format("%.1f%%", (adultos * 100.0 / totalClientes)) + ")");

        System.out.println("\n  " + "=".repeat(70));
        System.out.println("  RESUMEN EJECUTIVO");
        System.out.println("  " + "=".repeat(70) + "\n");

        System.out.println("  Estado del sistema:");
        System.out.println("  - Total de peliculas en cartelera:  " + gestorCartelera.getCantidadPeliculas());
        System.out.println("  - Total de funciones disponibles:   " + contarFuncionesTotal());
        System.out.println("  - Capacidad total del cine:          " + (contarFuncionesTotal() * gestorAforo.CAPACIDAD_MAXIMA) + " asientos");
        System.out.println("  - Asientos vendidos:                 " + totalAsientosVendidos + " asientos");
        System.out.println("  - Tasa de ocupacion global:          " + String.format("%.1f%%", ocupacionPromedio));

        System.out.println("\n  " + "=".repeat(70) + "\n");
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
    
    //Metodo aux para truncar strings largo
    private static String truncar(String texto, int maxLength) {
        if (texto.length() <= maxLength) {
            return texto;
        }
        return texto.substring(0, maxLength - 3) + "...";
    }
    
    //Grafico ASCII de ocupacion de las funciones
    private static void mostrarGraficoOcupacion() {
        ArrayList<pelicula> peliculas = gestorCartelera.obtenerPeliculas();

        System.out.println("  Nivel de ocupacion por funcion:");
        System.out.println("  (Cada █ = 10% de ocupacion)\n");

        for (pelicula p : peliculas) {
            for (String horario : p.getHorarios()) {
                double ocupacion = gestorAforo.getPorcentajeOcupacion(p.getTitulo(), horario);
                int barras = (int) Math.round(ocupacion / 10);

                String nombreFuncion = String.format("%-25s %s", 
                    truncar(p.getTitulo(), 20), horario);

                String grafico = "█".repeat(barras) + "░".repeat(10 - barras);

                System.out.printf("  %s | %s | %5.1f%%\n", nombreFuncion, grafico, ocupacion);
            }
        }
    }
    
    //Contar numero total de funciones disponibles
    private static int contarFuncionesTotal() {
        int total = 0;
        for (pelicula p : gestorCartelera.obtenerPeliculas()) {
            total += p.getHorarios().length;
        }
        return total;
    }
}
