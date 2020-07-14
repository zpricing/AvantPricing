package cl.zpricing.avant.webservices.model;

import java.util.ArrayList;
import java.util.List;

public class RevenueManagementMovieAvailabilityResponse {
	private String cinemaId;
	private String movieId;
	private String date;
	private String message;
	private List<CapacidadCuposPorPeliculaYDia> sessionAvailability;
	
	public RevenueManagementMovieAvailabilityResponse() {
		sessionAvailability = new ArrayList<CapacidadCuposPorPeliculaYDia>();
	}
	
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void addAvailabilityPerSession(CapacidadCuposPorPeliculaYDia cupos) {
		this.sessionAvailability.add(cupos);
	}
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public List<CapacidadCuposPorPeliculaYDia> getSessionAvailability() {
		return sessionAvailability;
	}
	public void setSessionAvailability(List<CapacidadCuposPorPeliculaYDia> sessionAvailability) {
		this.sessionAvailability = sessionAvailability;
	}
}
