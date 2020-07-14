package cl.zpricing.avant.servicios.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.servicios.BusinessIntelligenceProcessDao;

public class BusinessIntelligenceProcessDaoImpl extends SqlMapClientDaoSupport implements BusinessIntelligenceProcessDao {
	@Override
	public void actualizarPreferenciasCineClientes(double factorAjuste) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("factor_ajuste", factorAjuste);
		getSqlMapClientTemplate().delete("cleanPreferenciasCineClientes");
		getSqlMapClientTemplate().update("actualizarPreferenciasCineClientes", params);
	}
	
	@Override
	public void updateClientCategoryPreferences(double adjustmentFactor, double miniumWeightToConsider) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("adjustment_factor", adjustmentFactor);
		params.put("minium_weight_to_consider", miniumWeightToConsider);
		getSqlMapClientTemplate().delete("cleanClientCategoryPreferences");
		getSqlMapClientTemplate().insert("insertClientCategoryPreferences", params);
	}

	@Override
	public void updateClientsCluster() {
		getSqlMapClientTemplate().delete("cleanClientesPorZonaGeografica");
		getSqlMapClientTemplate().insert("actualizarClientesPorZonaGeografica");
		getSqlMapClientTemplate().insert("populateClusterClients");
	}
	@Override
	public void updateMoviesCluster() {
		getSqlMapClientTemplate().delete("cleanGrupoPeliculasPorZonaGeografica");		
		getSqlMapClientTemplate().insert("actualizarGrupoPeliculasPorZonaGeografica");
		getSqlMapClientTemplate().update("markGrupoPeliculasZonaGeograficaAsStarring");
		getSqlMapClientTemplate().delete("cleanClustersPelicula");
		getSqlMapClientTemplate().insert("populateClusterMovies");
	}

	@Override
	public void updateCategoriesAttendanceWeight() {
		getSqlMapClientTemplate().update("updateCategoriesAttendanceWeight");
	}

	
	@Override
	public void buildClusters(double minimumCategoryAttendanceWeightToConsider) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("minimum_weight", minimumCategoryAttendanceWeightToConsider);
		getSqlMapClientTemplate().delete("cleanClustersCliente");
		getSqlMapClientTemplate().delete("cleanClustersPelicula");
		getSqlMapClientTemplate().delete("cleanClusters");
		getSqlMapClientTemplate().insert("recreateClusters", params);
	}

	@Override
	public void addSegmentIdToCluster(int clusterId, String segmentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cluster_id", clusterId);
		params.put("segment_id", segmentId);
		getSqlMapClientTemplate().update("addSegmentIdToCluster", params);
		
	}
}
