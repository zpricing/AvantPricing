package cl.zpricing.avantors.webservices.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RevenueManagementUpdateAvailabilityResponse")
public class RevenueManagementUpdateAvailabilityResponse {
	private String message;
	private String transactionId;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
