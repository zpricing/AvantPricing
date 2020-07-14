/**
 *
 */
package cl.zpricing.avant.web.form;

/**
 * Clase de formulario para crear nuevos usuarios
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 17-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class NewUserForm {
	private String usuario;
	private String password;
	private String old_password;
	private String nombreCompleto;
	private String email;
	private String rol;

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param nombreCompleto
	 *            the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
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

	/**
	 * @param rol
	 *            the rol to set
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

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getOld_password() {
		return old_password;
	}
}
