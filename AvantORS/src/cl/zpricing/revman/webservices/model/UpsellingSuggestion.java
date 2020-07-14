package cl.zpricing.revman.webservices.model;

import java.util.Date;

public class UpsellingSuggestion {

	private String sessionId;
	private Date sessionTime;
	private String movieName;
	private int order;
	private String ticketId;
	private String cinemaId;
	private String ticketPrice;
	private String ticketBookingFee;
	private String ticketPriceFull;
	private String ticketBookingFeeFull;
	private Integer available;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Date getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(Date sessionTime) {
		this.sessionTime = sessionTime;
	}
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
	public String getTicketPriceFull() {
		return ticketPriceFull;
	}
	public void setTicketPriceFull(String ticketPriceFull) {
		this.ticketPriceFull = ticketPriceFull;
	}
	public String getTicketBookingFeeFull() {
		return ticketBookingFeeFull;
	}
	public void setTicketBookingFeeFull(String ticketBookingFeeFull) {
		this.ticketBookingFeeFull = ticketBookingFeeFull;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
}
