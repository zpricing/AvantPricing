/**
 * 
 */
package cl.zpricing.avant.web.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejemplo para aprender a utilizar el paquete charts
 * 
 * Registro de versiones:
 * <ul>
 * <li>2.0 07-01-2009 Oliver Cordero: genera el XML en un string.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class GraficoPrueba {

	/****
	 * El ejemplo consiste basicamente en agregar 2 series de datos a un grafico
	 * para finalmente generar un XML que pueda graficar FusionCharts
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		// Creamos los valores de la 1era serie de datos
		List<Valor> valores = new ArrayList<Valor>();

		valores.add(new Valor(31));
		valores.add(new Valor(32));

		// Creamos la 1era serie de datos
		SerieDatos datos = new SerieDatos();

		List<Double> lista2 = new ArrayList<Double>();
		lista2.add(new Double(1));
		lista2.add(new Double(1));

		datos.setValores(valores);
		datos.setNombreSerie("serie1");
		datos.setColor(Color.generarColor(200, 200, 200));
		datos.setPesosDias(lista2);
		datos.setPesoPel(new Double(1));
		// Creamos los valores de la 2da serie de datos
		List<Valor> valores2 = new ArrayList<Valor>();

		valores2.add(new Valor(46));
		valores2.add(new Valor(21));

		// Creamos la 2da serie de datos
		SerieDatos datos2 = new SerieDatos();

		List<Double> lista = new ArrayList<Double>();
		lista.add(new Double(1));
		lista.add(new Double(1));

		datos2.setValores(valores2);
		datos2.setNombreSerie("serie2");
		datos2.setColor(Color.generarColor(20, 200, 200));
		datos2.setPesosDias(lista);
		datos2.setPesoPel(new Double(1));
		// datos2.setEjeY(2);

		// Guardamos las series de datos en una lista
		List<SerieDatos> dataSets = new ArrayList<SerieDatos>();

		dataSets.add(datos);
		dataSets.add(datos2);

		// Creamos las categorias del grafico
		List<CategoriaGrafico> categorias = new ArrayList<CategoriaGrafico>();

		categorias.add(new CategoriaGrafico("a"));
		categorias.add(new CategoriaGrafico("b"));
		categorias.add(new CategoriaGrafico("c"));

		// Definimos el grafico como tal con todo lo anterior
		GeneradorXMLGrafico gen = new GeneradorXMLGrafico();
		gen.setDatos(dataSets);
		gen.setCategorias(categorias);
		gen.setLegendaX("a");
		gen.setLegendaY1("y");
		// gen.setLegendaY2("y2");
		String salida = gen.aXML();

		System.out.println(salida);
	}
}
