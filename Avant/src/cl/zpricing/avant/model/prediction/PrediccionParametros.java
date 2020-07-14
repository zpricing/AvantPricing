package cl.zpricing.avant.model.prediction;

import cl.zpricing.avant.model.Pelicula;

/**
 * 
 * <b>Esta clase contiene el ponderador que se le asignó a una película en
 * particular al realizar una Predicción. </b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 22-12-2009 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class PrediccionParametros {
	private Prediccion prediccion;
	private Pelicula pelicula;
	private Double ponderacion;

	public Prediccion getPrediccion() {
		return prediccion;
	}

	public void setPrediccion(Prediccion prediccion) {
		this.prediccion = prediccion;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Double getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(Double ponderacion) {
		this.ponderacion = ponderacion;
	}

}
