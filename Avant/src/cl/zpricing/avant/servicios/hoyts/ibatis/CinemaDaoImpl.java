package cl.zpricing.avant.servicios.hoyts.ibatis;

import java.util.List;

import org.apache.log4j.Logger;

import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.model.loadmanager.AreaCount;
import cl.zpricing.commons.utils.ErroresUtils;


public class CinemaDaoImpl extends cl.zpricing.avant.servicios.ibatis.CinemaDaoImpl implements cl.zpricing.avant.servicios.hoyts.CinemaDao {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public String consultaNombreCinema() {
		return (String) getSqlMapClientTemplate().queryForObject("consultaNombreComplejo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AreaCount> consultaCapacidadFuncion(String idFuncion) {
		log.debug("consultaCapacidadFuncion");
		log.debug("    idFuncion : [" + idFuncion + "]");
		return getSqlMapClientTemplate().queryForList("consultaCapaciadadesArea", idFuncion);
	}

	@Override
	public Integer consultaSalaFuncion(String idFuncion) {
		return (Integer)getSqlMapClientTemplate().queryForObject("consultaSalaFuncion", idFuncion);
	}

	@Override
	public String consultaEstadoFuncion(String idFuncion) {
		return (String)getSqlMapClientTemplate().queryForObject("consultaEstadoFuncion", idFuncion);
	}

	@Override
	public Integer consultaOrdenesEnProceso(String idFuncion) {
		return (Integer)getSqlMapClientTemplate().queryForObject("consultaOrdenesEnProceso", idFuncion);
	}

	@Override
	public Integer consultaLayoutSala(Sala sala) {
		Integer resultado = null;
		try {
			resultado = (Integer)getSqlMapClientTemplate().queryForObject("consultaLayoutSala", sala.getIdExterno());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		return resultado;
	}

}
