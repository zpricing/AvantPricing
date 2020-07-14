package cl.zpricing.avant.web.form.reports;

/**
 * <b>Descripci�n de la Clase</b>
 * Formulario encargado de manejar la vista de reportes
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 22-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class ReportesForm {

	private String fecha_inicio;
	private String fecha_fin;
	
	private String[] complejos;
	
	private String[] precios;
	
	private String tipo_reporte;
	
	/**
	 * @param fecha_inicio
	 */
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	/**
	 * @return fecha_inicio
	 */
	public String getFecha_inicio() {
		return fecha_inicio;
	}

	/**
	 * @param fecha_fin
	 */
	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	/**
	 * @return fecha_fin
	 */
	public String getFecha_fin() {
		return fecha_fin;
	}

	/**
	 * @param complejo
	 */
	public void setComplejo(String[] complejo) {
		this.complejos = complejo;
	}

	/**
	 * @return complejos
	 */
	public String[] getComplejo() {
		return complejos;
	}

	/**
	 * @param tipo_reporte
	 */
	public void setTipo_reporte(String tipo_reporte) {
		this.tipo_reporte = tipo_reporte;
	}

	/**
	 * @return tipo_reporte
	 */
	public String getTipo_reporte() {
		return tipo_reporte;
	}

	/**
	 * @param precios
	 */
	public void setPrecios(String[] precios) {
		this.precios = precios;
	}

	/**
	 * @return precios
	 */
	public String[] getPrecios() {
		return precios;
	}


}
