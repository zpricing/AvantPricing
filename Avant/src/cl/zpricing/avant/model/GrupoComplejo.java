package cl.zpricing.avant.model;

import java.util.ArrayList;

public class GrupoComplejo extends DescripcionId {
	private int orden;
	private GrupoComplejo padre;
	private ArrayList<GrupoComplejo> hijos;
	private ArrayList<Complejo> complejos;
	private Empresa empresa;
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public GrupoComplejo getPadre() {
		return padre;
	}
	public void setPadre(GrupoComplejo padre) {
		this.padre = padre;
	}
	public ArrayList<GrupoComplejo> getHijos() {
		return hijos;
	}
	public void setHijos(ArrayList<GrupoComplejo> hijos) {
		this.hijos = hijos;
	}
	public ArrayList<Complejo> getComplejos() {
		return complejos;
	}
	public void setComplejos(ArrayList<Complejo> complejos) {
		this.complejos = complejos;
	}
}
