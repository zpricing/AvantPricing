package cl.zpricing.avant.model.loadmanager;

import java.util.ArrayList;
import java.util.HashMap;

import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.model.DescripcionId;

/**
 * Es un grupo de precios
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 29-01-2009 MARIO: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Plantilla extends DescripcionId {

	/**
	 * Se refiere a los dias hasta la funcion que se debe aplicar la plantilla.
	 * 
	 */
	private int diasFuncion;
	private int idExterno;
	private boolean padre;
	private ArrayList<Area> areas;
	private ArrayList<Clase> clases;
	private HashMap<Area, Clase> mapAreaClase;

	/**
	 * @return dias Funcion
	 */
	public int getDiasFuncion() {
		return diasFuncion;
	}

	/**
	 * @param diasFuncion
	 */
	public void setDiasFuncion(int diasFuncion) {
		this.diasFuncion = diasFuncion;
	}

	/**
	 * @return id externo
	 */
	public int getIdExterno() {
		return idExterno;
	}

	/**
	 * @param idExterno
	 */
	public void setIdExterno(int idExterno) {
		this.idExterno = idExterno;
	}

	/**
	 * @return padre
	 */
	public boolean getPadre() {
		return padre;
	}

	/**
	 * @param padre
	 */
	public void setPadre(boolean padre) {
		this.padre = padre;
	}

	/**
	 * @return lista de areas
	 */
	public ArrayList<Area> getAreas() {
		return areas;
	}

	/**
	 * @param areas
	 */
	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}

	/**
	 * @return lista de clases
	 */
	public ArrayList<Clase> getClases() {
		return clases;
	}

	/**
	 * @param clases
	 */
	public void setClases(ArrayList<Clase> clases) {
		this.clases = clases;
	}

	/**
	 * @return lista de areas con las clases asociadas a cada area
	 */
	public HashMap<Area, Clase> getMapAreaClase() {
		return mapAreaClase;
	}

	/**
	 * @param lista
	 *            de areas con las clases asociadas a cada area
	 */
	public void setMapAreaClase(HashMap<Area, Clase> mapAreaClase) {
		this.mapAreaClase = mapAreaClase;
	}
}
