package co.icreated.portal.bean;

public class IdNameBean {
	
	int id = 0;
	String name;
	
	
	
	
	public IdNameBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public IdNameBean() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
