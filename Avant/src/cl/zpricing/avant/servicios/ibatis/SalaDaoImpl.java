
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.servicios.SalaDao;
/**
 * <b>Implementacion DAO sobre iBatis para el manejo de salas</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17/12/2008 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class SalaDaoImpl extends SqlMapClientDaoSupport implements SalaDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	public void actualizarSala(Sala sala) {
		getSqlMapClientTemplate().update("actualizarSala", sala);
	}

	public void agregarSala(Sala sala) {
		int id = (Integer) getSqlMapClientTemplate().insert("agregarSala", sala);
		sala.setId(id);
	}

	public boolean eliminarSala(int id) {
		try{
		getSqlMapClientTemplate().delete("eliminarSala", id);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}

	public Sala obtenerSala(int id) {
		return (Sala) getSqlMapClientTemplate().queryForObject("obtenerSala", id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Sala> obtenerTodas() {
		List<Sala> users = new ArrayList<Sala>();
		users = (List<Sala>) getSqlMapClientTemplate().queryForList("obtenerTodas");
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sala> obtenerTodasByComplejo(Complejo complejo) {
		log.debug("obtenerTodasByComplejo...");
		log.debug("complejo id: " + complejo.getId());
		List<Sala> salas = new ArrayList<Sala>();
		salas = (List<Sala>) getSqlMapClientTemplate().queryForList("obtenerTodasByComplejo",complejo);
		log.debug("salas: " + salas.toString());
		return salas;
	}

	@Override
	public Sala obtenerPorIdExterno(Complejo complejo, String idExterno) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("complejo_id", complejo.getId());
		mapa.put("sala_id_externo", idExterno);
		return (Sala) getSqlMapClientTemplate().queryForObject("obtenerSalaPorIdExterno", mapa);
	}
 }
