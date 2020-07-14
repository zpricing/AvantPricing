/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.TipoMarker;
import cl.zpricing.avant.servicios.TipoMarkerDao;
import cl.zpricing.avant.web.form.TipoMarkerForm;

/**
 * <b>Controlador de la vista modifytipomarker</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 13-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class ModifyTipoMarkerController extends SimpleFormController {

	private TipoMarkerDao tipoMarkerDao;
	
	/**
	 * @param tipoMarkerDao
	 */
	public void setTipoMarkerDao(TipoMarkerDao tipoMarkerDao) {
		this.tipoMarkerDao = tipoMarkerDao;
	}

	/**
	 * @return dao de tipoMarker
	 */
	public TipoMarkerDao getTipoMarkerDao() {
		return tipoMarkerDao;
	}
	
	/**
	 * Descripci�n de M�todo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 16-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @param errors
	 * @return
	 * @throws Exception 
	 * @since 1.0
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
	
	TipoMarkerForm form = (TipoMarkerForm) command;
	String descripcion = form.getDescripcion();
	String color = form.getColor();
	String codigo = form.getCodigo();
	int id = Integer.parseInt(form.getId());
	TipoMarker nuevo = new TipoMarker();
	
	nuevo.setDescripcion(descripcion);
	nuevo.setCodigo(codigo);
	nuevo.setColor(color);
	nuevo.setId(id);
	ModelAndView mv = new ModelAndView();
	
	if(!tipoMarkerDao.modificarTipoMarker(nuevo)){
		mv = showForm(request, response, errors);
		mv.addObject("error", true);
	}
	else
		mv = new ModelAndView(new RedirectView(getSuccessView()));
		
	
	return mv;
	
	}
	

	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> refdata = new HashMap<String, Object>();				
		refdata.put("tipomarkers", tipoMarkerDao.listarTipoMarker());		
		return refdata;
	}
	

	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		int id = Integer.parseInt(request.getParameter("id_tipomarker"));
		TipoMarker tm = tipoMarkerDao.obtenerTipoMarker(id);
		TipoMarkerForm form = new TipoMarkerForm();	
		form.setId(id+"");
		form.setCodigo(tm.getCodigo());
		form.setColor(tm.getColor());
		form.setDescripcion(tm.getDescripcion());
		
		return form;
	}
	
	/**
	 * Descripci�n de M�todo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 16-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 * @param command
	 * @param errors
	 * @throws Exception 
	 * @since 1.0
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codigo", "error.notnull");
	}

}
