
package cl.zpricing.avant.web.administrators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.loadmanager.GrupoPlantillas;
import cl.zpricing.avant.servicios.ClaseDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.servicios.PlantillaDao;
import cl.zpricing.avant.web.form.PlantillasForm;

/**
 * Controlador para la vista que administra las plantillas
 * para cada complejo
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 05-02-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PlantillasController extends SimpleFormController {

	private PlantillaDao plantillaDao;
	private ComplejoDao complejoDao;
	private ClaseDao claseDao;
	private MascaraDao mascaraDao;
	
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
	 * @return complejoDao
	 */
	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	/**
	 * @param complejoDao
	 */
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
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

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<Complejo> complejos = (ArrayList<Complejo>) complejoDao.complejosTodos();
		HashMap<Integer, Object> areas = new HashMap<Integer, Object>();
		Iterator<Complejo> iterComplejos = complejos.iterator();
		//ArrayList<Area> areas = new ArrayList<Area>();
		int i = 0;
		while(iterComplejos.hasNext()){
			Complejo complejo = iterComplejos.next();
			try {
				areas.put(i, mascaraDao.obtenerAreasComplejo(complejo));
				i++;
			} catch (Exception e) {
				areas.put(i, null);
				i++;
			}
		}
		map.put("areas", areas);
		map.put("clases", claseDao.obtenerListaDeClases());
		return map;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		ArrayList<Complejo> complejos = (ArrayList<Complejo>) complejoDao.complejosTodos();
		PlantillasForm form = new PlantillasForm();
		Complejo[] comp = new Complejo[complejos.size()];
		GrupoPlantillas[][] group = new GrupoPlantillas[complejos.size()][4];
		Iterator<Complejo> iterComplejos = complejos.iterator();
		int i = 0;
		while(iterComplejos.hasNext()){
			Complejo complejo = iterComplejos.next();
			ArrayList<GrupoPlantillas> grupo = plantillaDao.obtenerGrupoPlantillasComplejo(complejo);
			comp[i] = complejo;
			Iterator<GrupoPlantillas> iterGrupo = grupo.iterator();
			int j = 0;
			while(iterGrupo.hasNext()){
				group[i][j] = iterGrupo.next();
				j++;
			}
			i++;
		}
		form.setComplejo(comp);
		form.setGrupoPlantillas(group);
		return form;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		PlantillasForm form = (PlantillasForm) command;
		for (int i = 0; i < form.getComplejo().length; i++) {
			for(int j = 0; j < 4 ; j++){
				if(form.getGrupoPlantillas()[i][j] != null){
					plantillaDao.actualizarPlantillasGrupoPlantillas(form.getGrupoPlantillas()[i][j]);
				}
			}
		}
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	
}
