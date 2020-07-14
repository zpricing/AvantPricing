package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Clase;

/**
 * 
 * <b>Implementaci�n DAO sobre iBatis para el manejo de clases</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 17-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class ClaseDaoImpl extends SqlMapClientDaoSupport implements
		cl.zpricing.avant.servicios.ClaseDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.ClaseDao#actualizarClase(cl.zpricing.revman
	 * .model.Clase)
	 */
	@Override
	public void actualizarClase(Clase Clase) {
		getSqlMapClientTemplate().update("actualizarClase", Clase);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.ClaseDao#agregarClase(cl.zpricing.revman
	 * .model.Clase)
	 */
	@Override
	public void agregarClase(Clase Clase) {
		getSqlMapClientTemplate().insert("agregarClase", Clase);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.ClaseDao#eliminarClase(cl.zpricing.revman
	 * .model.Clase)
	 */
	@Override
	public void eliminarClase(Clase Clase) {
		getSqlMapClientTemplate().delete("eliminarClase", Clase);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.ClaseDao#obtenerListaDeClases()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Clase> obtenerListaDeClases() {
		return (ArrayList<Clase>) getSqlMapClientTemplate().queryForList(
				"obtenerListaDeClases");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.ClaseDao#obtenerListaDeClasesDesc()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Clase> obtenerListaDeClasesDesc() {
		return (ArrayList<Clase>) getSqlMapClientTemplate().queryForList(
				"obtenerListaDeClasesDesc");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.ClaseDao#obtenerClase(int)
	 */
	@Override
	public Clase obtenerClase(int id) {
		return (Clase) getSqlMapClientTemplate().queryForObject("obtenerClase",
				id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.ClaseDao#obtenerClasePrecio(double)
	 */
	public Clase obtenerClasePrecio(double precio) {
		return (Clase) getSqlMapClientTemplate().queryForObject(
				"obtenerClasePrecio", precio);
	}
}
