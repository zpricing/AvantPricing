package cl.zpricing.avant.web.form;

/**
 * Formulario utilizado para la administracion de markers
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 Julio Andres Olivares Alarcon: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ManageMarkersForm {
	public String id;
	public String descripcion;
	public String fecha;
	public String fecha_hasta;
	public String tipo_marker_id;
	public String usuario_id;
	public String pelicula_id;
	public String complejo_id;
	public String clase_id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFecha_hasta() {
		return fecha_hasta;
	}
	public void setFecha_hasta(String fecha_hasta) {
		this.fecha_hasta = fecha_hasta;
	}
	public String getTipo_marker_id() {
		return tipo_marker_id;
	}
	public void setTipo_marker_id(String tipo_marker_id) {
		this.tipo_marker_id = tipo_marker_id;
	}
	public String getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(String usuario_id) {
		this.usuario_id = usuario_id;
	}
	public String getPelicula_id() {
		return pelicula_id;
	}
	public void setPelicula_id(String pelicula_id) {
		this.pelicula_id = pelicula_id;
	}
	public String getComplejo_id() {
		return complejo_id;
	}
	public void setComplejo_id(String complejo_id) {
		this.complejo_id = complejo_id;
	}
	public String getClase_id() {
		return clase_id;
	}
	public void setClase_id(String clase_id) {
		this.clase_id = clase_id;
	}
}
