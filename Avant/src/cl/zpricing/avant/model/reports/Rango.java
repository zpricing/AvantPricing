/**
 * 
 */
package cl.zpricing.avant.model.reports;


/**
 * <b>Descripci�n de la Clase</b>
 * Sirve para ingresar el rango de fechas en formato a string en ciertas consultas
 * de informes, tambien lleva el complejo del cual se desea entregar el informe
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 20-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Rango {
	private String inicio;
	private String fin;
	private String idComplejo;
	
	/**
	 * @return the inicio
	 */
	public String getInicio() {
		return inicio;
	}
	/**
	 * @param inicio the inicio to set
	 */
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	/**
	 * @return the fin
	 */
	public String getFin() {
		return fin;
	}
	/**
	 * @param fin the fin to set
	 */
	public void setFin(String fin) {
		this.fin = fin;
	}
	/**
	 * @return the idComplejo
	 */
	public String getIdComplejo() {
		return idComplejo;
	}
	/**
	 * @param idComplejo the idComplejo to set
	 */
	public void setIdComplejo(String idComplejo) {
		this.idComplejo = idComplejo;
	}
	
}
