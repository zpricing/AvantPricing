
package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.servicios.ClaseDao;

/**
 * <b>Controlador de la vista eliminarclase</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 29-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EliminarClaseController extends SimpleFormController {
	
	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
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
	 * M�todo responsable de la vista eliminarclase al hacer request.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @param arg1 Respuesta HTTP
	 * @return Modelo y vista que redirecciona a vista aeclase
	 * @throws Exception
	 * @since 1.0
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
	int id = Integer.parseInt(request.getParameter("idclase"));
	Clase nueva = new Clase();
	nueva.setId(id);
	claseDao.eliminarClase(nueva);
	

	return new ModelAndView(new RedirectView(getSuccessView()));
	}

}
