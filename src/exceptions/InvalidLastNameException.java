package exceptions;

@SuppressWarnings("serial")
public class InvalidLastNameException extends AllExceptions{
	
	public InvalidLastNameException (String msg) {
		super (msg);
	}
	
	public InvalidLastNameException () {
		super ("Invalid Last Name");
	}

}
