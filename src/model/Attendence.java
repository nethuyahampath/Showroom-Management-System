package model;

public class Attendence {

	private String attDate;
	private String attTime;
	private String empID;
	private String levTime;
	private String designation;
	
	public Attendence() {
		super();
	}



	public Attendence(String attDate, String attTime, String empID, String levTime) {
		super();
		this.attDate = attDate;
		this.attTime = attTime;
		this.empID = empID;
		this.levTime = levTime;
	}

	public void setDesignation( String designation ) {
		this.designation = designation;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public String getAttDate() {
		return attDate;
	}



	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}



	public String getAttTime() {
		return attTime;
	}



	public void setAttTime(String attTime) {
		this.attTime = attTime;
	}



	public String getEmpID() {
		return empID;
	}



	public void setEmpID(String empID) {
		this.empID = empID;
	}



	public String getLevTime() {
		return levTime;
	}



	public void setLevTime(String levTime) {
		this.levTime = levTime;
	}

	
	
}
