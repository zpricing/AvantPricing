package cl.zpricing.avantors.webservices.model;

public class LastMinuteFeaturedResponse {
	private String message;
	private LastMinuteSuggestion lastMinuteSuggestion;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LastMinuteSuggestion getLastMinuteSuggestion() {
		return lastMinuteSuggestion;
	}
	public void setLastMinuteSuggestion(LastMinuteSuggestion lastMinuteSuggestion) {
		this.lastMinuteSuggestion = lastMinuteSuggestion;
	}
}
