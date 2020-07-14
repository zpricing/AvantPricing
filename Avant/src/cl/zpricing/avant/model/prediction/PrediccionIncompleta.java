package cl.zpricing.avant.model.prediction;

import java.util.ArrayList;
import java.util.Date;

import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.model.Usuario;

/**
 * Clase que se usa para recuperar los datos mas relevantes que posee una prediccion incompleta
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 27-01-2009 Oliver Cordero: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PrediccionIncompleta {
	private int idPrediccion;
	private String pelicula;
	private String complejo;
	private Date fecha;
	private Usuario usuario;
	private Integer estado;
	private ArrayList<Marker> markersFecha;
	
	
	public ArrayList<Marker> getMarkersFecha() {
		return markersFecha;
	}
	public void setMarkersFecha(ArrayList<Marker> markersFecha) {
		this.markersFecha = markersFecha;
	}
	public int getIdPrediccion() {
		return idPrediccion;
	}
	public void setIdPrediccion(int idPrediccion) {
		this.idPrediccion = idPrediccion;
	}
	public String getPelicula() {
		return pelicula;
	}
	public void setPelicula(String pelicula) {
		this.pelicula = pelicula;
	}
	public String getComplejo() {
		return complejo;
	}
	public void setComplejo(String complejo) {
		this.complejo = complejo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String toString(){
		return "id: "+idPrediccion+ " fecha: "+fecha+"pelicula: "+pelicula;
	}
}
