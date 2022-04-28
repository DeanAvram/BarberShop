package exceptions;

@SuppressWarnings("serial")
public class InvalidFirstNameException extends AllExceptions{
	
	public InvalidFirstNameException (String msg) {
		super (msg);
	}
	
	public InvalidFirstNameException () {
		super ("Invalid First Name");
	}

}
