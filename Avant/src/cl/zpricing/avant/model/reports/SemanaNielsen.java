package cl.zpricing.avant.model.reports;

import java.util.Date;

public class SemanaNielsen {
	private Date fechaInicio;
	private int cantidadComplejos;
	private int weekOfYear;
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public int getCantidadComplejos() {
		return cantidadComplejos;
	}
	public void setCantidadComplejos(int cantidadComplejos) {
		this.cantidadComplejos = cantidadComplejos;
	}
	public int getWeekOfYear() {
		return weekOfYear;
	}
	public void setWeekOfYear(int weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

}
