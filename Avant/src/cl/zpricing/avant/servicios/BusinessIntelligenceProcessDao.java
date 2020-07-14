package cl.zpricing.avant.servicios;

import java.util.Date;

public interface BusinessIntelligenceProcessDao {
	public void actualizarPreferenciasCineClientes(double factorAjuste);
	public void updateClientCategoryPreferences(double adjustmentFactor, double miniumWeightToConsider);
	public void updateCategoriesAttendanceWeight();
	public void buildClusters(double minimumCategoryAttendanceWeightToConsider);
	public void updateClientsCluster();
	public void updateMoviesCluster();
	public void addSegmentIdToCluster(int clusterId, String segmentId);
}
