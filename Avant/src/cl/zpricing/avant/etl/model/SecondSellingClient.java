package cl.zpricing.avant.etl.model;

public class SecondSellingClient extends SecondSelling {
	private static final long serialVersionUID = 1L;
	private String clientId;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
