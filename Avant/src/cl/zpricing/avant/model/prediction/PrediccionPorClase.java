package cl.zpricing.avant.model.prediction;

import cl.zpricing.avant.model.Clase;

/**
 * <b>Clase usada para manejar los datos de una prediccion por clase.
 * Una prediccion por funcion contiene muchas predicciones por clase</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class PrediccionPorClase {
	private int id;

	private Clase clase;

	private int asistencia;

	private PrediccionPorFuncion prediccionPorFuncion;

	private double varianza;
	

	/**
	 * @return the varianza
	 */
	public double getVarianza() {
		return varianza;
	}

	/**
	 * @param varianza
	 *            the varianza to set
	 */
	public void setVarianza(double varianza) {
		this.varianza = varianza;
	}

	/**
	 * @return the prediccionPorFuncion
	 */
	public PrediccionPorFuncion getPrediccionPorFuncion() {
		return prediccionPorFuncion;
	}

	/**
	 * @param prediccionPorFuncion
	 *            the prediccionPorFuncion to set
	 */
	public void setPrediccionPorFuncion(
			PrediccionPorFuncion prediccionPorFuncion) {
		this.prediccionPorFuncion = prediccionPorFuncion;
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return clase
	 */
	public Clase getClase() {
		return clase;
	}

	/**
	 * @param clase
	 */
	public void setClase(Clase clase) {
		this.clase = clase;
	}

	/**
	 * @return asistencia
	 */
	public int getAsistencia() {
		return asistencia;
	}

	/**
	 * @param asistencia
	 */
	public void setAsistencia(int asistencia) {
		this.asistencia = asistencia;
	}

}
