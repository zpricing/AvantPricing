/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.web.form.ParametroForm;

/**
 * <b>Descripci�n de la Clase</b> Controlador encargado de la edicion de
 * parametros
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 09-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ParametroEditarController extends SimpleFormController {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private ServiciosRevenueManager serviciosRM;

	/**
	 * @return the serviciosRevenueManager
	 */
	public ServiciosRevenueManager getServiciosRM() {
		return serviciosRM;
	}

	/**
	 * @param serviciosRevenueManager
	 *            the serviciosRevenueManager to set
	 */
	public void setServiciosRM(ServiciosRevenueManager serviciosRevenueManager) {
		this.serviciosRM = serviciosRevenueManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		log.debug("formBackingObject...");
		ParametroForm form = new ParametroForm();
		String sistema = request.getParameter("sistema");
		String subsistema = request.getParameter("subsistema");
		Parametro reg = serviciosRM.obtenerParametro(sistema, subsistema);
		form.setSistema(reg.getSistema());
		form.setSubSistema(reg.getSubSistema());
		form.setCodigo(reg.getCodigo());
		return form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#doSubmitAction
	 * (java.lang.Object)
	 */
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		log.debug("doSubmitAction");
		ParametroForm form = (ParametroForm) command;
		Parametro reg = new Parametro();
		log.debug("Reg.Sistema: " + reg.getSistema());
		log.debug("Sistema: " + form.getSistema());
		log.debug("Codigo: " + form.getCodigo());
		// TODO: Revisar porque aparece doble sistema y con , entre medio �?
		reg.setSistema(form.getSistema().split(",")[0]);
		reg.setSubSistema(form.getSubSistema());
		reg.setCodigo(form.getCodigo());
		serviciosRM.actualizarParametro(reg);
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

}
