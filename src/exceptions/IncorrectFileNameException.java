package exceptions;

public class IncorrectFileNameException extends RuntimeException {
	public IncorrectFileNameException(String errorMessage) {
        super(errorMessage);
    }
}
