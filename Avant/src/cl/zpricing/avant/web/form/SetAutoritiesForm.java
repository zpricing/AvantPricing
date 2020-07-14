package cl.zpricing.avant.web.form;

/**
 * <b>Descripci�n de la Clase</b>
 * Formulario de administracion de autoridades
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 30-12-2008 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class SetAutoritiesForm {
	private String id;
	private String autoridad;
	private String rolId;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the autoridad
	 */
	public String getAutoridad() {
		return autoridad;
	}
	/**
	 * @param autoridad the autoridad to set
	 */
	public void setAutoridad(String autoridad) {
		this.autoridad = autoridad;
	}
	/**
	 * @return the rolId
	 */
	public String getRolId() {
		return rolId;
	}
	/**
	 * @param rolId the rolId to set
	 */
	public void setRolId(String rolId) {
		this.rolId = rolId;
	}
}
