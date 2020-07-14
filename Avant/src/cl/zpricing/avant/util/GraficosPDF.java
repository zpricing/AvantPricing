/**
 * 
 */
package cl.zpricing.avant.util;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;

import com.lowagie.text.Font;

/**
 * Clase para generar facilmente los graficos utilizados en los reportes PDF.
 * 
 * Utiliza la clase org.jfree.chart.ChartFactory para generar los gr�ficos de acuerdo
 * a los datos que se le pasa en cada m�todo, por ahora est�n implementados m�todos
 * para generar gr�ficos de barra 3D(getBarChart), gr�fico de lineas 3D(getLineChart),
 * gr�fico tipo torta 3D(getPieChart) y gr�fico de barra apilados horizontal (getStackedBar).
 * Se pueden implementar todos los dem�s tipos de gr�ficos existentes en org.jfree.chart.ChartFactory.
 * 
 * Los m�todos devuelven un objeto JFreeChart que hasta el momento es utilizado al generar documentos PDF,
 * incrustandose en ellos como imagen.
 * 
 * Para mayor informaci�n mirar la documentaci�n de JFreeChart:
 * http://www.jfree.org/jfreechart/api/javadoc/index.html
 * 
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 27-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class GraficosPDF {
	private static Logger log = (Logger) Logger.getLogger(GraficosPDF.class);

	 /**
	 * @param titulo: titulo del gr�fico
	 * @param ejeX: String identificador de los valores del eje de las abscisas, e.g. "Asistencias".
	 * @param ejeY: String identificador de los valores del eje de las ordenadas, e.g. "Ingresos".
	 * @param lista_de_maps_grafico: lista que contiene un map por cada elemento(complejo) que haya que graficar
	 * @param valores_ejeX : lista de Strings de las categorias del eje X, e.g. "Lun,Mar,Mie..","Enero,Feb,Mar...".
	 * @param legend : booleano que determinara si muestra las leyendas del gr�fico
	 * @return grafico de barra JFreeChart 
	 */
	@SuppressWarnings("unchecked")
	public static JFreeChart getBarChart(String titulo,String ejeX,String ejeY,List<Map<String, Object>> lista_de_maps_grafico,List<String> valores_ejeX,boolean legend) {
	    	
	    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    	
	    	int contador=0;
	    	for (Map<String, Object> map_grafico : lista_de_maps_grafico) {
	    		
	    		List<Double> lista_valores_map = (List<Double>)map_grafico.get("valores");
	    		String nombre_categoria=(String)map_grafico.get("categoria");
				
	    		contador=0;
	    		for (Double valor : lista_valores_map) {
	    			dataset.setValue(valor, nombre_categoria, valores_ejeX.get(contador));	
	    			System.out.println(lista_valores_map.size());
	    			contador++;
				}
	    		
			}
	    	JFreeChart chart = ChartFactory.createBarChart3D(titulo,ejeX, ejeY, dataset, PlotOrientation.VERTICAL, legend, false, false);; 
	    	
	    	/* Todo lo siguiente es para cambiar los tipos de letra,
	    	 * disminuir los tama�os y seleccionar fuentes.
	    	 */
	    	//Primero se obtiene el Plot del gr�fico generado.
			CategoryPlot plot = chart.getCategoryPlot();
			//set�o la fuente de los elementos del Dominio(eje x).
			plot.getDomainAxis().setLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			//Obtengo el Render del gr�fico para setearle la fuente de la leyenda.
			org.jfree.chart.renderer.category.BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
			renderer.setBaseLegendTextFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			//Obtengo las caracter�sticas del ejeX y hago un "cast" de acuerdo al tipo de gr�fico (Puede variar en otros tipos de gr�fico).
			CategoryAxis3D axisX = (CategoryAxis3D)plot.getDomainAxis();
			axisX.setTickLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			axisX.setLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			ValueAxis axisY = (ValueAxis)plot.getRangeAxis();
			axisY.setTickLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			axisY.setLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			//fin
			
	    	return chart;
	    }
	 
	 /**
	 * @param titulo
	 * @param ejeX
	 * @param ejeY
	 * @param lista_de_maps_grafico
	 * @param valores_ejeX
	 * @param legend
	 * @return grafico de linea JFreeChart
	 */
	@SuppressWarnings("unchecked")
	public static JFreeChart getLineChart(String titulo,String ejeX,String ejeY,List<Map<String, Object>> lista_de_maps_grafico,List<String> valores_ejeX,boolean legend) {
	    	
	    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    	
	    	int contador=0;
	    	for (Map<String, Object> map_grafico : lista_de_maps_grafico) {
	    		
	    		List<Double> lista_valores_map = (List<Double>)map_grafico.get("valores");
	    		String nombre_categoria=(String)map_grafico.get("categoria");
				
	    		contador=0;
	    		for (Double valor : lista_valores_map) {
	    			dataset.setValue(valor, nombre_categoria, valores_ejeX.get(contador));	
	    			System.out.println(lista_valores_map.size());
	    			contador++;
				}
	    		
			}
	       
	    	JFreeChart chart = ChartFactory.createLineChart3D(titulo, ejeX, ejeY, dataset, PlotOrientation.VERTICAL, legend, true, true);
	    	
	    	//todo esto es para cambiar los tipos de letra 
			CategoryPlot plot = chart.getCategoryPlot();
			plot.getDomainAxis().setLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			LineRenderer3D renderer = (LineRenderer3D) plot.getRenderer();
			renderer.setBaseLegendTextFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			CategoryAxis3D axisX = (CategoryAxis3D)plot.getDomainAxis();
			axisX.setTickLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			axisX.setLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			ValueAxis axisY = (ValueAxis)plot.getRangeAxis();
			axisY.setTickLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			axisY.setLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
			//fin
	    	
	    return chart;
	}
	
	/**
	 * @param titulo
	 * @param lista_de_maps_grafico
	 * @param legend
	 * @return grafico tipo torta JFreeChart  
	 */
	public static JFreeChart getPieChart(String titulo,List<Map<String, Object>> lista_de_maps_grafico,boolean legend) {
	    	
	    	DefaultPieDataset pieDataSet = new DefaultPieDataset();
	    	
	    	for (Map<String, Object> map_grafico : lista_de_maps_grafico) {
	    		
	    		Double valor_categoria = (Double)map_grafico.get("valor");
	    		String nombre_categoria=(String)map_grafico.get("categoria");
				pieDataSet.setValue(nombre_categoria,valor_categoria );	
			}

	    	JFreeChart chart = ChartFactory.createPieChart3D(titulo, pieDataSet, true,true, true);
	    
	    PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setLabelGenerator(null);
        
        return chart;
	    }
	 
	/**
	 * @param titulo
	 * @param ejeX
	 * @param ejeY
	 * @param catFilas
	 * @param catColumnas
	 * @param datos
	 * @param legend
	 * @return grafico de barras con subconjuntos JFreeChart
	 */
	public static JFreeChart getStackedBar(String titulo,String ejeX, String ejeY, List<String> catFilas, List<String> catColumnas, Map<String, Map<String, Double>> datos,boolean legend){
		
		double dataset[][] = new double[catFilas.size()][catColumnas.size()];

		Iterator<String> itFil= catFilas.iterator();
		int i=0;
		while (itFil.hasNext()) {
			String catFila = itFil.next();
			Map<String,Double> datosFila = datos.get(catFila);
			Iterator<String> itCol = catColumnas.iterator();
			int j=0;
    		while (itCol.hasNext()){
    			String catCol = itCol.next();
    			dataset[i][j] = datosFila.get(catCol);
    			log.debug("dato Grafico pdf f: "+catFila+" c: "+catCol+" v: "+datosFila.get(catCol));
    			j++;
    		}
    		i++;
		}
		CategoryDataset dataSet = DatasetUtilities.createCategoryDataset(catFilas.toArray(new String[0]), catColumnas.toArray(new String[0]), dataset);
		
		JFreeChart chart = ChartFactory.createStackedBarChart(null, ejeX, ejeY, dataSet, PlotOrientation.HORIZONTAL, legend, false, false);
				
		//todo esto es para cambiar los tipos de letra
		chart.setBackgroundPaint(new Color(255, 255, 255));
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getDomainAxis().setLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
		
		StackedBarRenderer renderer = (StackedBarRenderer)plot.getRenderer();
		renderer.setSeriesPaint(0, new Color(255, 0, 0));
		renderer.setSeriesPaint(1, new Color(200, 200, 200));
		renderer.setBaseLegendTextFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
		
		CategoryAxis axisX = (CategoryAxis)plot.getDomainAxis();
		axisX.setTickLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
		axisX.setLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
		
		ValueAxis axisY = (ValueAxis)plot.getRangeAxis();
		axisY.setTickLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
		axisY.setLabelFont(new java.awt.Font("SansSerif",Font.NORMAL,6));
		
		//fin
		return chart;
		 
	}

	public static void setLog(Logger log) {
		GraficosPDF.log = log;
	}

	public static Logger getLog() {
		return log;
	}
}
