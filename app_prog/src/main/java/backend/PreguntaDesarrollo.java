package backend;

import java.time.LocalDate;
import java.util.List;

public class PreguntaDesarrollo extends Pregunta {
	private String respuestaModelo;

	public PreguntaDesarrollo() {}

	public PreguntaDesarrollo(int id, String autor, String curso, String grupo, String modulo, String ra, String tema,
			String enunciado, LocalDate fechaCreacion, List<String> palabrasClave, String respuestaModelo) {
		super(id, autor, curso, grupo, modulo, ra, tema, enunciado, fechaCreacion, palabrasClave);
		this.respuestaModelo = respuestaModelo;
	}

	public String getRespuestaModelo() {
		return respuestaModelo;
	}

	public void setRespuestaModelo(String respuestaModelo) {
		this.respuestaModelo = respuestaModelo;
	}

	@Override
	public String toString() {
		return super.toString() + " -> PreguntaDesarrollo [respuestaModelo=" + respuestaModelo + "]";
	}
}
