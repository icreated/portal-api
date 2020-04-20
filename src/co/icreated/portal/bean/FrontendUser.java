package co.icreated.portal.bean;

public class FrontendUser {
	
	int id;
	String username;
	String name;
	String token;
	
	public FrontendUser(int id, String username, String name, String token) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.token = token;
	}
	
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getName() {
		return name;
	}
	public String getToken() {
		return token;
	}


}