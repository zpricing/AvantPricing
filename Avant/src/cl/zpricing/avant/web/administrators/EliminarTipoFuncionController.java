/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.TipoFuncion;
import cl.zpricing.avant.servicios.TipoFuncionDao;

/**
 * <b>Controlador de la vista eliminartipofuncion</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EliminarTipoFuncionController extends SimpleFormController {

	private TipoFuncionDao tipoFuncionDao;

	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/**
	 * @return the tipoFuncionDao
	 */
	public TipoFuncionDao getTipoFuncionDao() {
		return tipoFuncionDao;
	}

	/**
	 * @param tipoFuncionDao
	 *            the tipoFuncionDao to set
	 */
	public void setTipoFuncionDao(TipoFuncionDao tipoFuncionDao) {
		this.tipoFuncionDao = tipoFuncionDao;
	}

	/**
	 * 
	 * M�todo responsable de la vista eliminartipofuncion al hacer request.
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
	 * @param arg1
	 *            Respuesta HTTP
	 * @return Modelo y vista que redirecciona a vista aetipofuncion
	 * @throws Exception
	 * @since 1.0
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		int id = Integer.parseInt(request.getParameter("idtipofuncion"));
		TipoFuncion nueva = new TipoFuncion();
		nueva.setDescripcion("");
		nueva.setId(id);
		tipoFuncionDao.eliminarTipoFuncion(nueva);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

}
