/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.TipoMarker;
import cl.zpricing.avant.servicios.TipoMarkerDao;
import cl.zpricing.commons.utils.ErroresUtils;

/**
 * <b>Descripci�n de la Clase</b> Implementacion de TipoMarkerDao
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 ZhetaPricing 2: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class TipoMarkerDaoImpl extends SqlMapClientDaoSupport implements TipoMarkerDao {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Override
	public boolean agregarTipoMarker(TipoMarker nuevoMarker) {
		try {
			getSqlMapClientTemplate().insert("agregarTipoMarker", nuevoMarker);
		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.TipoMarkerDao#listarTipoMarker()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TipoMarker> listarTipoMarker() {
		return (ArrayList<TipoMarker>) getSqlMapClientTemplate().queryForList(
				"listarTipoMarker");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.TipoMarkerDao#eliminarTipoMarker(int)
	 */
	@Override
	public boolean eliminarTipoMarker(int id) {
		getSqlMapClientTemplate().delete("eliminarTipoMarker", id);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.TipoMarkerDao#obtenerTipoMarker(int)
	 */
	@Override
	public TipoMarker obtenerTipoMarker(int id) {
		return (TipoMarker) getSqlMapClientTemplate().queryForObject(
				"obtenerTipoMarker", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.TipoMarkerDao#modificarTipoMarker(cl.zpricing
	 * .revman.model.TipoMarker)
	 */
	@Override
	public boolean modificarTipoMarker(TipoMarker nuevomarker) {
		try {
			getSqlMapClientTemplate()
					.update("modificarTipoMarker", nuevomarker);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

}
