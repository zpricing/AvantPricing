package cl.zpricing.avant.negocio.forecastmanager;

import java.util.Date;

import org.apache.log4j.Logger;

import cl.zpricing.avant.alerts.ProcessAlert;
import cl.zpricing.avant.alerts.ProcessAlertFactory;
import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.LogProcesosDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.ErroresUtils;

public class SecondSellingManager extends Proceso {
protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	protected FuncionDao funcionDao;
	protected ServiciosRevenueManager serviciosRM;
	protected LogProcesosDao logProcesosDao; 
	protected LogProcesos logProcesos;
	protected ProcessAlertFactory processAlertFactory;
	protected volatile boolean iniciado=false;
	
	public void run() {
		iniciado = true;
		logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(),"Running actualizarFuncionesSecondSelling");

		log.info("************* EJECUTANDO RUN *************");
		log.info("Comenzando ejecucion de actualizarFuncionesSecondSelling...");
		
		try {
			funcionDao.actualizarFuncionesSecondSelling();
		
			Parametro parametro = new Parametro();
			parametro.setSistema("SecondSellingManager");
			parametro.setSubSistema("ultimaActualizacion");
			parametro.setCodigo(DateUtils.format_EEE_ddMMyyyy_HHmm.format(new Date()));
			serviciosRM.actualizarParametro(parametro);
		}
		catch (Exception e) {
			ProcessAlert alert = (ProcessAlert) processAlertFactory.makeAlert(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e));
			alert.notifyAlert();
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e),true);
		}
		
		log.info("Termino de actualizarFuncionesSecondSelling");
		LogProcesosManager.finalizado(logProcesos.getNombreProceso());
		iniciado = false;
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
}
