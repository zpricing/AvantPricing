package cl.zpricing.avantors.webservices.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LastMinuteSellingAvailabilityResponse")
public class LastMinuteSellingAvailabilityResponse {
	private String transactionId;
	private String message;
	private List<LastMinuteSuggestion> lastMinuteSuggestions;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public List<LastMinuteSuggestion> getLastMinuteSuggestions() {
		return lastMinuteSuggestions;
	}
	public void setLastMinuteSuggestions(
			List<LastMinuteSuggestion> lastMinuteSuggestions) {
		this.lastMinuteSuggestions = lastMinuteSuggestions;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
