/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.HashMap;

/**
 * <b>Dao para consultar la información extraída de los reportes de Nielsen.</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 25-01-2010 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */

public interface HistoricoDao {

	public Integer obtenerAsistenciaHistoricaPorComplejo(Integer complejo_id);
	public Integer obtenerRecaudacionHistoricaPorComplejo(Integer complejo_id);
	public Integer obtenerAsistenciaHistoricaPorComplejoDesdeFecha(HashMap<String, Object> map);
	public Integer obtenerRecaudacionHistoricaPorComplejoDesdeFecha(HashMap<String, Object> map); 
	public Integer obtenerAsistenciaHistoricaPorComplejoEntreFechas(HashMap<String, Object> map);
	public Integer obtenerRecaudacionHistoricaPorComplejoEntreFechas(HashMap<String, Object> map);
}
