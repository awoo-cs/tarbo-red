/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import java.util.ArrayList;
import modelo.pelicula;

/**
 *
 * @author leo
 */
public class gestorCartelera {
    private ArrayList<pelicula> peliculas;

    public gestorCartelera(){
        this.peliculas = new ArrayList<>();
        cargarPeliculasIniciales();
    }
    
    //Carga peliculas del sistema
    private void cargarPeliculasIniciales(){
        //Pelicula 1
        peliculas.add(new pelicula(
                "Oppenheimer",
                "Drama/Historia",
                180,
                new String[]{"14:00", "17:30", "21:00"},
                20.00
        ));
        
        //Pelicula 2
        peliculas.add(new pelicula(
                "Barbie",
                "Comedia/Fantasia",
                114,
                new String[]{"13:00", "15:30", "18:00", "20:30"},
                18.00
        ));
        
        //Pelicula 3
        peliculas.add(new pelicula(
                "Dune: Parte Dos",
                "Ciencia Ficcion",
                166,
                new String[]{"15:00", "18:30", "22:00"},
                22.00
        ));
        
        //Pelicula 4
        peliculas.add(new pelicula(
                "Kung Fu Panda 4",
                "Animacion/Aventura",
                94,
                new String[]{"12:00", "14:00", "16:00", "18:00"},
                15.00
        ));
        
        //Pelicula 5
        peliculas.add(new pelicula(
                "Deadpool y Wolverine",
                "Accion/Comedia",
                128,
                new String[]{"16:00", "19:00", "22:00"},
                20.00
        ));
    }
    
    //Muestra cartelera en CLI
    public void mostrarCartelera(){
        System.out.println("\n PELICULAS DISPONIBLES:");
        System.out.println("  " + "=".repeat(80) + "\n");
        
        for (int i = 0; i < peliculas.size(); i++) {
            pelicula p = peliculas.get(i);
            System.out.println("  [" + (i + 1) + "] " + p.getTitulo());
            System.out.println("      Genero: " + p.getGenero());
            System.out.println("      Duracion: " + p.getDuracion() + " minutos");
            System.out.println("      Horarios: " + p.getHorariosFormateados());
            System.out.println("      Precio base: S/ " + String.format("%.2f", p.getPrecioBase()));
            System.out.println();
        }
        
        System.out.println("  " + "=".repeat(80));
    }
    
    //Mostrar cartelera en tabla
    public void mostrarCarteleraCompacta(){
        System.out.println("\n  " + "=".repeat(80));
        System.out.println("  #  | TITULO                        | GENERO           | DURACION | PRECIO");
        System.out.println("  " + "=".repeat(80));
        
        for (int i = 0; i < peliculas.size(); i++) {
            pelicula p = peliculas.get(i);
            System.out.printf("  %-2d | %-29s | %-16s | %3d min  | S/ %.2f\n",
                (i + 1),
                p.getTitulo(),
                p.getGenero(),
                p.getDuracion(),
                p.getPrecioBase()
            );
        }
        
        System.out.println("  " + "=".repeat(80) + "\n");
    }
    
    //Buscar pelicula x titulo
    public pelicula buscarPeliculaPorTitulo(String titulo){
        for(pelicula p : peliculas){
            if(p.getTitulo().equalsIgnoreCase(titulo)){
                return p;
            }
        }
        return null;
    }
    
    //Obtener pelicula x indice de lista
    public pelicula obtenerPeliculaPorIndice(int indice){
        if (indice >= 0 && indice < peliculas.size()) {
            return peliculas.get(indice);
        }
        return null;
    }
    
    //Obtener lista completa de peliculas
    public ArrayList<pelicula> obtenerPeliculas(){
        return peliculas;
    }
    
    //Obtener el numero total de peliculas en cartelera
    public int getCantidadPeliculas(){
        return peliculas.size();
    }
    
    //Verifica si un indice de pelicula es valido
    public boolean indiceValido(int indice){
        return indice >= 1 && indice <= peliculas.size();
    }
    
    //Muestra los horarios disponibles de una peli especifica
    public void mostrarHorarios(pelicula pelicula){
        System.out.println("\n  HORARIOS DISPONIBLES PARA: " + pelicula.getTitulo());
        System.out.println("  " + "-".repeat(50));
        
        String[] horarios = pelicula.getHorarios();
        for (int i = 0; i < horarios.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + horarios[i]);
        }
        
        System.out.println("  " + "-".repeat(50));
    }
}
