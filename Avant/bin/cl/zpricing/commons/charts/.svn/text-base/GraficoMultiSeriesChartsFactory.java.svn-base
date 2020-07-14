package cl.zpricing.commons.charts;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import cl.zpricing.commons.charts.vo.DatosGrafico;
import cl.zpricing.commons.charts.vo.DatosGraficoMultiSeriesCharts;
import cl.zpricing.commons.charts.vo.DatosGraficoSingleSeriesCharts;

/**
 * <p>Clase de implementacion de generacion de XML para graficos con multiples series de FusionCharts (MultiSeriesCharts).</p>
 *
 * <p>
 * <b>Registro de versiones:</b>
 * <ul>
 * 	   <li>1.0 (02-12-2009: Nicolas Dujovne W.): version inicial.</li>
 * </ul>
 * </p>
 * <p>
 *   <b>Todos los derechos reservados por ZhetaPricing.</b>
 * </p>
 */
public class GraficoMultiSeriesChartsFactory extends GraficoFactory {

	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private DatosGraficoMultiSeriesCharts datosGraficoMultiSeries;
	
	@Override
	protected void generarEstructuraXML(DatosGrafico datosGrafico) {
		datosGraficoMultiSeries = (DatosGraficoMultiSeriesCharts) datosGrafico;
		this.configurarDocumento();
		
		Element raizEle = documentDOM.createElement("graph");
		documentDOM.appendChild(raizEle);
		
		String[] llaves = datosGraficoMultiSeries.getEncabezado().keySet().toArray(new String[0]);
		
		for (int i = 0 ; i < llaves.length ; i++) {
			String valor = datosGraficoMultiSeries.getEncabezado().get(llaves[i]);
			raizEle.setAttribute(llaves[i], valor);
		}
		
		//Creando elemento CATEGORIES con sus atributos
		Element categoriesElement = documentDOM.createElement("categories");
		String[] categoriesLlaves = datosGraficoMultiSeries.getEncabezadoCategorias().keySet().toArray(new String[0]);
		
		for (String llave : categoriesLlaves) {
			String valor = datosGraficoMultiSeries.getEncabezadoCategorias().get(llave);
			categoriesElement.setAttribute(llave, valor);
		}
		
		//Creando los elementos CATEGORY con sus atributos.
		for (HashMap<String, String> categoria : datosGraficoMultiSeries.getCategorias()) {
			Element categoryElement = documentDOM.createElement("category");
			
			String[] atributosCategoria = categoria.keySet().toArray(new String[0]);
			
			for (String llave : atributosCategoria) {
				String valor = categoria.get(llave);
				categoryElement.setAttribute(llave, valor);
			}
			
			categoriesElement.appendChild(categoryElement);
		}
		raizEle.appendChild(categoriesElement);
		
		
		for (DatosGraficoSingleSeriesCharts dataSet : datosGraficoMultiSeries.getDataSet()) {
			Element dataSetElement = documentDOM.createElement("dataset");
			String[] datosEncabezadoDataSet = dataSet.getEncabezado().keySet().toArray(new String[0]);
			
			for (String llave : datosEncabezadoDataSet) {
				String valor = dataSet.getEncabezado().get(llave);
				dataSetElement.setAttribute(llave, valor);
			}
			
			for (HashMap<String, String> set : dataSet.getValores()) {
				Element setElement = documentDOM.createElement("set");
				
				String[] datosSet = set.keySet().toArray(new String[0]);
				
				for (String llave : datosSet) {
					String valor = set.get(llave);
					setElement.setAttribute(llave, valor);
				}
				dataSetElement.appendChild(setElement);
			}
			
			raizEle.appendChild(dataSetElement);
		}
	}
}
