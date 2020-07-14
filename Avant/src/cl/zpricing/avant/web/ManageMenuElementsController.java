package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.MenuElement;
import cl.zpricing.avant.model.Rol;
import cl.zpricing.avant.servicios.MenuElementDao;
import cl.zpricing.avant.servicios.RolDao;
import cl.zpricing.avant.web.form.ManageMenuElementsForm;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 22-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class ManageMenuElementsController extends SimpleFormController {

	private MenuElementDao menuElementDao;
	private RolDao rolDao;
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java
	 * .lang.Object)
	 */
	public ModelAndView onSubmit(Object command) throws ServletException {

		String id_menu_element_from_form = ((ManageMenuElementsForm) command)
				.getId_menu_element();
		String rol_id = ((ManageMenuElementsForm) command).getRol_id();
		String title = ((ManageMenuElementsForm) command).getTitle();
		String link = ((ManageMenuElementsForm) command).getLink();
		String parent = ((ManageMenuElementsForm) command).getParent();
		String modoForm = ((ManageMenuElementsForm) command).getModoForm();

		MenuElement menuElement = new MenuElement();
		Rol rol = new Rol();

		rol.setId(Integer.parseInt(rol_id));

		menuElement.setRol(rol);
		menuElement.setTitle(title);
		menuElement.setLink(link);
		menuElement.setParent(parent);

		if (modoForm.compareTo("0") == 0) {
			menuElementDao.agregarMenuElement(menuElement);
		} else {
			// la linea siguiente es rara =s
			menuElement.setId_menu_element(Integer
					.parseInt(id_menu_element_from_form.split(",")[0]));
			menuElementDao.updateMenuElement(menuElement);
		}
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		String id_menu_element = request.getParameter("id_menu_element");

		ManageMenuElementsForm modifyform = new ManageMenuElementsForm();

		if (id_menu_element == null) {
			modifyform.setId_menu_element("");
			modifyform.setLink("");
			modifyform.setModoForm("0");
			modifyform.setTitle("");
			modifyform.setParent("");
		} else {
			MenuElement menuElement = new MenuElement();

			menuElement = menuElementDao.obtenerMenuElement(Integer
					.parseInt(id_menu_element));

			modifyform.setId_menu_element(String.valueOf(menuElement
					.getId_menu_element()));
			modifyform.setLink(menuElement.getLink());
			modifyform.setTitle(menuElement.getTitle());
			modifyform.setParent(menuElement.getParent());
			// modifyform.setRol_id(menuElement.getRol().getId());
			modifyform.setModoForm("1");
		}
		return modifyform;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#referenceData
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> refdata = new HashMap<String, Object>();

		List<MenuElement> elementos = new ArrayList<MenuElement>();
		List<Rol> roles = new ArrayList<Rol>();

		elementos = menuElementDao.MenuElementTodos();
		roles = rolDao.sacarTodos();

		refdata.put("elementos", elementos);
		refdata.put("roles", roles);

		boolean anadir = true;
		if (request.getParameter("id_menu_element") != null)
			anadir = false;

		refdata.put("anadir", anadir);

		return refdata;
	}

	public void setMenuElementDao(MenuElementDao menuElementDao) {
		this.menuElementDao = menuElementDao;
	}
	public void setRolDao(RolDao rolDao) {
		this.rolDao = rolDao;
	}
}
