package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.servicios.CategoriaDao;

/**
 * 
 * <b>Implementaci�n DAO sobre iBatis para el manejo de las categor�as</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 17-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class CategoriaDaoImpl extends SqlMapClientDaoSupport implements CategoriaDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.CategoriaDao#actualizarCategoria(cl.zpricing
	 * .revman.model.Categoria)
	 */
	@Override
	public void actualizarCategoria(Categoria categoria) {
		getSqlMapClientTemplate().update("actualizarCategoria", categoria);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.CategoriaDao#agregarCategoria(cl.zpricing
	 * .revman.model.Categoria)
	 */
	@Override
	public void agregarCategoria(Categoria categoria) {
		getSqlMapClientTemplate().insert("agregarCategoria", categoria);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.CategoriaDao#eliminarCategoria(cl.zpricing
	 * .revman.model.Categoria)
	 */
	@Override
	public void eliminarCategoria(Categoria categoria) {
		getSqlMapClientTemplate().delete("eliminarCategoria", categoria);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.CategoriaDao#obtenerCategoria(int)
	 */
	@Override
	public Categoria obtenerCategoria(int id) {
		return (Categoria) getSqlMapClientTemplate().queryForObject("obtenerCategoria", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.CategoriaDao#obtenerCategorias()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Categoria> obtenerCategorias() {
		return (ArrayList<Categoria>) getSqlMapClientTemplate().queryForList("obtenerCategorias");
	}

	@Override
	public Integer agregarCategoriaSiNoExiste(String externalSourceCode) {
		return (Integer) getSqlMapClientTemplate().insert("agregarCategoriaSiNoExiste", externalSourceCode);
	}
}
