package Exceptions;

public class UniqueValueNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UniqueValueNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}

	public UniqueValueNotFoundException(String msg) {
        this(msg, null);
    }

}
