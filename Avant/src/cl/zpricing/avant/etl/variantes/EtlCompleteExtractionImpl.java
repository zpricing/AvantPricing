package cl.zpricing.avant.etl.variantes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cl.zpricing.avant.alerts.ProcessAlert;
import cl.zpricing.avant.etl.EtlManager;
import cl.zpricing.avant.etl.model.ResultCompleteExecution;
import cl.zpricing.avant.etl.model.ResultEtl;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.ErroresUtils;

public class  EtlCompleteExtractionImpl extends EtlManager {
	
	public void run() {
		log.info("Iniciando executeCompleteExtraction");
		iniciado = true;
		logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(),"Complete Extraction (CE)");
		
		List<Complejo> complejos = complejoDao.complejosTodos();
		List<ResultCompleteExecution> resultList = new ArrayList<ResultCompleteExecution>();
		
		for (Complejo complejo : complejos) {
			boolean extractionType = complejo.getUltimaCargaCompleta() == null || DateUtils.cantidadDeDias(complejo.getUltimaCargaCompleta(), new Date()) >= 5;
			ResultCompleteExecution result = this.completeExtraction(complejo, extractionType);
			resultList.add(result);
		}
		
		checkInterrupt();
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Updating Movie Groups");
		ResultCompleteExecution resultExecutionGroupMovies = new ResultCompleteExecution();
		try {
			boolean result = false;
			long id = Math.round(Math.random() * 1000);
			result = groupMovies();
			String resultMessage = result ? "OK" : "ERROR";
			log.info("groupMovies: " + resultMessage);
			log.info("  [" + id + "] groupMovies: " + resultMessage);
			resultExecutionGroupMovies.addResultEtl(new ResultEtl("groupMovies", result));
		} catch (Exception e) {
			resultExecutionGroupMovies.addError(ErroresUtils.extraeStackTrace(e));
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
		}
		finally {
			resultList.add(resultExecutionGroupMovies);
		}
		
		Iterator<ResultCompleteExecution> it = resultList.iterator();
		String msgError = "";
		String nl = System.getProperty("line.separator");
		while(it.hasNext()) {
			ResultCompleteExecution result = (ResultCompleteExecution) it.next();
			Complejo complejo = result.getComplejo();
			if(result.getErrors().size() > 0) {
				msgError += "[" + (complejo == null ? "All" : complejo.getNombre()) + "]" + nl;
			}
		}
		if(!msgError.equals("")) {
			ProcessAlert alert = (ProcessAlert) processAlertFactory.makeAlert(logProcesos.getNombreProceso(), "Fallo en la extraccion de data de los complejos: " + nl + msgError);
			alert.notifyAlert();
		}
		
		Parametro parametro = serviciosRM.obtenerParametro("etl", "ultimaEjecucion");
		parametro.setCodigo(DateUtils.format_EEE_ddMMyyyy_HHmm.format(new Date()));
		serviciosRM.actualizarParametro(parametro);
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Sending E-Mail: ");
		logProcesos = LogProcesosManager.finalizado(logProcesos.getNombreProceso());
	}	
	
	
}
