package hello.model;

public class CategoryDetails {
	
	String category,usertype;

	public CategoryDetails(String category, String usertype) {
		super();
		this.category = category;
		this.usertype = usertype;
	}

	public CategoryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

}
