/**
 * 
 */
package cl.zpricing.avant.web.form;

/**
 * <b>Formulario de administracion del tipo funcion</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class TipoFuncionForm {

	private String id;
	private String descripcion;

	/**
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
}
