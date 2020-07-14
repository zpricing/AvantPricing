package cl.zpricing.revman.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Session implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String cinemaId;
	private String sessionId;
	private String movieId;
	private Date date;
	private Date time;
	private String fullPrice;
	private String fullPriceBookingFee;
	private String movieName;
	private String movieGraphicUrl;
	private String movieRating;
	private List<Availability> availability;
	private List<Upselling> upselling;
	private List<SecondSelling> secondSelling;
	private boolean lastMinuteSelling;
	private boolean eliminada;
	
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
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getFullPrice() {
		return fullPrice;
	}
	public void setFullPrice(String fullPrice) {
		this.fullPrice = fullPrice;
	}
	public String getFullPriceBookingFee() {
		return fullPriceBookingFee;
	}
	public void setFullPriceBookingFee(String fullPriceBookingFee) {
		this.fullPriceBookingFee = fullPriceBookingFee;
	}
	public List<Availability> getAvailability() {
		return availability;
	}
	public void setAvailability(List<Availability> availability) {
		this.availability = availability;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Upselling> getUpselling() {
		return upselling;
	}
	public void setUpselling(List<Upselling> upselling) {
		this.upselling = upselling;
	}
	public List<SecondSelling> getSecondSelling() {
		return secondSelling;
	}
	public void setSecondSelling(List<SecondSelling> secondSelling) {
		this.secondSelling = secondSelling;
	}
	public boolean isLastMinuteSelling() {
		return lastMinuteSelling;
	}
	public void setLastMinuteSelling(boolean lastMinuteSelling) {
		this.lastMinuteSelling = lastMinuteSelling;
	}
	public boolean isEliminada() {
		return eliminada;
	}
	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
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
}
