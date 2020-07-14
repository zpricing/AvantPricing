/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.log.Log;
import cl.zpricing.avant.model.log.TipoLog;
import cl.zpricing.avant.servicios.LogDao;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 24-04-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class LogDaoImpl extends SqlMapClientDaoSupport implements LogDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.LogDao#agregarLog(cl.zpricing.revman.model
	 * .log.Log)
	 */
	@Override
	public void agregarLog(Log log) {
		getSqlMapClientTemplate().insert("insertarLog", log);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.LogDao#obtenerLogsTipoLog(cl.zpricing.revman
	 * .model.log.TipoLog)
	 */
	@Override
	public ArrayList<Log> obtenerLogsTipoLog(TipoLog tipoLog) {

		return (ArrayList<Log>) getSqlMapClientTemplate().queryForList(
				"obtenerLogsTipoLog", tipoLog);
	}

}
