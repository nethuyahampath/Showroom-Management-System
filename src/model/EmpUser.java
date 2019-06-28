package model;

public class EmpUser {

	private String id;
	private String f_name;
	private String l_name;
	private String designation;
	
	public EmpUser(String id, String f_name, String l_name, String designation) {
		super();
		this.id = id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.designation = designation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getL_name() {
		return l_name;
	}

	public void setL_name(String l_name) {
		this.l_name = l_name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
}
