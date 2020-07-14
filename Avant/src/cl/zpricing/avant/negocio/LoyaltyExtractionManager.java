package cl.zpricing.avant.negocio;


import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.BusinessIntelligenceExtractionProcessDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.util.JobExecutor;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.ErroresUtils;

public class LoyaltyExtractionManager extends Proceso {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private JobExecutor loyaltyCustomerDataExtractionJobExecutor;
	private JobExecutor loyaltyTransactionExtractionJobExecutor;
	
	@Autowired
	private ServiciosRevenueManager serviciosRevenueManagement;
	
	@Autowired
	@Qualifier("businessIntelligenceProcessLoyaltyDao")
	private BusinessIntelligenceExtractionProcessDao businessIntelligenceDao;
	
	@Override
	public void run() {
		log.info("Iniciando LoyaltyExtractionManager");
		this.logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		this.iniciado = true;
		
		try {
			log.info(" [Loyalty] Paso 1/12 Limpiando datos de tablas temporales.");
			businessIntelligenceDao.cleanTemporalData();
			
			Parametro lastCustomerLoyaltyDate = serviciosRevenueManagement.obtenerParametro("data_extraction", "loyalty_last_client");
			log.info(" [Loyalty] Paso 2/12 Extraccion de clientes de Loyalty: " + lastCustomerLoyaltyDate.getCodigo());
			
			loyaltyCustomerDataExtractionJobExecutor.getJobParametersBuilder().addString("date_from", lastCustomerLoyaltyDate.getCodigo());
			loyaltyCustomerDataExtractionJobExecutor.execute();
			
			Date ultimaFechaModificacionCliente = businessIntelligenceDao.obtenerUltimaFechaModificacionCliente();
			if (ultimaFechaModificacionCliente != null) {
				log.info("   [Loyalty] Actualizando fecha de ultima modificacion de cliente: " + DateUtils.format_yyyyMMdd_HHmmss.format(ultimaFechaModificacionCliente));
				lastCustomerLoyaltyDate.setCodigo(DateUtils.format_yyyyMMdd_HHmmss.format(ultimaFechaModificacionCliente));
				serviciosRevenueManagement.actualizarParametro(lastCustomerLoyaltyDate);
			}
			
			log.info(" [Loyalty] Paso 3/12 agregando nuevas comunas.");
			businessIntelligenceDao.agregarComunas();
			
			log.info(" [Loyalty] Paso 4/12 actualizando datos de clientes.");
			businessIntelligenceDao.actualizarDataCliente();
			
			log.info(" [Loyalty] Paso 5/12 agregando nuevos clientes.");
			businessIntelligenceDao.agregarClientesNuevos();
			
			log.info(" [Loyalty] Paso 6/12 agregando nuevos clientes.");
			businessIntelligenceDao.agregarOrigen();
			
			Parametro lastTransactionDate = serviciosRevenueManagement.obtenerParametro("data_extraction", "loyalty_last_transaction");
			log.info(" [Loyalty] Paso 7/12 Extraccion de transacciones de Loyalty con ultima fecha de extraccion : " + lastTransactionDate.getCodigo());
			loyaltyTransactionExtractionJobExecutor.getJobParametersBuilder().addString("date_from", lastTransactionDate.getCodigo());
			loyaltyTransactionExtractionJobExecutor.execute();
			
			log.info(" [Loyalty] Paso 8/12 agregando transacciones.");
			businessIntelligenceDao.agregarTransacciones();
			
			log.info(" [Loyalty] Paso 9/12 actualizando ultima fecha de transaccion procesada.");
			Date ultimaFechaTransaccion = businessIntelligenceDao.obtenerUltimaFechaTransaccion();
			
			if (ultimaFechaTransaccion != null) {
				String ultimaFecha = DateUtils.format_yyyyMMdd_HHmmss.format(ultimaFechaTransaccion);
				lastTransactionDate.setCodigo(ultimaFecha);
				serviciosRevenueManagement.actualizarParametro(lastTransactionDate);
			}
			
			log.info(" Paso 10/12 actualizando comportamiento de asistencia de clientes Loyalty.");
			businessIntelligenceDao.actualizarComportamientoAsistenciaClientes();
		      
			log.info(" [Loyalty] Paso 11/12 agregando nuevos comportamientos de asistencia de clientes.");
			businessIntelligenceDao.agregarComportamientoAsistenciaClientes();
		      
			log.info(" [Loyalty] Paso 12/12 agregando peliculas vistas por clientes Loyalty.");
			businessIntelligenceDao.agregarPeliculasVistasClientes();
			
			this.successful = true;
			LogProcesosManager.finalizado(logProcesos.getNombreProceso());
		} catch (Exception e) {
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
			log.error(ErroresUtils.extraeStackTrace(e));
		} catch (Throwable e) {
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		finally {
			log.info("Finalizando LoyaltyExtractionManager");
			this.iniciado = false;
		}
	}

	public void setLoyaltyTransactionExtractionJobExecutor(
			JobExecutor loyaltyTransactionExtractionJobExecutor) {
		this.loyaltyTransactionExtractionJobExecutor = loyaltyTransactionExtractionJobExecutor;
	}
	public void setLoyaltyCustomerDataExtractionJobExecutor(JobExecutor loyaltyCustomerDataExtractionJobExecutor) {
		this.loyaltyCustomerDataExtractionJobExecutor = loyaltyCustomerDataExtractionJobExecutor;
	}
}
