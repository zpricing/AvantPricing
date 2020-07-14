package cl.zpricing.avant.negocio.forecastmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cl.zpricing.avant.alerts.ProcessAlert;
import cl.zpricing.avant.alerts.ProcessAlertFactory;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.LastMinuteSession;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.LogProcesosDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.ErroresUtils;

public class LastMinuteSellingManager extends Proceso {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	protected FuncionDao funcionDao;
	@Autowired
	protected ComplejoDao complejoDao;
	@Autowired
	protected MascaraDao mascaraDao;
	protected ServiciosRevenueManager serviciosRM;
	protected LogProcesosDao logProcesosDao; 
	protected LogProcesos logProcesos;
	protected ProcessAlertFactory processAlertFactory;
	protected volatile boolean iniciado=false;

	public void run() {
		iniciado = true;
		logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		StringBuffer alerta = new StringBuffer();
		LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(),"Running actualizarFuncionesLastMinuteSelling");
		
		log.info("************* EJECUTANDO RUN *************");
		log.info("Comenzando ejecucion de actualizarFuncionesLastMinuteSelling...");
		
		try {
			int activado = serviciosRM.obtenerIntParametro("LastMinuteSelling", "activado");
			int maxFuncionesDiarias = serviciosRM.obtenerIntParametro("LastMinuteSelling", "max_funciones_diarias");
			int maxPorcentajeOcupacionProyectado = serviciosRM.obtenerIntParametro("LastMinuteSelling", "max_porcentaje_ocupacion_proyectado");
			int minDiasCartelera = serviciosRM.obtenerIntParametro("LastMinuteSelling", "min_dias_cartelera");
			log.debug("Parametros:");
			log.debug("  activado : " + activado);
			log.debug("  maxFuncionesDiarias : " + maxFuncionesDiarias);
			log.debug("  maxPorcentajeOcupacionProyectado : " + maxPorcentajeOcupacionProyectado);
			log.debug("  minDiasCartelera : " + minDiasCartelera);
			
			if(activado == 1) {
				funcionDao.actualizarFuncionesLastMinuteSelling(maxFuncionesDiarias, maxPorcentajeOcupacionProyectado, minDiasCartelera);
				List<Complejo> complejos = complejoDao.complejosTodos();
				
				for (Complejo complejo : complejos) {
					log.debug("  Seleccionando funciones para complejo " + complejo.getNombre());
					Mascara mascara = mascaraDao.obtenerMascaraLastMinute(complejo.getId());
					if (mascara != null) {
						log.debug("  Mascara LastMinute Id: " + mascara.getId());
						List<LastMinuteSession> lastMinuteSessions =  funcionDao.obtenerFuncionesLastMinuteSelling(complejo.getId());
						
						List<Integer> bloquesAsignados = new ArrayList<Integer>();
						List<Integer> peliculasAsignadas = new ArrayList<Integer>();
						
						for (LastMinuteSession lastMinuteSession : lastMinuteSessions) {
							if (!bloquesAsignados.contains(lastMinuteSession.getBloque()) && !peliculasAsignadas.contains(lastMinuteSession.getGrupoPeliculaId())) {
								log.debug("    Se asigna mascara LM a funcion " + lastMinuteSession.getFuncionId());
								funcionDao.actualizaMascaraYBloquear(lastMinuteSession.getFuncionId(), mascara.getId());
								bloquesAsignados.add(lastMinuteSession.getBloque());
								peliculasAsignadas.add(lastMinuteSession.getGrupoPeliculaId());
							}
							
							if (bloquesAsignados.size() == maxFuncionesDiarias) {
								break;
							}
						}
						
						log.debug("  Cantidad de bloques asignados : " + bloquesAsignados.size());
						
						if (bloquesAsignados.size() < maxFuncionesDiarias) {
							for (LastMinuteSession lastMinuteSession : lastMinuteSessions) {
								if (!bloquesAsignados.contains(lastMinuteSession.getBloque())) {
									funcionDao.actualizaMascaraYBloquear(lastMinuteSession.getFuncionId(), mascara.getId());
									bloquesAsignados.add(lastMinuteSession.getBloque());
								}
							}
						}
					}
					else {
						alerta.append("No se encontro mascara LastMinute para complejo " + complejo.getNombre() + "<br>");
						log.warn("No se encontro mascara LastMinute para complejo " + complejo.getNombre());
					}
				}
				this.successful = true;
				LogProcesosManager.finalizado(logProcesos.getNombreProceso());
			}
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.finalizarConError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e));
		}
		iniciado = false;
		log.info("Termino de actualizarFuncionesLastMinuteSelling");
}

	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}

	public void setLogProcesos(LogProcesos logProcesos) {
		this.logProcesos = logProcesos;
	}

	public void setProcessAlertFactory(ProcessAlertFactory processAlertFactory) {
		this.processAlertFactory = processAlertFactory;
	}
	
	public boolean isEnEjecucion() {
		return iniciado;
	}

	public void setEnEjecucion(boolean estado) {
		this.iniciado = estado;
	}

	@Override
	public LogProcesos logProceso() {
		return logProcesos;
	}

	public void checkInterrupt() {
		if(Thread.currentThread().isInterrupted()) 
			throw new IllegalStateException("Interrumpido");
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}
}