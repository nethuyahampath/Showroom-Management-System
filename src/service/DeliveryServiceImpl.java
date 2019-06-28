package  service;

import java.awt.Dimension;  
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.mysql.fabric.xmlrpc.base.Array;
import com.mysql.jdbc.Constants;

import model.Attendence;
import model.CustomerOrder;
import model.Delivery;
import model.DriverManage;
import model.Vehicle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import util.CommonConstants;
import util.DBConnect;
import util.Generator;


	
public class DeliveryServiceImpl<JasperDesign> implements IDeliveryService{
	
	private static Connection con;
	private static PreparedStatement prepStmt;
	private static ResultSet resultSet;
	
	
	/*----------------------Delivery----------------------*/
	
	
	@Override
	public ArrayList<Delivery> getAllDeliveryDetails() {
		
		ArrayList<Delivery> deliveryList = new ArrayList<Delivery>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_DELIVERY_DETAILS);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				
				Delivery delivery = new Delivery();
				
				delivery.setDeliveryId(resultSet.getString(1));
				delivery.setNo(resultSet.getInt(2));
				delivery.setStreet(resultSet.getString(3));
				delivery.setCity(resultSet.getString(4));
				delivery.setDeliveryDate(resultSet.getString(5));
				delivery.setVehicalId(resultSet.getString(6));
				delivery.setOrderID(resultSet.getString(7));
				
				
				deliveryList.add(delivery);
			}
			
		}catch(Exception e) {
			System.out.println("Exception in getting delivery Deatials  :");
			System.out.println(e);
		}
		return deliveryList;
	}
	

	

	
	@Override
	public void addDeliveryDetails(Delivery delivery) {
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt =con.prepareStatement(CommonConstants.QUERY_ADD_DELIVERY_DETAILS);
			
			prepStmt.setString(1, delivery.getDeliveryId()); 
			prepStmt.setInt(2, delivery.getNo());
			prepStmt.setString(3, delivery.getStreet());
			prepStmt.setString(4, delivery.getCity());
			prepStmt.setString(5, delivery.getDeliveryDate());
			prepStmt.setString(6, delivery.getVehicalId());
			prepStmt.setString(7, delivery.getOrderID());
			
			prepStmt.execute();
			
			
			
		}catch (Exception e) {
			System.out.println("Exception in getting Delivery Details :");
			System.out.println(e);
		}
		
	}
	
	@Override
	public void updateDeliveryDetails( Delivery delivery) {
		try {
			
		
			con = DBConnect.getDBConnection();
		
			prepStmt = con.prepareStatement(CommonConstants.QUERY_UPDATE_DELIVERY_DETAILS);

			//prepStmt.setString(1, delivery.getDeliveryId()); 
			prepStmt.setInt(1, delivery.getNo());
			prepStmt.setString(2, delivery.getStreet());
			prepStmt.setString(3, delivery.getCity());
			prepStmt.setString(4, delivery.getDeliveryDate());
			prepStmt.setString(5, delivery.getVehicalId());
			prepStmt.setString(6, delivery.getDeliveryId()); 
			
		
			prepStmt.executeUpdate();
			
		}catch(Exception e) {
			
			System.out.println("Exception in update Delivery Details : ");
			System.out.println(e);
		}
	}



	@Override
	public void deleteDeliveryDetails(String deliveryId) {
	
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = con.prepareStatement("DELETE FROM delivery WHERE delivery_id = ? ");
			
			prepStmt.setString(1, deliveryId);
			
			prepStmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("Exception in delete Delivery Details : ");
			System.out.println(e);
		}
		
	}



	/*------------------- vehicle -------------------------*/

	
	@Override
	public ArrayList<Vehicle> getAllVehicleDetails() {
		
		ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
		
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_DELIVERY_VEHICLE_DETAILS);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				
				Vehicle vehicle = new Vehicle();
				
				vehicle.setVehicleId(resultSet.getString(1));
				vehicle.setVehicleType(resultSet.getString(2));
				vehicle.setVehicleStatus(resultSet.getString(3));
				
				
				vehicleList.add(vehicle);
		
			}
			
		}catch(Exception e) {
			System.out.println("Exception in getting vehicle Deatials  :");
			System.out.println(e);
		}
		return vehicleList;
	}
	
	

	
	@Override
	public void addVehicleDetails(Vehicle vehicle) {
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt =con.prepareStatement(CommonConstants.QUERY_ADD_DELIVERY_VEHICLE_DETAILS);
			
			prepStmt.setString(1, vehicle.getVehicleId());
			prepStmt.setString(2, vehicle.getVehicleType());
			prepStmt.setString(3, vehicle.getVehicleStatus());
			
			prepStmt.execute();
			
			
			
		}catch (Exception e) {
			System.out.println("Exception in getting Vehicle Details :");
			System.out.println(e);
		}
		
	}
		
		
	

	@Override
	public void updateDeliveryVehicleDetails(Vehicle vehicle) {
		
		try {
			
			
			con = DBConnect.getDBConnection();
		
			prepStmt = con.prepareStatement(CommonConstants.QUERY_UPDATE_DELIVERY_VEHICLE_DETAILS);

			//navod
			//prepStmt.setString(1, vehicle.getVehicleId());
			prepStmt.setString(1, vehicle.getVehicleType());
			prepStmt.setString(2, vehicle.getVehicleStatus());
			prepStmt.setString(3, vehicle.getVehicleId());
			
			
			prepStmt.execute();
			
		}catch(Exception e) {
			
			System.out.println("Exception in update Vehicle Details : ");
			System.out.println(e);
		}
		
	}





	@Override
	public void deleteDeliveryVehicleDetails(String vehicleId) {
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = con.prepareStatement("DELETE FROM vehicle WHERE vehicle_id = ? ");
			
			prepStmt.setString(1, vehicleId);
			
			prepStmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("Exception in delete Vehicle Details : ");
			System.out.println(e);
		}
		
		
	}


/*--------------------------------------Report Generation---------------------------------------------------*/


	@Override
	public ArrayList<String> getDeliveryDetailsList() {
		
		ArrayList<String> deliveryList = new ArrayList<String>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_DELIVERY_CODES);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				deliveryList.add(resultSet.getString(1));
			}//end while
			
			
		}catch(Exception e) {
			System.out.println("Exception in getting delivery list :");
			System.out.println(e);
		}
		
		return deliveryList;
	}





	@Override
	public void generateDDReportByMonthAndYear(String loadPath, int month, int year, String btnInput,
			String storePath) {
		
		
		try {
    		
			con = DBConnect.getDBConnection();
			
			//JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			net.sf.jasperreports.engine.design.JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			//get the query
			String query = "SELECT * FROM delivery WHERE EXTRACT(MONTH FROM delivery_date) = "+month+" AND EXTRACT(YEAR FROM delivery_date) = "+year;           
			JRDesignQuery jrQuery = new JRDesignQuery();
			jrQuery.setText(query);
			jasperDesign.setQuery(jrQuery);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , null , con );
			JRViewer viewer = new JRViewer( jasperPrint );
			
			viewer.setOpaque(true);
			viewer.setVisible(true);
			
			if( btnInput == "view") {
				JFrame frame = new JFrame("Jasper report");
		        frame.add(viewer);
		        frame.setSize(new Dimension(800, 900));
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);
			}else
				if( btnInput == "save" ) {
					JasperExportManager.exportReportToPdfFile(jasperPrint, storePath );
				}

        
		}catch(Exception e) {
			System.out.println("Exception generating customer order report by month and year : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
	}





	@Override
	public void generateFullDDReport(String loadPath, String btnInput, String storePath) {
		
		
		try {
    		
			con = DBConnect.getDBConnection();
			
			net.sf.jasperreports.engine.design.JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			//get the query
			String query = "SELECT * FROM delivery";           
			JRDesignQuery jrQuery = new JRDesignQuery();
			jrQuery.setText(query);
			jasperDesign.setQuery(jrQuery);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , null , con );
			JRViewer viewer = new JRViewer( jasperPrint );
			
			viewer.setOpaque(true);
			viewer.setVisible(true);
			
			if( btnInput == "view") {
				JFrame frame = new JFrame("Jasper report");
		        frame.add(viewer);
		        frame.setSize(new Dimension(800, 900));
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);
			}else
				if( btnInput == "save" ) {
					JasperExportManager.exportReportToPdfFile(jasperPrint, storePath );
				}

		}catch(Exception e) {
			System.out.println("Exception in viewing full delivery report : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
		
	}
	/*---------------------------------------------------------------------------------------------------------------------------------*/


	/*------------------------------Driver Manage-----------------------------------------*/


	@Override
	public ArrayList<Attendence> getEmpAttendanceDetails() {
		
		ArrayList<Attendence> attendList = new ArrayList<Attendence>();
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_DRIVER_ATTENDANCE_DETAILS);
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				Attendence attendance = new Attendence();
				
				attendance.setDesignation(resultSet.getString(1));
				attendance.setEmpID(resultSet.getString(2));
				
				attendList.add(attendance);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return attendList;
	}

	@Override
	public void addDriverDetails(DriverManage driverManage) {

		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_ADD_Delivery_Driver_Details);
			
			prepStmt.setString(1, driverManage.getDelivery_id());
			prepStmt.setString(2, driverManage.getEmp_id());
			
			prepStmt.execute();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}





	@Override
	public ArrayList<DriverManage> getDriverManageDetails() {
		
		
		ArrayList<DriverManage> driverList = new ArrayList<DriverManage>();
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_DRIVER_DETAILS);
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				DriverManage driverManage = new DriverManage();
				
				driverManage.setDelivery_id(resultSet.getString(1));
				driverManage.setEmp_id(resultSet.getString(2));
				
				System.out.println(driverManage.getDelivery_id());
				System.out.println(driverManage.getEmp_id()
						);
				driverList.add(driverManage);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return driverList;
	}



	@Override
	public void deleteDriverManage(String delivery_id) {
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = con.prepareStatement(CommonConstants.QUERY_DELETE_DELIVERY_DETAILS);
			
			prepStmt.setString(1, delivery_id);
			
			prepStmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}





	@Override
	public void updateDriverManage(DriverManage driverManage) {
		
try {
			
			
			con = DBConnect.getDBConnection();
		
			prepStmt = con.prepareStatement(CommonConstants.QUERY_UPDATE_DELIVERY_MANAGE_DETAILS);

			//navod
			//prepStmt.setString(1, vehicle.getVehicleId());
			prepStmt.setString(1, driverManage.getDelivery_id());
			prepStmt.setString(2, driverManage.getEmp_id());
			prepStmt.setString(3, driverManage.getDelivery_id());
			
			
			prepStmt.execute();
			
		}catch(Exception e) {
			
			System.out.println("Exception in update Vehicle Details : ");
			System.out.println(e);
		}
		
	}

		
	}
		
	
    
 