package cl.zpricing.avant.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.servicios.AutoridadDao;

/**
 * <b>Descripci�n de la Clase</b>
 * Controlador de la eliminaci�n de autoridad 
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 18-02-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class DeleteAuthorityController extends SimpleFormController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private AutoridadDao autoridadDao;
	
	/**
	 * @return autoridadDao
	 */
	public AutoridadDao getRolDao() {
		return autoridadDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setAutoridadDao(AutoridadDao autoridadDao) {
		this.autoridadDao = autoridadDao;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int id_authority = Integer.parseInt(request.getParameter("id_authority"));
		String id_rol = request.getParameter("id_rol");
		
		boolean exito = autoridadDao.eliminarAutoridad(id_authority);
		
		if(exito)
		{return new ModelAndView(new RedirectView("admin_setautorities.htm?id_rol="+id_rol));}
		else
		{return new ModelAndView(new RedirectView("admin_setautorities.htm?id_rol="+id_rol+"&error=1"));}

	}

	
}