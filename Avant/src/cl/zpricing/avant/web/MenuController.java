package cl.zpricing.avant.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.MenuElement;
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.servicios.MenuElementDao;
import cl.zpricing.avant.servicios.UsuarioDao;

/**
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 18-02-2009 Oliver Cordero: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class MenuController implements Controller {
	private UsuarioDao usuarioDao;
	private MenuElementDao menuElementDao;
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Rescatamos la informacion de usuario de la sesion
		String user = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		Usuario usuario = new Usuario();
		Map<String, Object> refdata = new HashMap<String, Object>();

		usuario = usuarioDao.obtenerUsuarioByName(user);

		refdata.put("rol", usuario.getRol().getRol());
		refdata.put("user", usuario.getNombreCompleto());

		request.getSession().setAttribute("Usuario", usuario);

		List<MenuElement> elementos = new ArrayList<MenuElement>();
		ArrayList<MenuElement> elementosMenu = menuElementDao.MenuElementTodos();

		Iterator<MenuElement> it = elementosMenu.iterator();

		// Cargamos los elementos del menu
		while (it.hasNext()) {
			MenuElement menuElement = it.next();
			// log.debug("................. Elemento = " + menuElement);
			if (usuario.getRol().getRol().compareTo("Administrador") != 0) {
				if (menuElement.getRol().getId() == usuario.getRol().getId()) {
					elementos.add(menuElement);
				}
			} else
				elementos.add(menuElement);
		}
		refdata.put("links", elementos);
		
		

		return new ModelAndView("menu", "map", refdata);
	}
	
	
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	public void setMenuElementDao(MenuElementDao menuElementDao) {
		this.menuElementDao = menuElementDao;
	}
}