package backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un examen compuesto por una lista de preguntas.
 * 
 * Incluye métodos para generar exámenes de forma aleatoria o mixta,
 * así como utilidades para gestionar la colección de preguntas.
 */
public class Examen {

    private int id;
    private String titulo;
    private LocalDate fecha;
    private List<Pregunta> preguntas;

    /**
     * Constructor por defecto.
     * Inicializa la lista de preguntas vacía.
     */
    public Examen() {
        this.preguntas = new ArrayList<>();
        this.fecha = LocalDate.now();
    }

    /**
     * Constructor con parámetros.
     */
    public Examen(String titulo, LocalDate fecha) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.preguntas = new ArrayList<>();
    }

    /**
     * Genera un examen aleatorio seleccionando preguntas al azar.
     * La lógica real se implementará en la capa de servicio.
     */
    public void generarAleatorio() {
        // TODO: Implementar lógica en la capa de servicio
        System.out.println("Generando examen aleatorio...");
    }

    /**
     * Genera un examen mixto combinando preguntas manuales y aleatorias.
     * La lógica real se implementará en la capa de servicio.
     */
    public void generarMixto() {
        // TODO: Implementar lógica en la capa de servicio
        System.out.println("Generando examen mixto...");
    }

    /**
     * Añade una pregunta al examen.
     */
    public void addPregunta(Pregunta p) {
        this.preguntas.add(p);
    }

    /**
     * Elimina una pregunta del examen.
     */
    public void removePregunta(Pregunta p) {
        this.preguntas.remove(p);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public String toString() {
        return "Examen [id=" + id + ", titulo=" + titulo + ", fecha=" + fecha + ", preguntas=" + preguntas + "]";
    }
}

