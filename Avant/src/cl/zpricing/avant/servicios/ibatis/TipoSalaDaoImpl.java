package cl.zpricing.avant.servicios.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.TipoSala;

/**
 * <b>Descripci�n de la Clase</b>
 * Implementacion de TipoSalaDao
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 29-12-2008 ZhetaPricing 2: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class TipoSalaDaoImpl extends SqlMapClientDaoSupport 
implements cl.zpricing.avant.servicios.TipoSalaDao {

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.TipoSalaDao#tipoSalaTodos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoSala> tipoSalaTodos() {
		return (List<TipoSala>) getSqlMapClientTemplate().queryForList("tipoSalaTodas");
	}

}
