package cl.zpricing.avant.model;

import java.util.Date;

/**
 * <p>Clase que determina la capacidad total, disponible y ocupada de una determinada area para una función.</p>
 *
 * <p>
 * <b>Registro de versiones:</b>
 * <ul>
 * 	   <li>1.0 (08-05-2010: Nicolas Dujovne W.): version inicial.</li>
 * </ul>
 * </p>
 * <p>
 *   <b>Todos los derechos reservados por ZhetaPricing.</b>
 * </p>
 */
public class FuncionAreaProxy {
	private int funcionAreaId;
	private String cinemaId;
	private String movieId;
	private String sessionId;
	private String ticketTypeId;
	private Double ticketPrice;
	private Double ticketBookingFee;
	private Date sessionDate;
	private Date sessionTime;
	private String availabilityType;
	private int totalCapacity;
	private int availableCapacity;
	private int occupiedCapacity;
	private int expirationDaysBeforeSession;
	private int levelRM;
	
	public int getFuncionAreaId() {
		return funcionAreaId;
	}
	public void setFuncionAreaId(int funcionAreaId) {
		this.funcionAreaId = funcionAreaId;
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
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public Date getSessionDate() {
		return sessionDate;
	}
	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}
	public String getAvailabilityType() {
		return availabilityType;
	}
	public void setAvailabilityType(String availabilityType) {
		this.availabilityType = availabilityType;
	}
	public int getTotalCapacity() {
		return totalCapacity;
	}
	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	public int getAvailableCapacity() {
		return availableCapacity;
	}
	public void setAvailableCapacity(int availableCapacity) {
		this.availableCapacity = availableCapacity;
	}
	public int getOccupiedCapacity() {
		return occupiedCapacity;
	}
	public void setOccupiedCapacity(int occupiedCapacity) {
		this.occupiedCapacity = occupiedCapacity;
	}
	public int getExpirationDaysBeforeSession() {
		return expirationDaysBeforeSession;
	}
	public void setExpirationDaysBeforeSession(int expirationDaysBeforeSession) {
		this.expirationDaysBeforeSession = expirationDaysBeforeSession;
	}
	public Double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public Date getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(Date sessionTime) {
		this.sessionTime = sessionTime;
	}
	public Double getTicketBookingFee() {
		return ticketBookingFee;
	}
	public void setTicketBookingFee(Double ticketBookingFee) {
		this.ticketBookingFee = ticketBookingFee;
	}
	public int getLevelRM() {
		return levelRM;
	}
	public void setLevelRM(int levelRM) {
		this.levelRM = levelRM;
	}
}
