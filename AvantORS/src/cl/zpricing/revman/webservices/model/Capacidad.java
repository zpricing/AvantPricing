package cl.zpricing.revman.webservices.model;

public class Capacidad {
	private String ticketTypeCode;
	private Integer disponible;
	
	public String getTicketId() {
		return ticketTypeCode;
	}
	public void setTicketId(String ticketId) {
		this.ticketTypeCode = ticketId;
	}
	public Integer getDisponible() {
		return disponible;
	}
	public void setDisponible(Integer disponible) {
		this.disponible = disponible;
	}
}