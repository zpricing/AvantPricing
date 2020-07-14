/**
 * 
 */
package cl.zpricing.avant.web.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.servicios.AggregateDao;
import cl.zpricing.avant.servicios.PrediccionDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.GraficoLineaSimple;
import cl.zpricing.avant.web.chart.SerieDatosSimple;

import com.infosoftglobal.fusioncharts.FusionChartsHelper;


/**
 * <b>Muestra un grafico para ver la prediccion y la asistencia real</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 24-05-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class MostrarPrediccionHistoricaDiariaController implements Controller {

	private PrediccionDao prediccionDao;
	private AggregateDao aggregateDao;
	
	public PrediccionDao getPrediccionDao() {
		return prediccionDao;
	}

	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}

	public AggregateDao getAggregateDao() {
		return aggregateDao;
	}

	public void setAggregateDao(AggregateDao aggregateDao) {
		this.aggregateDao = aggregateDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("idPrediccion");
		int idPrediccion;
		try{
			idPrediccion = (Integer.parseInt(id));
		}catch(NumberFormatException e){
			idPrediccion = 0;
		}
		Prediccion prediccion = prediccionDao.obtenerPrediccion(idPrediccion);
		
		//Obtener las predicciones por dia.
		ArrayList<PrediccionPorDia> predsPorDia = prediccion.getPrediccionPorDia();
		//Los datos por dia
		ArrayList<Integer> preds = new ArrayList<Integer>();
		ArrayList<Integer> reales = new ArrayList<Integer>();
		ArrayList<Integer> error = new ArrayList<Integer>();
		//Datos para el grafico.
		ArrayList<String> diasGrafico = new ArrayList<String>();
		//Ingreso los datos a los arraylist
		Iterator<PrediccionPorDia> iterPredDia = predsPorDia.iterator();
		while(iterPredDia.hasNext())
		{
			PrediccionPorDia predDia = iterPredDia.next();
			diasGrafico.add(Util.DateToString(predDia.getFecha()));
			preds.add(predDia.getEstimacion());
			int real = aggregateDao.obtenerAsistenciaDiariaPeliculaComplejo(predDia.getFecha(), prediccion.getPelicula().getId(), prediccion.getComplejo().getId());
			reales.add(real);
			int erTemp = predDia.getEstimacion() - real;
			error.add(erTemp);
		}
		//Agrego la informacion al map para la vista.
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("predsPorDia", predsPorDia);
		map.put("predicciones", preds);
		map.put("reales", reales);
		map.put("error", error);
		map.put("complejo", prediccion.getComplejo());
		map.put("pelicula", prediccion.getPelicula());
		map.put("prediccion", prediccion);
		//Cargo la informacion para el grafico.
		ArrayList<SerieDatosSimple> datos = new ArrayList<SerieDatosSimple>();
		FusionChartsHelper helper = new FusionChartsHelper();
		SerieDatosSimple datosPred = new SerieDatosSimple("Prediccion", helper.getFCColor(), preds);
		SerieDatosSimple datosReales = new SerieDatosSimple("Real", helper.getFCColor(), reales);
		datos.add(datosPred);
		datos.add(datosReales);
		GraficoLineaSimple grafico = new GraficoLineaSimple("Prediccion",
				"Dia", "Asistencia", "", 0, diasGrafico, datos);
		map.put("grafico", grafico);
		return new ModelAndView("predictions/mostrarPrediccionHistoricaDiaria", map);
	}
	
}
