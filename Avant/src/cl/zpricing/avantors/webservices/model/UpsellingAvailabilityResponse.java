package cl.zpricing.avantors.webservices.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UpsellingAvailabilityResponse")
public class UpsellingAvailabilityResponse {
	private String transactionId;
	private String sessionId;
	private String message;
	private List<UpsellingSuggestion> upsellingSuggestions;
	
	public UpsellingAvailabilityResponse() {
	}
	
	public UpsellingAvailabilityResponse(String sessionId) {
		this.sessionId = sessionId;
		this.upsellingSuggestions = new ArrayList<UpsellingSuggestion>();
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
	
	public List<UpsellingSuggestion> getUpsellingSuggestions() {
		return upsellingSuggestions;
	}

	public void setUpsellingSuggestions(
			List<UpsellingSuggestion> upsellingSuggestions) {
		this.upsellingSuggestions = upsellingSuggestions;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
