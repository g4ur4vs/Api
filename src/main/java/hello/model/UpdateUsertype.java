package hello.model;

public class UpdateUsertype {
	
	String Email,usertype,category;
	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public UpdateUsertype(String email, String usertype, String category) {
		super();
		Email = email;
		this.usertype = usertype;
		this.category = category;
	}

	public UpdateUsertype() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
