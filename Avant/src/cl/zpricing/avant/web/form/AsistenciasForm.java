package cl.zpricing.avant.web.form;

/**
 * <b>Formulario utilizado para la administracion de asistencias</b>
 * 
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class AsistenciasForm {
	private int asistencia;
	private int clase;
	private int funcion;
	private int mascara;
	
	public int getAsistencia() {
		return asistencia;
	}
	public void setAsistencia(int asistencia) {
		this.asistencia = asistencia;
	}
	public int getClase() {
		return clase;
	}
	public void setClase(int clase) {
		this.clase = clase;
	}
	public int getFuncion() {
		return funcion;
	}
	public void setFuncion(int funcion) {
		this.funcion = funcion;
	}
	public int getMascara() {
		return mascara;
	}
	public void setMascara(int mascara) {
		this.mascara = mascara;
	}
}
