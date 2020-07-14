/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;


import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Rol;

/**
 * <b>Descripci�n de la Clase</b>
 * Implementacion de RolDao
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 22-12-2008 ZhetaPricing 2: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class RolDaoImpl extends SqlMapClientDaoSupport implements cl.zpricing.avant.servicios.RolDao {

	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.RolDao#eliminarRol(int)
	 */
	@Override
	public void eliminarRol(int id) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("deleteRol", id);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.RolDao#agregarRol(cl.zpricing.revman.model.Rol)
	 */
	@Override
	public void agregarRol(Rol rol) {
		getSqlMapClientTemplate().insert("insertRol", rol);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.RolDao#sacarTodos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> sacarTodos() {
		
		return (List<Rol>) getSqlMapClientTemplate().queryForList("sacarTodos");

	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.RolDao#modificarRol(cl.zpricing.revman.model.Rol)
	 */
	@Override
	public void modificarRol(Rol rol) {
		getSqlMapClientTemplate().insert("modifyRol", rol);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.RolDao#obtenerRol(int)
	 */
	@Override
	public Rol obtenerRol(int id) {
		return (Rol) getSqlMapClientTemplate().queryForObject("getRol",id);
	}

}
