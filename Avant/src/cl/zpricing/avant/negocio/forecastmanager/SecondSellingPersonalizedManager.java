package cl.zpricing.avant.negocio.forecastmanager;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import cl.zpricing.avant.alerts.ProcessAlert;
import cl.zpricing.avant.alerts.ProcessAlertFactory;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.commons.utils.ErroresUtils;

public class SecondSellingPersonalizedManager extends Proceso {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private JobLauncher jobLauncher;
	private Job job;
	
	protected LogProcesos logProcesos;
	protected ProcessAlertFactory processAlertFactory;
	
	protected FuncionDao funcionDao;
	
	protected volatile boolean iniciado = false;
	
	@Override
	public void run() {
		this.iniciado = true;
		log.info("************* EJECUTANDO RUN *************");
		logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		
		try {
			funcionDao.actualizarFuncionesSecondSellingClientSuggestions();
			log.info("  Ejecutando Job : " + job.getName());
			jobLauncher.run(job, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
			log.info("Finalizando carga de datos de Second Selling personalizado.");
		}
		catch(Exception e) {
			ProcessAlert alert = (ProcessAlert) processAlertFactory.makeAlert(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e));
			alert.notifyAlert();
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e),false);
		}
		
		iniciado = false;
		LogProcesosManager.finalizado(logProcesos.getNombreProceso());
	}
	
	@Override
	public boolean isEnEjecucion() {
		return iniciado;
	}
	@Override
	public void setEnEjecucion(boolean estado) {
		this.iniciado = estado;
	}
	@Override
	public LogProcesos logProceso() {
		return this.logProcesos;
	}
	@Override
	public void checkInterrupt() {
		if(Thread.currentThread().isInterrupted()) 
			throw new IllegalStateException("Interrumpido");
	}


	public void setLogProcesos(LogProcesos logProcesos) {
		this.logProcesos = logProcesos;
	}
	public void setProcessAlertFactory(ProcessAlertFactory processAlertFactory) {
		this.processAlertFactory = processAlertFactory;
	}
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
}
