package cl.zpricing.avant.etl.variantes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cl.zpricing.avant.alerts.ProcessAlert;
import cl.zpricing.avant.etl.EtlManager;
import cl.zpricing.avant.etl.model.ResultCompleteExecution;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;

public abstract class EtlSessionExtraction extends EtlManager{

	public abstract ResultCompleteExecution sessionExtraction(Complejo complejo);
	
	public void run(){

		logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		iniciado = true;
		log.info("Iniciando executeSessionExtraction");
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(),"Session Extraction (SE)");
		
		List<Complejo> complejos = complejoDao.complejosTodos();
		List<ResultCompleteExecution> resultList = new ArrayList<ResultCompleteExecution>();
		
		for (Complejo complejo : complejos) {
			if (complejo.getUltimaCargaCompleta() != null) {
				ResultCompleteExecution result = this.sessionExtraction(complejo);
				resultList.add(result);
			}
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

		logProcesos = LogProcesosManager.finalizado(logProcesos.getNombreProceso());

	}

}
