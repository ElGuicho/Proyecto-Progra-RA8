package backend;

import java.util.List;

public class PreguntaTest extends Pregunta {
	private List<String> opciones;
	private int correcta;
	
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
