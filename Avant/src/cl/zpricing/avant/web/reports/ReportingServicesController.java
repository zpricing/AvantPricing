/**
 * 
 */
package cl.zpricing.avant.web.reports;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.util.GraficosPDF;
import cl.zpricing.avant.util.Util;

/**
 * <b>Recibe un request por un reporte, genera la URL para llamar al reporte
 * (según la configuración guardada en reportingServicesConfiguration.xml) y la
 * pasa a la vista.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 13-01-2010 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */

public class ReportingServicesController extends SimpleFormController {
	private static Logger	log					= (Logger) Logger.getLogger(GraficosPDF.class);

	private String			toolbarParameterUse	= "&rc:Toolbar=true";
	private String			toolbarParameterDoNotUse	= "&rc:Toolbar=false";

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		log.debug("@handleRequestInternal de ReportingServicesController");
		// Inicialización.
		Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("map_reporte");
		ModelAndView mv = new ModelAndView("reports/reportingServices");

		// ¿Qué reporte es el que vamos a mostrar?
		String reporteElegido = (String) map.get("reporteElegido");

		// Obtener el objeto ReportingServicesParamters que contiene la URL del
		// servidor de Reporting Services que ocuparemos
		ReportingServicesParameters rsp = (ReportingServicesParameters) cl.zpricing.avant.util.AppContext.getApplicationContext().getBean(
			"reportingServicesConfiguration");

		// En el siguiente objeto obtenemos todos los reportes registrados:
		Map<String, ReportRoot> reportes = rsp.getReportes();

		// Obtener el reporte elegido desde el applicationContext:
		ReportRoot reporte = reportes.get(reporteElegido);
		// ¿Cuáles son los parámetros que necesita ocupar este reporte?
		Map<String, ReportParameters> parametros = reporte.getParametros();
		// Ver si los tenemos todos (i.e., si es que acaso recibimos de parte
		// del usuario los valores de todos los parámetros
		// que ocupa este reporte).
		boolean estanTodosLosParametros = true;
		String todosLosParametros = "";
		for (String parameterName : parametros.keySet()) {
			ReportParameters thisParametro = parametros.get(parameterName);

			if (map.get(thisParametro.getParametroWeb()) == null) {
				estanTodosLosParametros = false;
			}
			String preparacionParametro = "&";
			if (map.get(thisParametro.getParametroWeb()) != null) {
				// Si tenemos el valor del parámetro, armamos el pedazo de URL
				// que corresponde
				preparacionParametro += thisParametro.getParametroNombre();
				preparacionParametro += "=";
				preparacionParametro += thisParametro.getParametroPrefijo();
				// preparacionParametro += ".%26[";

				String parametroEnSi = (String) map.get(thisParametro.getParametroWeb());
				;
				if (thisParametro.isConvertibleEnFecha()) {
					SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
					parametroEnSi = formateador.format(Util.StringToDate(parametroEnSi));
				}
				preparacionParametro += parametroEnSi;
				preparacionParametro += thisParametro.getParametroSufijo();
				// preparacionParametro += "]";

				log.debug("Se agrega \"" + preparacionParametro + "\" a la url del reporte.");
			}
			todosLosParametros += preparacionParametro;
		}

		if (!estanTodosLosParametros) {
			// fuck
			log.debug("Fuck! No esán todos los parámetros");
			return new ModelAndView(getSuccessView());
			
			// TODO tirar un error al usuario
		}

		// Si estaban todos los parámetros necesarios disponibles, armamos la
		// URL:
		String toolbar = rsp.isUsingToolbar() ? this.toolbarParameterUse : this.toolbarParameterDoNotUse;
		
		String reporteURL = rsp.getBaseURL() + reporte.getUbicacion() + todosLosParametros + toolbar;
		log.debug("reporteURL: " + reporteURL);
		
		// Para manejar el caso en que no se pasan todos los parámetros y se deben ocultar los links de exportación:
		mv.addObject("ocultarExportacion", reporte.isOcultarExportacion());

		// Finalmente pasamos el nombre y la URL formada del reporte a la vista:
		mv.addObject("nombreReporte", reporteElegido);
		// And there we go
		mv.addObject("reportURL", reporteURL);
		return mv;
	}

}
