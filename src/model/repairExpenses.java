package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class repairExpenses {
	
	private String repairExpenseId;
	private String repairId;
	private String date;
	private String amount;
	private String description;
	
	
	public String getRepairExpenseId() {
		return repairExpenseId;
	}
	public void setRepairExpenseId(String repairExpenseId) {
		this.repairExpenseId = repairExpenseId;
	}
	public String getRepairId() {
		return repairId;
	}
	public void setRepairId(String repairId) {
		this.repairId = repairId;
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
