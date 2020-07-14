/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.servicios.ClaseDao;
import cl.zpricing.avant.web.form.ClasesForm;

/**
 * <b>Controlador de la vista aeclase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class ClasesAeController extends SimpleFormController {

	/**
	 * Impresi�n de log
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private ClaseDao claseDao;

	/**
	 * @return the claseDao
	 */
	public ClaseDao getClaseDao() {
		return claseDao;
	}

	/**
	 * @param claseDao
	 *            the claseDao to set
	 */
	public void setClaseDao(ClaseDao claseDao) {
		this.claseDao = claseDao;
	}

	/**
	 * 
	 * M�todo responsable de la vista aeclase al hacer submit en su form
	 * asociado.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
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
	 * @return Modelo y vista para aeclase
	 * @throws Exception
	 * @since 1.0
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		ClasesForm form = (ClasesForm) command;
		String descripcion = form.getDescripcion();
		double precio = Double.parseDouble(form.getPrecio());
		Clase nueva = new Clase();
		nueva.setDescripcion(descripcion);
		nueva.setPrecio(precio);
		nueva.setId(0);
		claseDao.agregarClase(nueva);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	/**
	 * 
	 * Objeto responsable de crear el form asociado a la vista aeclase.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP.
	 * @return objeto ClasesForm
	 * @throws ServletException
	 * @since 1.0
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		log.debug("Iniciando formBackingObject...");
		ClasesForm form = new ClasesForm();
		form.setId("0");
		return form;
	}

	/**
	 * 
	 * Objeto responsable de establecer las variables globales en la vista
	 * aeclase.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
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
		return null;
	}

}
