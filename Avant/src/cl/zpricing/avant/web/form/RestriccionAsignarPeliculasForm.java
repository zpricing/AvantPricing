package cl.zpricing.avant.web.form;

import java.util.List;

public class RestriccionAsignarPeliculasForm {
	private Integer restriccion;
	private List<Integer> peliculas;
	private String fecha;
	
	public Integer getRestriccion() {
		return restriccion;
	}
	public void setRestriccion(Integer restriccion) {
		this.restriccion = restriccion;
	}
	public List<Integer> getPeliculas() {
		return peliculas;
	}
	public void setPeliculas(List<Integer> peliculas) {
		this.peliculas = peliculas;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
