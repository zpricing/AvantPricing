/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;

/**
 * Controlador para el Administrador de Parametros
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 08-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ParametroController implements Controller {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private ServiciosRevenueManager serviciosRM;
	
	/**
	 * @return the serviciosRevenueManager
	 */
	public ServiciosRevenueManager getServiciosRM() {
		return serviciosRM;
	}

	/**
	 * @param serviciosRevenueManager the serviciosRevenueManager to set
	 */
	public void setServiciosRM(
			ServiciosRevenueManager serviciosRevenueManager) {
		this.serviciosRM = serviciosRevenueManager;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("handleRequest...");
		log.debug("Sistema Parameter: " + request.getParameter("Sistema"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		String sistema = request.getParameter("Sistema");
		ArrayList<String> sistemas = serviciosRM.obtenerSistemas();
		log.debug("sistemas" + sistemas.toString());
		map.put("sistemas", sistemas);
		
		if(sistema == null) {
			sistema = sistemas.get(0);
		}
		
		ArrayList<Parametro> parametros = new ArrayList<Parametro>();
		parametros = serviciosRM.obtenerParametro(sistema);
		log.debug("parametros: " + parametros.toString());
		map.put("parametros", parametros);
		ModelAndView mv = new ModelAndView("parametro", map);
		mv.addObject("sistema",sistema);
		mv.addObject("parametro", true);
		
		return mv;
		
	}

	
	
}
