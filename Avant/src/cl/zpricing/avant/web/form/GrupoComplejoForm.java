package cl.zpricing.avant.web.form;

public class GrupoComplejoForm {
	
	private String id;
	private String padre;
	private String descripcion;
	private String empresa;
	private String orden;
	private String action;
	private String[] complejos;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPadre() {
		return padre;
	}
	public void setPadre(String padre) {
		this.padre = padre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String[] getComplejos() {
		return complejos;
	}
	public void setComplejos(String[] complejos) {
		this.complejos = complejos;
	}
	
}
