package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Publico;
import cl.zpricing.avant.servicios.PublicoDao;


/**
 * <b>Controlador de la vista eliminarpublico</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 23-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EliminarPublicoController extends SimpleFormController {

	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private PublicoDao publicoDao;

	/**
	 * @return publicoDao
	 */
	public PublicoDao getPublicoDao() {
		return publicoDao;
	}

	/**
	 * @param publicoDao
	 */
	public void setPublicoDao(PublicoDao publicoDao) {
		this.publicoDao = publicoDao;
	}

	/**
	 * 
	 * M�todo responsable de la vista eliminarpublico al hacer request.
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
	 * @return Modelo y vista que redirecciona a vista aepublico
	 * @throws Exception
	 * @since 1.0
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		int id = Integer.parseInt(request.getParameter("idpublico"));
		Publico nueva = new Publico();
		nueva.setDescripcion("");
		nueva.setId(id);
		publicoDao.eliminarPublico(nueva);
		

		return new ModelAndView(new RedirectView(getSuccessView()));
	}


}
