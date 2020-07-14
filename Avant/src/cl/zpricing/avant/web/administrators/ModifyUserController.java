
package cl.zpricing.avant.web.administrators;

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

import cl.zpricing.avant.model.Rol;
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.servicios.RolDao;
import cl.zpricing.avant.servicios.UsuarioDao;
import cl.zpricing.avant.web.form.ModifyUserForm;

/**
 * <b>Clase controladora para la modificaci�n de usuarios</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 19-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class ModifyUserController extends SimpleFormController {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private UsuarioDao usuarioDao;
	private RolDao rolDao;
	/**
	 * @return the userDao
	 */
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	/**
	 * @return rolDao
	 */
	public RolDao getRolDao() {
		return rolDao;
	}
	
	/**
	 * @param rolDao
	 */
	public void setRolDao(RolDao rolDao) {
		this.rolDao = rolDao;
	}
	
	/**
	 * ModelAndView: Recoge los valores del formulario y los guarda en un objeto usuario,
	 * el que psoteriormente se pasa al Dao para que actualize los valores del
	 * Usuario seg�n el id.
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 22/12/2008 Julio: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param command Para obtener valores del formulario.
	 * @return Retorna la vista exitosa
	 * @throws ServletException Excepcion general por si un servlet encuentra problemas.
	 * @since 1.0
	 */

	public ModelAndView onSubmit(Object command)throws ServletException {
		
		log.debug("entrando a onSubmit...");
		String id = ((ModifyUserForm) command).getId();
		String user = ((ModifyUserForm) command).getUsuario();
		String nombre = ((ModifyUserForm) command).getNombreCompleto();
		String email = ((ModifyUserForm) command).getEmail();
		String rol_id = ((ModifyUserForm) command).getRol();
		String password = ((ModifyUserForm) command).getPassword();
		
		Rol rol = new Rol();
		rol.setId(Integer.parseInt(rol_id));
		
		Usuario usuario = new Usuario();
		
		usuario.setId(Integer.parseInt(id));
		usuario.setNombreCompleto(nombre);
		usuario.setUsuario(user);
		usuario.setPassword(password);
		usuario.setEmail(email);
		usuario.setRol(rol);
		
		log.debug("info usuario: " + usuario.getId() + " - " + usuario.getNombreCompleto() + " - " + usuario.getEmail() + " - " + usuario.getRol().getId());
		
		usuarioDao.actualizarUsuario(usuario);
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		log.debug("entrando a formBackingObject");
		String id_user = request.getParameter("iduser");
		Usuario usuario = new Usuario();
		
		usuario = usuarioDao.obtenerUsuario(Integer.parseInt(id_user));
		
		ModifyUserForm modifyform = new ModifyUserForm();
		
		modifyform.setId(id_user);
		modifyform.setUsuario(usuario.getUsuario());
		modifyform.setNombreCompleto(usuario.getNombreCompleto());
		modifyform.setPassword(usuario.getPassword()); //Solo para tenerlo para actualizar al usuario de nuevo.
		modifyform.setEmail(usuario.getEmail().trim());
		modifyform.setRol(Integer.toString(usuario.getRol().getId()));
		return modifyform;
		}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		log.debug("entrando a referenceData...");
		Map<String, Object> refdata = new HashMap<String, Object>();
		
		List<Rol> roles = new ArrayList<Rol>();
		
		roles = rolDao.sacarTodos();
		
		refdata.put("rol", roles);
		
		return refdata;
	}
}
