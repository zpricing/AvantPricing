/**
 * 
 */
package cl.zpricing.avant.web.form;

/**
 * Form que describe un registro
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 08-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ParametroForm {

	private String sistema;
	private String subSistema;
	private String codigo;

	/**
	 * @return the sistema
	 */
	public String getSistema() {
		return sistema;
	}

	/**
	 * @param sistema
	 *            the sistema to set
	 */
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	/**
	 * @return the sub_sistema
	 */
	public String getSubSistema() {
		return subSistema;
	}

	/**
	 * @param sub_sistema
	 *            the sub_sistema to set
	 */
	public void setSubSistema(String subSistema) {
		this.subSistema = subSistema;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
