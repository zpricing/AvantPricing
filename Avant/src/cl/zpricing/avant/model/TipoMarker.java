package cl.zpricing.avant.model;

/**
 * Clase modelo que representa un tipo de Marker
 * e.g. Feriado, Navidad, Vacaciones
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 30-12-2008 Julio Olivares Alarcon: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class TipoMarker extends DescripcionId{
	private String color;
	private String codigo;
	private Double ponderador;

	public Double getPonderador() {
		return ponderador;
	}
	public void setPonderador(Double ponderador) {
		this.ponderador = ponderador;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
