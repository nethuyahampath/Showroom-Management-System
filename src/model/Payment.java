package model;

public class Payment {

	protected String PaymentID;
	protected String Order_id;
	protected String Type;
	protected String Date;
	protected String Time;
	protected String Amount;
	
	public Payment() {
		
	}
	
	public Payment(String paymentID, String order_id, String type, String date, String time, String amount) {
		super();
		PaymentID = paymentID;
		Order_id = order_id;
		Type = type;
		Date = date;
		Time = time;
		Amount = amount;
	}
	public String getPaymentID() {
		return PaymentID;
	}
	public void setPaymentID(String paymentID) {
		PaymentID = paymentID;
	}
	public String getOrder_id() {
		return Order_id;
	}
	public void setOrder_id(String order_id) {
		Order_id = order_id;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	
	
}
