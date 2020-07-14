package cl.zpricing.avant.model;

import java.util.List;

/**
 * Clase modelo que representa el Rol de un usuario en el Sistema
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 12-01-2009 Julio Olivares Alarcon: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class Rol extends DescripcionId {
	private int id;
	private String rol;
	private List<Autoridad> autoridades;


	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getRol() {
		return rol;
	}
	public List<Autoridad> getAutoridades() {
		return autoridades;
	}
	public void setAutoridades(List<Autoridad> autoridades) {
		this.autoridades = autoridades;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
