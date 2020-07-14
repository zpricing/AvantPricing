/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.MenuElement;
import cl.zpricing.avant.servicios.MenuElementDao;

/**
 * <b>Descripci�n de la Clase</b>
 * Implementacion de MenuElementDao
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 13-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class MenuElementDaoImpl extends SqlMapClientDaoSupport implements MenuElementDao {

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MenuElementDao#MenuElementTodos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<MenuElement> MenuElementTodos() {
		return (ArrayList<MenuElement>) getSqlMapClientTemplate().queryForList("menuElementTodos");
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MenuElementDao#agregarMenuElement()
	 */
	@Override
	public boolean agregarMenuElement(MenuElement menuElement) {
		getSqlMapClientTemplate().insert("agregarMenuElement",menuElement);
		return true; 
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MenuElementDao#obtenerMenuElement(int)
	 */
	@Override
	public MenuElement obtenerMenuElement(int id_menu_element) {
		
		return (MenuElement)getSqlMapClientTemplate().queryForObject("obtenerMenuElement",id_menu_element);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MenuElementDao#updateMenuElement(cl.zpricing.revman.model.MenuElement)
	 */
	@Override
	public void updateMenuElement(MenuElement menuElement) {
		getSqlMapClientTemplate().update("updateMenuElement", menuElement);
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MenuElementDao#eliminarMenuElement(java.lang.String)
	 */
	@Override
	public boolean eliminarMenuElement(MenuElement menuElement) {
		try{
		getSqlMapClientTemplate().update("eliminarMenuElement", menuElement);
		return true;
		}catch(Exception e)
		{}
		return false;
		
	}
}
