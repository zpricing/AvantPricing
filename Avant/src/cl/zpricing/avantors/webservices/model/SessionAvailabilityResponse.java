package cl.zpricing.avantors.webservices.model;

import java.util.List;

public class SessionAvailabilityResponse {
	private List<Capacidad> detalleCupos;
	
	public SessionAvailabilityResponse() {
	}

	public List<Capacidad> getDetalleCupos() {
		return detalleCupos;
	}
	public void setDetalleCupos(List<Capacidad> detalleCupos) {
		this.detalleCupos = detalleCupos;
	}
}
