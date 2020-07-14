package cl.zpricing.avant.web.form;

/**
 * <b>Descripci�n de la Clase</b>
 * Formulario usado para la administracion de roles
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 22-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ManageRolesForm {

	private String id;
	private String rol;
	private String descripcion;
	private String tipoIng;
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param autoridad the autoridad to set
	 */
	public void setTipoIng(String tipoIng) {
		this.tipoIng = tipoIng;
	}
	/**
	 * @return the autoridad
	 */
	public String getTipoIng() {
		return tipoIng;
	}
}
