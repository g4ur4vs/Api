package hello.model;

public class Resources {
	
	String name,url,description,category,res_type;

	public Resources(String name, String url, String description, String category, String res_type) {
		super();
		this.name = name;
		this.url = url;
		this.description = description;
		this.category = category;
		this.res_type=res_type;
	}

	public Resources() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getRes_type() {
		return res_type;
	}

	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
