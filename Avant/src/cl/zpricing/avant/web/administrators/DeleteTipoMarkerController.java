/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.servicios.TipoMarkerDao;

/**
 * <b>Descripci�n de la Clase</b> Controlador encargado de la eliminacion de los
 * tipoMarkers
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class DeleteTipoMarkerController extends SimpleFormController implements
		Controller {

	private TipoMarkerDao tipoMarkerDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractController#handleRequest(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String id_tipomarker = request.getParameter("id_tipomarker");

		@SuppressWarnings("unused")
		boolean exito = tipoMarkerDao.eliminarTipoMarker(Integer
				.parseInt(id_tipomarker));

		/*
		 * if(exito) System.out.println("true:" + exito); else return new
		 * ModelAndView();
		 */

		return new ModelAndView(new RedirectView(getSuccessView()));
		// return new ModelAndView(new
		// RedirectView("managempresas.htm?error=1"));
	}

	/**
	 * @param tipoMarkerDao
	 */
	public void setTipoMarkerDao(TipoMarkerDao tipoMarkerDao) {
		this.tipoMarkerDao = tipoMarkerDao;
	}

	/**
	 * @return tipoMarkerDao
	 */
	public TipoMarkerDao getTipoMarkerDao() {
		return tipoMarkerDao;
	}

}
