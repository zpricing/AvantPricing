package cl.zpricing.avant.model;

import java.util.Date;

public class Transaccion extends DescripcionId {
	private Funcion funcion;
	private Ticket ticket;
	private Date fecha;
	private Integer cantidad;
	private Double venta;
	private boolean anulado;
	private String transaccionIdExterno;
	
	public Funcion getFuncion() {
		return funcion;
	}
	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getVenta() {
		return venta;
	}
	public void setVenta(Double venta) {
		this.venta = venta;
	}
	public boolean isAnulado() {
		return anulado;
	}
	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}
	public String getTransaccionIdExterno() {
		return transaccionIdExterno;
	}
	public void setTransaccionIdExterno(String transaccionIdExterno) {
		this.transaccionIdExterno = transaccionIdExterno;
	}
}
