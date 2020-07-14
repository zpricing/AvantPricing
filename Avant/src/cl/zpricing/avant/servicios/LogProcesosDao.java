package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.commons.dao.DaoGenerico;

public interface LogProcesosDao extends DaoGenerico {
	public LogProcesos obtenerProcesoExtraccionData();
	public LogProcesos obtenerProcesoAnalisisAsistencia();
	public LogProcesos obtenerProcesoActualizacionMascara();
	public LogProcesos obtenerProcesoActualizacionCupos();
	public LogProcesos obtenerProcesoExtraccionSessiones();
	public LogProcesos obtenerProcesoUpselling();
	public LogProcesos obtenerProcesoSecondSelling();
	public LogProcesos obtenerProcesoLastMinuteSelling();
	public LogProcesos obtenerProceso(String tipoProceso);
	public List<String> obtenerErrores(String tipoProceso);
	public List<LogProcesos> obtenerTodos();
}