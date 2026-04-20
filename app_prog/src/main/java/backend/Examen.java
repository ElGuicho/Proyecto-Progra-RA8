package backend;

import java.time.LocalDate;
import java.util.List;

public class Examen {
	private int id;
	private LocalDate fecha;
	private List<Pregunta> preguntas;
	
	public void generarAleatorio(){
			
	}

	public void generarMixto(){

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "Examen [id=" + id + ", fecha=" + fecha + ", preguntas=" + preguntas + "]";
	}
}
