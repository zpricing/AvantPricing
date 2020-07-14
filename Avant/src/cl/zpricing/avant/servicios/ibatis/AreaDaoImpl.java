package cl.zpricing.avant.servicios.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Distribuidor;
import cl.zpricing.avant.model.Grupo;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.MascaraAreaGrupo;
import cl.zpricing.avant.model.loadmanager.TimeSpan;
import cl.zpricing.avant.servicios.AreaDao;
import cl.zpricing.commons.utils.ErroresUtils;

public class AreaDaoImpl extends SqlMapClientDaoSupport implements AreaDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Override
	public Area obtenerAreaPorIdExterno(String idExterno) {
		log.debug("    obtenerAreaPorIdExterno");
		log.debug("    idExterno : [" + idExterno + "]");
		try {
			List<Area> lista = (List<Area>)getSqlMapClientTemplate().queryForList("obtenerAreaPorIdExterno", idExterno); 
			if (lista != null && lista.size() > 0) {
				return lista.get(0);
			}
			return null;

		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		return null;
		
	}

	public List<MascaraAreaGrupo> obtenerMascarasAreaGrupos(Grupo grupo, Mascara mascara) {
		log.debug("Iniciando obtenerMascarasAreaGrupos");
		log.debug("grupo_id : [" + grupo.getId() + "]");
		log.debug("mascara_id : [" + mascara.getId() + "]");
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("grupo_id", grupo.getId());
		mapa.put("mascara_id", mascara.getId());
		return (List<MascaraAreaGrupo> ) getSqlMapClientTemplate().queryForList("obtenerMascarasAreasGrupos", mapa);
	}
	
	@Override
	public List<MascaraAreaGrupo> obtenerMascarasAreaGruposRevenueManagement(Grupo grupo, Mascara mascara){
		log.debug("Iniciando obtenerMascarasAreaGruposRevenueManagement");
		log.debug("grupo_id : [" + grupo.getId() + "]");
		log.debug("mascara_id : [" + mascara.getId() + "]");
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("grupo_id", grupo.getId());
		mapa.put("mascara_id", mascara.getId());
		return (List<MascaraAreaGrupo> ) getSqlMapClientTemplate().queryForList("obtenerMascarasAreaGruposRevenueManagement", mapa);
	}
	
	@Override
	public List<Integer> obtenerAreaExcepcionDistribuidor(TimeSpan timeSpan, Distribuidor distribuidor) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("timespan_id", timeSpan.getId());
		mapa.put("distribuidor_id", distribuidor.getId());
		return (List<Integer> ) getSqlMapClientTemplate().queryForList("obtenerAreasExcepcionDistribuidor", mapa);
	}

	@Override
	public MascaraAreaGrupo actualizarMascaraAreaGrupo(MascaraAreaGrupo mascaraAreaGrupo) {
		getSqlMapClientTemplate().update("actualizarMascaraAreaGrupo", mascaraAreaGrupo);
		return mascaraAreaGrupo;
	}

	@Override
	public List<Area> obtenerAreasRevenueManagement() {
		return (List<Area> ) getSqlMapClientTemplate().queryForList("obtenerAreasRevenueManagement");
	}
}
