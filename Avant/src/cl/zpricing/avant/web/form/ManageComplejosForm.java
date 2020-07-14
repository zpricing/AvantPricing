package cl.zpricing.avant.web.form;

/**
 * <b>Descripci�n de la Clase</b> Formulario utilizado para la administracion de
 * complejos
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 24-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ManageComplejosForm {

	private String id;
	private String nombre;
	private String direccion;
	private String empresa;
	private String ip_servidor;
	private String complejo_id_externo;
	private String idNielsen;

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
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}

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
	 * @param ip_servidor
	 *            the ip_servidor to set
	 */
	public void setIp_servidor(String ip_servidor) {
		this.ip_servidor = ip_servidor;
	}

	/**
	 * @return the ip_servidor
	 */
	public String getIp_servidor() {
		return ip_servidor;
	}

	/**
	 * @param complejo_id_externo
	 */
	public void setComplejo_id_externo(String complejo_id_externo) {
		this.complejo_id_externo = complejo_id_externo;
	}

	/**
	 * @return id complejo externo
	 */
	public String getComplejo_id_externo() {
		return complejo_id_externo;
	}

	public void setIdNielsen(String idNielsen) {
		this.idNielsen = idNielsen;
	}

	public String getIdNielsen() {
		return idNielsen;
	}

}
