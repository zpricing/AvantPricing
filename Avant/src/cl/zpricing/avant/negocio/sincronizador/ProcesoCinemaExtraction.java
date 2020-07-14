package cl.zpricing.avant.negocio.sincronizador;

public abstract class ProcesoCinemaExtraction extends MetaProceso {
	private Proceso proceso;

	@Override
	public Proceso ejecutarProceso() {
		return this.proceso;
	}
	
	public abstract Proceso obtenerProceso();

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}
}
