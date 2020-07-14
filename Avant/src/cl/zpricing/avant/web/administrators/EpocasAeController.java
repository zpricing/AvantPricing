package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Epoca;
import cl.zpricing.avant.servicios.EpocaDao;
import cl.zpricing.avant.web.form.EpocasForm;

/**
 * <b>Clase controladora de la vista aeepoca</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 21-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EpocasAeController extends SimpleFormController {
	
	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private EpocaDao epocaDao;
		
	/**
	 * @return epocaDao
	 */
	public EpocaDao getEpocaDao(){
		return epocaDao;
	}
	
	/**
	 * @param epocaDao
	 */
	public void setEpocaDao(EpocaDao epocaDao){
		this.epocaDao = epocaDao;
	}
	
	
	/**
	 * 
	 * M�todo responsable de la vista aeepoca al hacer submit en su form asociado.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 21-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @param response Respuesta HTTP
	 * @param command Objeto recibido por el form asociado a la vista
	 * @param errors Errores
	 * @return Modelo y vista para aeepoca
	 * @throws Exception
	 * @since 1.0
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		EpocasForm epocasForm = (EpocasForm) command;
		String descripcion = epocasForm.getDescripcion();
		Epoca nueva = new Epoca();
		nueva.setDescripcion(descripcion);
		nueva.setId(0);
		epocaDao.agregarEpoca(nueva);
		

		ModelAndView mv = showForm(request, response, errors);

		return mv;
	}
	/**
	 * 
	 * Objeto responsable de crear el form asociado a la vista aeepoca.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 21-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP.
	 * @return objeto EpocasForm
	 * @throws ServletException 
	 * @since 1.0
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		log.debug("Iniciando formBackingObject...");
		EpocasForm epocasForm = new EpocasForm();
		epocasForm.setId("0");
		return epocasForm;
	} 
	/**
	 * 
	 * Objeto responsable de establecer las variables globales en la vista aeepoca.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 21-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @return Map de variables
	 * @throws Exception
	 * @since 1.0
	 */		
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object>  mapEpoca= new HashMap<String, Object>(1);
		mapEpoca.put("epocas", epocaDao.obtenerEpocas());
		return mapEpoca;
	}
	
	

}
