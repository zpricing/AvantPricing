/**
 * 
 */
package cl.zpricing.avant.web.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.servicios.AggregateDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.ReporteDao;
import cl.zpricing.avant.servicios.SalaDao;
import cl.zpricing.avant.servicios.UsuarioDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.Valor;
import cl.zpricing.avant.web.form.reports.ReportesForm;

/**
 * <b>Controlador de la vista de reportes, enlaza con todos los informes que se pueden generar</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 22-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class IndexReportesController extends SimpleFormController {
	private Logger		log	= (Logger) Logger.getLogger(this.getClass());

	private ComplejoDao		complejoDao;
	private FuncionDao		funcionDao;
	private SalaDao			salaDao;
	private AggregateDao	aggregateDao;
	private ReporteDao		reporteDao;
	private UsuarioDao		usuarioDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
	throws Exception{
		ReportingServicesParameters rsp = (ReportingServicesParameters) cl.zpricing.avant.util.AppContext.getApplicationContext().getBean("reportingServicesConfiguration");

		// Si pedimos un reporte de Reporting Services, entraremos a este if de acá, buscaremos los parámetros que se necesitan
		// y los pondremos en un mapa para mandárselos a ReportingServicesController.
		if (request.getParameter("useRS") != null) {
			if (request.getParameter("useRS").equalsIgnoreCase("true")) {
				String tipoReporte = request.getParameter("tipo_reporte");
				Map<String, Object> map = new HashMap<String, Object>();
				ReportRoot reporte = rsp.getReportes().get(tipoReporte);
				for (String key : reporte.getParametros().keySet()) {
					ReportParameters param = reporte.getParametros().get(key);
					map.put(param.getParametroWeb(), request.getParameter(param.getParametroWeb()));
				}
				map.put("reporteElegido" , tipoReporte);
				request.getSession().setAttribute("map_reporte", map);
				return new ModelAndView(new RedirectView("reportingservices.htm"));
			}
		}
		
		// Si no, pasamos aquí a hacer los reportes "clásicos":
		
		ReportesForm form =   (ReportesForm) command;
		Map<String, Object> map = new HashMap<String, Object>();
		
		String tipo_reporte = form.getTipo_reporte();
		
		//Tomamos los datos relevantes para los informes desde el formulario 
		map.put("fecha_inicio",form.getFecha_inicio());
		map.put("fecha_fin",form.getFecha_fin());
		map.put("id_complejos", form.getComplejo());
		map.put("precios", form.getPrecios());
		map.put("reporteElegido", form.getTipo_reporte());
		/// TODO Hay que hacer que tipo_reporte sólo pueda ser uno de los registrados en el applicationContext
		
		// Se ponen en sesión también para que al volver a Reporte tengamos las mismas fechas que pusimos antes 
		request.getSession().setAttribute("reporte_desde", form.getFecha_inicio());
		request.getSession().setAttribute("reporte_hasta", form.getFecha_fin());
		
		//Cargamos los datos del usuario en memoria por si no estan
		Usuario usuario = (Usuario)request.getSession().getAttribute("Usuario");
		if(usuario == null){
			String user = SecurityContextHolder.getContext().getAuthentication().getName();
			usuario = usuarioDao.obtenerUsuarioByName(user);
			request.getSession().setAttribute("Usuario", usuario);
		}
		
		request.getSession().setAttribute("map_reporte", map);
		
		//Redirigimos a cada informe segun lo que haya seleccionado el usuario


		if (rsp.isUsingClassicReports()) {
			if (tipo_reporte.compareTo("asist_por_periodo") == 0) {
				return new ModelAndView("redirect:reporteasistenciaperiodo2.htm");
			}
			if (tipo_reporte.compareTo("ing_por_periodo") == 0)
				return new ModelAndView("redirect:reporteingresosperiodo2.htm");
			if (tipo_reporte.compareTo("ticket_promedio") == 0)
				return new ModelAndView("redirect:reporteticketpromedioporcomplejo2.htm");
			if (tipo_reporte.compareTo("confiteria") == 0)
				return new ModelAndView("redirect:reporteconfiteria.htm");
			if (tipo_reporte.compareTo("asistencia_ingreso_rm") == 0)
				return new ModelAndView("redirect:reporteaiprm.htm");
			if (tipo_reporte.compareTo("ventas_anticipadas_dias") == 0)
				return new ModelAndView("redirect:ventasAnticipadasDiasPeriodo.htm");
		}
//		if (rsp.isUsingReportingServices()) {
////			return new ModelAndView("redirect:reportingservices.htm");
//			return new ModelAndView(new RedirectView("reportingservices.htm"));
//		}
			return new ModelAndView(new RedirectView(getSuccessView()));
//		}
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {

		//cargamos los datos relevantes para la vista de reportes
		Map<String, Object> refdata = new HashMap<String, Object>();
		
		List<Complejo> complejos = new ArrayList<Complejo>();
		complejos = complejoDao.complejosTodos();
		//complejos
		refdata.put("complejos", complejos);
		//si es resultado
		refdata.put("resultado", new Boolean(false));
		//precios
		List<Valor> precios = reporteDao.obtenerPrecios();
		refdata.put("precios",precios);
		//Datos reporte ticket promedio
		request.getSession().setAttribute("reporteTicket",refdata);
		
		// Obtener los reportes disponibles registrados en applicationContext.xml
		ReportingServicesParameters rsp = (ReportingServicesParameters) cl.zpricing.avant.util.AppContext.getApplicationContext().getBean("reportingServicesConfiguration");
		
		/// Experimental
		
		Map<String, Map<String, ReportRoot>> reportes = new HashMap<String, Map<String,ReportRoot>>();
		
		Map<String,ReportRoot> reportesDisponibles = rsp.getReportes();
		for (String key : reportesDisponibles.keySet()) {
			String[] componentes = new String[3];
			StringTokenizer tokens = new StringTokenizer(key, ".");
			int i = 0;
			while (tokens.hasMoreElements() && i < 3) {
				componentes[i] = (String) tokens.nextElement();
				i++;
			}
			if (componentes.length == 3 && componentes[0].equalsIgnoreCase("report")) {
				if (reportes.containsKey(componentes[1])) {
					// El segundo componente corresponde a la categoría de reporte, por lo que vemos si ya está agregada.
					reportes.get(componentes[1]).put(key, reportesDisponibles.get(key));
				} else {
					// Si no, la creamos.
					Map<String,ReportRoot> mapCategoria = new HashMap<String, ReportRoot>();
					reportes.put(componentes[1], mapCategoria);
					// Y agregamos el reporte igual.
					reportes.get(componentes[1]).put(key, reportesDisponibles.get(key));
				}
			}
		}
		refdata.put("reportesDisponiblesCategorizados", reportes);
		
		// Fin del experimento
		
		refdata.put("reportesDisponibles", rsp.getReportes());
		refdata.put("usingReportingServices", rsp.isUsingReportingServices());
		refdata.put("usingClassicReports", rsp.isUsingClassicReports());
		return refdata;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

		Calendar fecha = Calendar.getInstance();
		ReportesForm form = new ReportesForm();
		String reporte_desde = (String) request.getSession().getAttribute("reporte_desde");
		String reporte_hasta = (String) request.getSession().getAttribute("reporte_hasta");
		
		if(reporte_desde==null)
			form.setFecha_inicio(Util.DateToString(fecha.getTime()));
		else
			form.setFecha_inicio(reporte_desde);
		
		if(reporte_hasta==null)
			form.setFecha_fin(Util.DateToString(fecha.getTime()));
		else
			form.setFecha_fin(reporte_hasta);
		
		return form;
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected void onBindAndValidate(HttpServletRequest request, Object arg0, BindException arg1) throws Exception {
		
		
		ReportesForm form = (ReportesForm) arg0;	
		System.out.println(form.getPrecios());
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "fecha_inicio", "error.fecha");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "fecha_fin", "error.fecha");
		
		String fecha = form.getFecha_inicio();
		String fecha_fin = form.getFecha_fin();
		
		//patron para comprobar el formato de la fecha
		Pattern patron = Pattern.compile("^([1-9]|[012][0-9]|3[01])-([0-9]|[0][1-9]|[1][012])-([2][0][0-1][0-9])$");
	    Matcher fecha1 = patron.matcher(fecha);
	    Matcher fecha2 = patron.matcher(fecha_fin);
	    
	      if (!fecha1.find() || !fecha2.find())
	    	  {
	    	   arg1.rejectValue( "fecha_inicio", "error.formato_fecha");
	    	   return;
	    	   }
	      
		try{
			int dia_inicio= Integer.parseInt(fecha.split("-")[0]);
			int mes_inicio= Integer.parseInt(fecha.split("-")[1])-1;
			int ano_inicio= Integer.parseInt(fecha.split("-")[2]);
			int dia_fin=Integer.parseInt(fecha_fin.split("-")[0]);
			int mes_fin=Integer.parseInt(fecha_fin.split("-")[1])-1;
			int ano_fin=Integer.parseInt(fecha_fin.split("-")[2]);
			
			String id_complejos[] = form.getComplejo();
			
			if(id_complejos.length==0)
				{
				arg1.rejectValue( "complejo", "error.complejos");
				return;
				}
			
			if(ano_fin<ano_inicio)
				{
				arg1.rejectValue( "complejo", "error.fecha_desfasada");
				return;
				}
			else
				if(ano_fin==ano_inicio){
					if(mes_fin<mes_inicio)
						{arg1.rejectValue( "complejo", "error.fecha_desfasada");
						return;
						}
					else
						if(mes_fin==mes_inicio){
							if(dia_fin<dia_inicio){
								arg1.rejectValue( "complejo", "error.fecha_desfasada");
								return;
								}
							}	
					}
			}catch(Exception e )
			{
				//=(
				}
			
		if(form.getTipo_reporte()==null)
			{
			arg1.rejectValue( "complejo", "error.seleccionar_reporte");
			return;
			}
	}
	/**
	 * @return the complejoDao
	 */
	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	/**
	 * @param complejoDao the complejoDao to set
	 */
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	/**
	 * @return the funcionDao
	 */
	public FuncionDao getFuncionDao() {
		return funcionDao;
	}

	/**
	 * @param funcionDao the funcionDao to set
	 */
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	/**
	 * @return the salaDao
	 */
	public SalaDao getSalaDao() {
		return salaDao;
	}

	/**
	 * @param salaDao the salaDao to set
	 */
	public void setSalaDao(SalaDao salaDao) {
		this.salaDao = salaDao;
	}

	/**
	 * @return the aggregateDao
	 */
	public AggregateDao getAggregateDao() {
		return aggregateDao;
	}

	/**
	 * @param aggregateDao the aggregateDao to set
	 */
	public void setAggregateDao(AggregateDao aggregateDao) {
		this.aggregateDao = aggregateDao;
	}

	public void setReporteDao(ReporteDao reporteDao) {
		this.reporteDao = reporteDao;
	}

	public ReporteDao getReporteDao() {
		return reporteDao;
	}

	/**
	 * @return the usuarioDao
	 */
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	/**
	 * @param usuarioDao the usuarioDao to set
	 */
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
}
