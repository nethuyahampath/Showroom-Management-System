package model;

public class SupplierPayment extends Payment{

	private String supquantity;
	
	
	public SupplierPayment() {
		super();
	}
	public SupplierPayment(String paymentID, String order_id, String type, String quantity, String date, String time, String amount) {
		super(paymentID, order_id, type, date, time, amount);
		supquantity = quantity;
		// TODO Auto-generated constructor stub
	}
	public String getSupquantity() {
		return supquantity;
	}
	public void setSupquantity(String supquantity) {
		this.supquantity = supquantity;
	}
	
}
