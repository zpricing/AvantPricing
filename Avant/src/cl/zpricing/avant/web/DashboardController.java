package cl.zpricing.avant.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.alerts.Alert;
import cl.zpricing.avant.alerts.ProcessAlertFactory;
import cl.zpricing.avant.negocio.sincronizador.EstadoProceso;
import cl.zpricing.avant.util.FactoryProcesos;
import cl.zpricing.commons.utils.ErroresUtils;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

@RemoteProxy(name="dwrDashboard")
public class DashboardController implements Controller {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private ProcessAlertFactory processAlertFactory;
	@Autowired
	private FactoryProcesos factoryProcesos;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("dashboard");
		return mv;
	}
	
	@RemoteMethod
	public String getAlerts() throws JSONException {
		try {
			JSONArray alerts = new JSONArray();
			List<Alert> unreadAlerts = processAlertFactory.getUnreadAlerts();
			for(Alert a:unreadAlerts) {
				JSONObject alert = new JSONObject();
				alert.put("id", a.getId());
				alert.put("createdAt", a.getCreatedAt());
				alert.put("title", a.getTitle());
				alert.put("description", a.getDescription());
				alerts.add(alert);
			}
			return alerts.toString();
		}
		catch(Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		return "";
	}
	
	@RemoteMethod
	public void markAlertAsRead(String alertId) {
		Alert alert = processAlertFactory.getAlert(alertId);
		processAlertFactory.markAsRead(alert);
	}
	
	@RemoteMethod
	public void markAllAlertsAsRead() {
		processAlertFactory.markAllAsRead();
	}
	
	@RemoteMethod
	public String getTasksQueue() throws JSONException {
		JSONObject tasks = new JSONObject();
		List<EstadoProceso> cola = factoryProcesos.obtenerCola();
		log.debug("  cantidad de proceso : " + cola.size());
		tasks.put("queue", this.mapeoProcesosEncolados(cola));
		return tasks.toString();
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
	
	public void setProcessAlertFactory(ProcessAlertFactory processAlertFactory) {
		this.processAlertFactory = processAlertFactory;
	}
	
	public void setFactoryProcesos(FactoryProcesos factoryProcesos) {
		this.factoryProcesos = factoryProcesos;
	}
	
}
