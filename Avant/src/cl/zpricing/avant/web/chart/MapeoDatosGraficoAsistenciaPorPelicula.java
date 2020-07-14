package cl.zpricing.avant.web.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.zpricing.avant.model.AsistenciaDiaria;
import cl.zpricing.avant.util.Util;
import cl.zpricing.commons.charts.mapping.MapeoDeDatosAbstract;
import cl.zpricing.commons.charts.vo.DatosGrafico;
import cl.zpricing.commons.charts.vo.DatosGraficoSingleSeriesCharts;

public class MapeoDatosGraficoAsistenciaPorPelicula extends
		MapeoDeDatosAbstract {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public DatosGrafico execute(Object estructuraDeDatos,
			Map<String, Object> parametros) {
		DatosGraficoSingleSeriesCharts datosGrafico = new DatosGraficoSingleSeriesCharts();

		String nombrePelicula = "";
		if (parametros != null) {
			if (parametros.get("nombrePelicula") != null) {
				try {
					nombrePelicula += (String) parametros.get("nombrePelicula");
				} catch (Exception e) {
					log
							.debug("No se pudo agregar el nombrePelicula al grafico; quedará en blanco.");
				}
			}
		}
		datosGrafico.getEncabezado().put("caption",
				"Asistencia diaria para " + nombrePelicula);
		datosGrafico.getEncabezado().put("xAxisName", "Dias");
		datosGrafico.getEncabezado().put("yAxisName", "Asistencia");
		datosGrafico.getEncabezado().put("decimalPrecision", "0");
		datosGrafico.getEncabezado().put("formatNumberScale", "0");
		datosGrafico.getEncabezado().put("showValues", "1");
		datosGrafico.getEncabezado().put("yAxisMinValue", "0");
		datosGrafico.getEncabezado().put("rotateNames", "1");
		// datosGrafico.getEncabezado().put("decimalSeparator", ".");
		// datosGrafico.getEncabezado().put("decimalPrecision", "2");

		ArrayList<AsistenciaDiaria> listaDeAsistencias = (ArrayList<AsistenciaDiaria>) estructuraDeDatos;
		Iterator itDatosGrafico = listaDeAsistencias.iterator();

		while (itDatosGrafico.hasNext()) {
			AsistenciaDiaria thisAsistenciaDiaria = (AsistenciaDiaria) itDatosGrafico
					.next();

			HashMap<String, String> set = new HashMap<String, String>();
//			log.debug("Dia de la semana: " + thisAsistenciaDiaria.getDiaDeLaSemana());
			String name = thisAsistenciaDiaria.getDiaDeLaSemana().substring(0,
					3);
			name += thisAsistenciaDiaria.getDiasDesdeEstreno();
			String value = thisAsistenciaDiaria.getAsistenciaDiaria();

//			log.debug("  Agregando name = [" + name + "] ; value = [" + value+ "]");
			set.put("name", name.length() > 15 ? name.substring(0, 15) : name);
			set.put("value", value);
			set.put("hoverText", thisAsistenciaDiaria.getDiaDeLaSemana() + " "
					+ Util.DateToString(thisAsistenciaDiaria.getFecha()));
			datosGrafico.getValores().add(set);
		}

		return datosGrafico;
	}

}
