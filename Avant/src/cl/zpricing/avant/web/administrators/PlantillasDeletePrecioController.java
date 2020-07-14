/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.servicios.PlantillaDao;

/**
 * Encargada de eliminar un Precio<->Area de una Plantilla
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 06-02-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PlantillasDeletePrecioController implements Controller {

	private PlantillaDao plantillaDao;
	
	
	/**
	 * @return plantillaDao
	 */
	public PlantillaDao getPlantillaDao() {
		return plantillaDao;
	}

	/**
	 * @param plantillaDao
	 */
	public void setPlantillaDao(PlantillaDao plantillaDao) {
		this.plantillaDao = plantillaDao;
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int plantillaId = Integer.parseInt(request.getParameter("plantillaId"));
		int claseId = Integer.parseInt(request.getParameter("claseId"));
		plantillaDao.borrarPlantillaClaseArea(plantillaId, claseId);
		return new ModelAndView(new RedirectView("admin_plantillas.htm"));
	}

}
