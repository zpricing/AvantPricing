package cl.zpricing.revman.webservices.model;

import java.util.Date;

public class SecondSellingSuggestion {
	private String cinemaId;
	private String sessionId;
	private Date sessionTime;
	private String movieName;
	private String ticketId;
	private String ticketPrice;
	private String ticketBookingFee;
	private int available;
	private int order;
	
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
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
	public Date getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(Date sessionTime) {
		this.sessionTime = sessionTime;
	}
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
