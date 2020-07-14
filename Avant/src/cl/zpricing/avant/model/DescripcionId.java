package cl.zpricing.avant.model;

/**
 * Clase base para otras clases que poseen id y descripcion <br>
 * Registro de versiones:
 * <ul>
 * <li>1.0 09-02-2009 Oliver Cordero: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class DescripcionId {
	protected int id;
	protected String descripcion;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
