package cl.zpricing.avantors.webservices.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RevenueManagementAvailabilityResponse")
public class RevenueManagementAvailabilityResponse {
	private String message;
	private String transactionId;
	private List<CapacidadCupos> detalleCupos;
	
	public RevenueManagementAvailabilityResponse() {
		this.detalleCupos = new ArrayList<CapacidadCupos>();
	}

	public List<CapacidadCupos> getDetalleCupos() {
		return this.detalleCupos;
	}
	public void setDetalleCupos(List<CapacidadCupos> capacidadCupos) {
		this.detalleCupos = capacidadCupos;
	}
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
