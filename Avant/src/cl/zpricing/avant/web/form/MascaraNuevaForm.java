package cl.zpricing.avant.web.form;

import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Complejo;

public class MascaraNuevaForm {
	private String descripcion;
	private Complejo complejo;
	private Area[] areas;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Complejo getComplejo() {
		return complejo;
	}
	public void setComplejo(Complejo complejo) {
		this.complejo = complejo;
	}
	public Area[] getAreas() {
		return areas;
	}
	public void setAreas(Area[] areas) {
		this.areas = areas;
	}
}
