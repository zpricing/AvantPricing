/**
 * 
 */
package cl.zpricing.avant.web.chart;

import java.util.ArrayList;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-05-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class SerieDatosSimple {

	private String nombre;
	private String color;
	private ArrayList<Integer> valores;

	/**
	 * @param nombre
	 * @param color
	 * @param valores
	 */
	public SerieDatosSimple(String nombre, String color,
			ArrayList<Integer> valores) {
		this.nombre = nombre;
		this.valores = valores;
		this.color = color;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public ArrayList<Integer> getValores() {
		return valores;
	}

	public void setValores(ArrayList<Integer> valores) {
		this.valores = valores;
	}

}
