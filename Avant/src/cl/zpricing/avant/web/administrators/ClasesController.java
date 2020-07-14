/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.servicios.ClaseDao;
import cl.zpricing.avant.web.form.ClasesPreciosForm;

/**
 * <b>Controlador de la vista clases</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class ClasesController extends SimpleFormController {
	/**
	 * Impresi�n de log.
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
	 * M�todo responsable de la vista clases al hacer submit en su form
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
	 * @return Modelo y vista para clases
	 * @throws Exception
	 * @since 1.0
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		ClasesPreciosForm form = (ClasesPreciosForm) command;
		ArrayList<Clase> clases = claseDao.obtenerListaDeClases();
		ModelAndView mv = showForm(request, response, errors);

		if (clases == null)
			return mv;

		boolean[] especial = form.getEspecial();
		Iterator<Clase> it_cla = clases.iterator();
		for (int i = 0; it_cla.hasNext(); i++) {
			Clase clase = it_cla.next();
			if (clase.getDescripcion() == null)
				clase.setDescripcion("");
			clase.setEsEspecial(especial[i]);
			claseDao.actualizarClase(clase);
		}

		return mv;
	}

	/**
	 * 
	 * Objeto responsable de crear el form asociado a la vista clases.
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
		ClasesPreciosForm form = new ClasesPreciosForm();
		ArrayList<Clase> clases = claseDao.obtenerListaDeClases();

		if (clases == null)
			return form;

		boolean[] especial = new boolean[clases.size()];
		Iterator<Clase> it_cla = clases.iterator();
		for (int i = 0; it_cla.hasNext(); i++) {
			Clase clase = it_cla.next();
			especial[i] = clase.isEsEspecial();
		}

		form.setEspecial(especial);
		return form;
	}

	/**
	 * 
	 * Objeto responsable de establecer las variables globales en la vista
	 * clases.
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
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("clases", claseDao.obtenerListaDeClases());

		return map;
	}

	/**
	 * 
	 * Retorna la descripci�n de la Clase de Id id. M�todo para utilizar con
	 * dwr.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 13-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 *            Id de la Clase a consultar
	 * @return String con la descripci�n de la Clase
	 * @since 1.0
	 */
	public String getDescripcionClase(String id) {
		int i = Integer.parseInt(id);
		log.debug("Entrando a getDescripcionClase..." + id);
		Clase clase = claseDao.obtenerClase(i);
		log.debug("Se obtuvo clase de precio " + clase.getPrecio());
		return clase.getDescripcion();
	}

}
