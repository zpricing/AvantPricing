
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

import cl.zpricing.avant.model.TipoFuncion;
import cl.zpricing.avant.servicios.TipoFuncionDao;
import cl.zpricing.avant.web.form.TipoFuncionForm;

/**
 * <b>Controlador de la vista tipofuncion</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class TipoFuncionController extends SimpleFormController{
	
	TipoFuncionDao tipoFuncionDao;
	
	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/**
	 * @return the tipoFuncionDao
	 */
	public TipoFuncionDao getTipoFuncionDao() {
		return tipoFuncionDao;
	}

	/**
	 * @param tipoFuncionDao the tipoFuncionDao to set
	 */
	public void setTipoFuncionDao(TipoFuncionDao tipoFuncionDao) {
		this.tipoFuncionDao = tipoFuncionDao;
	}	
	
	/**
	 * 
	 * M�todo responsable de la vista tipofuncion al hacer submit en su form asociado.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @param response Respuesta HTTP
	 * @param command Objeto recibido por el form asociado a la vista
	 * @param errors Errores
	 * @return Modelo y vista para tipofuncion
	 * @throws Exception
	 * @since 1.0
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		TipoFuncionForm form = (TipoFuncionForm) command;
		int id = Integer.parseInt(form.getId());
		TipoFuncion tipofuncion = tipoFuncionDao.obtenerTipoFuncion(id);
		
		if(form.getDescripcion()!=null && form.getDescripcion()!=tipofuncion.getDescripcion()){
			
			tipofuncion.setDescripcion(form.getDescripcion());
			tipoFuncionDao.actualizarTipoFuncion(tipofuncion);
		}	
		
		ModelAndView mv = showForm(request, response, errors);
		form.setDescripcion(tipofuncion.getDescripcion());
		return mv;
	}
	/**
	 * 
	 * Objeto responsable de crear el form asociado a la vista tipofuncion.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP.
	 * @return objeto TipoFuncionForm
	 * @throws ServletException 
	 * @since 1.0
	 */	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		log.debug("Iniciando formBackingObject...");
		TipoFuncionForm form = new TipoFuncionForm();
		
		return form;
	} 
	/**
	 * 
	 * Objeto responsable de establecer las variables globales en la vista tipofuncion.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @return Map de variables
	 * @throws Exception
	 * @since 1.0
	 */		
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object>  map= new HashMap<String, Object>(1);
		map.put("tipofuncion", tipoFuncionDao.obtenerTipoFunciones());
		return map;
	}
	

}
