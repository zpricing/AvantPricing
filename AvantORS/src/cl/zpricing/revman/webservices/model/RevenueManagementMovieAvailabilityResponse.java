package cl.zpricing.revman.webservices.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RevenueManagementMovieAvailabilityResponse")
public class RevenueManagementMovieAvailabilityResponse {
	private String transactionId;
	private String cinemaId;
	private String movieId;
	private String date;
	private String message;
	private List<CapacidadCuposPorPeliculaYDia> sessionAvailability;
	
	public RevenueManagementMovieAvailabilityResponse() {
		sessionAvailability = new ArrayList<CapacidadCuposPorPeliculaYDia>();
	}
	
	public void parseSessionAvailabilityFromMap(Map<String , CapacidadCuposPorPeliculaYDia> availabilityPerSession) {
		Iterator<String> iter = availabilityPerSession.keySet().iterator();
		while(iter.hasNext()) {
			sessionAvailability.add(availabilityPerSession.get(iter.next()));
		}
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
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
