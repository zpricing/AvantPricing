package cl.zpricing.avantors.webservices.model;

public class CapacidadCupos {
	private String ticketTypeCode;
	private Integer booking;
	private Integer purchase;
	
	public String getTicketTypeCode() {
		return ticketTypeCode;
	}
	public void setTicketTypeCode(String ticketTypeCode) {
		this.ticketTypeCode = ticketTypeCode;
	}
	public Integer getBooking() {
		return booking;
	}
	public void setBooking(Integer booking) {
		this.booking = booking;
	}
	public Integer getPurchase() {
		return purchase;
	}
	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}
}
