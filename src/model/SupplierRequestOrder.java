package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SupplierRequestOrder {

	protected String supplierReuestOrderId;
	protected String supplierRequestOrderCategory;
	protected String supplierRequestOrderModel;
	protected String supplierRequestOrderQuantity;
	protected String supplierRequestOrderStatus;
	protected String supplierRequestOrderDate;
	
	
	
	public String getSupplierRequestOrderDate() {
		return supplierRequestOrderDate;
	}
	public void setSupplierRequestOrderDate(String supplierRequestOrderDate) {
		this.supplierRequestOrderDate = supplierRequestOrderDate;
	}
	public String getSupplierReuestOrderId() {
		return supplierReuestOrderId;
	}
	public void setSupplierReuestOrderId(String supplierReuestOrderId) {
		this.supplierReuestOrderId = supplierReuestOrderId;
	}
	public String getSupplierRequestOrderCategory() {
		return supplierRequestOrderCategory;
	}
	public void setSupplierRequestOrderCategory(String supplierRequestOrderCategory) {
		this.supplierRequestOrderCategory = supplierRequestOrderCategory;
	}
	public String getSupplierRequestOrderModel() {
		return supplierRequestOrderModel;
	}
	public void setSupplierRequestOrderModel(String supplierRequestOrderModel) {
		this.supplierRequestOrderModel = supplierRequestOrderModel;
	}
	public String getSupplierRequestOrderQuantity() {
		return supplierRequestOrderQuantity;
	}
	public void setSupplierRequestOrderQuantity(String supplierRequestOrderQuantity) {
		this.supplierRequestOrderQuantity = supplierRequestOrderQuantity;
	}
	public String getSupplierRequestOrderStatus() {
		return supplierRequestOrderStatus;
	}
	public void setSupplierRequestOrderStatus(String supplierRequestOrderStatus) {
		this.supplierRequestOrderStatus = supplierRequestOrderStatus;
	}
	
	
	
	public static LocalDate getCurrentDate() {
		
		Date currntDate=new Date();
		

		LocalDate date = currntDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		
		
		return date;
	}
	
	
}
