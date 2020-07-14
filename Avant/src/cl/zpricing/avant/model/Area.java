package cl.zpricing.avant.model;

/**
 * Representa un numero de asientos de la sala que poseen un precio en comun
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 29-01-2009 Mario Lavandero: versión inicial.</li>
 *   <li>1.1 29-01-2009 Nicolas Dujovne W.: se agrega campo diasExpiracion.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Area extends DescripcionId {
	private String descripcionCorta;
	private double capacidad;
	private Clase clase;
	private String id_externo;

	public double getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(double capacidad) {
		this.capacidad = capacidad;
	}
	public Clase getClase() {
		return clase;
	}
	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public String getId_externo() {
		return id_externo;
	}
	public void setId_externo(String id_externo) {
		this.id_externo = id_externo;
	}
	public String getDescripcionCorta() {
		return descripcionCorta;
	}
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}
}
