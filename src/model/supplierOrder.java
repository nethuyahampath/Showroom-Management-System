package model;

public class supplierOrder extends Order{

	private String SID;
	private String Oamount;
	
	
	public supplierOrder() {
		super();
	}
	public supplierOrder(String oID,String sID, String odate, String otime,String oamount) {
		super(oID, odate, otime);
		SID = sID;
		Oamount = oamount;
		// TODO Auto-generated constructor stub
	}
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	public String getOamount() {
		return Oamount;
	}
	public void setOamount(String oamount) {
		Oamount = oamount;
	}
	
	
}
