package model;

public class Customer {

	private String customerID;
	private String First_Name;
	private String Last_Name;
	private String NIC;
	private String Email;
	private String Contact_Num;
	private String Address;
	//private String cus_id;
	
	public Customer(String customerID,String first_Name, String last_Name, String nIC, String email, String contact_Num,
			String address) {
		super();
		this.customerID = customerID;
		First_Name = first_Name;
		Last_Name = last_Name;
		NIC = nIC;
		Email = email;
		Contact_Num = contact_Num;
		Address = address;
		//cus_id =id;
	}
	
	public String getCustomerID( ) {
		return this.customerID;
	}

	public void setCustomerID( String customerID  ) {
		this.customerID = customerID;
	}
	
	public String getFirst_Name() {
		return First_Name;
	}
	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}
	public String getLast_Name() {
		return Last_Name;
	}
	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}
	public String getNIC() {
		return NIC;
	}
	public void setNIC(String nIC) {
		NIC = nIC;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getContact_Num() {
		return Contact_Num;
	}
	public void setContact_Num(String contact_Num) {
		Contact_Num = contact_Num;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	
}
