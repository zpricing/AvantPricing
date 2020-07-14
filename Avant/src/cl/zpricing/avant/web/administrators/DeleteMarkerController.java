
package cl.zpricing.avant.web.administrators;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.servicios.MarkerDao;

/**
 * <b>Descripci�n de la Clase</b>
 * Controlador encargado de la eliminacion de markers
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 05-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class DeleteMarkerController extends SimpleFormController implements Controller {
	
	private MarkerDao markerDao;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id_marker = request.getParameter("id_marker");
		
		Marker marker = new Marker();
		
		marker.setId(Integer.parseInt(id_marker));
		
		@SuppressWarnings("unused")
		boolean exito = markerDao.eliminarMarker(marker);
		
		return new ModelAndView(new RedirectView("admin_managemarkers.htm"));
	}

	/**
	 * @param markerDao
	 */
	public void setMarkerDao(MarkerDao markerDao) {
		this.markerDao = markerDao;
	}

	/**
	 * @return markerDao
	 */
	public MarkerDao getMarkerDao() {
		return markerDao;
	}

}
