package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.servicios.MascaraDao;

/**
 * Implementacion para iBatis de MascaraDao
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 01-02-2009 MARIO: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class MascaraDaoImpl extends SqlMapClientDaoSupport implements MascaraDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.MascaraDao#obtenerMascarasSala(cl.zpricing
	 * .revman.model.Sala)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Mascara> obtenerMascarasSala(Sala sala) {
		//TODO cambiar por complejo en el parametro
		return (List<Mascara>) getSqlMapClientTemplate().queryForList("obtenerMascaras", sala.getComplejoAlCualPertenece().getId());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Mascara> obtenerMascaras(Complejo complejo) {
		return (List<Mascara>) getSqlMapClientTemplate().queryForList("obtenerMascaras", complejo.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.MascaraDao#obtenerMascarasSalaActivos(cl
	 * .zpricing.revman.model.Sala)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Mascara> obtenerMascarasSalaActivos(Sala sala) {
		return (List<Mascara>) getSqlMapClientTemplate().queryForList("obtenerMascarasSalaActivos", sala.getComplejoAlCualPertenece().getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.MascaraDao#obtenerMascarasSalaDefault(cl
	 * .zpricing.revman.model.Sala)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Mascara> obtenerMascarasSalaDefault(Sala sala) {

		return (ArrayList<Mascara>) getSqlMapClientTemplate().queryForList("obtenerMascarasSalaDefault", sala.getComplejoAlCualPertenece().getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.MascaraDao#obtenerMascaraSala(cl.zpricing
	 * .revman.model.Sala, int)
	 */
	@Override
	public Mascara obtenerMascaraSala(Sala sala, int mascaraId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("complejoId", sala.getComplejoAlCualPertenece().getId());
		map.put("mascaraId", mascaraId);
		log.debug("salaId: " + sala.getId() + " - mascaraId: " + mascaraId);
		return (Mascara) getSqlMapClientTemplate().queryForObject("obtenerMascaraSala", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.MascaraDao#obtenerAreasComplejo(cl.zpricing
	 * .revman.model.Complejo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Area> obtenerAreasComplejo(Complejo complejo) {
		return (ArrayList<Area>) getSqlMapClientTemplate().queryForList("obtenerAreasComplejo", complejo.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.MascaraDao#actualizarMascara(cl.zpricing
	 * .revman.model.Mascara)
	 */
	@Override
	public void actualizarMascara(Mascara mascara) {
		getSqlMapClientTemplate().update("actualizarMascara", mascara);
	}
	
	@Override
    public Mascara obtenerMascara(int idMascara) {
          return (Mascara)getSqlMapClientTemplate().queryForObject("obtenerMascara", idMascara);
    }

	@Override
	public Mascara obtenerMascaraCargadaParaUnaFuncion(Funcion funcionPredecida) {
		log.debug("Se pidio obtener la mascara cargada para la funcion " + funcionPredecida.getId());
		
		Mascara mascaraAsiganda = null;
		try {
			mascaraAsiganda = (Mascara)getSqlMapClientTemplate().queryForObject("obtenerMascaraCargadaParaUnaFuncion", new Integer(funcionPredecida.getId()));
			log.debug("La mascara obtenida es " + mascaraAsiganda.getDescripcion());
		} catch (Exception e) {
			log.debug("No se pudo obtener la mascara");
		}
		return mascaraAsiganda;
	}

	@Override
	public Mascara obtenerMascara(Sala sala, String mascaraDescripcion) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("complejo_id", sala.getComplejoAlCualPertenece().getId());
		mapa.put("mascara", mascaraDescripcion);
		return (Mascara) getSqlMapClientTemplate().queryForObject("obtenerMascaraPorSalaDescripcion", mapa);
	}
	
	@Override
	public void agregar(Mascara mascara) {
		Integer mascaraId = (Integer) getSqlMapClientTemplate().insert("guardarMascara", mascara);
		mascara.setId(mascaraId);
	}

	@Override
	public Mascara obtenerMascaraLastMinute(int complejoId) {
		return (Mascara) getSqlMapClientTemplate().queryForObject("obtenerMascaraLastMinute", complejoId);
	}

}
