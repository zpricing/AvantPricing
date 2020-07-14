package cl.zpricing.avant.model;

/**
 * Clase usada para dar propiedades de acceso a un rol
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 30-12-2008 Oliver Cordero: Implementacion de la clase.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class Autoridad{
	private int id;
	private String autoridad;
	private int rolId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAutoridad() {
		return autoridad;
	}
	public void setAutoridad(String autoridad) {
		this.autoridad = autoridad;
	}
	public int getRolId() {
		return rolId;
	}
	public void setRolId(int rolId) {
		this.rolId = rolId;
	}
	@Override 
	public String toString(){
		return this.autoridad;
	}
}
