package cl.zpricing.avant.negocio;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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

public class CineticketExtractionManager extends Proceso {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private JobLauncher jobLauncher;
	
	private JobExecutor cineticketDataExtractionJobExecutor;
	private JobExecutor cineticketCustomerDataExtractionJobExecutor;
	
	@Autowired
	private ServiciosRevenueManager serviciosRevenueManagement;
	
	@Autowired
	@Qualifier("businessIntelligenceProcessCineticketDao")
	private BusinessIntelligenceExtractionProcessDao businessIntelligenceDao;
	
	@Override
	public void run() {
		log.info("Iniciando CineticketExtractionManager");
		this.logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		this.iniciado = true;
		
		try {
			log.info(" [Cineticket] Paso 1/11 Limpiando datos de tablas temporales.");
			businessIntelligenceDao.cleanTemporalData();
			
			Parametro lastClientDate = serviciosRevenueManagement.obtenerParametro("data_extraction", "cineticket_last_client");
			log.info(" [Cineticket] Paso 2/11 Extraccion de clientes de Cineticket: " + lastClientDate.getCodigo());
			cineticketCustomerDataExtractionJobExecutor.getJobParametersBuilder().addString("date_from", lastClientDate.getCodigo());
			cineticketCustomerDataExtractionJobExecutor.execute();

			log.info(" [Cineticket] Paso 3/11 agregando nuevas comunas.");
			businessIntelligenceDao.agregarComunas();
			
			log.info(" [Cineticket] Paso 4/11 actualizando datos de clientes.");
			businessIntelligenceDao.actualizarDataCliente();
			
			log.info(" [Cineticket] Paso 5/11 agregando nuevos clientes.");
			businessIntelligenceDao.agregarClientesNuevos();
			
			log.info(" [Cineticket] Paso 6/11 agregando origen de cliente.");
			businessIntelligenceDao.agregarOrigen();
			
			Date ultimaFechaModificacionCliente = businessIntelligenceDao.obtenerUltimaFechaModificacionCliente();
			if (ultimaFechaModificacionCliente != null) {
				log.info("   Actualizando fecha de ultima modificacion de cliente : " + DateUtils.format_yyyyMMdd_HHmmss.format(ultimaFechaModificacionCliente));
				lastClientDate.setCodigo(DateUtils.format_yyyyMMdd_HHmmss.format(ultimaFechaModificacionCliente));
				serviciosRevenueManagement.actualizarParametro(lastClientDate);
			}
	
			Parametro lastTransactionDate = serviciosRevenueManagement.obtenerParametro("data_extraction", "cineticket_last_transaction");
			log.info(" [Cineticket] Paso 7/11 Extraccion de transacciones de Cineticket con ultima fecha de extraccion : " + lastTransactionDate.getCodigo());
			cineticketDataExtractionJobExecutor.getJobParametersBuilder().addString("date_from", lastTransactionDate.getCodigo());
			cineticketDataExtractionJobExecutor.execute();
			
			log.info(" [Cineticket] Paso 8/11 agregando transacciones.");
			businessIntelligenceDao.agregarTransacciones();
			
			log.info(" [Cineticket] Paso 9/11 actualizando comportamiento de asistencia de clientes.");
			businessIntelligenceDao.actualizarComportamientoAsistenciaClientes();
		      
			log.info(" [Cineticket] Paso 10/11 agregando nuevos comportamientos de asistencia de clientes.");
			businessIntelligenceDao.agregarComportamientoAsistenciaClientes();
		      
			log.info(" [Cineticket] Paso 11/11 agregando peliculas vistas por clientes.");
			businessIntelligenceDao.agregarPeliculasVistasClientes();
			
			Date ultimaFechaTransaccion = businessIntelligenceDao.obtenerUltimaFechaTransaccion();
			if (ultimaFechaTransaccion != null) {
				String ultimaFecha = DateUtils.format_yyyyMMdd_HHmmss.format(ultimaFechaTransaccion);
				lastTransactionDate.setCodigo(ultimaFecha);
				serviciosRevenueManagement.actualizarParametro(lastTransactionDate);
			}
			
			LogProcesosManager.finalizado(logProcesos.getNombreProceso());
			this.successful = true;
		} catch (JobExecutionAlreadyRunningException e) {
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
			log.error(ErroresUtils.extraeStackTrace(e));
		} catch (JobRestartException e) {
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
			log.error(ErroresUtils.extraeStackTrace(e));
		} catch (JobInstanceAlreadyCompleteException e) {
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
			log.error(ErroresUtils.extraeStackTrace(e));
		} catch (Exception e) {
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
			log.error(ErroresUtils.extraeStackTrace(e));
		} catch (Throwable e) {
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		finally {
			log.info("Finalizando CineticketExtractionManager");
			this.iniciado = false;
		}
	}

	public void setCineticketDataExtractionJobExecutor(
			JobExecutor cineticketDataExtractionJobExecutor) {
		this.cineticketDataExtractionJobExecutor = cineticketDataExtractionJobExecutor;
	}
	public void setCineticketCustomerDataExtractionJobExecutor(
			JobExecutor cineticketCustomerDataExtractionJobExecutor) {
		this.cineticketCustomerDataExtractionJobExecutor = cineticketCustomerDataExtractionJobExecutor;
	}
}
