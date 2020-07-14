package cl.zpricing.avant.model;

import java.util.Date;

public class LastMinuteSession {
	private int funcionId;
	private int complejoId;
	private int grupoPeliculaId;
	private Date fechaContabilidad;
	private int bloque;
	private int orden;
	
	public int getFuncionId() {
		return funcionId;
	}
	public void setFuncionId(int funcionId) {
		this.funcionId = funcionId;
	}
	public int getComplejoId() {
		return complejoId;
	}
	public void setComplejoId(int complejoId) {
		this.complejoId = complejoId;
	}
	public int getGrupoPeliculaId() {
		return grupoPeliculaId;
	}
	public void setGrupoPeliculaId(int grupoPeliculaId) {
		this.grupoPeliculaId = grupoPeliculaId;
	}
	public Date getFechaContabilidad() {
		return fechaContabilidad;
	}
	public void setFechaContabilidad(Date fechaContabilidad) {
		this.fechaContabilidad = fechaContabilidad;
	}
	public int getBloque() {
		return bloque;
	}
	public void setBloque(int bloque) {
		this.bloque = bloque;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
}
