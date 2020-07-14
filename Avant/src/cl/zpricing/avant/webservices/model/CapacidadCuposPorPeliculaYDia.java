package cl.zpricing.avant.webservices.model;

import java.util.ArrayList;
import java.util.List;

public class CapacidadCuposPorPeliculaYDia {
	private String sessionId;
	private List<CapacidadCupos> ticketAvailability;
	
	public CapacidadCuposPorPeliculaYDia() {
		ticketAvailability = new ArrayList<CapacidadCupos>();
	}

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public void addCapacidadCupo(CapacidadCupos capacidadCupos) {
		ticketAvailability.add(capacidadCupos);
	}
	public List<CapacidadCupos> getTicketAvailability() {
		return ticketAvailability;
	}
	public void setTicketAvailability(List<CapacidadCupos> ticketAvailability) {
		this.ticketAvailability = ticketAvailability;
	}
}
