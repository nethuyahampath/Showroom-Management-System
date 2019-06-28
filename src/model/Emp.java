package model;

public class Emp {

	private String id;
	private String f_name;
	private String l_name;
	private String nic;
	private String add;
	private String mail;
	private String Tel;
	private String designation;
	
	public Emp(String id, String f_name, String l_name, String nic, String add, String mail, String tel, String designation) {
		super();
		this.id = id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.nic = nic;
		this.add = add;
		this.mail = mail;
		this.Tel = tel;
		this.designation = designation;
	}
	public Emp() {
		// TODO Auto-generated constructor stub
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
	public String getNic() {
		return nic;
	}
	public void setNic(String nic) {
		this.nic = nic;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		this.Tel = tel;
	}
	
	public String getDesignation() {
		return this.designation;
	}
	
	public void setDesignation( String designation ) {
		this.designation = designation;
	}
	
	
}

