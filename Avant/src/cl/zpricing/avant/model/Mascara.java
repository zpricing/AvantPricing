package cl.zpricing.avant.model;

import java.util.ArrayList;

/**
 * Este objeto representa una Mascara de asientos. Es una forma de organizar los
 * asientos dentro de una sala. Tiene Areas y cada area tiene un numero de
 * asientos y el precio a que esa area se vende.
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 29-01-2009 Mario Lavandero: version inicial.</li>
 * <li>1.1 20-02-2009 Mario Lavandero: Agregado el boolean Activo para saber si es una
 * mascara activa.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Mascara extends DescripcionId {
	private Sala sala;
	private Integer idExterno;
	private Double porcentajeDefault;
	private boolean activo;
	private Integer orden;
	private Complejo complejo;
	private String descripcionDetallada;
	
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public Integer getIdExterno() {
		return idExterno;
	}
	public void setIdExterno(Integer idExterno) {
		this.idExterno = idExterno;
	}
	public Double getPorcentajeDefault() {
		return porcentajeDefault;
	}
	public void setPorcentajeDefault(Double porcentajeDefault) {
		this.porcentajeDefault = porcentajeDefault;
	}
	public boolean getActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Complejo getComplejo() {
		return complejo;
	}
	public void setComplejo(Complejo complejo) {
		this.complejo = complejo;
	}
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public String getDescripcionDetallada() {
		return descripcionDetallada;
	}
	public void setDescripcionDetallada(String descripcionDetallada) {
		this.descripcionDetallada = descripcionDetallada;
	}
	
	/**
	 * Retorna los precios asociados a la Mascara.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 29-01-2009 Mario Lavandero : Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return ArrayList<Double> de los precios de la mascara.
	 * @since 1.0
	 */
	public ArrayList<Double> getArrayPrecios() {
		/*
		double[] tempPrecios = new double[this.areas.size()];
		for (int i = 0; i < this.areas.size(); i++) {
			tempPrecios[i] = this.areas.get(i).getClase().getPrecio();
		}
		Util.quicksort(tempPrecios, 0, tempPrecios.length);
		ArrayList<Double> preciosOrdenados = new ArrayList<Double>();
		for (int j = tempPrecios.length + 1; j >= 0; j--) {
			preciosOrdenados.add(tempPrecios[j]);
		}
		return preciosOrdenados;
		*/
		return null;
	}
	
	public String toString() {
		return this.getDescripcion();
	}
}
