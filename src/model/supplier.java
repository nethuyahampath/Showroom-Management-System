package model;

public class supplier {

	private String SID;
	private String SName;
	private String STel;
	private String SEmail;
	private String SAdd;
	private String SCountry;
	private String Name_category;
	
	
	public supplier() {
		super();
	}
	public supplier(String sID, String sName, String sTel, String sEmail, String sAdd, String sCountry, String name_category) {
		super();
		SID = sID;
		SName = sName;
		STel = sTel;
		SEmail = sEmail;
		SAdd = sAdd;
		SCountry = sCountry;
		Name_category = name_category;
	}
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	public String getSName() {
		return SName;
	}
	public void setSName(String sName) {
		SName = sName;
	}
	public String getSTel() {
		return STel;
	}
	public void setSTel(String sTel) {
		STel = sTel;
	}
	public String getSEmail() {
		return SEmail;
	}
	public void setSEmail(String sEmail) {
		SEmail = sEmail;
	}
	public String getSAdd() {
		return SAdd;
	}
	public void setSAdd(String sAdd) {
		SAdd = sAdd;
	}
	public String getSCountry() {
		return SCountry;
	}
	public void setSCountry(String sCountry) {
		SCountry = sCountry;
	}
	public String getName_category() {
		return Name_category;
	}
	public void setName_category(String name_category) {
		Name_category = name_category;
	}
	
			
}
