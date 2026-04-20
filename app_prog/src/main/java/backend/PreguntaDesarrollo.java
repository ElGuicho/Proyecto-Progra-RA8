package backend;

public class PreguntaDesarrollo extends Pregunta {
	private String respuestaModelo;

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
