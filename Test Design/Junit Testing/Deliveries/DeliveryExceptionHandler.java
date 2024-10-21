package Newsagent;

public class DeliveryExceptionHandler extends Exception {
	
	String message;
	
	public DeliveryExceptionHandler(String errMessage) {
		message = errMessage;
	}
	
	public String getMessage() {
		return message;
	}

}
