/**
 * 
 */
package cl.zpricing.avant.web.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * <b>Controlador para generar los pdfs de los informes</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 26-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class GeneradorPDFController implements Controller{

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		return new ModelAndView("GenerarPDF");
	}
	

}
