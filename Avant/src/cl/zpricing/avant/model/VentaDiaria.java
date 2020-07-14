package cl.zpricing.avant.model;

import java.util.Date;

public class VentaDiaria {
	private Date fecha;
	private int ventaRevenueCantidad;
	private int ventaRevenueBoleteriaCantidad;
	private int ventaRevenueWebCantidad;
	private int ventaTotalCantidad;
	private double ventaRevenueIngresos;
	private double ventaTotalIngresos;
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getVentaRevenueCantidad() {
		return ventaRevenueCantidad;
	}
	public void setVentaRevenueCantidad(int ventaRevenueCantidad) {
		this.ventaRevenueCantidad = ventaRevenueCantidad;
	}
	public double getVentaRevenueIngresos() {
		return ventaRevenueIngresos;
	}
	public void setVentaRevenueIngresos(double ventaRevenueIngresos) {
		this.ventaRevenueIngresos = ventaRevenueIngresos;
	}
	public int getVentaTotalCantidad() {
		return ventaTotalCantidad;
	}
	public void setVentaTotalCantidad(int ventaTotalCantidad) {
		this.ventaTotalCantidad = ventaTotalCantidad;
	}
	public double getVentaTotalIngresos() {
		return ventaTotalIngresos;
	}
	public void setVentaTotalIngresos(double ventaTotalIngresos) {
		this.ventaTotalIngresos = ventaTotalIngresos;
	}
	public int getVentaRevenueBoleteriaCantidad() {
		return ventaRevenueBoleteriaCantidad;
	}
	public void setVentaRevenueBoleteriaCantidad(int ventaRevenueBoleteriaCantidad) {
		this.ventaRevenueBoleteriaCantidad = ventaRevenueBoleteriaCantidad;
	}
	public int getVentaRevenueWebCantidad() {
		return ventaRevenueWebCantidad;
	}
	public void setVentaRevenueWebCantidad(int ventaRevenueWebCantidad) {
		this.ventaRevenueWebCantidad = ventaRevenueWebCantidad;
	}
}
