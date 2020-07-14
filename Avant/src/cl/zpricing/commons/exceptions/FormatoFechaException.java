package cl.zpricing.commons.exceptions;

public class FormatoFechaException extends Exception {
	private static final long	serialVersionUID	= 1L;
	
	public FormatoFechaException(Exception e) {
		super(e);
	}
	
	public FormatoFechaException(String mensaje) {
		super(mensaje);
	}
}
