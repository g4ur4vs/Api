package hello.model;

public class AdminLoginModel {
	
	String email;
	String pass;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public AdminLoginModel(String email, String pass) {
		super();
		this.email = email;
		this.pass = pass;
	}
	public AdminLoginModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
