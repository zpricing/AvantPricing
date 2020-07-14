package cl.zpricing.avant.etl.scriptella;

import org.apache.log4j.Logger;

import scriptella.execution.EtlExecutor;
import scriptella.execution.EtlExecutorException;
import scriptella.execution.ExecutionStatistics;
import scriptella.interactive.ConsoleProgressIndicator;
import scriptella.interactive.ProgressIndicator;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.commons.utils.ErroresUtils;

public class EtlExecutorProcess extends Proceso {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	protected volatile boolean iniciado = false;
	protected LogProcesos logProcesos;
	protected boolean activated;
	
	protected EtlExecutor etlExecutor;
	
	public void run() {
		if (activated) {
			log.debug("INICIANDO RUN");
			iniciado = true;
			logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
			try {
				ProgressIndicator indicator = new ConsoleProgressIndicator();
				etlExecutor.execute(indicator);
				log.debug("termino de ejecucion.");
				LogProcesosManager.finalizado(this.getCodigo());
			} catch (EtlExecutorException e) {
				log.error(ErroresUtils.extraeStackTrace(e));
				logProcesos = LogProcesosManager.cambioError(this.getCodigo(), ErroresUtils.extraeStackTrace(e), true);
			}
		}
		else {
			log.debug("Cineticket ETL deactivated");
		}
	}

	@Override
	public boolean isEnEjecucion() {
		return this.iniciado;
	}

	@Override
	public void setEnEjecucion(boolean estado) {
		this.iniciado = estado;
	}

	@Override
	public LogProcesos logProceso() {
		return this.logProcesos;
	}

	@Override
	public void checkInterrupt() {
		
	}
	public void setEtlExecutor(EtlExecutor etlExecutor) {
		this.etlExecutor = etlExecutor;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
