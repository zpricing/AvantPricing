package cl.zpricing.avant.model;

public class MascaraAreaGrupo extends DescripcionId  {
	private Area area;
	private Grupo grupo;
	private Integer capacidad;
	private Integer diasExpiracion;
	private Double porcentajeMinimo;
	
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public Integer getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	public Integer getDiasExpiracion() {
		return diasExpiracion;
	}
	public void setDiasExpiracion(Integer diasExpiracion) {
		this.diasExpiracion = diasExpiracion;
	}
	public Double getPorcentajeMinimo() {
		return porcentajeMinimo;
	}
	public void setPorcentajeMinimo(Double porcentajeMinimo) {
		this.porcentajeMinimo = porcentajeMinimo;
	}
}
