package model;

import java.util.Date;

public class Order {

	protected String OID;
	protected String Odate;
	protected String Otime;
	
	public Order() {
		
	}
	
	protected Order(String oID, String odate, String otime) {
		super();
		OID = oID;
		Odate = odate;
		Otime = otime;
	}
	public String getOID() {
		return OID;
	}
	public void setOID(String oID) {
		OID = oID;
	}
	public String getOdate() {
		return Odate;
	}
	public void setOdate(String odate) {
		Odate = odate;
	}
	public String getOtime() {
		return Otime;
	}
	public void setOtime(String otime) {
		Otime = otime;
	}
	/*public String getOtotal() {
		return Ototal;
	}
	public void setOtotal(String ototal) {
		Ototal = ototal;
	}*/
	
	
}
