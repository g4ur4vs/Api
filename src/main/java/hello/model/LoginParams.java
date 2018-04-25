package hello.model;

public class LoginParams {

	String email;
	String pass;
	String imei;
	String token;
	
	
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
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
	
	
	
	public LoginParams(String email, String pass, String imei, String token) {
		super();
		this.email = email;
		this.pass = pass;
		this.imei = imei;
		this.token = token;
	}
	public LoginParams() {
		super();
	}
	
	
	
	
}
