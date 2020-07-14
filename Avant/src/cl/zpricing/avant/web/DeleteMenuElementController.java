
package cl.zpricing.avant.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.MenuElement;
import cl.zpricing.avant.servicios.MenuElementDao;

/**
 * <b>Clase controladora para eliminar elementos del men�.</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 14-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class DeleteMenuElementController extends SimpleFormController implements Controller  {

	private MenuElementDao menuElementDao;
	
/* (non-Javadoc)
 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	/*
	 * Obtiene el id del elemento que se eliminar� desde el par�metro "id_menu_element"
	 * que obtiene desde el request.
	 */
		String id_menu_element = request.getParameter("id_menu_element");
		
		MenuElement menuElement = new MenuElement();
		
		menuElement.setId_menu_element(Integer.parseInt(id_menu_element));
		
		menuElementDao.eliminarMenuElement(menuElement);
		
		return new ModelAndView(new RedirectView("managemenu.htm"));
	}

	/**
	 * @param menuElementDao
	 */
	public void setMenuElementDao(MenuElementDao menuElementDao) {
		this.menuElementDao = menuElementDao;
	}

	/**
	 * @return menuElementDao
	 */
	public MenuElementDao getMenuElementDao() {
		return menuElementDao;
	}
	
}
