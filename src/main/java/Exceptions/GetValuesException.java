package Exceptions;

public class GetValuesException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public GetValuesException(String msg, Throwable e) {
		super(msg, e);
	}

	public GetValuesException(String msg) {
        this(msg, null);
    }

}
