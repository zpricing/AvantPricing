package cl.zpricing.avant.web.administrators;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.servicios.SalaDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.web.form.FuncionesCalForm;
import cl.zpricing.commons.utils.DateUtils;

/**
 * <b>Controlador de la vista funciones</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 24/12/2008 Daniel Estévez: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
@RemoteProxy(name="dwrFunciones")
public class FuncionesController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private FuncionDao funcionDao;
	private ComplejoDao complejoDao;
	private SalaDao salaDao;
	private ServiciosRevenueManager serviciosRevenueManager;
	private MascaraDao mascaraDao;

	/**
	 * Método responsable de la vista funciones al hacer submit en su form
	 * asociado.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24-12-2008 Daniel Estévez Garay: Versión Inicial</li>
	 * <li>1.1 04-06-2009 Mario Lavandero: Agregada funcionalidad para volver al
	 * mismo dia luego de eliminar funcion. Salas son ordenadas por columna
	 * orden.</li>
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
	 * @return Modelo y vista para funciones
	 * @throws Exception
	 * @since 1.0
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		log.debug("OnSubmit");
		FuncionesCalForm form = (FuncionesCalForm) command;
		
		String fecha = form.getFecha();
		int id_complejo = form.getIdComplejo();
		
		log.debug("  Form id_complejo : " + id_complejo);
		log.debug("  Form fecha : " + fecha);

		ModelAndView mv = showForm(request, response, errors, new HashMap());
		return mv;
	}

	/**
	 * Objeto responsable de crear el form asociado a la vista funciones.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24-12-2008 Daniel Estevez Garay: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP.
	 * @return objeto FuncionesCalForm
	 * @throws ServletException
	 * @since 1.0
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		log.debug("Iniciando formBackingObject...");
		log.debug("  formBackingObject IdComplejo : " + request.getParameter("idComplejo"));
		log.debug("  formBackingObject Fecha : " + request.getParameter("fecha"));
		FuncionesCalForm form = new FuncionesCalForm();
		if (request.getParameter("idComplejo") != null && request.getParameter("fecha") != null) {
			form.setFecha(request.getParameter("fecha"));
			form.setIdComplejo(Integer.parseInt(request.getParameter("idComplejo")));
		} else {
			form.setFecha(DateUtils.format_ddMMyyyy.format(Calendar.getInstance().getTime()));
		}
		return form;
	}

	/**
	 * 
	 * Objeto responsable de establecer las variables globales en la vista
	 * funciones.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24-12-2008 Daniel Estevez Garay: Versi�n Inicial</li>
	 * <li>1.1 28-05-2009 Mario Lavandero: Agregado attributos para ser leidos
	 * al eliminar funciones. fecha y complejoId.</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP
	 * @return Map de variables
	 * @throws Exception
	 * @since 1.0
	 */
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		// Se declaran los objetos principales
		Calendar calendar = null;
		Complejo complejo = null;
		
		String idComplejo = request.getParameter("idComplejo");
		String id_complejo = request.getParameter("id_complejo");
		String fecha = request.getParameter("fecha");
		
		log.debug("  id_complejo : " + id_complejo);
		log.debug("  IdComplejo : " + idComplejo);
		log.debug("  Fecha : " + fecha);
		
		// Si se pasan los parametros por GET se ocupan para armar los objetos
		if (idComplejo != null && fecha != null) {
			complejo = complejoDao.obtenerComplejo(Integer.parseInt(idComplejo));
			calendar = new GregorianCalendar();
			calendar.setTime(DateUtils.format_ddMMyyyy.parse(fecha));
		} else {
			// Si no hay parametros se ocupa los parametros por defecto
			calendar = Calendar.getInstance();
			List<Complejo> todosComplejos = complejoDao.complejosTodos();
			complejo = todosComplejos.get(0);
		}
		
		String mascaras = serviciosRevenueManager.obtenerStringParametro("Revenue Manager", "mascaras");
		List<Mascara> listaMascaras = mascaraDao.obtenerMascaras(complejo);
		
		
		GregorianCalendar fecha_inicio = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 8, 0);
		GregorianCalendar fecha_termino = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 3, 0);
		fecha_termino.add(Calendar.DAY_OF_MONTH, 1);
		
		log.debug("  Fecha de Inicio : " + fecha_inicio.getTime());
		log.debug("  Fecha de Termino : " + fecha_termino.getTime());
		
		Map<String, Object> map = obtieneFechasNavegacion(fecha_inicio);
		
		map.put("fechaFormateada", DateUtils.format_ddMMyyyy.format(calendar.getTime()));
		map.put("complejoId", complejo.getId());
		map.put("complejo", complejo);
		map.put("year", calendar.get(Calendar.YEAR));
		map.put("mascaras", listaMascaras);
		
		log.debug("Antes de Obtener Funciones");
		List<Funcion> funciones = funcionDao.obtenerFuncionesComplejo(fecha_inicio, fecha_termino, complejo);
		log.debug("Despues de Obtener Funciones");
		map.put("funciones", funciones);
		
		List<Sala> salas = (List<Sala>) salaDao.obtenerTodasByComplejo(complejo);		
		
		map.put("salas", salas);
		map.put("complejos", complejoDao.complejosTodos());
		return map;
	}
	
	@RemoteMethod
	public Funcion cargaCapacidadFuncion(int funcionId) {
		Funcion funcion = funcionDao.obtenerFuncion(funcionId);
		return funcion;
	}
	
	private Map<String, Object> obtieneFechasNavegacion(Calendar calendario) {
		Calendar cal = (Calendar) calendario.clone();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("fecha_actual", cal.getTime());
		cal.add(Calendar.DATE, 1);
		parametros.put("fecha_adelante", cal.getTime());
		cal.add(Calendar.DATE, -2);
		parametros.put("fecha_atras", cal.getTime());
		parametros.put("fecha_hoy", new Date());
		return parametros;
	}
	
	/*** SETTERS ***/
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
	public void setSalaDao(SalaDao salaDao) {
		this.salaDao = salaDao;
	}
	public void setServiciosRevenueManager(ServiciosRevenueManager serviciosRevenueManager) {
		this.serviciosRevenueManager = serviciosRevenueManager;
	}
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}
}
