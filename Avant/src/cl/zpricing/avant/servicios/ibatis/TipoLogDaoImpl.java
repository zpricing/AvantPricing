/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.log.TipoLog;
import cl.zpricing.avant.servicios.TipoLogDao;

/**
 * <b>Implementacion de TipoLogDao</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 24-04-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class TipoLogDaoImpl extends SqlMapClientDaoSupport implements TipoLogDao {

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.TipoLogDao#agregarTipoLog(cl.zpricing.revman.model.log.TipoLog)
	 */
	@Override
	public void agregarTipoLog(TipoLog tipoLog) {
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.TipoLogDao#obtenerTiposLog()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TipoLog> obtenerTiposLog() {
		return (ArrayList<TipoLog>) getSqlMapClientTemplate().queryForList("obtenerTodosTipoLog");
	}

}
