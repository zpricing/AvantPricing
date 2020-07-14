package cl.zpricing.avant.negocio.sincronizador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

public class Sincronizador {
	private static Sincronizador INSTANCE= new Sincronizador();
	private  ExecutorService cola = Executors.newSingleThreadExecutor();
	private  List<EstadoProceso> procesosEncolados= new ArrayList<EstadoProceso>();
	
	//Clase singleton
	private Sincronizador(){}
	public static Sincronizador getInstance(){
		return INSTANCE;
	}
	
	//Metodo que encola un proceso
	//Si Forzar es true entonces lo agrega aunque ya este encolado anteriormente
	//Retorna true si pudo agregarlo. 
	//Retorna false cuando ya estaba en la cola y forzar esta en false
	public  boolean agregarCola(MetaProceso metaProceso, boolean Forzar){
		Proceso proceso = metaProceso.ejecutarProceso();
		if(!Forzar && estaEnCola(metaProceso.getCodigo())){
			return false;
		}
		proceso.setCodigo(metaProceso.getCodigo());
		Future estado = cola.submit((Runnable) proceso);
		procesosEncolados.add(new EstadoProceso(metaProceso.getCodigo(), metaProceso.getNombre(), estado, proceso));
		return true;
	}
	
	public  boolean estaEnCola(String tipoDeProceso){
		for (EstadoProceso proceso : procesosEncolados) {
			if(proceso.getTipoProceso().equals(tipoDeProceso) &&
					(proceso.getEstado().equals(EstadoProceso.getEncola()) || 
					 proceso.getEstado().equals(EstadoProceso.getEnejecucion())
					)
			   ){
				return true;
			}
		}
		return false;
	}
	
	public void agregarInicioCola(Runnable proceso, String tipoDeProceso){		
		//Por implementar
		
	}
	
	public boolean quitarDeCola(int id, boolean terminarEjecucion){
		int index = -1;
		int contador = 0;
		for(EstadoProceso proceso : procesosEncolados){
			if( proceso.getId() == id){
				index = contador;
				contador=-1;
				break;
			}
			contador++;
		}
		if (index!=-1 && contador==-1 && procesosEncolados.get(index).quitar(terminarEjecucion)){
			//procesosEncolados.remove(index);
			return true;
		}
		return false;
	}
	
	public List<EstadoProceso> mostrarCola() {
		return this.procesosEncolados;
	}
	
	public boolean limpiarCola(){
		Iterator<EstadoProceso> it = procesosEncolados.iterator();
		while(it.hasNext()){
			EstadoProceso current = it.next();
			if(!(current.getEstado().equals(EstadoProceso.getEnejecucion()) || current.getEstado().equals(EstadoProceso.getEncola()))){
				it.remove();
			}
		}
		
		return true;
	}
}
