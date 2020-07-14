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
 * <b>Clase controladora de la vista epocas</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 21-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EpocasController extends SimpleFormController {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private EpocaDao epocaDao;

	public EpocaDao getEpocaDao() {
		return epocaDao;
	}

	public void setEpocaDao(EpocaDao epocaDao) {
		this.epocaDao = epocaDao;
	}

	/**
	 * 
	 * M�todo responsable de la vista epocas al hacer submit en su form
	 * asociado.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 21-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP
	 * @param response
	 *            Respuesta HTTP
	 * @param command
	 *            Objeto recibido por el form asociado a la vista
	 * @param errors
	 *            Errores
	 * @return Modelo y vista para epocas
	 * @throws Exception
	 * @since 1.0
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		EpocasForm epocasForm = (EpocasForm) command;
		int id = Integer.parseInt(epocasForm.getId());
		Epoca epoca = epocaDao.obtenerEpoca(id);

		epoca.setDescripcion(epocasForm.getDescripcion());
		epocaDao.actualizarEpoca(epoca);

		ModelAndView mv = showForm(request, response, errors);
		mv.addObject("test", true);
		epocasForm.setDescripcion(epoca.getDescripcion());
		return mv;
	}

	/**
	 * 
	 * Objeto responsable de crear el form asociado a la vista epocas.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 21-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP.
	 * @return objeto EpocasForm
	 * @throws ServletException
	 * @since 1.0
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		log.debug("Iniciando formBackingObject...");
		EpocasForm epocasForm = new EpocasForm();

		return epocasForm;
	}

	/**
	 * 
	 * Objeto responsable de establecer las variables globales en la vista
	 * epocas.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 21-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP
	 * @return Map de variables
	 * @throws Exception
	 * @since 1.0
	 */
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> mapCategoria = new HashMap<String, Object>(2);
		mapCategoria.put("epocas", epocaDao.obtenerEpocas());
		mapCategoria.put("test", false);
		return mapCategoria;
	}

}
