package service;

import java.util.ArrayList;

import model.CustomerOrder;
import model.Delivery;
import model.DriverManage;
import model.Vehicle;
import model.Attendence;

public interface IDeliveryService {
	
	/*-----------------Delivery------------------*/
	
	public ArrayList<Delivery> getAllDeliveryDetails();
	public void addDeliveryDetails(Delivery delivery);
	public void updateDeliveryDetails(Delivery delivery);
	public void deleteDeliveryDetails(String deliveryId);
	
	
	/*----------------Vehicle------------------*/
	
	public ArrayList<Vehicle> getAllVehicleDetails();
	public void addVehicleDetails(Vehicle vehicle);
	public void updateDeliveryVehicleDetails(Vehicle vehicle);
	public void deleteDeliveryVehicleDetails(String vehicleId);
	
	
	/*----------------Driver Manage----------------*/
	
	public ArrayList<Attendence> getEmpAttendanceDetails();
	public void addDriverDetails(DriverManage driverManage);
	
	public ArrayList<DriverManage> getDriverManageDetails();
	public void deleteDriverManage(String order_id);
	public void updateDriverManage(DriverManage driverManage);
	
	/*----------------Report------------------*/
	
	public ArrayList<String> getDeliveryDetailsList();
	public void generateDDReportByMonthAndYear(String loadPath , int month, int year, String btnInput, String storePath );
	public void generateFullDDReport( String loadPath ,  String btnInput, String storePath);
	
}
