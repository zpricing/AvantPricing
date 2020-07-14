package cl.zpricing.avant.web.form;

/**
 * <b>Descripci�n de la Clase</b>
 * Formulario utilizado en el login
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 10-02-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class LoginForm {
	
    private String usuario;

    private String password;

	/**
	 * @return usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
