package cl.zpricing.avant.etl.model;

import java.io.Serializable;

public class Availability implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String ticketTypeId;
	private String ticketPrice;
	private String ticketBookingFee;
	private int available;
	private int occupied;
	private int total;
	private int daysBeforeSessionExpiration;
	private int level;
	private String type;
	
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public String getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public String getTicketBookingFee() {
		return ticketBookingFee;
	}
	public void setTicketBookingFee(String ticketBookingFee) {
		this.ticketBookingFee = ticketBookingFee;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getOccupied() {
		return occupied;
	}
	public void setOccupied(int occupied) {
		this.occupied = occupied;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getDaysBeforeSessionExpiration() {
		return daysBeforeSessionExpiration;
	}
	public void setDaysBeforeSessionExpiration(int daysBeforeSessionExpiration) {
		this.daysBeforeSessionExpiration = daysBeforeSessionExpiration;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
