package cl.zpricing.avant.model.prediction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.DescripcionId;
import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Usuario;

/**
 * <b>Clase que se usa para manejar todos los datos concernientes a una prediccion</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class Prediccion extends DescripcionId implements Serializable {
	private static final long serialVersionUID = 5558425696266332885L;
	private Date fecha;
    public Usuario usuario;
    public Pelicula pelicula;
	public ArrayList<PrediccionPorDia> prediccionPorDia;
    public Complejo complejo;
    private double varianza;
    private boolean error;
    private ArrayList<Marker> markersFecha;
    private ArrayList<PrediccionParametros> prediccionParametros; 
    private Integer estado;
    
    public Prediccion() {
    	estado = 0;
    }

	public ArrayList<PrediccionParametros> getPrediccionParametros() {
		return prediccionParametros;
	}
	public void setPrediccionParametros(ArrayList<PrediccionParametros> prediccionParametros) {
		this.prediccionParametros = prediccionParametros;
	}
	public ArrayList<Marker> getMarkersFecha() {
		return markersFecha;
	}
	public void setMarkersFecha(ArrayList<Marker> markersFecha) {
		this.markersFecha = markersFecha;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public double getVarianza() {
		return varianza;
	}
	public void setVarianza(double varianza) {
		this.varianza = varianza;
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
	public Pelicula getPelicula() {
		return pelicula;
	}
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
	public ArrayList<PrediccionPorDia> getPrediccionPorDia() {
		return prediccionPorDia;
	}
	public void setPrediccionPorDia(ArrayList<PrediccionPorDia> prediccionPorDia) {
		this.prediccionPorDia = prediccionPorDia;
	}
	public Complejo getComplejo() {
		return complejo;
	}
	public void setComplejo(Complejo complejo) {
		this.complejo = complejo;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	@Deprecated
	public boolean getHayPrediccionesPorFuncion(){
		if(this.prediccionPorDia!=null)
			if(this.prediccionPorDia.size()>0)
				if(this.prediccionPorDia.get(0).getPrediccionesPorFuncion()!=null)
					if(this.prediccionPorDia.get(0).getPrediccionesPorFuncion().size()>0)
						return true;
		return false;
	}
	
	/**
	 * <b>Descripcion de la funci�n</b>
	 * Restaura los links hacia arriba en las predicciones por dia, funcion y clases que contiene
	 * para posteriormente poder realizar su almacenamiento en base de datos<br>
	 * 
	 * Registro de versiones:
	 * <ul>
	 *   <li>1.0 09-02-2009 Oliver Cordero: versi�n inicial.</li>
	 * </ul>
	 * <P>
	 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
	 * <P>
	 */ 
	public void restaurarPrediccion(){
		if(this.prediccionPorDia!=null){
			Iterator<PrediccionPorDia> itppDia = this.prediccionPorDia.iterator();
			//Pasando prediccion a prediccion por dia
			while(itppDia.hasNext()){
				PrediccionPorDia ppDia = itppDia.next();
				ppDia.setPrediccion(this);
				if(ppDia.getPrediccionesPorFuncion() != null){
					Iterator<PrediccionPorFuncion> itppFuncion = ppDia.getPrediccionesPorFuncion().iterator();
					//Pasando prediccion por dia a prediccion por funcion
					while(itppFuncion.hasNext()){
						PrediccionPorFuncion ppFuncion = itppFuncion.next();
						ppFuncion.setPrediccionPorDia(ppDia);
						if(ppFuncion.getPrediccionesPorClase() != null){
							Iterator<PrediccionPorClase> itppClase = ppFuncion.getPrediccionesPorClase().iterator();
							//Pasando prediccion por funcion a prediccion por clase
							while(itppClase.hasNext()){
								PrediccionPorClase ppClase = itppClase.next();
								ppClase.setPrediccionPorFuncion(ppFuncion);
							}
						}
					}
				}
			}
		}
	}
 }
