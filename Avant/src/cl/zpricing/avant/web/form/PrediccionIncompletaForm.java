package cl.zpricing.avant.web.form;

/**
 * <b>Descripci�n de la Clase</b>
 * Formulario utilizado en la vista de predicciones incompletas
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 26-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class PrediccionIncompletaForm {
	private String[] prediccionFuncion;
	private String[] prediccionClase;
	private String[] prediccionNoCargadas;
	
	/**
	 * @return the prediccionFuncion
	 */
	public String[] getPrediccionFuncion() {
		return prediccionFuncion;
	}
	/**
	 * @param prediccionFuncion the prediccionFuncion to set
	 */
	public void setPrediccionFuncion(String[] prediccionFuncion) {
		this.prediccionFuncion = prediccionFuncion;
	}
	/**
	 * @return the prediccionClase
	 */
	public String[] getPrediccionClase() {
		return prediccionClase;
	}
	/**
	 * @param prediccionClase the prediccionClase to set
	 */
	public void setPrediccionClase(String[] prediccionClase) {
		this.prediccionClase = prediccionClase;
	}
	public void setPrediccionNoCargadas(String[] prediccionNoCargadas) {
		this.prediccionNoCargadas = prediccionNoCargadas;
	}
	public String[] getPrediccionNoCargadas() {
		return prediccionNoCargadas;
	}
	
}
