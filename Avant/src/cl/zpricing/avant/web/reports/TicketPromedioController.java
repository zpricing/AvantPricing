package cl.zpricing.avant.web.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.reports.PrecioAsistencia;
import cl.zpricing.avant.model.reports.Rango;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.ReporteDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.ArchivoGrafico;
import cl.zpricing.avant.web.chart.CategoriaGrafico;
import cl.zpricing.avant.web.chart.GeneradorXMLGrafico;
import cl.zpricing.avant.web.chart.SerieDatos;
import cl.zpricing.avant.web.chart.Valor;

/**
 * <b>Descripci�n de la Clase</b> Controller del informe de ticket promedio
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 23-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class TicketPromedioController implements Controller {

	private ReporteDao reporteDao;

	private ComplejoDao complejoDao;
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = (Map<String, Object>) request.getSession()
				.getAttribute("map_reporte");

		ModelAndView mv = new ModelAndView(
				"reports/reporteticketpromedioporcomplejo2");
		// Obtener los precios seleccionados
		String[] precios = (String[]) map.get("precios");

		Rango rango = new Rango();
		rango.setInicio(Util.DateToString2(Util.StringToDate((String) map
				.get("fecha_inicio")))
				+ " 00:00:00");
		rango.setFin(Util.DateToString2(Util.StringToDate((String) map
				.get("fecha_fin")))
				+ " 00:00:00");

		// Recorremos todos los complejos seleccionados
		String idComplejos[] = (String[]) map.get("id_complejos");
		List<Map<String, Object>> graficos = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < idComplejos.length; i++) {
			Map<String, Object> grafico = new HashMap<String, Object>();

			rango.setIdComplejo(idComplejos[i]);

			// Obtenemos la lista de pares precio asistencia para el complejo
			// seleccionado en el
			// intervalo definido
			List<PrecioAsistencia> precioAsistencia = reporteDao
					.precioAsistenciaPorPeriodo(rango);
			List<PrecioAsistencia> precioAsistenciaAux = new ArrayList<PrecioAsistencia>();

			// iniciamos los datos
			long ticketProm = 0;
			Boolean existePrecioAsistencia = new Boolean(false);

			List<CategoriaGrafico> catGraf = new ArrayList<CategoriaGrafico>();
			SerieDatos serieDat = new SerieDatos();
			List<Valor> valores = new ArrayList<Valor>();

			if (precioAsistencia.size() > 0) {
				long suma = 0;
				long sumaProd = 0;

				Iterator<PrecioAsistencia> irPA = precioAsistencia.iterator();
				// Recorremos la lista de pares precio asistencia.
				// Acumulamos las asistencias y la multiplicacion entre precio y
				// asistenciapara sacar el ticket promedio
				while (irPA.hasNext()) {
					PrecioAsistencia pa = irPA.next();
					for (int j = 0; j < precios.length; j++) {
						if (Integer.parseInt(precios[j]) == pa.getPrecio()) {
							suma += pa.getAsistencia();
							sumaProd += (pa.getAsistencia() * pa.getPrecio());
							valores.add(new Valor(pa.getAsistencia()));
							catGraf.add(new CategoriaGrafico(""
									+ pa.getPrecio()));
							precioAsistenciaAux.add(pa);
							break;
						}
					}
				}
				if (suma > 0) {
					ticketProm = sumaProd / suma;
					existePrecioAsistencia = new Boolean(true);
				}
				precioAsistencia = precioAsistenciaAux;
			}

			Complejo complejo = complejoDao.obtenerComplejo(Integer
					.parseInt(idComplejos[i]));
			grafico.put("complejo", complejo.getNombre());

			grafico.put("ticketProm", ticketProm);
			grafico.put("existePrecioAsistencia", existePrecioAsistencia);
			grafico.put("precioAsistencia", precioAsistencia);

			// Guardar los datos del grafico
			GeneradorXMLGrafico gen = new GeneradorXMLGrafico();
			gen.setCategorias(catGraf);

			List<SerieDatos> seriesDat = new ArrayList<SerieDatos>();
			serieDat.setValores(valores);
			seriesDat.add(serieDat);
			gen.setDatos(seriesDat);

			gen.setLegendaX("Precio");
			// gen.setLegendaY1("Asistencia");
			gen.setTitulo("Asistencia por Precio en " + complejo.getNombre());

			ArchivoGrafico graXML = new ArchivoGrafico(request,
					"precioAsistencia"
							+ (complejo.getNombre().replace(" ", "")));
			gen.setRutaArchivo(graXML.rutaGraficoInterna());

			gen.setEsPie();
			gen.setMostrarNombres();
			gen.aXML();

			grafico.put("xmlGraf", graXML.rutaGraficoExterna());

			log.debug("ticket promedio " + ticketProm + " complejo "
					+ complejo.getNombre());

			graficos.add(grafico);
		}

		map.put("desde", (String) map.get("fecha_inicio"));
		map.put("hasta", (String) map.get("fecha_fin"));
		map.put("graficos", graficos);

		mv.addObject("map", map);

		// Guarda datos en sesion por si se quiere realizar el pdf
		request.getSession().setAttribute("para_reportes", mv);

		return mv;

	}

	/**
	 * @return the reporteDao
	 */
	public ReporteDao getReporteDao() {
		return reporteDao;
	}

	/**
	 * @param reporteDao
	 *            the reporteDao to set
	 */
	public void setReporteDao(ReporteDao reporteDao) {
		this.reporteDao = reporteDao;
	}

	/**
	 * @return the complejoDao
	 */
	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	/**
	 * @param complejoDao
	 *            the complejoDao to set
	 */
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	/**
	 * @return the log
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * @param log
	 *            the log to set
	 */
	public void setLog(Logger log) {
		this.log = log;
	}

}
