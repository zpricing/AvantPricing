package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Rol;
import cl.zpricing.avant.servicios.RolDao;
import cl.zpricing.avant.web.form.ManageRolesForm;

/**
 * <b>Descripci�n de la Clase</b> Controlador de la vista para la administraci�n
 * de roles
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 22-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ManageRolesController extends SimpleFormController {

	private RolDao rolDao;

	/**
	 * @return the userDao
	 */
	public RolDao getRolDao() {
		return rolDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setRolDao(RolDao RolDao) {
		this.rolDao = RolDao;
	}

	/**
	 * ModelAndView: Recoge los valores del formulario y los guarda en un objeto
	 * usuario, el que psoteriormente se pasa al Dao para que actualize los
	 * valores del Usuario seg�n el id.
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 22/12/2008 Julio: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param command
	 *            Para obtener valores del formulario.
	 * @return Retorna la vista exitosa
	 * @throws ServletException
	 *             Excepcion general por si un servlet encuentra problemas.
	 * @since 1.0
	 */
	public ModelAndView onSubmit(Object command) throws ServletException {

		String id = ((ManageRolesForm) command).getId();
		String rol_name = ((ManageRolesForm) command).getRol();
		String descripcion = ((ManageRolesForm) command).getDescripcion();
		String tipoIng = ((ManageRolesForm) command).getTipoIng();

		Rol rol = new Rol();

		rol.setRol(rol_name);
		rol.setDescripcion(descripcion);

		if (tipoIng.compareTo("add") == 0)
			rolDao.agregarRol(rol);
		else {
			rol.setId(Integer.parseInt(id));
			rolDao.modificarRol(rol);
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

		String id_rol = request.getParameter("id_rol");

		ManageRolesForm modifyform = new ManageRolesForm();
		if (id_rol == null) {
			modifyform.setId("");
			modifyform.setRol("");
			modifyform.setDescripcion("");
			modifyform.setTipoIng("add");
		} else {
			Rol rol = new Rol();

			rol = rolDao.obtenerRol(Integer.parseInt(id_rol));

			modifyform.setId(id_rol);
			modifyform.setRol(rol.getRol());
			modifyform.setDescripcion(rol.getDescripcion());
			modifyform.setTipoIng("modify");
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

		List<Rol> roles = new ArrayList<Rol>();

		roles = rolDao.sacarTodos();

		refdata.put("rol", roles);

		boolean anadir = true;
		if (request.getParameter("id_rol") != null)
			anadir = false;

		refdata.put("anadir", anadir);

		return refdata;
	}

}
