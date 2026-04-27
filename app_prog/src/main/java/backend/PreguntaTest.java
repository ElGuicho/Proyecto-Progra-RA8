package backend;

import java.time.LocalDate;
import java.util.List;

public class PreguntaTest extends Pregunta {
	private List<String> opciones;
	private int correcta;
	
	public PreguntaTest() {}

	public PreguntaTest(int id, String autor, String curso, String grupo, String modulo, String ra, String tema,
			String enunciado, LocalDate fechaCreacion, List<String> palabrasClave, List<String> opciones,
			int correcta) {
		super(id, autor, curso, grupo, modulo, ra, tema, enunciado, fechaCreacion, palabrasClave);
		this.opciones = opciones;
		this.correcta = correcta;
	}

	public List<String> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<String> opciones) {
		this.opciones = opciones;
	}

	public int getCorrecta() {
		return correcta;
	}

	public void setCorrecta(int correcta) {
		this.correcta = correcta;
	}

	@Override
	public String toString() {
		return super.toString() + " -> PreguntaTest [opciones=" + opciones + ", correcta=" + correcta + "]";
	}
}
