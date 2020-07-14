package cl.zpricing.avant.web.form;

/**
 * <b>Formulario utilizado en la administracion de publicos</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 10-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class PublicosForm {
	private String id;
	private String descripcion;
	
	/**
	 * @return descripcion
	 */
	public String getDescripcion(){
		return descripcion;
	}
	
	/**
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	
	/**
	 * @return id
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * @param id
	 */
	public void setId(String id){
		this.id = id;
	}
}
