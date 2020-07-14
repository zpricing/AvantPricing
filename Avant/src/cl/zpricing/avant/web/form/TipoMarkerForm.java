/**
 * 
 */
package cl.zpricing.avant.web.form;

/**
 * <b>Formulario de administracion del tipo marker</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * <li>1.5 12-02-2008 Daniel Est�vez Garay: agregados campos codigo y color.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class TipoMarkerForm {

	private String id;
	private String descripcion;
	private String color;
	private String codigo;

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
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

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
}
