package cl.zpricing.avant.etl.scriptella;

import java.util.Date;

import org.apache.log4j.Logger;

import cl.zpricing.avant.alerts.ProcessAlert;
import cl.zpricing.avant.etl.model.ResultCompleteExecution;
import cl.zpricing.avant.etl.model.ResultEtl;
import cl.zpricing.avant.etl.variantes.EtlSessionExtraction;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.commons.utils.ErroresUtils;

public class EtlSessionExtractionImpl extends EtlSessionExtraction {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Override
	public ResultCompleteExecution sessionExtraction(Complejo complejo) {
		long id = Math.round(Math.random() * 1000);
		log.info("[" + id + "] Iniciando sessionExtraction [" + complejo.getNombre() + "]");
		ResultCompleteExecution resultExecution = new ResultCompleteExecution(complejo);
	
		boolean result = false;
		String resultMessage = "";
		try {
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(this.getCodigo(), "Starting session extraction");
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract movies");
			result = extractMovies(complejo, false);
			resultMessage = result ? "OK" : "ERROR";
			log.info("[" + id + "] Movies: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Movies", result));
			
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract sessions");
			result = extractSessions(complejo, false);
			resultMessage = result ? "OK" : "ERROR";
			log.info("[" + id + "] Sessions: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Sessions", result));
			
			result = true;
			
			if (resultExecution.isSuccessful()) {
				complejo.setUltimaCargaFunciones(new Date());
				complejoDao.actualizarComplejo(complejo);
			}
			
			log.info("[" + id + "] Fin sessionExtraction");
		} catch (Exception e) {
			resultExecution.addError(ErroresUtils.extraeStackTrace(e));
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), false);
		} finally {
			LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(),"Iteration Finished: "+complejo.getNombre());		
		}
		return resultExecution;
	}	
}
