package cl.zpricing.avant.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import cl.zpricing.avant.etl.scriptella.EtlExecutorProcess;
import cl.zpricing.avant.etl.variantes.EtlCompleteExtractionImpl;
import cl.zpricing.avant.etl.variantes.EtlSessionExtraction;
import cl.zpricing.avant.negocio.forecastmanager.AsistenciasManager;
import cl.zpricing.avant.negocio.forecastmanager.AvailabilityManager;
import cl.zpricing.avant.negocio.forecastmanager.LastMinuteSellingManager;
import cl.zpricing.avant.negocio.forecastmanager.MascarasManager;
import cl.zpricing.avant.negocio.forecastmanager.SecondSellingManager;
import cl.zpricing.avant.negocio.forecastmanager.UpsellingManager;
import cl.zpricing.avant.negocio.sincronizador.EstadoProceso;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.MetaProceso;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.negocio.sincronizador.Sincronizador;
import cl.zpricing.avant.servicios.ibatis.LogProcesosDaoImpl;
import cl.zpricing.commons.utils.ErroresUtils;

public class FactoryProcesos {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private Sincronizador sincronizador = Sincronizador.getInstance();
	private LogProcesosDaoImpl logProcesosDao;
	
	private Map<String, MetaProceso> procesos;
	private List<String> procesosActualizacionDiaria;
	private List<String> procesosActualizacionNuevasFunciones;
	private List<String> procesosExtraerDataUsuarios;
	private List<String> procesosSecondSellingPersonalizedSuggestions;
	private List<String> procesosLastMinuteSelling;
	
	public void actualizacionDiaria() {
		this.ejecucionListaProcesos(this.procesosActualizacionDiaria);
	}
	
	public void actualizacionNuevasFunciones() {
		this.ejecucionListaProcesos(this.procesosActualizacionNuevasFunciones);
	}
	
	public void extraerDataUsuarios() {
		this.ejecucionListaProcesos(this.procesosExtraerDataUsuarios);
	}
	
	public void procesarYCargarSecondSellingPersonalizedSuggestions() {
		this.ejecucionListaProcesos(this.procesosSecondSellingPersonalizedSuggestions);
	}
	
	public void procesarYCargarLastMinuteSelling() {
		this.ejecucionListaProcesos(this.procesosLastMinuteSelling);
	}
	
	private void ejecucionListaProcesos(List<String> procesos) {
		Iterator<String> iterProcesos = procesos.iterator();
		
		while(iterProcesos.hasNext()) {
			String nombreProceso = iterProcesos.next();
			MetaProceso proceso = this.procesos.get(nombreProceso);
			this.iniciarProceso(proceso);
		}
	}
	
	public boolean iniciarProceso(MetaProceso proceso) {
		log.debug("Iniciando nuevo proceso");
		log.debug("  Tipo: " + proceso.getCodigo());
		
		try {
			sincronizador.agregarCola(proceso, true);
			log.debug("Proceso agregado...");
		}
		
		catch (IllegalStateException e) {
			String error = "Interrumpido manualmente";
			log.error(error);
			proceso.setEnEjecucion(false);
			LogProcesosManager.cambioError(proceso.getCodigo(),"Interrumpido manualmente", true);
			return false;
		}
		catch (Exception e) {
			String error = "";
			if (proceso != null) {
				error += "Error al intentar ejecutar " + proceso.getCodigo() + ": ";
			}
		
			log.error(error + ErroresUtils.extraeStackTrace(e));
			proceso.setEnEjecucion(false);
			LogProcesosManager.cambioError(proceso.getCodigo(), e.getMessage(), true);
			return false;
		}
		return true;
	}
	
	public  List<LogProcesos> obtenerUltimasEjecuciones(){
		return logProcesosDao.obtenerTodos();
	}
	
	public boolean limpiarCola() {
		return sincronizador.limpiarCola();
	}

	public boolean quitarCola(int id) {
		try {
			sincronizador.quitarDeCola(id, true);
		}
		catch(Exception e){
			String error = e.getMessage();
		}
		return sincronizador.quitarDeCola(id, true);
	}
	
	public List<EstadoProceso> obtenerCola() {
		return sincronizador.mostrarCola();
	}

	public Map<String, MetaProceso> getProcesos() {
		return procesos;
	}
	public void setProcesos(Map<String, MetaProceso> procesos) {
		this.procesos = procesos;
	}
	public void setLogProcesosDao(LogProcesosDaoImpl logProcesosDao) {
		this.logProcesosDao = logProcesosDao;
	}

	public void setProcesosActualizacionDiaria(List<String> procesosActualizacionDiaria) {
		this.procesosActualizacionDiaria = procesosActualizacionDiaria;
	}

	public void setProcesosActualizacionNuevasFunciones(List<String> procesosActualizacionNuevasFunciones) {
		this.procesosActualizacionNuevasFunciones = procesosActualizacionNuevasFunciones;
	}

	public void setProcesosExtraerDataUsuarios(List<String> procesosExtraerDataUsuarios) {
		this.procesosExtraerDataUsuarios = procesosExtraerDataUsuarios;
	}

	public void setProcesosSecondSellingPersonalizedSuggestions(List<String> procesosSecondSellingPersonalizedSuggestions) {
		this.procesosSecondSellingPersonalizedSuggestions = procesosSecondSellingPersonalizedSuggestions;
	}

	public void setProcesosLastMinuteSelling(List<String> procesosLastMinuteSelling) {
		this.procesosLastMinuteSelling = procesosLastMinuteSelling;
	}
}
