package cl.zpricing.avant.model;

public class Grupo extends DescripcionId {
	private Integer orden;
	public Complejo complejoAlCualPertenece;
	
	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Complejo getComplejoAlCualPertenece() {
		return complejoAlCualPertenece;
	}

	public void setComplejoAlCualPertenece(Complejo complejoAlCualPertenece) {
		this.complejoAlCualPertenece = complejoAlCualPertenece;
	}

}
