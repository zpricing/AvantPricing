package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.servicios.AreaDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.web.form.MascarasForm;

/**
 * Controller para la administracion de las Mascaras. Muestra las mascaras que
 * estan configuradas en un complejo
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 01-02-2009 MARIO: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class MascaraController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private ComplejoDao complejoDao;
	private MascaraDao mascaraDao;
	private AreaDao areaDao;

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		log.debug(" Iniciando formBackingObject");
		MascarasForm form = new MascarasForm();
		List<Complejo> complejos = complejoDao.complejosTodos();
		
		Iterator<Complejo> iterComplejos = complejos.iterator();
		int indiceComplejos = 0;
		
		Complejo[] formComplejo = new Complejo[complejos.size()];
		Mascara[][] formMascara = new Mascara[complejos.size()][];
		Double[][] formCuposRestantes = new Double[complejos.size()][];
		
		while (iterComplejos.hasNext()) {
			formComplejo[indiceComplejos] = iterComplejos.next();
			List<Mascara> mascarasComplejo = mascaraDao.obtenerMascaras(formComplejo[indiceComplejos]);
			formMascara[indiceComplejos] = new Mascara[mascarasComplejo.size()];
			formCuposRestantes[indiceComplejos] = new Double[mascarasComplejo.size()];
			
			log.debug(" Antes de comenzar iteracion");
			
			Iterator<Mascara> iterMascaras = mascarasComplejo.iterator();
			int indiceMascara = 0;
			while (iterMascaras.hasNext()) {
				Mascara mascara = iterMascaras.next();
				log.debug("   Mascara : " + mascara.getDescripcion());
				log.debug("   Mascara Desc : " + mascara.getDescripcionDetallada());
				
				formMascara[indiceComplejos][indiceMascara] = mascara;
				
				double cuposAcumulados = 0.0;
				
				formCuposRestantes[indiceComplejos][indiceMascara] = new Double(100.00 - cuposAcumulados);
				indiceMascara++;
			}
			indiceComplejos++;
		}
		
		form.setMascaras(formMascara);
		form.setComplejos(formComplejo);
		form.setCuposRestantes(formCuposRestantes);
		return form;
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areas", areaDao.obtenerAreasRevenueManagement());
		return params;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		MascarasForm form = (MascarasForm) command;
		
		Mascara[][] mascaras = form.getMascaras();
		Complejo[] complejos = form.getComplejos();
		
		for (int i = 0 ; i < complejos.length ; i++) {
			Complejo complejo = complejos[i];
			for (int j = 0 ; j < mascaras[i].length ; j++) {
				Mascara mascara = mascaras[i][j];
				mascaraDao.actualizarMascara(mascara);
			}
		}
		
		
		/*
		log.debug("submit de modificar mascaras...");
		MascarasForm form = (MascarasForm) command;
		for (int i = 0; i < form.getComplejos().length; i++) {
			log.debug("i: " + i);
			int numSalas = salaDao.obtenerTodasByComplejo(form.getComplejos()[i]).size();
			for (int j = 0; j < numSalas; j++) {
				log.debug("j: " + j);
				Iterator<Mascara> iter = form.getSalas()[i][j].getMascaras().iterator();
				while (iter.hasNext()) {
					mascaraDao.actualizarMascara(iter.next());
				}
			}
		}*/
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}
	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}
}
