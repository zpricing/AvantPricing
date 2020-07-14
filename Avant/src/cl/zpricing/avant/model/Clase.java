package cl.zpricing.avant.model;

import java.util.ArrayList;

/**
 * Es una categoria de entrada que tiene asociada un precio, un funcion tiene muchas clases 
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Oliver Cordero: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class Clase extends DescripcionId {
	private double precio;
	private boolean esEspecial;
	private ArrayList<Marker> markers;
	
	public ArrayList<Marker> getMarkers() {
		return markers;
	}
	public void setMarkers(ArrayList<Marker> markers) {
		this.markers = markers;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public boolean isEsEspecial() {
		return esEspecial;
	}
	public void setEsEspecial(boolean esEspecial) {
		this.esEspecial = esEspecial;
	}
 }
