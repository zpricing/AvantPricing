package cl.zpricing.avant.negocio.bi;


import org.apache.log4j.Logger;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;

import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.BusinessIntelligenceProcessDao;
import cl.zpricing.avant.util.JobExecutor;
import cl.zpricing.commons.utils.ErroresUtils;

public class LoadClusters extends Proceso {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private JobLauncher jobLauncher;
	
	private JobExecutor loadClustersjobExecutor;
	
	@Autowired
	private BusinessIntelligenceProcessDao businessIntelligenceDao;
	
	@Autowired
	@Qualifier("mongoIzyTemplate")
	private MongoOperations mongoOperation;

	@Override
	public void run() {
		iniciado = true;
		log.info("Init LoadClusters");
		logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		
		try {
			businessIntelligenceDao.updateMoviesCluster();
			loadClustersjobExecutor.execute();
			this.successful = true;
			LogProcesosManager.finalizado(logProcesos.getNombreProceso());
			log.info("Finalizing LoadClusters");
		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.finalizarConError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e));
		} finally {
			iniciado = false;
		}
	}

	public void setLoadClustersjobExecutor(JobExecutor loadClustersjobExecutor) {
		this.loadClustersjobExecutor = loadClustersjobExecutor;
	}

}
