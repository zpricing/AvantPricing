package cl.zpricing.avant.negocio.sincronizador;

import cl.zpricing.avant.alerts.ProcessAlertFactory;


public abstract class Proceso implements Runnable {
	protected LogProcesos logProcesos;
	protected ProcessAlertFactory processAlertFactory;
	protected volatile boolean iniciado = false;
	protected boolean successful = false;
	
	private String codigo;
	
	public boolean isEnEjecucion() {
		return this.iniciado;
	}
	
	public void checkInterrupt() {
		if(Thread.currentThread().isInterrupted()) 
			throw new IllegalStateException("Interrumpido");
	}
	
	public void setEnEjecucion(boolean estado) {
		this.iniciado = estado;
	}
	
	public LogProcesos logProceso() {
		return this.logProcesos;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setLogProcesos(LogProcesos logProcesos) {
		this.logProcesos = logProcesos;
	}
	public void setProcessAlertFactory(ProcessAlertFactory processAlertFactory) {
		this.processAlertFactory = processAlertFactory;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successResult) {
		this.successful = successResult;
	}
}
