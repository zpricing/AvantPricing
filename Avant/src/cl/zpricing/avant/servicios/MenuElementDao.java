/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.MenuElement;

/**
 * Dao para accesar los datos de los Elementos del Menu en la base de datos
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 13-01-2009 Julio Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface MenuElementDao {

	/**
	 * @return lista de todos los elementos del menu
	 */
	public ArrayList<MenuElement> MenuElementTodos();

	/**
	 * @param menuElement a agregar
	 * @return si el elemento de menu fue agregado
	 */
	public boolean agregarMenuElement(MenuElement menuElement);

	/**
	 * @param id_menu_element
	 * @return menuElement correspondiente al id dado
	 */
	public MenuElement obtenerMenuElement(int id_menu_element);

	/**
	 * @param menuElement a actualizar
	 */
	public void updateMenuElement(MenuElement menuElement);

	/**
	 * @param menuElement a eliminar
	 * @return si el menu element fue eliminado
	 */
	public boolean eliminarMenuElement(MenuElement menuElement);
}
