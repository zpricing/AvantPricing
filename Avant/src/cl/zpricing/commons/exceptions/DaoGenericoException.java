package cl.zpricing.commons.exceptions;

/**
 * <p>Excepción utilizada para capturar errores en el uso del Dao Generico.</p>
 *
 * <p>
 * <b>Registro de versiones:</b>
 * <ul>
 * 	   <li>1.0 (22-07-2009: Nicolás Dujovne W.): versión inicial.</li>
 * </ul>
 * </p>
 * <p>
 *   <b>Todos los derechos reservados por ZhetaPricing.</b>
 * </p>
 */

public class DaoGenericoException extends Exception {

	/**
	 * Mensaje de la excepción.
	 */
	private String mensaje;
	
	/**
	 * Excepción original.
	 */
	private Exception exception;
	
	public DaoGenericoException(Exception e) {
		this.mensaje = e.getMessage();
		this.setStackTrace(e.getStackTrace());
	}
	
	public DaoGenericoException(Exception e, String mensaje) {
		this.mensaje = mensaje;
		this.setStackTrace(e.getStackTrace());
	}

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
}
