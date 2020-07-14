package cl.zpricing.avant.model;

import java.util.Date;

/**
 * Clase usada para extraer informacion agregada de las bases de datos
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 12-01-2009 Mario Lavandero: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Aggregate {
	private Date fecha;
	private int pelicula_id;
	private int complejo_id;
	private int asistencia;

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getPelicula_id() {
		return pelicula_id;
	}
	public void setPelicula_id(int pelicula_id) {
		this.pelicula_id = pelicula_id;
	}
	public int getComplejo_id() {
		return complejo_id;
	}
	public void setComplejo_id(int complejo_id) {
		this.complejo_id = complejo_id;
	}
	public int getAsistencia() {
		return asistencia;
	}
	public void setAsistencia(int asistencia) {
		this.asistencia = asistencia;
	}
}
