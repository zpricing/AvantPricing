package cl.zpricing.avant.negocio.bi;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.BusinessIntelligenceProcessDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.util.JobExecutor;
import cl.zpricing.commons.utils.ErroresUtils;

public class LoadBusinessIntelligenceData extends Proceso {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private ServiciosRevenueManager serviciosRevenueManagement;
	
	@Autowired
	private BusinessIntelligenceProcessDao businessIntelligenceDao;
	
	private JobExecutor loadClustersjobExecutor;
	private JobExecutor loadClientsjobExecutor;
	
	@Autowired
	@Qualifier("mongoIzyTemplate")
	private MongoOperations mongoOperation;
	
	@Override
	public void run() {
		log.info("Iniciando LoadBusinesIntelligence data");
		this.logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		this.iniciado = true;
		try {
			this.buildClusters();
			businessIntelligenceDao.updateClientsCluster();
			businessIntelligenceDao.updateMoviesCluster();
			this.deleteRemoteClusters();
			loadClustersjobExecutor.execute();
			loadClientsjobExecutor.execute();
			log.info("  Ejecutando command updateClientTags.");
			mongoOperation.executeCommand("{'eval': 'updateClientTags()', 'nolock' : 'true'}");
			this.successful = true;
			LogProcesosManager.finalizado(logProcesos.getNombreProceso());
		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.finalizarConError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e));
			this.successful = false;
		} 
		finally {
			this.iniciado = false;
		}
	}
	
	public void buildClusters() throws Exception {
		double minimumWeightToConsider = serviciosRevenueManagement.obtenerDoubleParametro("business_intelligence", "factor_minimo_preferencia_categoria");
		log.info("  Minimun Weight To Consider : " + minimumWeightToConsider);
		log.info("  Updating clients per geographical zone.");
		
		log.info("  Updating clusters.");
		businessIntelligenceDao.buildClusters(minimumWeightToConsider);	
	}
	
	private void deleteRemoteClusters() {
		log.debug("  Deleting current clusters.");
		mongoOperation.remove(new Query(Criteria.where("name").is("Todos por Zona Geografica por Categoria")), "segments");
		mongoOperation.remove(new Query(Criteria.where("name").is("Todos por Zona Geografica")), "segments");
	}

	public void setLoadClustersjobExecutor(JobExecutor loadClustersjobExecutor) {
		this.loadClustersjobExecutor = loadClustersjobExecutor;
	}
	public void setLoadClientsjobExecutor(JobExecutor loadClientsjobExecutor) {
		this.loadClientsjobExecutor = loadClientsjobExecutor;
	}
}
