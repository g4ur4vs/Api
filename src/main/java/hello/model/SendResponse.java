package hello.model;

public class SendResponse {
	String message;
	boolean error;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public SendResponse(String message, boolean error) {
		super();
		this.message = message;
		this.error = error;
	}
	
	

}
