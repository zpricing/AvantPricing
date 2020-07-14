package cl.zpricing.avant.etl.model;

public class ResultEtl {
	private String element;
	private boolean successful;
	
	public ResultEtl(String elementName, boolean result) {
		this.element = elementName;
		this.successful = result;
	}
	
	public String getElement() {
		return element;
	}
	public boolean isSuccessful() {
		return successful;
	}
}
