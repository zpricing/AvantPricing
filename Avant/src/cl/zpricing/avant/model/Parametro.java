package cl.zpricing.avant.model;

/**
 * Clase usada para manejar informacion asignada por defecto
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Oliver Cordero: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class Parametro {
	private String sistema;
	private String subSistema;
	private String codigo;
	

	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public String getSubSistema() {
		return subSistema;
	}
	public void setSubSistema(String subSistema) {
		this.subSistema = subSistema;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
