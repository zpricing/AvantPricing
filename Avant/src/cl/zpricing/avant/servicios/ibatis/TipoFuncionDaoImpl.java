/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.TipoFuncion;

/**
 * <b>Implementaci�n DAO sobre iBatis para el objeto TipoFuncion</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class TipoFuncionDaoImpl extends SqlMapClientDaoSupport implements
		cl.zpricing.avant.servicios.TipoFuncionDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.TipoFuncionDao#actualizarTipoFuncion(cl.
	 * zpricing.revman.model.TipoFuncion)
	 */
	@Override
	public void actualizarTipoFuncion(TipoFuncion tipofuncion) {
		getSqlMapClientTemplate().update("actualizarTipoFuncion", tipofuncion);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.TipoFuncionDao#agregarTipoFuncion(cl.zpricing
	 * .revman.model.TipoFuncion)
	 */
	@Override
	public void agregarTipoFuncion(TipoFuncion tipofuncion) {
		getSqlMapClientTemplate().insert("agregarTipoFuncion", tipofuncion);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.TipoFuncionDao#eliminarTipoFuncion(cl.zpricing
	 * .revman.model.TipoFuncion)
	 */
	@Override
	public void eliminarTipoFuncion(TipoFuncion tipofuncion) {
		getSqlMapClientTemplate().delete("eliminarTipoFuncion", tipofuncion);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.TipoFuncionDao#obtenerTipoFuncion(int)
	 */
	@Override
	public TipoFuncion obtenerTipoFuncion(int id) {
		return (TipoFuncion) getSqlMapClientTemplate().queryForObject(
				"obtenerTipoFuncion", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.TipoFuncionDao#obtenerTipoFunciones()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TipoFuncion> obtenerTipoFunciones() {
		return (ArrayList<TipoFuncion>) getSqlMapClientTemplate().queryForList(
				"obtenerTipoFunciones");
	}

}
