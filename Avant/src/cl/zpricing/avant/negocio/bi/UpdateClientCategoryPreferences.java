package cl.zpricing.avant.negocio.bi;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.BusinessIntelligenceProcessDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.commons.utils.ErroresUtils;

public class UpdateClientCategoryPreferences extends Proceso {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private ServiciosRevenueManager serviciosRevenueManagement;
	
	@Autowired
	private BusinessIntelligenceProcessDao businessIntelligenceDao;

	@Override
	public void run() {
		log.info("Starting UpdateClientCategoryPreferences");
		this.logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		this.iniciado = true;
		
		try {
			double adjustmentFactor = serviciosRevenueManagement.obtenerDoubleParametro("business_intelligence", "factor_ajuste_preferencia_categoria");
			double miniumWeightToConsider = serviciosRevenueManagement.obtenerDoubleParametro("business_intelligence", "factor_minimo_preferencia_categoria");
			log.info("  adjustmentFactor : " + adjustmentFactor);
			log.info("  miniumWeightToConsider : " + miniumWeightToConsider);
			businessIntelligenceDao.updateCategoriesAttendanceWeight();
			businessIntelligenceDao.updateClientCategoryPreferences(adjustmentFactor, miniumWeightToConsider);
			LogProcesosManager.finalizado(logProcesos.getNombreProceso());
			this.successful = true;
		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
			this.successful = false;
		}
		finally {
			log.info("Finishing UpdateClientCategoryPreferences");
			this.iniciado = false;
		}
	}

}
