package hello.model;

public class RegisterUser {

	
	String name;
	String email;
	String pass;
	String mobile;
	String location;
	String category = "default";
	String userType = "bond";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public RegisterUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterUser(String name, String email, String pass, String mobile, String location, String category,
			String userType) {
		super();
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.mobile = mobile;
		this.location = location;
		this.category = category;
		this.userType = userType;
	}


}
