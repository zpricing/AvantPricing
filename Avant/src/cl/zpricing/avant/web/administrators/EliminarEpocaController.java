package cl.zpricing.avant.web.administrators;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Epoca;
import cl.zpricing.avant.servicios.EpocaDao;



/**
 * <b>Controlador de la vista eliminarepoca</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 23-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EliminarEpocaController extends SimpleFormController {
	
	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
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
	 * M�todo responsable de la vista eliminarepoca al hacer request.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 23-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @param arg1 Respuesta HTTP
	 * @return Modelo y vista que redirecciona a vista aeepoca
	 * @throws Exception
	 * @since 1.0
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		int id = Integer.parseInt(request.getParameter("idepoca"));
		Epoca nueva = new Epoca();
		nueva.setDescripcion("");
		nueva.setId(id);
		epocaDao.eliminarEpoca(nueva);
		

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

}
