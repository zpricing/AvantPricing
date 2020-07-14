package cl.zpricing.revman.webservices.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CancelTransactionResponse")
public class CancelTransactionResponse {
	private String message;
	
	public CancelTransactionResponse() {}
	
	public CancelTransactionResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
