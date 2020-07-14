package cl.zpricing.avant.servicios.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.restriccion.Restriction;
import cl.zpricing.avant.model.restriccion.Rule;
import cl.zpricing.avant.model.restriccion.dto.RestrictionMovie;
import cl.zpricing.avant.servicios.RestrictionDao;
import cl.zpricing.commons.dao.impl.DaoGenericoImpl;

public class RestrictionDaoImpl extends DaoGenericoImpl implements RestrictionDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	@Override
	public List<Restriction> obtenerRestricciones(Complejo complejo) {
		List<Restriction> lista = (List<Restriction>)getSqlMapClientTemplate().queryForList("obtenerRestricciones");
		
		for (Restriction restriction : lista) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("complejo_id", complejo.getId());
			params.put("restriccion_id", restriction.getId());
			log.debug("restriccion_id : " + restriction.getId());
			restriction.setReglas((List<Rule>)getSqlMapClientTemplate().queryForList("obtenerReglas", params));
		}
		return lista;
	}

	@Override
	public List<Restriction> obtenerRestricciones(Funcion funcion) {
		Map<String, Object> paramsRestriccion = new HashMap<String, Object>();
		paramsRestriccion.put("pelicula_id", funcion.getPeliculaAsociada().getId());
		paramsRestriccion.put("sala_id", funcion.getSala().getId());
		paramsRestriccion.put("distribuidor_id", funcion.getPeliculaAsociada().getDistribuidor().getId());
		//paramsRestriccion.put("dia_semana", DateUtils.obtenerRepresentacionDia(funcion.getFechaContable()));
		
		log.debug("Pelicula_id : " + funcion.getPeliculaAsociada().getId());
		log.debug("Sala_id : " + funcion.getSala().getId());
		log.debug("Distribuidor_id : " + funcion.getPeliculaAsociada().getDistribuidor().getId());
		
		List<Restriction> lista = (List<Restriction>) getSqlMapClientTemplate().queryForList("obtenerRestriccionesFuncion", paramsRestriccion);
		
		log.debug("Cantidad de Restricciones : " + lista.size());
		
		for (Restriction restriction : lista) {
			log.debug("Restriccion : " + restriction.getDescripcion());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("complejo_id", funcion.getSala().getComplejoAlCualPertenece().getId());
			params.put("restriccion_id", restriction.getId());
			params.put("pelicula_id", funcion.getPeliculaAsociada().getId());
			log.debug("  complejo_id : " + funcion.getSala().getComplejoAlCualPertenece().getId());
			log.debug("  restriccion_id : " + restriction.getId());
			log.debug("  pelicula_id : " + funcion.getPeliculaAsociada().getId());
			List<Rule> reglas = (List<Rule>) getSqlMapClientTemplate().queryForList("obtenerReglas", params);
			restriction.setReglas(reglas);
			log.debug("  Cantidad de Reglas : " + reglas.size());
		}
		return lista;
	}

	@Override
	public void asignarRestriccionPelicula(Integer peliculaId, Integer restriccionId, Date fechaHasta) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pelicula_id", peliculaId);
		params.put("restriccion_id", restriccionId);
		params.put("fecha_hasta", fechaHasta);
		
		getSqlMapClientTemplate().insert("asignarRestriccionPelicula", params);
	}

	@Override
	public List<RestrictionMovie> obtenerPeliculasConRestriccion() {
		return (List<RestrictionMovie>) getSqlMapClientTemplate().queryForList("obtenerRestriccionesPelicula");
	}

	@Override
	public int eliminarRestriccionPelicula(Integer peliculaId, Integer restriccionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pelicula_id", peliculaId);
		params.put("restriccion_id", restriccionId);
		int result = getSqlMapClientTemplate().delete("eliminarRestriccionPelicula", params);
		log.debug("  Resultado de eliminar : " + result);
		return result;
	}

}
