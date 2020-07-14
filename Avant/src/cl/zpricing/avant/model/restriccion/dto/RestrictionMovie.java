package cl.zpricing.avant.model.restriccion.dto;

import java.util.Date;

import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.restriccion.Restriction;

public class RestrictionMovie {
	private Pelicula pelicula;
	private Restriction restriccion;
	private Date fechaHasta;
	
	public Pelicula getPelicula() {
		return pelicula;
	}
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
	public Restriction getRestriccion() {
		return restriccion;
	}
	public void setRestriccion(Restriction restriccion) {
		this.restriccion = restriccion;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
}
