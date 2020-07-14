package cl.zpricing.avantors.webservices.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SecondSellingAvailabilityResponse")
public class SecondSellingAvailabilityResponse {
	private String transactionId;
	private String sessionId;
	private String message;
	List<SecondSellingSuggestion> secondSellingSuggestions;
	
	public SecondSellingAvailabilityResponse() {
	}
	
	public SecondSellingAvailabilityResponse(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<SecondSellingSuggestion> getSecondSellingSuggestions() {
		return secondSellingSuggestions;
	}
	public void setSecondSellingSuggestions(
			List<SecondSellingSuggestion> secondSellingSuggestions) {
		this.secondSellingSuggestions = secondSellingSuggestions;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
