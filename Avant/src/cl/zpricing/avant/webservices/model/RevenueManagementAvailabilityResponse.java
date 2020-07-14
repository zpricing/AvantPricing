package cl.zpricing.avant.webservices.model;

import java.util.List;

public class RevenueManagementAvailabilityResponse {
	private List<CapacidadCupos> detalleCupos;

	public List<CapacidadCupos> getDetalleCupos() {
		return detalleCupos;
	}
	public void setDetalleCupos(List<CapacidadCupos> detalleCupos) {
		this.detalleCupos = detalleCupos;
	}
}
