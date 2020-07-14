/**
 * 
 */
package cl.zpricing.avant.web.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.servicios.AggregateDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.ReporteDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.GraficoStackedSimple;
import cl.zpricing.avant.web.chart.SerieDatosSimple;

import com.infosoftglobal.fusioncharts.FusionChartsHelper;

/**
 * <b>Controlador para el reporte que ve las Ventas Anticipadas, ventas de
 * entradas realizadas dias antes de la funcion, agregadas por dia. Muestra el
 * numero de entradas, el ingreso y ademas el numero total de entradas vendidas
 * y el ingreso total si es que el dia ya paso. Lo hace para cada complejo
 * seleccionado.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 07-07-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class VentasAnticipadasDiasPeriodoController implements Controller {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private ComplejoDao complejoDao;
	private AggregateDao aggregateDao;
	private ReporteDao reporteDao;

	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public AggregateDao getAggregateDao() {
		return aggregateDao;
	}

	public void setAggregateDao(AggregateDao aggregateDao) {
		this.aggregateDao = aggregateDao;
	}

	public ReporteDao getReporteDao() {
		return reporteDao;
	}

	public void setReporteDao(ReporteDao reporteDao) {
		this.reporteDao = reporteDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mv = new ModelAndView(
				"reports/ventasAnticipadasDiasPeriodo");
		Map<String, Object> map = (Map<String, Object>) arg0.getSession()
				.getAttribute("map_reporte");
		String[] id_complejos_seleccionados = null;
		// El siguiente try-catch es por si actualizan la p�gina de este
		// reporte,
		// ya que si no encuentra los id_complejos en el map, se redirige a la
		// p�gina principal de reportes
		try {
			id_complejos_seleccionados = (String[]) map.get("id_complejos");
		} catch (Exception e) {
			return new ModelAndView(new RedirectView("reportes.htm"));
		}
		// Obtengo los complejos seleccionados
		ArrayList<Complejo> complejos = new ArrayList<Complejo>();
		for (int i = 0; i < id_complejos_seleccionados.length; i++) {
			complejos.add(complejoDao.obtenerComplejo(Integer
					.parseInt(id_complejos_seleccionados[i])));
		}
		// mv.addObject("complejos", complejos);
		// Obtengo las fechas ingresadas en el formulario anterior.
		String fecha = (String) map.get("fecha_inicio");
		String fecha_fin = (String) map.get("fecha_fin");
		int dia = Integer.parseInt(fecha.split("-")[0]);
		int mes = Integer.parseInt(fecha.split("-")[1]) - 1;
		int ano = Integer.parseInt(fecha.split("-")[2]);
		int dia_fin = Integer.parseInt(fecha_fin.split("-")[0]);
		int mes_fin = Integer.parseInt(fecha_fin.split("-")[1]) - 1;
		int ano_fin = Integer.parseInt(fecha_fin.split("-")[2]);
		GregorianCalendar fechaInicio = new GregorianCalendar(ano, mes, dia);
		GregorianCalendar fechaFin = new GregorianCalendar(ano_fin, mes_fin,
				dia_fin);

		// Creo un arraylist de map para guardar la info de cada complejo
		ArrayList<Map> datos = new ArrayList<Map>();
		// Itero sobre los complejos
		Iterator<Complejo> iterComplejos = complejos.iterator();
		while (iterComplejos.hasNext()) {
			// Map por complejo
			Map<String, Object> mapComplejo = new HashMap<String, Object>();
			Complejo complejoTemp = iterComplejos.next();
			log.debug("Entrando al complejo: " + complejoTemp.getNombre());
			mapComplejo.put("complejo", complejoTemp);
			Calendar fechaActual = new GregorianCalendar();
			fechaActual.setTime(fechaInicio.getTime());
			// Variables para guardar los totales
			double totalRMAssistance = 0.0;
			double totalRMSales = 0.0;
			double totalAssistance = 0.0;
			double totalSales = 0.0;
			double totalPercentRMAssistance = 0.0;
			double totalPercentRMSales = 0.0;
			// ArrayList para guardar los datos para los graficos.
			List<String> datosCategorias = new ArrayList<String>();
			ArrayList<Integer> datosRMAssistance = new ArrayList<Integer>();
			ArrayList<Integer> datosAssistance = new ArrayList<Integer>();
			ArrayList<Integer> datosRMSales = new ArrayList<Integer>();
			ArrayList<Integer> datosSales = new ArrayList<Integer>();
			// ArrayList para guardar los datos de cada fecha
			ArrayList<Map> listaMapsDatos = new ArrayList<Map>();
			while (fechaFin.compareTo(fechaActual) >= 0) {
				log.debug("viendo fecha: " + fechaActual.getTime().toString());
				Map<String, Object> mapDia = new HashMap<String, Object>();
				mapDia.put("fecha", fechaActual.getTime());
				datosCategorias.add(Util.DateToString(fechaActual.getTime()));
				// Variables para obtener los datos diarios.
				double tempRMAssistance = aggregateDao
						.obtenerAsistenciaRMDiarioComplejo(fechaActual
								.getTime(), complejoTemp);
				double tempRMSales = aggregateDao
						.obtenerIngresosRMDiarioComplejo(fechaActual.getTime(),
								complejoTemp);
				double tempAssistance = aggregateDao
						.obtenerAsistenciaDiariaComplejo(fechaActual.getTime(),
								complejoTemp.getId());
				double tempSales = aggregateDao.obtenerIngresosDiarioComplejo(
						fechaActual.getTime(), complejoTemp);
				double tempPercentRMAssistance = (tempAssistance / (tempAssistance - tempRMAssistance)) - 1;
				double tempPercentRMSales = (tempSales / (tempSales - tempRMSales)) - 1;
				// Agrego los datos al map para el dia
				mapDia.put("rmassistance", tempRMAssistance);
				mapDia.put("rmsales", tempRMSales);
				mapDia.put("assistance", tempAssistance);
				mapDia.put("sales", tempSales);
				mapDia.put("percentRMassistance", tempPercentRMAssistance);
				mapDia.put("percentRMsales", tempPercentRMSales);
				// Agrego los datos para los graficos.
				datosRMAssistance.add((int) tempRMAssistance);
				datosRMSales.add((int) tempRMSales);
				datosAssistance.add((int) (tempAssistance - tempRMAssistance));
				datosSales.add((int) (tempSales - tempRMSales));
				// Sumo las variables temp para tener el total al final.
				totalRMAssistance += tempRMAssistance;
				totalRMSales += tempRMSales;
				totalAssistance += tempAssistance;
				totalSales += tempSales;
				// Agrego el map del dia a la lista de maps.
				listaMapsDatos.add(mapDia);
				// Sumo un dia a la fecha actual
				fechaActual.add(Calendar.DAY_OF_MONTH, 1);
			}
			// Calculo los porcentajes de RM totales
			totalPercentRMAssistance = (totalAssistance / (totalAssistance - totalRMAssistance)) - 1;
			totalPercentRMSales = (totalSales / (totalSales - totalRMSales)) - 1;
			// agrego el map al array de datos y los valores totales
			mapComplejo.put("dias", listaMapsDatos);
			mapComplejo.put("totalRMAssistance", totalRMAssistance);
			mapComplejo.put("totalRMSales", totalRMSales);
			mapComplejo.put("totalAssistance", totalAssistance);
			mapComplejo.put("totalSales", totalSales);
			mapComplejo.put("totalPercentRMAssistance",
					totalPercentRMAssistance);
			mapComplejo.put("totalPercentRMSales", totalPercentRMSales);
			// Agrego la info para desplegar los graficos.
			// Primero el grafico de asistencias.
			FusionChartsHelper helper = new FusionChartsHelper();
			SerieDatosSimple serieRMAssistance = new SerieDatosSimple(
					"Asistencia RM", helper.getFCColor(), datosRMAssistance);
			SerieDatosSimple serieAssistance = new SerieDatosSimple(
					"Asistencia no RM", helper.getFCColor(), datosAssistance);
			ArrayList<SerieDatosSimple> seriesGraficoAssistance = new ArrayList<SerieDatosSimple>();
			seriesGraficoAssistance.add(serieRMAssistance);
			seriesGraficoAssistance.add(serieAssistance);
			GraficoStackedSimple graficoAssistance = new GraficoStackedSimple(
					"Asistencias", "", "Fecha", "# Asistencias", "100", "1",
					"1", "5", "", "0", "800", "500", datosCategorias,
					seriesGraficoAssistance);
			mapComplejo.put("graficoAssistance", graficoAssistance);
			// Ahora el grafico de ingresos.
			SerieDatosSimple serieRMSales = new SerieDatosSimple("Ingresos RM",
					helper.getFCColor(), datosRMSales);
			SerieDatosSimple serieSales = new SerieDatosSimple(
					"Ingresos no RM", helper.getFCColor(), datosSales);
			ArrayList<SerieDatosSimple> seriesGraficoSales = new ArrayList<SerieDatosSimple>();
			seriesGraficoSales.add(serieRMSales);
			seriesGraficoSales.add(serieSales);
			GraficoStackedSimple graficoSales = new GraficoStackedSimple(
					"Ingresos", "", "Fecha", "Ingresos", "100", "1", "1", "5",
					"", "0", "800", "500", datosCategorias, seriesGraficoSales);
			mapComplejo.put("graficoSales", graficoSales);
			datos.add(mapComplejo);
		}
		mv.addObject("datos", datos);
		return mv;
	}

}
