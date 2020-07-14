package cl.zpricing.avant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.servicios.UsuarioDao;


/**
 * <b>Clase controladora de la vista que muestra usuarios</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17-12-2008 Oliver: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class ShowUsersController implements Controller  {

	//private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private UsuarioDao usuarioDao;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		return new ModelAndView("showusers", "user",usuarioDao.obtenerTodos());
		
	}
	
	/**
	 * @return usuarioDao
	 */
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	/**
	 * @param usuarioDao a setear
	 */
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
}