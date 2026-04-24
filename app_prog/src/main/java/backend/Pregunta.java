package backend;

import java.time.LocalDate;
import java.util.List;

public class Pregunta {
	private int id;
	private String autor;
	private String curso;
	private String grupo;
	private String modulo;
	private String ra;
	private String tema;
	private String enunciado;
	private LocalDate fechaCreacion;
	private List<String> palabrasClave;

	public boolean coincideFiltro(){
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public List<String> getPalabrasClave() {
		return palabrasClave;
	}

	public void setPalabrasClave(List<String> palabrasClave) {
		this.palabrasClave = palabrasClave;
	}
	public Pregunta(){

	}
	public Pregunta(int id, String autor, String curso, String grupo, String modulo, String ra, String tema,
			String enunciado, LocalDate fechaCreacion, List<String> palabrasClave) {
		this.id = id;
		this.autor = autor;
		this.curso = curso;
		this.grupo = grupo;
		this.modulo = modulo;
		this.ra = ra;
		this.tema = tema;
		this.enunciado = enunciado;
		this.fechaCreacion = fechaCreacion;
		this.palabrasClave = palabrasClave;
	}
	
	@Override
	public String toString() {
		return "Pregunta [id=" + id + ", autor=" + autor + ", curso=" + curso + ", grupo=" + grupo + ", modulo="
				+ modulo + ", ra=" + ra + ", tema=" + tema + ", enunciado=" + enunciado + ", fechaCreacion="
				+ fechaCreacion + ", palabrasClave=" + palabrasClave + "]";
	}
}
