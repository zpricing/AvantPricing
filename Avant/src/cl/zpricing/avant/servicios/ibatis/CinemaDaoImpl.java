package cl.zpricing.avant.servicios.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.negocio.loadmanager.CinemaContextHolder;
import cl.zpricing.avant.servicios.CinemaDao;

public class CinemaDaoImpl extends SqlMapClientDaoSupport implements CinemaDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean existeFuncion(Complejo complejo, Funcion funcion) {
		CinemaContextHolder.setCinemaRoute(complejo.getComplejo_id_externo());
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("complejo", complejo.getComplejo_id_externo());
		mapa.put("sala", funcion.getSala().getIdExterno());
		mapa.put("funcion", funcion.getIdExterno());
		
		log.debug("  Complejo : " + complejo.getComplejo_id_externo());
		log.debug("  Sala : " + funcion.getSala().getIdExterno());
		log.debug("  Funcion : " + funcion.getIdExterno());
		
		Map<String, Object> result = (Map<String, Object>)getSqlMapClientTemplate().queryForObject("consultaFuncion", mapa);
		if (result != null && !result.isEmpty()) {
			return true;
		}
		return false;
	}
}
