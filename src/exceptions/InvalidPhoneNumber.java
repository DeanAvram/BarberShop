package exceptions;

@SuppressWarnings("serial")
public class InvalidPhoneNumber extends AllExceptions{
	
	public InvalidPhoneNumber (String msg) {
		super (msg);
	}
	
	public InvalidPhoneNumber () {
		super ("Invalid Phone Number");
	}

}
