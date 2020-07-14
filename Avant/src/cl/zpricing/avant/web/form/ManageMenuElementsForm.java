package cl.zpricing.avant.web.form;

/**
 * <b>Descripci�n de la Clase</b>
 * Formulario utilizado para la administracion de los elementos de menu
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 13-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ManageMenuElementsForm {

	private String id_menu_element;
	private String rol_id;
	private String title;
	private String link;
	private String parent;
	private String modoForm;
	/**
	 * @return the id_menu_element
	 */
	
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the id_menu_element
	 */
	public String getId_menu_element() {
		return id_menu_element;
	}
	/**
	 * @param id_menu_element the id_menu_element to set
	 */
	public void setId_menu_element(String id_menu_element) {
		this.id_menu_element = id_menu_element;
	}
	/**
	 * @return the rol_id
	 */
	public String getRol_id() {
		return rol_id;
	}
	/**
	 * @param rol_id the rol_id to set
	 */
	public void setRol_id(String rol_id) {
		this.rol_id = rol_id;
	}
	/**
	 * @return the modoForm
	 */
	public String getModoForm() {
		return modoForm;
	}
	/**
	 * @param modoForm the modoForm to set
	 */
	public void setModoForm(String modoForm) {
		this.modoForm = modoForm;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParent() {
		return parent;
	}
	
}
