package cl.zpricing.avant.model;

/**
 * Clase modelo que representa una Empresa o Cadena
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 23-12-2008 Julio Olivares Alarcon: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Empresa {
	private int id;
	private String nombre;
	private String direccion;
	private String email;
	private RptEmpresa rptEmpresa;
	
	public RptEmpresa getRptEmpresa() {
		return rptEmpresa;
	}
	public void setRptEmpresa(RptEmpresa rptEmpresa) {
		this.rptEmpresa = rptEmpresa;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
}
