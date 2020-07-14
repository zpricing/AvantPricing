package cl.zpricing.revman.model;

import java.util.Date;

public class StatisticRevenueManagement extends Statistic {
	private String cinemaId;
	private String sessionId;
	private String movieId;
	private String dateQueried;
	
	public StatisticRevenueManagement() {
		this.type = "rm";
	}
	
	public StatisticRevenueManagement(String cinemaId, String movieId, String availabilityForDate) {
		this.type = "rm";
		this.status = "queried";
		this.date = new Date();
		this.cinemaId = cinemaId;
		this.movieId = movieId;
		this.dateQueried = availabilityForDate;
	}
	
	public StatisticRevenueManagement(String cinemaId, String sessionId) {
		this.type = "rm";
		this.status = "queried";
		this.date = new Date();
		this.cinemaId = cinemaId;
		this.sessionId = sessionId;
	}

	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getDateQueried() {
		return dateQueried;
	}
	public void setDateQueried(String dateQueried) {
		this.dateQueried = dateQueried;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
