/**
 * 
 */
package cl.zpricing.avant.web.chart;

import java.util.Iterator;
import java.util.List;

/**
 * <b>Descripci�n de la Clase</b>
 * Permite mantener la asociacion entre una serie a graficar y su color
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 14-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class ColorSerie {
	private int idSerie;
	private String color;
	
	/**
	 * @param idSerie un entero que represente la serie de datos a la que se le esta
	 * asignando el color
	 */
	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}
	
	/**
	 * @param color en formato RRGGBB
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * @param listaColores
	 * @param idSerie
	 * @return color correspondiente a un cierto id
	 */
	public static String colorSerie(List<ColorSerie> listaColores, int idSerie){
		Iterator<ColorSerie> itColor = listaColores.iterator();
		ColorSerie color;
		while(itColor.hasNext()){
			color = itColor.next();
			if(color.idSerie == idSerie)
				return color.color;
		}
		return null;
	}
}
