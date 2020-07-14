/**
 * 
 */
package cl.zpricing.avant.web.administrators;

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
 * <b>Clase controladora de la vista editarclase</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 13-01-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class ClasesEditarController extends SimpleFormController{

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
	 * @param claseDao the claseDao to set
	 */
	public void setClaseDao(ClaseDao claseDao) {
		this.claseDao = claseDao;
	}
	
	/**
	 * 
	 * M�todo responsable de la vista aeclase al hacer submit en su form asociado.
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
		int id = Integer.parseInt(form.getId());
		Clase clase = new Clase();
		clase.setDescripcion(descripcion);
		clase.setPrecio(precio);
		clase.setId(id);
		claseDao.actualizarClase(clase);
		

		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	/**
	 * 
	 * Objeto responsable de crear el form asociado a la vista aeclase.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP.
	 * @return objeto ClasesForm
	 * @throws ServletException 
	 * @since 1.0
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		log.debug("Iniciando formBackingObject...");
		int id = Integer.parseInt(request.getParameter("idclase"));
		Clase clase = claseDao.obtenerClase(id);
		ClasesForm form = new ClasesForm();
		form.setId(clase.getId()+"");
		form.setDescripcion(clase.getDescripcion());
		form.setPrecio(clase.getPrecio()+"");
		
		return form;
	}
	


}
