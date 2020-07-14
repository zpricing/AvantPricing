
package cl.zpricing.avant.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.servicios.ibatis.UsuarioDaoImpl;
import cl.zpricing.avant.util.PropertiesUtil;
import cl.zpricing.avant.web.form.LoginForm;


/**
 * <b>Descripci�n de la Clase</b>
 * Controlador encargado de la vista para logearse
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 16-12-2008 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class LoginController extends SimpleFormController  {

	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private UsuarioDaoImpl usuarioDao;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object)
	 */
	public ModelAndView onSubmit(Object command)throws ServletException {
		log.debug("@onSubmit de LoginController");
		String user = ((LoginForm) command).getUsuario();
		String pass = ((LoginForm) command).getPassword();
		
		this.usuarioDao = new UsuarioDaoImpl();
		
		//log.debug("Ingreso usuario: " + user + " y Password: " + pass);

		return new ModelAndView("categorias");
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		log.debug("Iniciando formBackingObject del login");
		LoginForm loginForm = new LoginForm();
		return loginForm;
	} 

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		log.debug("@referenceData() de LoginController");
		Map<String, Object> refdata = new HashMap<String, Object>();
		refdata.put("year", Calendar.getInstance().get(Calendar.YEAR));
		refdata.put("version", PropertiesUtil.getProperty("zpcinemas.version"));
		return refdata;
	}

	/**
	 * @return the usuarioDao
	 */
	public UsuarioDaoImpl getUsuarioDao() {
		return usuarioDao;
	}

	/**
	 * @param usuarioDao the usuarioDao to set
	 */
	public void setUsuarioDao(UsuarioDaoImpl usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

}