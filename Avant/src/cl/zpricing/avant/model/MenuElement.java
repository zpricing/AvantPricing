package cl.zpricing.avant.model;

/**
 * Clase que representa un elemento a mostrar en el Menu principal
 * de la aplicacion, esto para controlar quien accede a donde dependiendo
 * de su rol en el Sistema.
 * title es el nombre a msotrar por pantalla, debe ir en el formato fmt
 * de acuerdo al archivo messages_xx.properties
 * link es la direccion web a la cual apunta.
 *  
 * Registro de versiones:
 * <ul>
 *   <li>1.0 13-01-2009 Julio Andres Olivares Alarcon: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class MenuElement {
	private int id_menu_element;
	private Rol rol;
	private String title;
	private String link;
	private String parent;

	public int getId_menu_element() {
		return id_menu_element;
	}
	public void setId_menu_element(int id_menu_element) {
		this.id_menu_element = id_menu_element;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public Rol getRol() {
		return rol;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParent() {
		return parent;
	}
}
