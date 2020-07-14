/**
 * 
 */
package cl.zpricing.avant.web.prediction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.model.prediction.PrediccionPorFuncion;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.PrediccionDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.GraficoLineaSimple;
import cl.zpricing.avant.web.chart.SerieDatosSimple;

import com.infosoftglobal.fusioncharts.FusionChartsHelper;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 28-05-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class MostrarPrediccionHistoricaFuncionesController implements
		Controller {

	private FuncionDao funcionDao;
	private PrediccionDao prediccionDao;

	public FuncionDao getFuncionDao() {
		return funcionDao;
	}

	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	public PrediccionDao getPrediccionDao() {
		return prediccionDao;
	}

	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("idPrediccionDia");
		int idPrediccionDia;
		try {
			idPrediccionDia = (Integer.parseInt(id));
		} catch (NumberFormatException e) {
			idPrediccionDia = 0;
		}
		PrediccionPorDia predPorDia = prediccionDao.obtenerPrediccionPorDia(idPrediccionDia);
		List<PrediccionPorFuncion> predsPorFunc = predPorDia.getPrediccionesPorFuncion();
		Iterator<PrediccionPorFuncion> iterPredPorFunc = predsPorFunc.iterator();
		ArrayList<Date> horas = new ArrayList<Date>();
		ArrayList<String> horasGrafico = new ArrayList<String>();
		ArrayList<Integer> predicciones = new ArrayList<Integer>();
		ArrayList<Integer> asistencias = new ArrayList<Integer>();
		ArrayList<Integer> error = new ArrayList<Integer>();
		while (iterPredPorFunc.hasNext()) {
			PrediccionPorFuncion ppf = iterPredPorFunc.next();
			if (ppf.getFuncionPredecida() != null) {
				horas.add(ppf.getFuncionPredecida().getFecha());
				horasGrafico.add(Util.DateToHourString(ppf
						.getFuncionPredecida().getFecha()));
				asistencias.add(ppf.getFuncionPredecida().getAsistenciaTotal());
				int erTemp = ppf.getEstimacion()
						- ppf.getFuncionPredecida().getAsistenciaTotal();
				error.add(erTemp);
			} else {
				horas.add(null);
				horasGrafico.add("N/A");
				asistencias.add(0);
				error.add(0);
			}
			predicciones.add(ppf.getEstimacion());
		}
		// Map de los datos.
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("horas", horas);
		map.put("predicciones", predicciones);
		map.put("asistencias", asistencias);
		map.put("error", error);
		map.put("pelicula", predPorDia.getPrediccion().getPelicula());
		map.put("complejo", predPorDia.getPrediccion().getComplejo());
		// Cargo la informacion para el grafico.
		ArrayList<SerieDatosSimple> datos = new ArrayList<SerieDatosSimple>();
		FusionChartsHelper helper = new FusionChartsHelper();
		SerieDatosSimple datosPred = new SerieDatosSimple("Prediccion", helper
				.getFCColor(), predicciones);
		SerieDatosSimple datosReales = new SerieDatosSimple("Real", helper
				.getFCColor(), asistencias);
		datos.add(datosPred);
		datos.add(datosReales);
		GraficoLineaSimple grafico = new GraficoLineaSimple("Prediccion",
				"Dia", "Asistencia", "", 0, horasGrafico, datos);
		map.put("grafico", grafico);
		return new ModelAndView(
				"predictions/mostrarPrediccionHistoricaFunciones", map);
	}

}
