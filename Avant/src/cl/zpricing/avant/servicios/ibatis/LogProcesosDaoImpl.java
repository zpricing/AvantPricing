package cl.zpricing.avant.servicios.ibatis;

import java.util.List;

import org.apache.log4j.Logger;

import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.servicios.LogProcesosDao;
import cl.zpricing.commons.dao.impl.DaoGenericoImpl;


public class LogProcesosDaoImpl extends DaoGenericoImpl implements LogProcesosDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	public LogProcesos obtenerProcesoExtraccionData() {
		return (LogProcesos) getSqlMapClientTemplate().queryForObject("obtenerProcesoExtraccionData");
	}
	public LogProcesos obtenerProcesoAnalisisAsistencia() {
		return (LogProcesos) getSqlMapClientTemplate().queryForObject("obtenerProcesoAnalisisAsistencia");
	}
	public LogProcesos obtenerProcesoActualizacionMascara() {
		return (LogProcesos) getSqlMapClientTemplate().queryForObject("obtenerProcesoActualizacionMascara");
	}
	public LogProcesos obtenerProcesoActualizacionCupos() {
		return (LogProcesos) getSqlMapClientTemplate().queryForObject("obtenerProcesoActualizacionCupos");
	}
	public LogProcesos obtenerProcesoExtraccionSessiones() {
		return (LogProcesos) getSqlMapClientTemplate().queryForObject("obtenerProcesoExtraccionSessiones");
	}
	public LogProcesos obtenerProcesoUpselling() {
		return (LogProcesos) getSqlMapClientTemplate().queryForObject("obtenerProcesoUpselling");
	}
	public LogProcesos obtenerProcesoSecondSelling() {
		return (LogProcesos) getSqlMapClientTemplate().queryForObject("obtenerProcesoSecondSelling");
	}
	public LogProcesos obtenerProcesoLastMinuteSelling() {
		return (LogProcesos) getSqlMapClientTemplate().queryForObject("obtenerProcesoLastMinuteSelling");
	}
	public LogProcesos obtenerProceso(String tipoProceso) {
		log.debug("obteniendo proceso : " + tipoProceso);
		return (LogProcesos) getSqlMapClientTemplate().queryForObject("obtenerProceso", tipoProceso);
	}
	public List<String> obtenerErrores(String tipoProceso) {
		return (List<String>) getSqlMapClientTemplate().queryForList("obtenerErrores", tipoProceso);
	}
	@Override
	public List<LogProcesos> obtenerTodos() {
		return (List<LogProcesos>) getSqlMapClientTemplate().queryForList("obtenerTodosLogProcesos");
	}
}
