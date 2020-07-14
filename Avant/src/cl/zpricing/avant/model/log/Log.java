/**
 * 
 */
package cl.zpricing.avant.model.log;

import java.util.Date;

import cl.zpricing.avant.model.DescripcionId;

/**
 * <b>Una entrada de log.</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 23-04-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Log extends DescripcionId {

	private Date timestamp;
	private TipoLog tipoLog;
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public TipoLog getTipoLog() {
		return tipoLog;
	}
	public void setTipoLog(TipoLog tipoLog) {
		this.tipoLog = tipoLog;
	}
	
	
}
