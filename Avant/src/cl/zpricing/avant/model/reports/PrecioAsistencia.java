/**
 * 
 */
package cl.zpricing.avant.model.reports;

/**
 * <b>Descripci�n de la Clase</b>
 * Asocia un par precio asistencia.
 * Usada principalmente en informes
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 21-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PrecioAsistencia {
	private long precio;
	private long asistencia;
	/**
	 * @return the precio
	 */
	public long getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(long precio) {
		this.precio = precio;
	}
	/**
	 * @return the asistencia
	 */
	public long getAsistencia() {
		return asistencia;
	}
	/**
	 * @param asistencia the asistencia to set
	 */
	public void setAsistencia(long asistencia) {
		this.asistencia = asistencia;
	}
	
}
