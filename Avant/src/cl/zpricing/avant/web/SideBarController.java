package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import atg.taglib.json.util.JSONException;

import cl.zpricing.avant.alerts.ProcessAlertFactory;
import cl.zpricing.avant.model.MenuElement;
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.servicios.MenuElementDao;
import cl.zpricing.avant.servicios.UsuarioDao;
import cl.zpricing.avant.util.PropertiesUtil;

/**
 * <b>Controlador de la vista del men� superior, y el header en general que va
 * en toda la aplicaci�n.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 05-02-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
@RemoteProxy(name="dwrSideBarAlerts")
public class SideBarController implements Controller {

	private UsuarioDao usuarioDao;
	private MenuElementDao menuElementDao;
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private ProcessAlertFactory processAlertFactory;
	
	@RemoteMethod
	public int countAlerts() throws JSONException {
		int c = processAlertFactory.countUnreadAlerts();
		return c;
	}
	@SuppressWarnings("unchecked")
	public void getMenu(HttpServletRequest request, Map mv) throws Exception {
		/*
		 * Los elementos del menú se guardan en la base de datos, junto al link
		 * correspondiente y la raíz de adonde salen visualmente en el menú. Una
		 * vez que se obtienen los elementos de la base de datos se mandan al
		 * jsp y mediante un "foreach" se van desplegando. Cada map en
		 * "lista_maps_header_elementos" corresponde a una raíz desde donde se
		 * desplegarán los elementos.
		 */
		List<Map<String, Object>> lista_maps_header_elementos = new ArrayList<Map<String, Object>>();

		// Se set�a el usuario en la variable de sesi�n "Usuario" dentro del
		// men� para que siempre este disponible.
		String user = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		Usuario usuario = new Usuario();
		usuario = usuarioDao.obtenerUsuarioByName(user);
		request.getSession().setAttribute("Usuario", usuario);

		// se obtienen todos los elementos Menú de la base de datos
		ArrayList<MenuElement> elementosMenu = menuElementDao
				.MenuElementTodos();

		// Luego se itera por cada uno
		// La variable usuario_valido sirve para saber si el usuario esta
		// autorizado para ver el elemento del menú
		boolean usuario_valido = false;
		// por cada "Parent"(raíz) desde donde se depliegan los elementos se
		// crea un map, la variable "encontro"
		// es para saber si, al sacar un elemento, el "parent" ya existe dentro
		// de los maps, entonces se adhiere el
		// elemento a esa lista, si no existe(encontro=false), se crea una nueva
		// raíz.
		boolean encontro = false;
		for (MenuElement menuElement : elementosMenu) {

			String parent = menuElement.getParent();
			// log.debug("......... menuElement = "+menuElement.getTitle());
			/*
			 * Si es "Administrador" puede ver todos los elementos del menú. Si
			 * no es "Administrador" se comprueba si el rol del usuario que esta
			 * desplegando el menú corresponde al rol autorizado para verlo
			 * según el campo Rol del Elemento Menú (si son iguales).
			 */
			if (usuario.getRol().getRol().compareTo("Administrador") != 0) {
				if (menuElement.getRol().getId() == usuario.getRol().getId())
					usuario_valido = true;
			} else
				usuario_valido = true;

			if (usuario_valido) {

				for (Map<String, Object> map_con_header : lista_maps_header_elementos) {

					if (parent.compareTo((String) map_con_header.get("header")) == 0) {
						((List<MenuElement>) map_con_header
								.get("lista_elementos")).add(menuElement);
						encontro = true;
					}
				}

				if (!encontro) {
					Map<String, Object> newmap = new HashMap<String, Object>();
					List<MenuElement> nueva_lista = new ArrayList<MenuElement>();
					nueva_lista.add(menuElement);
					newmap.put("header", menuElement.getParent());
					newmap.put("lista_elementos", nueva_lista);
					lista_maps_header_elementos.add(newmap);
				}

				encontro = false;
			}
			usuario_valido = false;
		}
		
		mv.put("map", lista_maps_header_elementos);
		mv.put("user", usuario);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		/*
		 * Los elementos del menú se guardan en la base de datos, junto al link
		 * correspondiente y la raíz de adonde salen visualmente en el menú. Una
		 * vez que se obtienen los elementos de la base de datos se mandan al
		 * jsp y mediante un "foreach" se van desplegando. Cada map en
		 * "lista_maps_header_elementos" corresponde a una raíz desde donde se
		 * desplegarán los elementos.
		 */
		List<Map<String, Object>> lista_maps_header_elementos = new ArrayList<Map<String, Object>>();

		// Se set�a el usuario en la variable de sesi�n "Usuario" dentro del
		// men� para que siempre este disponible.
		String user = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		Usuario usuario = new Usuario();
		usuario = usuarioDao.obtenerUsuarioByName(user);
		request.getSession().setAttribute("Usuario", usuario);

		// se obtienen todos los elementos Menú de la base de datos
		ArrayList<MenuElement> elementosMenu = menuElementDao
				.MenuElementTodos();

		// Luego se itera por cada uno
		// La variable usuario_valido sirve para saber si el usuario esta
		// autorizado para ver el elemento del menú
		boolean usuario_valido = false;
		// por cada "Parent"(raíz) desde donde se depliegan los elementos se
		// crea un map, la variable "encontro"
		// es para saber si, al sacar un elemento, el "parent" ya existe dentro
		// de los maps, entonces se adhiere el
		// elemento a esa lista, si no existe(encontro=false), se crea una nueva
		// raíz.
		boolean encontro = false;
		for (MenuElement menuElement : elementosMenu) {

			String parent = menuElement.getParent();
			// log.debug("......... menuElement = "+menuElement.getTitle());
			/*
			 * Si es "Administrador" puede ver todos los elementos del menú. Si
			 * no es "Administrador" se comprueba si el rol del usuario que esta
			 * desplegando el menú corresponde al rol autorizado para verlo
			 * según el campo Rol del Elemento Menú (si son iguales).
			 */
			if (usuario.getRol().getRol().compareTo("Administrador") != 0) {
				if (menuElement.getRol().getId() == usuario.getRol().getId())
					usuario_valido = true;
			} else
				usuario_valido = true;

			if (usuario_valido) {

				for (Map<String, Object> map_con_header : lista_maps_header_elementos) {

					if (parent.compareTo((String) map_con_header.get("header")) == 0) {
						((List<MenuElement>) map_con_header
								.get("lista_elementos")).add(menuElement);
						encontro = true;
					}
				}

				if (!encontro) {
					Map<String, Object> newmap = new HashMap<String, Object>();
					List<MenuElement> nueva_lista = new ArrayList<MenuElement>();
					nueva_lista.add(menuElement);
					newmap.put("header", menuElement.getParent());
					newmap.put("lista_elementos", nueva_lista);
					lista_maps_header_elementos.add(newmap);
				}

				encontro = false;
			}
			usuario_valido = false;
		}

		ModelAndView mv = new ModelAndView("sidebar");
		mv.addObject("map", lista_maps_header_elementos);
		mv.addObject("user", usuario);
		mv.addObject("year", Calendar.getInstance().get(Calendar.YEAR));
		mv.addObject("version", PropertiesUtil.getProperty("zpcinemas.version"));

		return mv;
	}

	/**
	 * @param usuarioDao
	 */
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	/**
	 * @return usuarioDao
	 */
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
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

	public void setProcessAlertFactory(ProcessAlertFactory processAlertFactory) {
		this.processAlertFactory = processAlertFactory;
	}
}
