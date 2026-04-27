package backend;

import java.time.LocalDate;

public class Operacion extends Usuario{
	private int id;
	private String tipo;
	private LocalDate fecha;
	private String descripcion;
	
	public Operacion(int id, String nombre, String password, int id2, String tipo, LocalDate fecha,
			String descripcion) {
		super(id, nombre, password);
		id = id2;
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
		return super.toString() + " -> Operacion [id=" + id + ", tipo=" + tipo + ", fecha=" + fecha + ", descripcion=" + descripcion + "]";
	}
}
