package hello.model;

public class User {

	String email;
	//boolean isRegistered;

	public User(String email/*, boolean isRegistered*/) {
		super();
		this.email = email;
		//this.isRegistered = isRegistered;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User() {
		super();
	}

	/*public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}*/

	

}
