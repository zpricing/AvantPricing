package cl.zpricing.avant.model;

public class Precio {
	private Double valor;
	private Double bookingFee;
	
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getBookingFee() {
		return bookingFee;
	}
	public void setBookingFee(Double bookingFee) {
		this.bookingFee = bookingFee;
	}
	
	public Double valorTotal() {
		return this.valor + this.bookingFee;
	}
}
