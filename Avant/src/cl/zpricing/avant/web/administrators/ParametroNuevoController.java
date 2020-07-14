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
 * <b>Descripci�n de la Clase</b> Controlador de la vista de creacion de nuevo
 * parametro
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 12-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ParametroNuevoController extends SimpleFormController {

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
	 * @param serviciosRM
	 *            the serviciosRM to set
	 */
	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
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
		String sistema = request.getParameter("Sistema");
		ParametroForm form = new ParametroForm();
		form.setSistema(sistema);
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
	public ModelAndView onSubmit(Object command) throws Exception {
		log.debug("onSubmit...");
		ParametroForm form = (ParametroForm) command;
		Parametro reg = new Parametro();
		log.debug("form.sistema: " + form.getSistema());
		reg.setSistema(form.getSistema());
		reg.setSubSistema(form.getSubSistema());
		reg.setCodigo(form.getCodigo());
		serviciosRM.nuevoParametro(reg);
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

}
