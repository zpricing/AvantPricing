package cl.zpricing.avant.negocio.bi;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.BusinessIntelligenceProcessDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.commons.utils.ErroresUtils;

public class UpdateClientCinemaPreferences extends Proceso {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private ServiciosRevenueManager serviciosRevenueManagement;
	
	@Autowired
	private BusinessIntelligenceProcessDao businessIntelligenceDao;
	
	@Override
	public void run() {
		log.info("Iniciando ActualizarPreferenciasCineClientes");
		this.logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		this.iniciado = true;
		
		try {
			double factor = serviciosRevenueManagement.obtenerDoubleParametro("business_intelligence", "factor_ajuste_preferencia_cines");
			log.info("  factor ajuste : " + factor);
			businessIntelligenceDao.actualizarPreferenciasCineClientes(factor);
			LogProcesosManager.finalizado(logProcesos.getNombreProceso());
			this.successful = true;
		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
			this.successful = false;
		}
		finally {
			log.info("Finalizando ActualizarPreferenciasCineClientes");
			this.iniciado = false;
		}
	}
}
