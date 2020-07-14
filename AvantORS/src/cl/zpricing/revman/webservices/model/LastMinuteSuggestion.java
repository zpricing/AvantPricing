package cl.zpricing.revman.webservices.model;

import java.util.Date;

public class LastMinuteSuggestion {
	private String cinemaId;
	private String cinemaName;
	private String sessionId;
	private Date sessionTime;
	private String movieName;
	private String movieGraphicUrl;
	private String movieRating;
	private String ticketId;
	private String ticketPrice;
	private String ticketBookingFee;
	private int available;
	
	public String getCinemaName() {
		return cinemaName;
	}
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
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
	public String getMovieGraphicUrl() {
		return movieGraphicUrl;
	}
	public void setMovieGraphicUrl(String movieGraphicUrl) {
		this.movieGraphicUrl = movieGraphicUrl;
	}
	public String getMovieRating() {
		return movieRating;
	}
	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
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
}
