package cl.zpricing.avant.negocio.forecastmanager;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.CommandResult;

import cl.zpricing.avant.alerts.ProcessAlert;
import cl.zpricing.avant.alerts.ProcessAlertFactory;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.LogProcesosDao;
import cl.zpricing.commons.utils.ErroresUtils;

public class AvailabilityManager extends Proceso {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoOperations mongoOperation;
	@Autowired
	private FuncionDao funcionDao;
	
	private JobLauncher jobLauncher;
	private Job job;
	protected LogProcesosDao logProcesosDao; 
	protected LogProcesos logProcesos;
	protected ProcessAlertFactory processAlertFactory;
	protected volatile boolean iniciado = false;
	
	public void run() {
		iniciado = true;
		log.info("Iniciando Carga de Cupos AvantOrs");
		logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		
		try {
			if (jobLauncher == null) {
				log.debug("  jobLauncher is null");
			}
			if (job == null) {
				log.debug("  job is null");
			}
			log.debug("  Antes de ejecucion de job");
			log.debug("  Job Name: " + job.getName());
			jobLauncher.run(job, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
			
			log.info("  Marcando Funciones como Cargadas");
			funcionDao.setCuposCargadosFuncionesFuturas();
			
			log.info("  Iniciando expiracion de cupos.");
			CommandResult result = mongoOperation.executeCommand("{'eval': 'expireAvailability()', 'nolock' : 'true'}");
			log.info("  Funciones analizadas para expiracion de cupos: " + result.getInt("retval"));
			LogProcesosManager.finalizado(logProcesos.getNombreProceso());
			this.successful = true;
			log.info("Finalizando Carga de Cupos AvantOrs");
		}
		catch(Exception e) {
			ProcessAlert alert = (ProcessAlert) processAlertFactory.makeAlert(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e));
			alert.notifyAlert();
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
		}
		
		iniciado = false;
	}
	
	public void setLogProcesosDao(LogProcesosDao logProcesosDao) {
		this.logProcesosDao = logProcesosDao;
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

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	
	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
	
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
}
