package hello.model;

public class GetResources {
	private String category,res_type;
	
	
	public GetResources() {
		super();
		
	}



	public GetResources(String category, String res_type) {
		super();
		this.category = category;
		this.res_type = res_type;
	}

	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRes_type() {
		return res_type;
	}

	public void setRes_type(String res_type) {
		this.res_type = res_type;
	} 

}
