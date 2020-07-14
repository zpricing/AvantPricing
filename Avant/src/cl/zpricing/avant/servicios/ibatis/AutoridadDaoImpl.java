/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Autoridad;
import cl.zpricing.avant.model.Rol;
import cl.zpricing.avant.servicios.AutoridadDao;

/**
 * <b>Descripci�n de la Clase</b>
 * Implementacion de AutoridadDao
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 29-12-2008 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class AutoridadDaoImpl extends SqlMapClientDaoSupport implements AutoridadDao {

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.AutoridadDao#agregarAutoridad(cl.zpricing.revman.model.Autoridad)
	 */
	@Override
	public void agregarAutoridad(Autoridad autoridad) {
		getSqlMapClientTemplate().insert("agregarAutoridad", autoridad);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.AutoridadDao#eliminarAutoridad(int)
	 */
	@Override
	public boolean eliminarAutoridad(int id) {
		try{
			getSqlMapClientTemplate().delete("eliminarAutoridad", id);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.AutoridadDao#obtenerAutoridades(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Autoridad> obtenerAutoridades(int rol_id) {
		List<Autoridad> autoridades = new ArrayList<Autoridad>();
		autoridades = (List<Autoridad>) getSqlMapClientTemplate().queryForList("obtenerAutoridades",rol_id);
		return autoridades;
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.AutoridadDao#obtenerRolAutoridad(int)
	 */
	@Override
	public Rol obtenerRolAutoridad(int rol_id) {
		return (Rol) getSqlMapClientTemplate().queryForObject("obtenerRolAutoridad",rol_id);
	}

}
