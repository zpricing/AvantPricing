package cl.zpricing.avant.model.loadmanager;

public class PriceCard {
	private String descripcion;
	private String codigoExterno;
	private String mascara;
	private String timeSpan;
	private int diasAnticipacion;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
	public String getMascara() {
		return mascara;
	}
	public void setMascara(String mascara) {
		this.mascara = mascara;
	}
	public String getTimeSpan() {
		return timeSpan;
	}
	public void setTimeSpan(String timeSpan) {
		this.timeSpan = timeSpan;
	}
	public int getDiasAnticipacion() {
		return diasAnticipacion;
	}
	public void setDiasAnticipacion(int diasAnticipacion) {
		this.diasAnticipacion = diasAnticipacion;
	}
}
