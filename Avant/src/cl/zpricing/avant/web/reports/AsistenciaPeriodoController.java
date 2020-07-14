package cl.zpricing.avant.web.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.servicios.AggregateDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.web.chart.CategoriaGrafico;
import cl.zpricing.avant.web.chart.Color;
import cl.zpricing.avant.web.chart.Dia;
import cl.zpricing.avant.web.chart.GeneradorXMLGrafico;
import cl.zpricing.avant.web.chart.SerieDatos;
import cl.zpricing.avant.web.chart.Valor;

/**
 * <b>Controlador de la vista del informe de asistencias.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 14-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class AsistenciaPeriodoController implements Controller {

	private ComplejoDao complejoDao;
	private AggregateDao aggregateDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> map = (Map<String, Object>) arg0.getSession()
				.getAttribute("map_reporte");

		ModelAndView mv = new ModelAndView("reports/asistenciaPeriodo");

		// categorias para gr�fico
		List<CategoriaGrafico> lista_categorias = new ArrayList<CategoriaGrafico>();
		// series de datos para gr�fico
		List<SerieDatos> lista_series = new ArrayList<SerieDatos>();

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

		double total_asistencias = 0;
		// total_semana es para cuando los dias pedidos son mayor que 28,
		// entonces se agrupan en semanas
		double total_semana = 0;
		int contador_dias = 0;
		int contador_semana = 0;
		// para definir el width del gr�fico de acuerdo a la cantidad de datos
		int largo_grafico = 0;
		// existe_grafico se ir� al jsp e indicar� si existen datos validos en
		// el gr�fico para evitar mostrar "basura"
		boolean existe_grafico = false;
		// si por_semana es true la cantidad de d�as es mayor que 28 y se
		// agrupar�n en semanas
		boolean por_semana = false;
		String fecha = (String) map.get("fecha_inicio");
		String fecha_fin = (String) map.get("fecha_fin");
		int dia = Integer.parseInt(fecha.split("-")[0]);
		int mes = Integer.parseInt(fecha.split("-")[1]) - 1;
		int ano = Integer.parseInt(fecha.split("-")[2]);
		int dia_fin = Integer.parseInt(fecha_fin.split("-")[0]);
		int mes_fin = Integer.parseInt(fecha_fin.split("-")[1]) - 1;
		int ano_fin = Integer.parseInt(fecha_fin.split("-")[2]);
		GregorianCalendar fecha_inicio_primer_periodo = new GregorianCalendar(
				ano, mes, dia);
		GregorianCalendar fecha_inicio_segundo_periodo = new GregorianCalendar(
				ano, mes, dia);
		GregorianCalendar fecha_inicio_tmp = new GregorianCalendar(ano, mes,
				dia);
		GregorianCalendar fecha_fin_primer_periodo = new GregorianCalendar(
				ano_fin, mes_fin, dia_fin);
		GregorianCalendar fecha_fin_segundo_periodo = new GregorianCalendar(
				ano_fin, mes_fin, dia_fin);
		GregorianCalendar fecha_fin_tmp = new GregorianCalendar(ano_fin,
				mes_fin, dia_fin);
		// El segundo Periodo corresponde al ingresado en el formulario de
		// reportes, el primer periodo es un a�o atr�s.

		// retrocedemos un a�o atr�s para sacar el primer periodo
		fecha_inicio_primer_periodo.add(Calendar.YEAR, -1);
		fecha_fin_primer_periodo.add(Calendar.YEAR, -1);

		// Este while es para comprobar que los dos periodos caigan en los
		// mismos d�as, i.e. si la fecha que se pide en
		// el formulario al generar el reporte(que ser� segundo reporte) cae d�a
		// Jueves, y en el primer periodo, al retroceder
		// un a�o, cae d�a Martes, entonces se avanzar� en 2 d�as para que los
		// dos comienzen en Jueves.
		while (fecha_inicio_segundo_periodo.get(Calendar.DAY_OF_WEEK) != fecha_inicio_primer_periodo
				.get(Calendar.DAY_OF_WEEK)) {
			fecha_inicio_primer_periodo.add(Calendar.DAY_OF_MONTH, 1);
			fecha_fin_primer_periodo.add(Calendar.DAY_OF_MONTH, 1);
		}

		mv.addObject("fecha_inicio_primer_periodo", Dia
				.getDia(fecha_inicio_primer_periodo.get(Calendar.DAY_OF_WEEK))
				+ " "
				+ fecha_inicio_primer_periodo.get(Calendar.DAY_OF_MONTH)
				+ " de "
				+ Dia.getMes(fecha_inicio_primer_periodo.get(Calendar.MONTH)));
		mv.addObject("fecha_fin_primer_periodo", Dia
				.getDia(fecha_fin_primer_periodo.get(Calendar.DAY_OF_WEEK))
				+ " "
				+ fecha_fin_primer_periodo.get(Calendar.DAY_OF_MONTH)
				+ " de "
				+ Dia.getMes(fecha_fin_primer_periodo.get(Calendar.MONTH))
				+ " del " + fecha_fin_primer_periodo.get(Calendar.YEAR));
		mv.addObject("fecha_inicio_segundo_periodo", Dia
				.getDia(fecha_inicio_segundo_periodo.get(Calendar.DAY_OF_WEEK))
				+ " "
				+ fecha_inicio_segundo_periodo.get(Calendar.DAY_OF_MONTH)
				+ " de "
				+ Dia.getMes(fecha_inicio_segundo_periodo.get(Calendar.MONTH)));
		mv.addObject("fecha_fin_segundo_periodo", Dia
				.getDia(fecha_fin_segundo_periodo.get(Calendar.DAY_OF_WEEK))
				+ " "
				+ fecha_fin_segundo_periodo.get(Calendar.DAY_OF_MONTH)
				+ " de "
				+ Dia.getMes(fecha_fin_segundo_periodo.get(Calendar.MONTH))
				+ " del " + fecha_fin_segundo_periodo.get(Calendar.YEAR));

		ArrayList<Map<String, Object>> arreglo_maps_por_complejo = new ArrayList<Map<String, Object>>();

		// para que busque todo el rango agrego el �ltimo d�a ya que la busqueda
		// tiene que durar hasta las 3 de la
		// ma�ana del �ltimo d�a.
		fecha_fin_primer_periodo.add(Calendar.DAY_OF_MONTH, 1);
		fecha_fin_segundo_periodo.add(Calendar.DAY_OF_MONTH, 1);

		// Para saber la cantidad de d�as del periodo se usa el contador_dias.
		fecha_inicio_tmp.set(fecha_inicio_primer_periodo.get(Calendar.YEAR),
				fecha_inicio_primer_periodo.get(Calendar.MONTH),
				fecha_inicio_primer_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
		fecha_fin_tmp.set(fecha_fin_primer_periodo.get(Calendar.YEAR),
				fecha_fin_primer_periodo.get(Calendar.MONTH),
				fecha_fin_primer_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
		contador_dias = 0;
		while (fecha_inicio_tmp.compareTo(fecha_fin_tmp) != 0) {
			fecha_inicio_tmp.add(Calendar.DAY_OF_MONTH, 1);
			contador_dias++;
		}

		// si el periodo dura m�s de 28 d�as se agrupa por semana
		if (contador_dias > 28)
			por_semana = true;

		// Por cada complejo solicitado
		for (int x = 0; x < id_complejos_seleccionados.length; x++) {

			// Cada map_complex contendr� el nombre del complejo y su asistencia
			// diaria y total de cada uno de los periodos
			Map<String, Object> map_complex = new HashMap<String, Object>();
			List<Double> asistencia_por_dias_primer = new ArrayList<Double>();
			List<Double> asistencia_por_dias_segundo = new ArrayList<Double>();
			// estas listas de String son para etiquetar los d�as y su n�mero,
			// se usa en las tablas y para el eje X en los gr�ficos
			List<String> lista_dias_primer_periodo = new ArrayList<String>();
			List<String> lista_dias_segundo_periodo = new ArrayList<String>();

			SerieDatos serieDat = new SerieDatos();
			SerieDatos serieDat2 = new SerieDatos();
			List<Valor> lista_valores = new ArrayList<Valor>();
			List<Valor> lista_valores2 = new ArrayList<Valor>();
			List<Double> lista_valores_falsos = new ArrayList<Double>();
			List<Double> lista_valores_falsos2 = new ArrayList<Double>();
			List<CategoriaGrafico> lista_categorias2 = new ArrayList<CategoriaGrafico>();
			List<SerieDatos> lista_series2 = new ArrayList<SerieDatos>();
			GeneradorXMLGrafico grafico = new GeneradorXMLGrafico();
			Color.iniciarColor();
			existe_grafico = false;

			Complejo complejo = complejoDao.obtenerComplejo(Integer
					.parseInt(id_complejos_seleccionados[x]));
			map_complex.put("nombre_complejo", complejo.getNombre());

			lista_categorias.add(new CategoriaGrafico(complejo.getNombre()));

			fecha_inicio_tmp.set(
					fecha_inicio_primer_periodo.get(Calendar.YEAR),
					fecha_inicio_primer_periodo.get(Calendar.MONTH),
					fecha_inicio_primer_periodo.get(Calendar.DAY_OF_MONTH), 0,
					0);
			fecha_fin_tmp.set(fecha_fin_primer_periodo.get(Calendar.YEAR),
					fecha_fin_primer_periodo.get(Calendar.MONTH),
					fecha_fin_primer_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
			contador_dias = 0;
			total_asistencias = 0;
			contador_semana = 0;
			total_semana = 0;
			// Para cada periodo comienza a avanzar uno a uno los d�as sacando
			// el valor diario de asistencia
			// hasta que el d�a alcanza al de la fecha final.
			while (fecha_inicio_tmp.compareTo(fecha_fin_tmp) != 0) {

				double valor = aggregateDao.obtenerAsistenciaDiariaComplejo(
						fecha_inicio_tmp.getTime(), Integer
								.parseInt(id_complejos_seleccionados[x]));
				total_asistencias = total_asistencias + valor;
				total_semana = total_semana + valor;

				if (por_semana) {
					// si el contador de dias llega a 7 entonces avanza a la
					// siguiente semana
					if (contador_dias == 7) {
						lista_categorias2.add(new CategoriaGrafico("Sem"
								+ contador_semana / 7));
						lista_dias_primer_periodo.add("Sem" + contador_semana
								/ 7);

						asistencia_por_dias_primer.add(total_semana);
						lista_valores_falsos.add(new Double(1));
						lista_valores.add(new Valor(((Double) total_semana)
								.intValue()));
						contador_dias = 0;
						total_semana = 0;
					}
					contador_semana++;
				} else {
					asistencia_por_dias_primer.add(valor);
					lista_valores_falsos.add(new Double(1));
					lista_valores.add(new Valor(((Double) valor).intValue()));

					/*
					 * al listar los dias se ocupa la inicial del d�a, el if
					 * siguiente es para poner una 'X' en la inicial si es que
					 * el d�a es Miercoles, para no confundirse con la 'M' de
					 * Martes.
					 */
					char inicial_dia = ' ';
					if (fecha_inicio_tmp.get(Calendar.DAY_OF_WEEK) != 4)
						inicial_dia = Dia.getDia(
								(fecha_inicio_tmp.get(Calendar.DAY_OF_WEEK)))
								.charAt(0);
					else
						inicial_dia = 'X';
					lista_categorias2.add(new CategoriaGrafico("" + inicial_dia
							+ fecha_inicio_tmp.get(Calendar.DAY_OF_MONTH)));
					lista_dias_primer_periodo.add("" + inicial_dia
							+ fecha_inicio_tmp.get(Calendar.DAY_OF_MONTH));
				}

				fecha_inicio_tmp.add(Calendar.DAY_OF_MONTH, 1);
				contador_dias++;
			}

			serieDat.setNombreSerie("Primer Periodo");
			serieDat.setValores(lista_valores);
			serieDat.setColor(Color.generarColor());
			serieDat.setPesosDias(lista_valores_falsos);
			serieDat.setPesoPel(new Double(1));
			lista_series2.add(serieDat);

			if (total_asistencias > 0)
				existe_grafico = true;
			double asistencia_primer_periodo = total_asistencias;
			map_complex.put("por_dias_primer_periodo",
					asistencia_por_dias_primer);
			map_complex.put("total_primer_periodo", total_asistencias);

			// fin y vamos con el otro periodo
			fecha_inicio_tmp.set(fecha_inicio_segundo_periodo
					.get(Calendar.YEAR), fecha_inicio_segundo_periodo
					.get(Calendar.MONTH), fecha_inicio_segundo_periodo
					.get(Calendar.DAY_OF_MONTH), 0, 0);
			fecha_fin_tmp.set(fecha_fin_segundo_periodo.get(Calendar.YEAR),
					fecha_fin_segundo_periodo.get(Calendar.MONTH),
					fecha_fin_segundo_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
			total_asistencias = 0;
			contador_semana = 0;
			contador_dias = 0;
			total_semana = 0;
			while (fecha_inicio_tmp.compareTo(fecha_fin_tmp) != 0) {

				double valor = aggregateDao.obtenerAsistenciaDiariaComplejo(
						fecha_inicio_tmp.getTime(), Integer
								.parseInt(id_complejos_seleccionados[x]));
				total_asistencias = total_asistencias + valor;
				total_semana = total_semana + valor;

				if (por_semana) {
					if (contador_dias == 7) {
						lista_dias_segundo_periodo.add("Sem" + contador_semana
								/ 7);

						asistencia_por_dias_segundo.add(total_semana);
						lista_valores_falsos2.add(new Double(1));
						lista_valores2.add(new Valor(((Double) total_semana)
								.intValue()));
						contador_dias = 0;
						total_semana = 0;
					}
					contador_semana++;
				} else {
					asistencia_por_dias_segundo.add(valor);
					lista_valores_falsos2.add(new Double(1));
					lista_valores2.add(new Valor(((Double) valor).intValue()));

					char inicial_dia = ' ';
					if (fecha_inicio_tmp.get(Calendar.DAY_OF_WEEK) != 4)
						inicial_dia = Dia.getDia(
								(fecha_inicio_tmp.get(Calendar.DAY_OF_WEEK)))
								.charAt(0);
					else
						inicial_dia = 'X';
					lista_dias_segundo_periodo.add("" + inicial_dia
							+ fecha_inicio_tmp.get(Calendar.DAY_OF_MONTH));

				}
				fecha_inicio_tmp.add(Calendar.DAY_OF_MONTH, 1);
				contador_dias++;
			}
			if (total_asistencias > 0)
				existe_grafico = true;

			serieDat2.setNombreSerie("Segundo Periodo");
			serieDat2.setValores(lista_valores2);
			serieDat2.setColor(Color.generarColor());
			serieDat2.setPesosDias(lista_valores_falsos2);
			serieDat2.setPesoPel(new Double(1));
			lista_series2.add(serieDat2);

			grafico.setCategorias(lista_categorias2);
			grafico.setDatos(lista_series2);
			grafico.setTitulo("Asistencias Diarias.");
			grafico.setLegendaX("Dias");
			grafico.setLegendaY1("Asistencia");
			String graficString = grafico.aXML().replace("\"", "'");
			graficString = graficString.replace("\n", "");
			map_complex.put("grafico", graficString);

			map_complex.put("lista_dias_primer_periodo",
					lista_dias_primer_periodo);
			map_complex.put("lista_dias_segundo_periodo",
					lista_dias_segundo_periodo);
			map_complex.put("por_dias_segundo_periodo",
					asistencia_por_dias_segundo);
			double asistencia_segundo_periodo = total_asistencias;
			map_complex.put("total_segundo_periodo", total_asistencias);
			map_complex.put("existe_grafico", existe_grafico);
			arreglo_maps_por_complejo.add(map_complex);

			mv.addObject("lista_dias_segundo_periodo",
					lista_dias_segundo_periodo);
			mv
					.addObject("lista_dias_primer_periodo",
							lista_dias_primer_periodo);

			double porcentaje_diferencia = (asistencia_segundo_periodo - asistencia_primer_periodo)
					/ asistencia_primer_periodo;
			map_complex.put("porcentaje_diferencia", porcentaje_diferencia);
		}
		// existe grafico se envia al jsp para no mostrar el grafico cuando no
		// hayan datos
		mv.addObject("arreglo_maps_por_complejo", arreglo_maps_por_complejo);
		mv.addObject("contador_dias", contador_dias);
		mv.addObject("existe_grafico", existe_grafico);

		// el if siguiente es para determinar el width que tendra el gr�fico
		// flash
		// de acuerdo a la cantidad de datos(cantidad de d�as) para que se vea
		// legible
		if (contador_dias >= 80)
			largo_grafico = 80;
		else if (contador_dias < 6)
			largo_grafico = 6;
		else
			largo_grafico = contador_dias;

		mv.addObject("largo_grafico", largo_grafico);

		// Guarda datos en sesion por si se quiere realizar el pdf
		arg0.getSession().setAttribute("para_reportes", mv);

		GeneradorXMLGrafico grafico = new GeneradorXMLGrafico();

		Color.iniciarColor();
		for (int i = 0; i < 2; i++) {
			SerieDatos serieDat = new SerieDatos();
			if (i == 0)
				serieDat.setNombreSerie("Primer Periodo");
			else
				serieDat.setNombreSerie("Segundo Periodo");
			List<Valor> lista_valores = new ArrayList<Valor>();
			/*
			 * lista_valores_falsos = son para evitar un error con los
			 * ponderadores ya que este grafico no tiene ponderadores, por lo
			 * que, en este caso, todos los ponderadores son 1
			 */
			List<Double> lista_valores_falsos = new ArrayList<Double>();

			for (Map<String, Object> mapa_complejo : arreglo_maps_por_complejo) {
				// un valor falso para cada valor real
				lista_valores_falsos.add(new Double(1));
				if (i == 0)
					lista_valores.add(new Valor(((Double) mapa_complejo
							.get("total_primer_periodo")).intValue()));
				else
					lista_valores.add(new Valor(((Double) mapa_complejo
							.get("total_segundo_periodo")).intValue()));

			}
			serieDat.setValores(lista_valores);
			serieDat.setColor(Color.generarColor());
			serieDat.setPesosDias(lista_valores_falsos);
			serieDat.setPesoPel(new Double(1));
			lista_series.add(serieDat);
		}
		grafico.setCategorias(lista_categorias);
		grafico.setDatos(lista_series);
		grafico.setTitulo("Asistencias por Periodo");
		grafico.setLegendaX("Periodos");
		grafico.setLegendaY1("Asistencia");
		String graficString = grafico.aXML().replace("\"", "'");
		graficString = graficString.replace("\n", "");
		mv.addObject("grafico", graficString);

		return mv;
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
	 * @return the aggregateDao
	 */
	public AggregateDao getAggregateDao() {
		return aggregateDao;
	}

	/**
	 * @param aggregateDao
	 *            the aggregateDao to set
	 */
	public void setAggregateDao(AggregateDao aggregateDao) {
		this.aggregateDao = aggregateDao;
	}
}
