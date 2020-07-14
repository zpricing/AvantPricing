package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Epoca;
/**
 * 
 * <b>Implementaci�n sobre iBatis del DAO para el manejo de epocas</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 18-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EpocaDaoImpl extends SqlMapClientDaoSupport implements cl.zpricing.avant.servicios.EpocaDao{ 



/* (non-Javadoc)
 * @see cl.zpricing.revman.servicios.EpocaDao#actualizarEpoca(cl.zpricing.revman.model.Epoca)
 */
@Override
public void actualizarEpoca(Epoca epoca) {
	getSqlMapClientTemplate().update("actualizarEpoca", epoca);
	
}

/* (non-Javadoc)
 * @see cl.zpricing.revman.servicios.EpocaDao#agregarEpoca(cl.zpricing.revman.model.Epoca)
 */
@Override
public void agregarEpoca(Epoca epoca) {
	getSqlMapClientTemplate().insert("agregarEpoca", epoca);
	
}

/* (non-Javadoc)
 * @see cl.zpricing.revman.servicios.EpocaDao#eliminarEpoca(cl.zpricing.revman.model.Epoca)
 */
@Override
public void eliminarEpoca(Epoca epoca) {
	getSqlMapClientTemplate().delete("eliminarEpoca", epoca);
	
}

/* (non-Javadoc)
 * @see cl.zpricing.revman.servicios.EpocaDao#obtenerEpoca(int)
 */
@Override
public Epoca obtenerEpoca(int id) {
	return (Epoca) getSqlMapClientTemplate().queryForObject("obtenerEpoca", id);
}

/* (non-Javadoc)
 * @see cl.zpricing.revman.servicios.EpocaDao#obtenerEpocas()
 */
@SuppressWarnings("unchecked")
@Override
public ArrayList<Epoca> obtenerEpocas() {
	return (ArrayList<Epoca>) getSqlMapClientTemplate().queryForList("obtenerEpocas");
}
}

