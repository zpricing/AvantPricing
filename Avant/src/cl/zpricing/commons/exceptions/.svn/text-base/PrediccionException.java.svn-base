/**
 * 
 */
package cl.zpricing.commons.exceptions;

/**
 * <b>Excepción utilizada para capturar errores de uso en las Predicciones</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 (22-10-2009 Daniel Estévez Garay): versión inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class PrediccionException extends Exception{
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * mensaje excepción
	 */
	private String mensaje;
	/**
	 * excepción original
	 */
	private Exception exception;
	
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public PrediccionException(Exception e) {
		this.exception =e;
		this.mensaje=e.getMessage();
		this.setStackTrace(e.getStackTrace());
	}
	
	public PrediccionException(Exception e, String mensaje){
		this.exception=e;
		this.mensaje=mensaje;
		this.setStackTrace(e.getStackTrace());
	}
	
}
