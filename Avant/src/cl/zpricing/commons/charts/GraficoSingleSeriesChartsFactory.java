package cl.zpricing.commons.charts;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import cl.zpricing.commons.charts.vo.DatosGrafico;
import cl.zpricing.commons.charts.vo.DatosGraficoSingleSeriesCharts;

/**
 * <p>Clase de implementacion de generacion de XML para graficos de una sola serie de FusionCharts (SingleSeriesCharts).</p>
 * 
 * <p>Se utiliza el value object DatosGraficoSingleSeriesCharts como input para poblar el XML.</p>
 *
 * <p>
 * <b>Registro de versiones:</b>
 * <ul>
 * 	   <li>1.0 (19-08-2009: Nicolas Dujovne W.): version inicial.</li>
 * </ul>
 * </p>
 * <p>
 *   <b>Todos los derechos reservados por ZhetaPricing.</b>
 * </p>
 */
public class GraficoSingleSeriesChartsFactory extends GraficoFactory {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	/**
	 * Contiene los datos para la creacion del XML.
	 */
	private DatosGraficoSingleSeriesCharts datosGraficoColumna;
	
	/**
	 * <p>Método implementa la generación del objeto document en formato DOM para la creación
	 * del XML para toda la gama de gráficos de una sola serie de FusionCharts (Single Series Charts).</p>
	 * 
	 * <p>
	 * Esta implementación utiliza el objeto DatosGraficoSingleSeriesCharts a modo de input de datos, este objeto
	 * debe ser poblado mediante el Mapeador ingresado en la clase GraficoFactory.
	 * </p>
	 * 
	 * <p>
	 * <b>Registro de versiones:</b>
	 * <ul>
	 * 	   <li>1.0 (19-08-2009: Nicolás Dujovne W.): versión inicial.</li>
	 * </ul>
	 * </p>
	 *
	 */
	@Override
	protected void generarEstructuraXML(DatosGrafico datosGrafico) {
		datosGraficoColumna = (DatosGraficoSingleSeriesCharts) datosGrafico;
		this.configurarDocumento();
		
		Element raizEle = documentDOM.createElement("graph");
		documentDOM.appendChild(raizEle);
		
		String[] llaves = datosGraficoColumna.getEncabezado().keySet().toArray(new String[0]);
		
		for (int i = 0 ; i < llaves.length ; i++) {
			String valor = datosGraficoColumna.getEncabezado().get(llaves[i]);
			raizEle.setAttribute(llaves[i], valor);
		}
		
		for (Iterator<HashMap<String, String>> iter = datosGraficoColumna.getValores().iterator() ; iter.hasNext() ; ) {
			HashMap<String, String> valorSet = iter.next();
			Element setEle = documentDOM.createElement("set");
			raizEle.appendChild(setEle);
			
			String[] llavesSet = valorSet.keySet().toArray(new String[0]);
			
			for (int i = 0 ; i < llavesSet.length ; i++) {
				String valor = valorSet.get(llavesSet[i]);
				setEle.setAttribute(llavesSet[i], valor);
			}
		}
	}
}
