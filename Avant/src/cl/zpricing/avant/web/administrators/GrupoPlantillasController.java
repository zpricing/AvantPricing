package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.servicios.PlantillaDao;

/**
 * <b>Descripci�n de la Clase</b> Controlador encargado de la administracion del
 * grupo de plantillas
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 02-02-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class GrupoPlantillasController implements Controller {

	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("grupoPlantillas", plantillaDao
				.obtenerTodosGrupoPlantillas());
		mv.setViewName("grupoplantillas");
		return mv;
	}

}
