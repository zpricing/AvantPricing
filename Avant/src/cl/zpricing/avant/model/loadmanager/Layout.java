package cl.zpricing.avant.model.loadmanager;

import java.util.List;

import cl.zpricing.avant.model.DescripcionId;

public class Layout extends DescripcionId {
	private List<LayoutArea> areas;
	private boolean esRM;
	private String idExterno;
	private String tipo;
	
	/* Getters y Setters */
	public boolean isEsRM() {
		return esRM;
	}
	public List<LayoutArea> getAreas() {
		return areas;
	}
	public void setAreas(List<LayoutArea> areas) {
		this.areas = areas;
	}
	public void setEsRM(boolean esRM) {
		this.esRM = esRM;
	}
	public String getIdExterno() {
		return idExterno;
	}
	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
