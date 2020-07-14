package cl.zpricing.avant.negocio.sincronizador;

import java.util.List;

import org.apache.log4j.Logger;

import cl.zpricing.avant.servicios.ibatis.LogProcesosDaoImpl;
import cl.zpricing.commons.exceptions.DaoGenericoException;
import cl.zpricing.commons.utils.ErroresUtils;

public class LogProcesosManager {
	private static LogProcesosDaoImpl logProcesosDao = new LogProcesosDaoImpl();
	private static Logger log = (Logger) Logger.getLogger(LogProcesosDaoImpl.class);
	
	public List<String> obtenerErrores(LogProcesos proceso){
		return logProcesosDao.obtenerErrores(proceso.getNombreProceso());
	}
	
	public static void forzarTerminoProceso(String nombreProceso){
		LogProcesos logProcesos = logProcesosDao.obtenerProceso(nombreProceso);
		finalizado(logProcesos.getNombreProceso());	
	}
	
	public static LogProcesos inicioProceso(String tipoProceso) {
		log.debug("Iniciando inicioProceso");
		LogProcesos logProceso = new LogProcesos();
		logProceso.setEstado(LogProcesos.getEnEjecucion());
		logProceso.setEstadoSubetapa(LogProcesos.getIniciando());
		logProceso.setNombreProceso(tipoProceso);
		
		try {
			logProcesosDao.insertar(logProceso);
		} catch (DaoGenericoException e) {
			logProceso.setError("Error con DAOGenerico");
		}
		return logProceso;
	}
	
	public static LogProcesos cambioDeEtapa(String tipoProceso, String subetapa) {
		LogProcesos logProcesos = logProcesosDao.obtenerProceso(tipoProceso);
		logProcesos.setError(null);
		logProcesos.setEstadoSubetapa(subetapa);
		try {
			logProcesosDao.insertar(logProcesos);
		} 
		catch (DaoGenericoException e) {
			logProcesos.setError("Error con DAOGenerico");
		}
		
		return logProcesos;
	}
	
	public static LogProcesos finalizarConError(String nombreProceso, String error) {
		return LogProcesosManager.cambioError(nombreProceso, error, true);
	}
	
	public static LogProcesos cambioError(String tipoProceso, String error, boolean finalizar) {
		LogProcesos logProcesos = logProcesosDao.obtenerProceso(tipoProceso);
		logProcesos.setError(error);
		logProcesos.setTieneError("true");
		if(finalizar){
			logProcesos.setEstado(LogProcesos.getFinalizado());
			logProcesos.setEstadoSubetapa(LogProcesos.getFinalizado());
		}
		try {
			logProcesosDao.insertar(logProcesos);
		} catch (DaoGenericoException e) {
			logProcesos.setError("Error con DAOGenerico");
		}
		finally{
			logProcesos.setError(null);
		}
		
		return logProcesos;
	}
	
	public static LogProcesos finalizado(String tipoProceso){
		LogProcesos logProcesos = logProcesosDao.obtenerProceso(tipoProceso);
		logProcesos.setEstado(LogProcesos.getFinalizado());
		logProcesos.setEstadoSubetapa(LogProcesos.getFinalizado());
		try {
			logProcesosDao.insertar(logProcesos);
		} catch (DaoGenericoException e) {
			logProcesos.setError("Error con DAOGenerico");
		}
		
		return logProcesos;
	}
	
	
	
	public void setLogProcesosDao(LogProcesosDaoImpl logProcesosDao) {
		LogProcesosManager.logProcesosDao = logProcesosDao;
	}

	public static LogProcesosDaoImpl getLogProcesosDao() {
		return logProcesosDao;
	}
}
