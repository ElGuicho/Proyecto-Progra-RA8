package backend;

import java.time.LocalDate;

public class Operacion extends Usuario{
	private int id;
	private String tipo;
	private LocalDate fecha;
	private String descripcion;
	
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
