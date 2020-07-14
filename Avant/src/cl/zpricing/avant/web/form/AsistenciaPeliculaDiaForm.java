package cl.zpricing.avant.web.form;

/**
 * <b>Descripci�n de la Clase</b> Formulario usado para la prediccion de la
 * asistencia por dia
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 05-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class AsistenciaPeliculaDiaForm {

	private String[][][] pesosDias;
	private String[][] pesosPel;
	private int borrarPeliculaX;
	private int borrarPeliculaY;

	/**
	 * @return the pesosDias
	 */
	public String[][][] getPesosDias() {
		return pesosDias;
	}

	/**
	 * @param pesosDias
	 *            the pesosDias to set
	 */
	public void setPesosDias(String[][][] pesosDias) {
		this.pesosDias = pesosDias;
	}

	/**
	 * @return the pesosPel
	 */
	public String[][] getPesosPel() {
		return pesosPel;
	}

	/**
	 * @param pesosPel
	 *            the pesosPel to set
	 */
	public void setPesosPel(String[][] pesosPel) {
		this.pesosPel = pesosPel;
	}

	public void setBorrarPeliculaX(int borrarPeliculaX) {
		this.borrarPeliculaX = borrarPeliculaX;
	}

	public int getBorrarPeliculaX() {
		return borrarPeliculaX;
	}

	public void setBorrarPeliculaY(int borrarPeliculaY) {
		this.borrarPeliculaY = borrarPeliculaY;
	}

	public int getBorrarPeliculaY() {
		return borrarPeliculaY;
	}

}
