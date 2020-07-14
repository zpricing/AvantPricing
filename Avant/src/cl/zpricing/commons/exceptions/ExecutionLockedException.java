package cl.zpricing.commons.exceptions;

public class ExecutionLockedException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExecutionLockedException(String message) {
		super(message);
	}
}
