/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.log.Log;
import cl.zpricing.avant.model.log.TipoLog;

/**
 * <b>Controla la persistencia de los registros del log</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 24-04-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface LogDao {

	/**
	 * Agrega una entrada de log.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 24-04-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param log
	 * @since 2.0
	 */
	public void agregarLog(Log log);
	
	/**
	 * Devuelve una lista de logs de un cierto Tipo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 24-04-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param tipoLog
	 * @return
	 * @since 2.0
	 */
	public ArrayList<Log> obtenerLogsTipoLog(TipoLog tipoLog);
	
}
