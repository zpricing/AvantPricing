package cl.zpricing.avant.model.loadmanager;

import java.util.ArrayList;
import java.util.List;

import cl.zpricing.avant.model.Complejo;

public class ActualizacionResultado {
	private Complejo complejo;
	private boolean resultado;
	private List<String> logResultado;
	
	public ActualizacionResultado() {
		this.logResultado = new ArrayList<String>();
		this.resultado = false;
	}
	
	public Complejo getComplejo() {
		return complejo;
	}
	public void setComplejo(Complejo complejo) {
		this.complejo = complejo;
	}
	public boolean isResultado() {
		return resultado;
	}
	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	public List<String> getLogResultado() {
		return logResultado;
	}
}