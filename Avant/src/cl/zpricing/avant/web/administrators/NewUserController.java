package cl.zpricing.avant.web.administrators;

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
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.servicios.RolDao;
import cl.zpricing.avant.servicios.UsuarioDao;
import cl.zpricing.avant.web.form.NewUserForm;

/**
 * <b>Descripci�n de la Clase</b> Controlador de la vista para agregar usuarios
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 17-12-2008 Oliver: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class NewUserController extends SimpleFormController {

	private UsuarioDao usuarioDao;
	private RolDao rolDao;

	/**
	 * @return the userDao
	 */
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public RolDao getRolDao() {
		return rolDao;
	}

	public void setRolDao(RolDao rolDao) {
		this.rolDao = rolDao;
	}

	public ModelAndView onSubmit(Object command) throws ServletException {

		String user = ((NewUserForm) command).getUsuario();
		String pass = ((NewUserForm) command).getPassword();
		String nombre = ((NewUserForm) command).getNombreCompleto();
		String email = ((NewUserForm) command).getEmail();
		String rol_id = ((NewUserForm) command).getRol();

		Rol rol = new Rol();
		rol.setId(Integer.parseInt(rol_id));

		Usuario usuario = new Usuario();

		usuario.setNombreCompleto(nombre);
		usuario.setPassword(pass);
		usuario.setUsuario(user);
		usuario.setEmail(email);
		usuario.setRol(rol);

		usuarioDao.agregarUsuario(usuario);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {

		Map<String, Object> refdata = new HashMap<String, Object>();

		List<Rol> roles = new ArrayList<Rol>();

		roles = rolDao.sacarTodos();

		refdata.put("rol", roles);

		return refdata;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		NewUserForm form = new NewUserForm();
		form.setUsuario("");
		form.setPassword("");
		return form;
	}
}