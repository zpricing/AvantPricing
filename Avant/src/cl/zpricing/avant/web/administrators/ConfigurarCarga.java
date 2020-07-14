/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.servicios.TrabajoDao;
import cl.zpricing.avant.web.form.ConfigurarCargaForm;

/**
 * <b>Controladora para vista de configuracion de la carga.
 * Activa y desactiva la carga automatica y setea la hora en
 * que se realiza esta.</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 21-04-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ConfigurarCarga extends SimpleFormController {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private ServiciosRevenueManager serviciosRM;

	private TrabajoDao trabajoDao;

	public ServiciosRevenueManager getServiciosRM() {
		return serviciosRM;
	}

	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}
	
	
	/**
	 * @return the trabajoDao
	 */
	public TrabajoDao getTrabajoDao() {
		return trabajoDao;
	}

	/**
	 * @param trabajoDao the trabajoDao to set
	 */
	public void setTrabajoDao(TrabajoDao trabajoDao) {
		this.trabajoDao = trabajoDao;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		
		int carga = Integer.parseInt(serviciosRM.obtenerStringParametro("Cine", "carga_automatizada"));
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("carga", carga);
		String nombreTrabajo = serviciosRM.obtenerStringParametro("Cine", "jobCargaZP");
		//String nombreTrabajo = "TrabajoDePrueba";
		map.put("nombreTrabajo", nombreTrabajo);
		return map;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		log.debug("Configurando carga... ");
		log.debug("entrando formBackingObject...");
		log.debug((request.getParameter("carga")!=null?"carga == null":"carga != null"));
		if(request.getParameter("carga")!=null){
			String carga = request.getParameter("carga");
			log.debug(carga);
			if(carga.equalsIgnoreCase("activar")){
				log.debug("entre a activar");
				Parametro parametro = serviciosRM.obtenerParametro("Cine", "carga_automatizada");
				parametro.setCodigo("1");
				serviciosRM.actualizarParametro(parametro);
			}
			if(carga.equalsIgnoreCase("desactivar")){
				log.debug("entre a desactivar");
				Parametro parametro = serviciosRM.obtenerParametro("Cine", "carga_automatizada");
				parametro.setCodigo("0");
				serviciosRM.actualizarParametro(parametro);
			}
		}
		if(request.getParameter("ejecutar")!=null)
		{
			//loadManager.actualizarPlantillas();
		}
		
		
		if(request.getParameter("ejecutarJob")!=null){			
			String nombreTrabajo = request.getParameter("ejecutarJob");
			log.debug("nombreTrabajo = "+nombreTrabajo);
			
				String resultado = trabajoDao.ejecutarTrabajo(nombreTrabajo, obtenerTiempoMaximo());
				log.debug("resultado ejecuci�n job = "+resultado);
			
		}
		ConfigurarCargaForm form = new ConfigurarCargaForm();
		String hora = serviciosRM.obtenerStringParametro("Cine", "hora_carga");
		return form;
	}
	
	private int obtenerTiempoMaximo() throws Exception {
		return serviciosRM.obtenerIntParametro("Cine", "tiempoMaximoEsperaTrabajo");
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ConfigurarCargaForm form = (ConfigurarCargaForm) command;
		int hora = form.getHora();
		Parametro parametro = serviciosRM.obtenerParametro("Cine", "hora_carga");
		//BORRAR~
		//jobs y stored procedures
		log.debug("*** Resultado job = "+trabajoDao.ejecutarTrabajo("TrabajoDePrueba", obtenerTiempoMaximo()));
		
		
		/*
		log.debug("Probando funcionalidad por tipo");
		int r1 = serviciosRM.obtenerIntParametro("Cine", "Dia_Estreno");
		int r2 = serviciosRM.obtenerIntParametro("Revenue Manager", "error_admitido");
		double r3 = serviciosRM.obtenerDoubleParametro("Revenue Manager", "optimizacion_corte");
		int r4 = serviciosRM.obtenerIntParametro("Revenue Manager", "hora_fin");
		log.debug("Dia_Estreno = "+r1);
		log.debug("error_admitido = "+r2);
		log.debug("optimizacion_corte = "+r3);
		log.debug("hora_fin = "+r4);
		*/
	
		
		parametro.setCodigo(Integer.toString(hora));
		serviciosRM.actualizarParametro(parametro);
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
}
