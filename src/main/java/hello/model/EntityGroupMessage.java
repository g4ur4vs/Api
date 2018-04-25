
package hello.model;

import java.io.Serializable;
import java.util.List;

public class EntityGroupMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String title;
	String msg;
	String createdBy;
	String time;
	List<String> tokenList;
	
	
	public List<String> getTokenList() {
		return tokenList;
	}
	public void setTokenList(List<String> tokenList) {
		this.tokenList = tokenList;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public EntityGroupMessage(String title, String msg, String createdBy, String time,List<String> tokenList) {
		super();
		this.title = title;
		this.msg = msg;
		this.createdBy = createdBy;
		this.time = time;
		this.tokenList = tokenList;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public EntityGroupMessage() {
		super();
	}
	
	
	
	

}
