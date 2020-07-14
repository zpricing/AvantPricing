package cl.zpricing.avant.etl.variantes;

import cl.zpricing.avant.etl.EtlManager;
import cl.zpricing.avant.etl.model.ResultCompleteExecution;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;

public class EtlCinemaExtractionImpl extends EtlManager {
	private Complejo complejo;
	private boolean isFullHistoryDataExtraction;

	@Override
	public void run() {
		log.info("Iniciando CinemaExtraction");
		if (complejo == null) {
			log.error("  Cinema is missing");
		}
		else {
			this.iniciado = true;
			this.logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
			this.logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Complete Extraction");
			
			ResultCompleteExecution result = this.completeExtraction(complejo, this.isFullHistoryDataExtraction);
		}
	}

	public void setComplejo(Complejo complejo) {
		this.complejo = complejo;
	}
	public void setFullHistoryDataExtraction(boolean isFullHistoryDataExtraction) {
		this.isFullHistoryDataExtraction = isFullHistoryDataExtraction;
	}
}
