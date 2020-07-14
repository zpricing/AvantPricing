package cl.zpricing.avant.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import cl.zpricing.avant.negocio.sincronizador.EstadoProceso;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.util.FactoryProcesos;
import cl.zpricing.commons.utils.ErroresUtils;

@RemoteProxy(name="dwrProcesos")
public class AdministracionProcesosController implements Controller {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	@Autowired
	private LogProcesosManager logProcesosManager;
	@Autowired
	private FactoryProcesos factoryProcesos;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("procesos", factoryProcesos.getProcesos().values());
		ModelAndView mv = new ModelAndView("administrator/procesos",params);
		
		Set<String> keys = factoryProcesos.getProcesos().keySet();
		Iterator<String> iteratorKeys = keys.iterator();
		log.debug("Mapa de procesos : ");
		while(iteratorKeys.hasNext()) {
			String key = iteratorKeys.next();
			log.debug("  " + factoryProcesos.getProcesos().get(key).getNombre());
		}
		
		return mv;
	}

	@RemoteMethod
	public String refreshTabla() throws JSONException {
		try {
			log.debug("administracionProcesosController refreshTabla...");
			
			List<LogProcesos> logs = factoryProcesos.obtenerUltimasEjecuciones();
			log.debug("# de log de procesos : " + logs.size());
			JSONObject mapa = this.mapeoProcesos(logs);
			
			log.debug("  despues de obtencion de mapa.");
			
			if (mapa == null) {
				log.debug("  mapa es nulo!!");
			}
			else {
				log.debug("  mapa.length :" + mapa.length());
			}
			
			List<EstadoProceso> procesosEncolados = factoryProcesos.obtenerCola();
			mapa.put("Cola", this.mapeoProcesosEncolados(procesosEncolados));
			return mapa.toString();
		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			return "";
		}
	}
	
	private JSONObject mapeoProcesos(List<LogProcesos> logs) throws JSONException {
		JSONObject mapa = new JSONObject();
		Iterator<LogProcesos> logsIterator = logs.iterator();

		while (logsIterator.hasNext()) {
			LogProcesos logProcesos = logsIterator.next();
			if (logProcesos != null) {
				log.debug("log Proceso : " + logProcesos.getNombreProceso());
				JSONObject json =  logProcesos.toJson();
				mapa.put(logProcesos.getNombreProceso(), json);
			}
		}
		return mapa;
	}
	
	private JSONObject mapeoProcesosEncolados(List<EstadoProceso> procesos) throws JSONException{
		JSONObject mapa = new JSONObject();
		int contador = 1;
		for (EstadoProceso proceso : procesos) {
			if(proceso!=null){
				JSONObject elementoDeCola = new JSONObject();
				elementoDeCola.put("tipoProceso", proceso.getTipoProceso());
				elementoDeCola.put("estado", proceso.getEstado());
				elementoDeCola.put("getId", proceso.getId());
				mapa.put("Cola"+contador, elementoDeCola);
				contador++;
			}
		}
		return mapa;
	}
	
	@RemoteMethod
	public boolean limpiarCola() {
		log.debug("administracionProcesosController limpiarCola");
		return factoryProcesos.limpiarCola();
	}
	
	@RemoteMethod
	public void forzarTermino(String proceso) {
		log.debug("administracionProcesosController forzarTermino");
		LogProcesosManager.forzarTerminoProceso(proceso);
	}
	
	@RemoteMethod
	public String ejecutarProceso(String codigo) {
		try {
			log.debug("Iniciando ejecucion de proceso : " + codigo);
			if (!factoryProcesos.getProcesos().containsKey(codigo)) {
				return "Process code doesnt exist";
			}
			
			boolean result = factoryProcesos.iniciarProceso(factoryProcesos.getProcesos().get(codigo));
			return result == true ? "Succesful" : "Error";
		} catch (Exception e) {
			ErroresUtils.extraeStackTrace(e);
			return e.getMessage();
		}
	}

	/** SETTERS **/
	public void setLogProcesosManager(LogProcesosManager logProcesosManager) {
		this.logProcesosManager = logProcesosManager;
	}
	public void setFactoryProcesos(FactoryProcesos factoryProcesos) {
		this.factoryProcesos = factoryProcesos;
	}
}
