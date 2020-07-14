
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.GrupoComplejo;
import cl.zpricing.avant.model.RptComplejo;
import cl.zpricing.avant.model.reports.SemanaNielsen;
import cl.zpricing.commons.exceptions.DaoException;

/**
 * 
 * Implementacion DAO para iBatis para el objeto Complejo
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17/12/2008 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class ComplejoDaoImpl extends SqlMapClientDaoSupport implements cl.zpricing.avant.servicios.ComplejoDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ComplejoDao#actualizarComplejo(cl.zpricing.revman.model.Complejo)
	 */
	public void actualizarComplejo(Complejo complejo) {
		if (complejo.getRptComplejo() == null) {
			RptComplejo rptComplejoNulo = new RptComplejo();
			rptComplejoNulo.setRptComplejoId(0);
			complejo.setRptComplejo(rptComplejoNulo);
		}
		getSqlMapClientTemplate().update("actualizarComplejo", complejo);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ComplejoDao#eliminarComplejo(cl.zpricing.revman.model.Complejo)
	 */
	public boolean eliminarComplejo(Complejo Complejo) {
		try{
			getSqlMapClientTemplate().delete("eliminarComplejo", Complejo);
			}catch(Exception e)
			{
			return false;
			}
			return true;
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ComplejoDao#agregarComplejo(cl.zpricing.revman.model.Complejo)
	 */
	public void agregarComplejo(Complejo complejo) {
		int id = (Integer) getSqlMapClientTemplate().insert("agregarComplejo", complejo);
		complejo.setId(id);
	}

	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ComplejoDao#obtenerComplejo(int)
	 */
	public Complejo obtenerComplejo(int id) {
		
		Complejo complejo = (Complejo) getSqlMapClientTemplate().queryForObject("obtenerComplejo", id);
		
		return complejo;
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ComplejoDao#complejosTodos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Complejo> complejosTodos() {
		return (List<Complejo>) getSqlMapClientTemplate().queryForList("complejoTodos");
	}
	
	@SuppressWarnings("unchecked")
	public List<RptComplejo> obtenerRptComplejosTodos() {
		return (List<RptComplejo>) getSqlMapClientTemplate().queryForList("obtenerRptComplejoTodos");
	}
	
	public RptComplejo obtenerRptComplejo(int id) {
		return (RptComplejo) getSqlMapClientTemplate().queryForObject("obtenerRptComplejo", new Integer(id));
	}

	@Override
	public void editarCantidadSalasComplejo(RptComplejo rptComplejo) {
		getSqlMapClientTemplate().update("editarCantidadSalasComplejo",rptComplejo);
		
	}

	@Override
	public void editarCiudadComplejo(RptComplejo rptComplejo) {
		getSqlMapClientTemplate().update("editarCiudadComplejo",rptComplejo);
		
	}

	@Override
	public void editarNombreComplejo(RptComplejo rptComplejo) {
		getSqlMapClientTemplate().update("editarNombreComplejo",rptComplejo);
		
	}

	@Override
	public void editarIdComplejo(HashMap<String, Object> map) {
		getSqlMapClientTemplate().update("editarIdComplejo", map);
		
	}

	@Override
	public void editarEmpresaComplejo(RptComplejo rptComplejo) {
		getSqlMapClientTemplate().update("editarEmpresaComplejo", rptComplejo);
		
	}

	@Override
	public void eliminarRptComplejo(RptComplejo rptComplejo) {
		getSqlMapClientTemplate().delete("eliminarRptComplejo", rptComplejo);
		
	}

	@Override
	public void agregarRptComplejo(RptComplejo rptComplejo) {
		getSqlMapClientTemplate().insert("agregarRptcomplejo", rptComplejo);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void editarRmComplejo(RptComplejo rptComplejo) {
		HashMap map = new HashMap();
		map.put("rm", new Boolean(rptComplejo.isRm()));
		map.put("rptComplejoId", new Integer(rptComplejo.getRptComplejoId()));
		map.put("fechaDesde", rptComplejo.getFechaDesde());
		log.debug("Ingresando a editarRmComplejo con los siguientes parámetros:");
		log.debug(map.get("rm"));
		log.debug(map.get("rptComplejoId"));
		log.debug(map.get("fechaDesde"));
		getSqlMapClientTemplate().update("editarRmComplejo", map);
		
	}

	@Override
	public Integer obtenerRptComplejoIdMasAlto() {
		return (Integer) getSqlMapClientTemplate().queryForObject("obtenerRptComplejoIdMasAlto");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<SemanaNielsen> obtenerSemanasNielsen() {
		return (ArrayList<SemanaNielsen>) getSqlMapClientTemplate().queryForList("obtenerSemanasNielsen");
	}

	@Override
	public void eliminarSemanaNielsen(Date fecha) {
		getSqlMapClientTemplate().delete("eliminarSemanaNielsen", fecha);
		
	}

	@Override
	public Complejo obtenerComplejoPorIdExterno(String idExterno) throws DaoException {
		Complejo complejo = (Complejo)getSqlMapClientTemplate().queryForObject("obtenerComplejoPorIdExterno", idExterno);
		if (complejo == null) {
			throw new DaoException("Complejo [" + idExterno + "] no encontrado.");
		}
		return complejo; 
	}
	
	@Override
	public void agregaRelacionGrupoComplejo(Complejo complejo, GrupoComplejo grupo){
		Map<String, Integer> param= new HashMap<String, Integer>(2);
		param.put("id", complejo.getId());
		param.put("idGrupo", grupo.getId());
		getSqlMapClientTemplate().insert("agregaRelacionGrupoComplejo", param);
	}
 }
