package cl.zpricing.commons.exceptions;

public class ParametroNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ParametroNotFoundException(String mensaje) {
		super(mensaje);
	}
}
