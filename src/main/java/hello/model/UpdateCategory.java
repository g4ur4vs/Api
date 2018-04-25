package hello.model;

public class UpdateCategory {

	
	String email;
	String category;
	
	public UpdateCategory() {
		super();
		
	}
	public UpdateCategory(String email, String category) {
		super();
		this.email = email;
		this.category = category;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
