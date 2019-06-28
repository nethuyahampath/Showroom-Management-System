package model;

import java.sql.Connection; 
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;

public class repairOrderFormModel {
	
	private String repairDetID;
	private String orderID;
	private String itemCode;
	private String description;
	private String date;
	private String stat;
	
	public repairOrderFormModel() {
		
	}
	
	public repairOrderFormModel(String orderID, String itemCode, String description, String date, String stat) {
		this.orderID = orderID;
		this.itemCode = itemCode;
		this.description = description;
		this.date = date;
		this.stat = stat;
	}

	public void setRepairDetID( String repairDetID ) {
		this.repairDetID = repairDetID;
	}
	
	public String getRepairDetID() {
		return repairDetID;
	}
	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	
	
	

	
	
	
	
	

}
