package cl.zpricing.avant.model;

public class Ticket extends DescripcionId {
	private String codigoExterno;
	private boolean ticketRevenue;
	private boolean ticketWeb;
	
	public String getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
	public boolean isTicketRevenue() {
		return ticketRevenue;
	}
	public void setTicketRevenue(boolean ticketRevenue) {
		this.ticketRevenue = ticketRevenue;
	}
	public boolean isTicketWeb() {
		return ticketWeb;
	}
	public void setTicketWeb(boolean ticketWeb) {
		this.ticketWeb = ticketWeb;
	}
}
