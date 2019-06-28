package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class showroomExpenses {

	private String showroomExpenseId;
	private String type;
	private String utility;
	private String date;
	private String amount;
	private String description;
	
	public String getShowroomExpenseId() {
		return showroomExpenseId;
	}
	public void setShowroomExpenseId(String showroomExpenseId) {
		this.showroomExpenseId = showroomExpenseId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUtility() {
		return utility;
	}
	public void setUtility(String utility) {
		this.utility = utility;
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
