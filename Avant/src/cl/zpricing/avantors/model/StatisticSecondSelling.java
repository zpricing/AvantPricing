package cl.zpricing.avantors.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "statistic")
public class StatisticSecondSelling extends Statistic {
	private String cinemaId;
	private String sessionId;
	private String userId;
	private Integer numberOfSuggestions;
	
	private String originalMovieName;
	private String originalPrice;
	private Integer originalNumberOfTickets;
	private String suggestedMovieName;
	private Integer selectedNumberOfTickets;
	
	private String selectedSessionId;
	private String selectedTicketTypeId;
	
	private boolean personalized;
	
	public StatisticSecondSelling() {
		this.type = "second_selling";
	}
	
	public StatisticSecondSelling(String cinemaId, String sessionId, String userId, Integer numberOfSuggestions) {
		this.type = "second_selling";
		this.status = "queried";
		this.date = new Date();
		this.cinemaId = cinemaId;
		this.sessionId = sessionId;
		this.userId = userId;
		this.numberOfSuggestions = numberOfSuggestions;
	}
	
	public Integer getNumberOfSuggestions() {
		return numberOfSuggestions;
	}
	public void setNumberOfSuggestions(Integer numberOfSuggestions) {
		this.numberOfSuggestions = numberOfSuggestions;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOriginalMovieName() {
		return originalMovieName;
	}
	public void setOriginalMovieName(String originalMovieName) {
		this.originalMovieName = originalMovieName;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Integer getOriginalNumberOfTickets() {
		return originalNumberOfTickets;
	}
	public void setOriginalNumberOfTickets(Integer originalNumberOfTickets) {
		this.originalNumberOfTickets = originalNumberOfTickets;
	}
	public String getSuggestedMovieName() {
		return suggestedMovieName;
	}
	public void setSuggestedMovieName(String suggestedMovieName) {
		this.suggestedMovieName = suggestedMovieName;
	}
	public Integer getSelectedNumberOfTickets() {
		return selectedNumberOfTickets;
	}
	public void setSelectedNumberOfTickets(Integer selectedNumberOfTickets) {
		this.selectedNumberOfTickets = selectedNumberOfTickets;
	}
	public String getSelectedSessionId() {
		return selectedSessionId;
	}
	public void setSelectedSessionId(String selectedSessionId) {
		this.selectedSessionId = selectedSessionId;
	}
	public String getSelectedTicketTypeId() {
		return selectedTicketTypeId;
	}
	public void setSelectedTicketTypeId(String selectedTicketTypeId) {
		this.selectedTicketTypeId = selectedTicketTypeId;
	}
	public boolean isPersonalized() {
		return personalized;
	}
	public void setPersonalized(boolean personalized) {
		this.personalized = personalized;
	}
}
