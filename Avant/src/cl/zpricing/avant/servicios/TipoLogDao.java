/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.log.TipoLog;

/**
 * <b>Servicios para los Tipos de Log que pueden existir.</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 24-04-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface TipoLogDao {

	/**
	 * Agrega un TipoLog a la base de datos.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 24-04-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param tipoLog
	 * @since 2.0
	 */
	public void agregarTipoLog(TipoLog tipoLog);
	
	/**
	 * Obtiene todos los tipos de Log.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 24-04-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return ArrayList<TipoLog>
	 * @since 2.0
	 */
	public ArrayList<TipoLog> obtenerTiposLog();
	
}
