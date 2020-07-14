package cl.zpricing.avant.negocio.sincronizador;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class EstadoProceso {
	private String tipoProceso;
	private String nombreProceso;
	private Proceso proceso;
	private boolean iniciado = false;
	private Future estado;
	private boolean isInterrumpido=false;
	
	private static final String enEjecucion = "Running";
	private static final String interrumpido = "Interrupted";
	private static final String cancelado = "Canceled";
	private static final String finalizado = "Finished";
	private static final String enCola = "Queued";
	
	
	public EstadoProceso(String tipoProceso, String nombreProceso, Future estado, Proceso proceso) {
		this.tipoProceso = tipoProceso;
		this.nombreProceso = nombreProceso;
		this.estado = estado;
		this.proceso = proceso;
	}

	public String getTipoProceso() {
		return tipoProceso;
	}

	public String getEstado() {
		
		/*
		 * CancellationException - if the computation was cancelled
		   ExecutionException - if the computation threw an exception
	   	   InterruptedException - if the current thread was interrupted while waiting
		   TimeoutException - if the wait timed out
		 */
		try{
			estado.get(1, TimeUnit.MILLISECONDS);
		}
		catch(CancellationException e){
			if(isInterrumpido){
				return interrumpido;
			}
			return cancelado;
		}
		catch(ExecutionException e){
			return "Error de ejecucion: "+e.getCause();
		}
		catch(InterruptedException e){
			return interrumpido+e.getCause();
		}
		catch(TimeoutException e){
			if(enEjecucion()){
				return enEjecucion;
			}
			return enCola;
		}
		if(estado.isDone()){
			return finalizado;
		}
		return "*";		
	}
	
	public boolean quitar(boolean mayInterruptIfRunning){
		if(enEjecucion() && mayInterruptIfRunning){
			isInterrumpido= estado.cancel(mayInterruptIfRunning);
			if(isInterrumpido){
				LogProcesosManager.finalizado(proceso.logProceso().getNombreProceso());
			}
		}
		else if(!enEjecucion()){
			estado.cancel(mayInterruptIfRunning);
		}
		return isInterrumpido;
	}
	
	public int getId(){
		return this.hashCode();
	}
	private boolean enEjecucion(){
		return proceso.isEnEjecucion();
	}

	public static String getEnejecucion() {
		return enEjecucion;
	}

	public static String getInterrumpido() {
		return interrumpido;
	}

	public static String getCancelado() {
		return cancelado;
	}

	public static String getFinalizado() {
		return finalizado;
	}

	public static String getEncola() {
		return enCola;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}
}

