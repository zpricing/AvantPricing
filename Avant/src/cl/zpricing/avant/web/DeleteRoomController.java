
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

import cl.zpricing.avant.servicios.SalaDao;

/**
 * <b>Controlador encargado de borrar las Salas</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 26-12-2008 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class DeleteRoomController extends SimpleFormController implements Controller {

protected final Log logger = LogFactory.getLog(getClass());
	
	private SalaDao salaDao;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String id_room = request.getParameter("id_room");
		
		boolean exito = salaDao.eliminarSala(Integer.parseInt(id_room));
		if(exito)
		{return new ModelAndView(new RedirectView(getSuccessView()));}
		else
		{return new ModelAndView(new RedirectView("admin_aeroom.htm?error=1"));}
	}

	/**
	 * @param salaDao
	 */
	public void setSalaDao(SalaDao salaDao) {
		this.salaDao = salaDao;
	}

	/**
	 * @return salaDao
	 */
	public SalaDao getEmpresaDao() {
		return salaDao;
	}

	
}
