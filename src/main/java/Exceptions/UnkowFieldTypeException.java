package Exceptions;

public class UnkowFieldTypeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnkowFieldTypeException(String msg, Throwable e) {
		super(msg, e);
	}

	public UnkowFieldTypeException(String msg) {
        this(msg, null);
    }

}
