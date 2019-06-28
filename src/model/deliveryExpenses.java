package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class deliveryExpenses {
	
	private String deliveryExpenseID;
	private String deliveryId;
	private String date;
	private String amount;
	private String description;
	
	
	public String getDeliveryExpenseID() {
		return deliveryExpenseID;
	}
	public void setDeliveryExpenseID(String deliveryExpenseID) {
		this.deliveryExpenseID = deliveryExpenseID;
	}
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date txtDate = new Date();
		String newDate = dateFormat.format(txtDate);
		
		return newDate;
	}
	
	

}
