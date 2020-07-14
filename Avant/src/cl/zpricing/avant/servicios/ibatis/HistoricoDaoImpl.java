package cl.zpricing.avant.servicios.ibatis;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.servicios.HistoricoDao;

public class HistoricoDaoImpl extends SqlMapClientDaoSupport implements HistoricoDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());


	/* Asistencia Histórica */
	@Override
	public Integer obtenerAsistenciaHistoricaPorComplejo(Integer complejoId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("obtenerAsistenciaHistoricaPorComplejo", complejoId);
	}

	@Override
	public Integer obtenerAsistenciaHistoricaPorComplejoDesdeFecha(HashMap<String, Object> map) {
		return (Integer) getSqlMapClientTemplate().queryForObject("obtenerAsistenciaHistoricaPorComplejoDesdeFecha", map);
	}

	@Override
	public Integer obtenerAsistenciaHistoricaPorComplejoEntreFechas(HashMap<String, Object> map) {
		return (Integer) getSqlMapClientTemplate().queryForObject("obtenerAsistenciaHistoricaPorComplejoEntreFechas", map);
	}

	/* Recaudación Histórica */
	@Override
	public Integer obtenerRecaudacionHistoricaPorComplejo(Integer complejoId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("obtenerRecaudacionHistoricaPorComplejo", complejoId);

	}

	@Override
	public Integer obtenerRecaudacionHistoricaPorComplejoDesdeFecha(HashMap<String, Object> map) {
		return (Integer) getSqlMapClientTemplate().queryForObject("obtenerRecaudacionHistoricaPorComplejoDesdeFecha", map);

	}

	@Override
	public Integer obtenerRecaudacionHistoricaPorComplejoEntreFechas(HashMap<String, Object> map) {
		return (Integer) getSqlMapClientTemplate().queryForObject("obtenerRecaudacionHistoricaPorComplejoEntreFechas", map);

	}

}
