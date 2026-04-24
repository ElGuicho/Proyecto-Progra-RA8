package backend;

import java.time.LocalDate;

/**
 * Clase que representa una operación realizada por un usuario dentro del sistema.
 * 
 * Cada operación registra:
 * - El usuario que la realizó
 * - El tipo de operación (LOGIN, CREAR_PREGUNTA, MODIFICAR, ELIMINAR, EXPORTAR, etc.)
 * - La fecha en que ocurrió
 * - Una descripción adicional
 */
public class Operacion {

    private int id;
    private int usuarioId;     // Relación con la tabla usuario
    private String tipo;
    private LocalDate fecha;
    private String descripcion;

    /**
     * Constructor vacío.
     */
    public Operacion() {}

    /**
     * Constructor completo.
     */
    public Operacion(int usuarioId, String tipo, LocalDate fecha, String descripcion) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Operacion [id=" + id + ", usuarioId=" + usuarioId + ", tipo=" + tipo 
                + ", fecha=" + fecha + ", descripcion=" + descripcion + "]";
    }
}
+ id + ", tipo=" + tipo + ", fecha=" + fecha + ", descripcion=" + descripcion + "]";
	}
}
