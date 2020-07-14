package cl.zpricing.avant.web.form;

/**
 * <b>Formulario utilizado para administrar el calendario de funciones</b>
 * 
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 30-12-2008 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class FuncionesCalForm {
	private String fecha;
	private int idComplejo;

	public int getIdComplejo() {
		return idComplejo;
	}
	public void setIdComplejo(int idComplejo) {
		this.idComplejo = idComplejo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	} 
}
