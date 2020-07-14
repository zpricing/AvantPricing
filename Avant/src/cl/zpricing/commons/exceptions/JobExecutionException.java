package cl.zpricing.commons.exceptions;

public class JobExecutionException extends Exception{
	private static final long	serialVersionUID	= 1L;
	
	public JobExecutionException(Exception e) {
		super(e);
	}
	
	public JobExecutionException(String message) {
		super(message);
	}
}
