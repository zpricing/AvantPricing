
package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.loadmanager.GrupoPlantillas;
import cl.zpricing.avant.model.loadmanager.Plantilla;
import cl.zpricing.avant.servicios.ClaseDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.servicios.PlantillaDao;
import cl.zpricing.avant.web.form.PlantillasAddPrecioForm;

/**
 * Controller para el agregado de precios y areas a una plantilla
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 06-02-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PlantillasAddPrecioController extends SimpleFormController {

	private PlantillaDao plantillaDao;
	private MascaraDao mascaraDao;
	private ClaseDao claseDao;
	
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
	
	/**
	 * @return mascaraDao
	 */
	public MascaraDao getMascaraDao() {
		return mascaraDao;
	}
	
	/**
	 * @param mascaraDao
	 */
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}
	
	/**
	 * @return claseDao
	 */
	public ClaseDao getClaseDao() {
		return claseDao;
	}
	
	/**
	 * @param claseDao
	 */
	public void setClaseDao(ClaseDao claseDao) {
		this.claseDao = claseDao;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		GrupoPlantillas grupo = plantillaDao.obtenerGrupoPlantillas(Integer.parseInt(request.getParameter("grupoId")));
		map.put("grupo", grupo);
		map.put("plantilla", plantillaDao.obtenerPlantillaGrupoPlantillas(grupo, Integer.parseInt(request.getParameter("plantillaId"))));
		map.put("areas", mascaraDao.obtenerAreasComplejo(grupo.getComplejo()));
		map.put("clases", claseDao.obtenerListaDeClases());
		return map;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		PlantillasAddPrecioForm form = (PlantillasAddPrecioForm) command;
		GrupoPlantillas grupo = plantillaDao.obtenerGrupoPlantillas(Integer.parseInt(request.getParameter("grupoId")));
		Plantilla plantilla = plantillaDao.obtenerPlantillaGrupoPlantillas(grupo, Integer.parseInt(request.getParameter("plantillaId")));
		plantillaDao.agregarClaseAreaPlantilla(plantilla, form.getClaseId(), form.getAreaId());
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
}
