package service;

import java.util.ArrayList;

import model.Model;
import model.repairOrderFormModel;

public interface IRepairService {
	
	/*----------------Repair Form-----------------*/
	
	public ArrayList<repairOrderFormModel> getAllItemRepairDetails();
	public void addItemRepairDetails(repairOrderFormModel repair);
	public void updateItemRepairDetails(repairOrderFormModel repair);
	public void deleteItemRepairDetails(String orderID);

	/*-------------------REPAIR MANAGE----------------------*/
	
	public ArrayList<Model> getAllModelDetails();
	public String getOrderDate(String ordrID);
	//public String loadOdrID(repairOrderFormModel repair);
	
	
	/*-------------------Report Generation-------------------------*/
	
	public ArrayList<String> getRepairDetailIDList();
	public void generateRepairReportByMonthAndYear(String loadPath , int month, int year, String btnInput, String storePath);
	public void generateFullRepairReport(String loadPath ,  String btnInput, String storePath);
	
}
