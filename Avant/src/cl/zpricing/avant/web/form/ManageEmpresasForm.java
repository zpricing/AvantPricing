package cl.zpricing.avant.web.form;

/**
 * <b>Descripci�n de la Clase</b> Formulario para la administracion de empresas
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 23-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ManageEmpresasForm {
	private String id;
	private String nombre;
	private String direccion;
	private String email;
	private String rpt_empresa_id;

	/**
	 * @param id
	 *            the id to set
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
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	public void setRpt_empresa_id(String rpt_empresa_id) {
		this.rpt_empresa_id = rpt_empresa_id;
	}

	public String getRpt_empresa_id() {
		return rpt_empresa_id;
	}

}
