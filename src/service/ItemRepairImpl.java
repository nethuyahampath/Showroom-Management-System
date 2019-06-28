package service;

import java.awt.Dimension;  
import java.sql.Connection;   
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import model.Delivery;
import model.Model;
import model.repairOrderFormModel;
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

public class ItemRepairImpl implements IRepairService{
	private static Connection con;
	private static PreparedStatement prepStmt;
	private static ResultSet resultSet;
	
	/*---------------------------REPAIR ORDER FORM----------------------------------------*/
	@Override
	public ArrayList<repairOrderFormModel> getAllItemRepairDetails() {
		
		ArrayList<repairOrderFormModel> repairList = new ArrayList<repairOrderFormModel>();
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_REPAIR_DETAILS);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				repairList.add(new repairOrderFormModel(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));	
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return repairList;
	
	}
	@Override
	public void addItemRepairDetails(repairOrderFormModel repair) {
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_ADD_REPAIR_DETAILS);
			
			prepStmt.setString(1, repair.getRepairDetID());
			prepStmt.setString(2, repair.getOrderID());
			prepStmt.setString(3, repair.getItemCode());
			prepStmt.setString(4, repair.getDescription());
			prepStmt.setObject(5, repair.getDate());
			prepStmt.setString(6, repair.getStat());
			
			prepStmt.execute();
			
		} catch (Exception e) {
			System.out.println(e);
		}
			
	}
	@Override
	public void updateItemRepairDetails(repairOrderFormModel repair) {
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_UPDATE_REPAIR_DETAILS);
			
			prepStmt.setString(1, repair.getOrderID());
			prepStmt.setString(2, repair.getItemCode());
			prepStmt.setString(3, repair.getDescription());
			prepStmt.setObject(4, repair.getDate());
			prepStmt.setString(5, repair.getStat());
			prepStmt.setString(6, repair.getOrderID());
			
			prepStmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@Override
	public void deleteItemRepairDetails(String orderID) {
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = con.prepareStatement(CommonConstants.QUERY_DELETE_REPAIR_DETAILS);
			
			prepStmt.setString(1, orderID);
			
			prepStmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	/*------------------------REPAIR MANAGE---------------------------*/
	
	@Override
	public ArrayList<Model> getAllModelDetails() {

		ArrayList<Model> modelList = new ArrayList<Model>();
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_MODEL_DETAILS);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				
				Model model = new Model();
				
				model.setModel(resultSet.getString(1));
				model.setCategory(resultSet.getString(2));
				model.setSupplier(resultSet.getString(3));
				model.setUnitPrice(resultSet.getString(4));
				model.setSellingPrice(resultSet.getString(5));
				model.setQuantity(resultSet.getString(6));
				model.setWarranty(resultSet.getString(7));
				
				
				modelList.add(model);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return modelList;
	}
	/*--------------------------------Report---------------------------------*/
	@Override
	public ArrayList<String> getRepairDetailIDList() {
		ArrayList<String> repairList = new ArrayList<>();
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_REPAIR_ID);
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				repairList.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			System.out.println("Exception in getting ID List : ");
			System.out.println(e);
		}
		return repairList;
	}
	
	@Override
	public void generateRepairReportByMonthAndYear(String loadPath, int month, int year, String btnInput, String storePath) {
		try {
    		//System.out.println("Load Path : " + loadPath);
			//System.out.println("save path " + storePath); 
    		
			con = DBConnect.getDBConnection();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
				
			HashMap param = new HashMap();
			
			//get the query
			String query = "";
			
			if( month == 0 && year == 0 ) {
				param.put("info", "Full Repair Details Report for all  the years and months : ");
				query = "SELECT * FROM repair_detail";
			}else 
				if( month == 0 && year != 0 ) {
					param.put("info", "Repair Details Report for year : " + year + " for all the months");
					query = "SELECT * FROM repair_detail WHERE EXTRACT(YEAR FROM repair_date) = '"+year+"'";  
				}else
					if( month != 0 && year == 0 ) {
						param.put("info", "Repair Details Report for month  : " + Generator.getMonthStringValue(month) + " for all the years" );
						query = "SELECT * FROM repair_detail WHERE EXTRACT(MONTH FROM repair_date) = "+month;
					}else
						if( month != 0 && year != 0 ) {
							param.put("info", "Repair Details Report for year : " + year + " and  month  : " +  Generator.getMonthStringValue(month) );
							query = "SELECT * FROM repair_detail WHERE EXTRACT(MONTH FROM repair_date) = "+month+" AND EXTRACT(YEAR FROM repair_date) = "+year;  
						}
			         
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
		System.out.println("Exception generating repair details report by month and year : " + e );
		Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
	}

		
	}
	@Override
	public void generateFullRepairReport(String loadPath, String btnInput, String storePath) {
		try {
			con = DBConnect.getDBConnection();
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			//get the query
			String query = "SELECT * FROM repair_detail";
			JRDesignQuery jrQuery = new JRDesignQuery();
			jrQuery.setText(query);
			jasperDesign.setQuery(jrQuery);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
			JRViewer viewer = new JRViewer(jasperPrint);
			
			viewer.setOpaque(true);
			viewer.setVisible(true);
			
			if(btnInput == "view") {
				JFrame frame = new JFrame("Jasper Report");
				frame.add(viewer);
				frame.setSize(new Dimension(800,900));
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}else {
				if(btnInput == "save") {
					JasperExportManager.exportReportToPdfFile(jasperPrint, storePath);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in viewing full customer order report : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
		
	}
	
	/*@Override
	public String loadOdrID(repairOrderFormModel repair) {
		String OdrID = "";
    	
    	String query = "select order_id from repair_detail where order_id=?";
    	try {
    		con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, repair.getOrderID());
			
			resultSet = prepStmt.executeQuery();
			
			
			while(resultSet.next()) {
				OdrID = resultSet.getString(1);
				System.out.println("Order ID : " + OdrID);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
    	
    	return OdrID;
    	
    }*/
	
	public String getOrderDate(String ordrID) {
		String result = null;
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_GET_ORDER_DATE);
			
			resultSet = prepStmt.executeQuery();
			result = resultSet.toString();
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
		
		
	}
}
	
	
	
	


