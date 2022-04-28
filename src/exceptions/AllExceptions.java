package exceptions;

@SuppressWarnings("serial")
public class AllExceptions extends Exception{
	
	public AllExceptions (String msg) {
		super (msg);
	}
	
	public AllExceptions () {
		super ("General exception");
	}

}
