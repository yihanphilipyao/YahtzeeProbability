package main;

public class InvalidArgumentException extends Exception {

	private static final long serialVersionUID = -5633915762703837868L;

	public InvalidArgumentException(String errorMessage) {
		super(errorMessage);
	}

}
