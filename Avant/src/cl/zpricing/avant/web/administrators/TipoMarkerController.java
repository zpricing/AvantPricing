package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.TipoMarker;
import cl.zpricing.avant.servicios.TipoMarkerDao;
import cl.zpricing.avant.web.form.TipoMarkerForm;

/**
 * <b>Controlador de la vista tipomarker</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 30-12-2008 Julio Andres Olivares Alarcon: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class TipoMarkerController extends SimpleFormController {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	private TipoMarkerDao tipoMarkerDao;

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		TipoMarkerForm form = (TipoMarkerForm) command;
		String descripcion = form.getDescripcion();
		String color = form.getColor();
		String codigo = form.getCodigo();
		
		log.debug("Agregar TipoMarker");
		log.debug("  Descripcion : [" + descripcion + "]");
		log.debug("  Color : [" + color + "]");
		log.debug("  Codigo : [" + codigo + "]");
		
		TipoMarker nuevo = new TipoMarker();
		
		nuevo.setDescripcion(descripcion);
		nuevo.setCodigo(codigo);
		nuevo.setColor(color);
		ModelAndView mv = new ModelAndView();
		
		if(!tipoMarkerDao.agregarTipoMarker(nuevo)) {
			mv = showForm(request, response, errors);
			mv.addObject("error", true);
		}
		else {
			mv = new ModelAndView(new RedirectView(getSuccessView()));
		}
		return mv; 
	}

	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> refdata = new HashMap<String, Object>();				
		refdata.put("tipomarkers", tipoMarkerDao.listarTipoMarker());		
		return refdata;
	}
	

	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		TipoMarkerForm agregarform = new TipoMarkerForm();	
		agregarform.setColor("#FFFFFF");
		return agregarform;
		
	}

	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codigo", "error.notnull");
	}

	public void setTipoMarkerDao(TipoMarkerDao tipoMarkerDao) {
		this.tipoMarkerDao = tipoMarkerDao;
	}	
}
