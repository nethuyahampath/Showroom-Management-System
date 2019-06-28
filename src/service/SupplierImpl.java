package service;

import java.awt.Dimension;
import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import model.SupplierPayment;
import model.supplier;
import model.supplierOrder;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.design.JRDesignQuery;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.swing.JRViewer;
import util.CommonConstants;
import util.DBConnect;
import util.Generator;

public class SupplierImpl implements ISupplierService{
	private static Connection con;
	private static ResultSet resultSet;
	private static PreparedStatement prepStmt;
	
	/*---------------------------------beginning of supplier details------------------------------------*/

	@Override
	public ArrayList<supplier> getAllSupplierDetails() {
		// TODO Auto-generated method stub
		ArrayList<supplier> supList = new ArrayList<supplier>();
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_SUPPLIER_DETAILS);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next())
			{
				supList.add(new supplier(
						resultSet.getString("supplier_id"),
						resultSet.getString("supplier_name"),
						resultSet.getString("telephone"),
						resultSet.getString("email"),
						resultSet.getString("address"),
						resultSet.getString("country"),
						resultSet.getString("category")
						));
				//table.setItems(list);
				//prepStmt.close();
				//resultSet.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return supList;
	}

	@Override
	public void addSupplierDetails(supplier sup) {
		// TODO Auto-generated method stub
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_ADD_SUPPLIER_DETAILS);
			
			prepStmt.setString(1, sup.getSID());
			prepStmt.setString(2, sup.getSName());
			prepStmt.setString(3, sup.getSTel());
			prepStmt.setString(4, sup.getSEmail());
			prepStmt.setString(5, sup.getSAdd());
			prepStmt.setString(6, sup.getSCountry());
			prepStmt.setString(7, sup.getName_category());
			
			prepStmt.execute();
			prepStmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		
	}

	@Override
	public void updateSupplierDetails(supplier sup) {
		// TODO Auto-generated method stub
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_UPDATE_SUPPLIER_DETAILS);
					
			prepStmt.setString(1, sup.getSID());
			prepStmt.setString(2, sup.getSName());
			prepStmt.setString(3, sup.getSTel());
			prepStmt.setString(4, sup.getSEmail());
			prepStmt.setString(5, sup.getSAdd());
			prepStmt.setString(6, sup.getSCountry());
			prepStmt.setString(7, sup.getName_category());
			prepStmt.setString(8, sup.getSID());
			
			prepStmt.executeUpdate();
			prepStmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}

	@Override
	public void deleteSupplierDetails(String SID) {
		// TODO Auto-generated method stub
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = con.prepareStatement(CommonConstants.QUERY_DELETE_SUPPLIER_DETAILS);
			
			prepStmt.setString(1, SID);
			
			prepStmt.executeUpdate();
			prepStmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}

	
	
	/*----------------------------------End of supplier details--------------------------------------*/

	/*---------------------------------beginning of supplier Order------------------------------------*/
	
	@Override
	public ArrayList<supplierOrder> getAllSupplierOrder() {
		// TODO Auto-generated method stub
		
		ArrayList<supplierOrder> orderList = new ArrayList<supplierOrder>();
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_SUPPLIER_ORDER);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next())
			{
				orderList.add(new supplierOrder(
						resultSet.getString("order_id"),
						resultSet.getString("supplier_id"),
						resultSet.getString("order_date"),
						resultSet.getString("order_time"),
						resultSet.getString("total")
						));
				//table.setItems(list);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return orderList;
	}

	@Override
	public void addSupplierOrder(supplierOrder order) {
		// TODO Auto-generated method stub
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_ADD_SUPPLIER_ORDER);
			
			prepStmt.setString(1, order.getOID());
			prepStmt.setString(2, order.getSID());
			prepStmt.setObject(3, order.getOdate());
			prepStmt.setString(4, order.getOtime());
			prepStmt.setString(5, order.getOamount());
			
			prepStmt.execute();
			prepStmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} 	
		
	}

	@Override
	public void updateSupplierOrder(supplierOrder order) {
		// TODO Auto-generated method stub
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_UPDATE_SUPPLIER_ORDER);
			
			prepStmt.setString(1, order.getOID());
			prepStmt.setString(2, order.getSID());
			prepStmt.setObject(3, order.getOdate());
			prepStmt.setString(4, order.getOtime());
			prepStmt.setString(5, order.getOamount());
			prepStmt.setString(6, order.getOID());
			
			prepStmt.executeUpdate();
			prepStmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	@Override
	public void deleteSupplierOrder(String OID) {
		// TODO Auto-generated method stub
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = con.prepareStatement(CommonConstants.QUERY_DELETE_SUPPLIER_ORDER);
			
			prepStmt.setString(1, OID);
			
			prepStmt.executeUpdate();
			prepStmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	/*-------------------------------end of supplier order------------------------------------*/
	
	/*-------------------------------beginning of supplier payment----------------------------*/

	@Override
	public ArrayList<SupplierPayment> getAllSupplierPay() {
		// TODO Auto-generated method stub
		ArrayList<SupplierPayment> supList = new ArrayList<SupplierPayment>();
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_SUPPLIER_PAYMENT);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next())
			{
				supList.add(new SupplierPayment(
						resultSet.getString("payment_id"),
						resultSet.getString("order_id"),
						resultSet.getString("type"),
						resultSet.getString("quantity"),
						resultSet.getString("payment_date"),
						resultSet.getString("payment_time"),
						resultSet.getString("amount")
						));
				//table.setItems(list);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return supList;
	}

	@Override
	public void addSupplierPay(SupplierPayment pay) {
		// TODO Auto-generated method stub
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_ADD_SUPPLIER_PAYMENT);
			
			prepStmt.setString(1, pay.getPaymentID());
			prepStmt.setString(2, pay.getOrder_id());
			prepStmt.setString(3, pay.getType());
			prepStmt.setString(4, pay.getSupquantity());
			prepStmt.setObject(5, pay.getDate());
			prepStmt.setString(6, pay.getTime());
			prepStmt.setString(7, pay.getAmount());
			
			prepStmt.execute();
			prepStmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}

	@Override
	public void updateSupplierPay(SupplierPayment pay) {
		// TODO Auto-generated method stub
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_UPDATE_SUPPLIER_PAYMENT);
			
			prepStmt.setString(1, pay.getPaymentID());
			prepStmt.setString(2, pay.getOrder_id());
			prepStmt.setString(3, pay.getType());
			prepStmt.setString(4, pay.getSupquantity());
			prepStmt.setObject(5, pay.getDate());
			prepStmt.setString(6, pay.getTime());
			prepStmt.setString(7, pay.getAmount());
			prepStmt.setString(8, pay.getPaymentID());
			
			prepStmt.executeUpdate();
			prepStmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	@Override
	public void deleteSupplierPay(String PaymentID) {
		// TODO Auto-generated method stub
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = con.prepareStatement(CommonConstants.QUERY_DELETE_SUPPLIER_PAYMENT);
			
			prepStmt.setString(1, PaymentID);
			
			prepStmt.executeUpdate();
			prepStmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	/*----------------report generation-------------------------------------------------*/
	public ArrayList<String> getSupplierOrderDetailsList(){
		
		ArrayList<String> supplierList = new ArrayList<String>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_SUPPLIER_CODES);
			
			resultSet = prepStmt.executeQuery();
			
			while( resultSet.next() ) {
				supplierList.add(resultSet.getString(1));
			} //end while
			
			
		}catch(Exception e) {
			System.out.println("Exception in getting supplier list : ");
			System.out.println(e);
		}
		
		return supplierList;
	}

	public void generateSupplierOrderReportByMonthAndYear(String loadPath ,int month, int year, String btnInput, String storePath ){
	try {
		
		con = DBConnect.getDBConnection();
		System.out.println("Load path : " + loadPath );
		 
		JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
		
		HashMap param = new HashMap();
		//get the query
		
		String query = ""; 
		if(month == 0 && year == 0) {
			
			param.put("info", "Full supplier order details report for all year and month");
			query = "SELECT * FROM supplier_order";
		}else
			if(month == 0 && year != 0) {
				param.put("info", "supplier order details report for year :" + year + "for all months");
				query = "SELECT * FROM supplier_order WHERE EXTRACT(YEAR FROM order_date) = '"+year+"'";
			}else
				if(month != 0 && year == 0) {
					param.put("info", "supplier order details report for month :" + Generator.getMonthStringValue(month) + "for all years");
					query = "SELECT * FROM supplier_order WHERE EXTRACT(MONTH FROM order_date) = '"+month+"'";
				}else
					if(month != 0 && year != 0) {
						param.put("info", "supplier order details report for year : " + year + " and month :" + Generator.getMonthStringValue(month) );
						query = "SELECT * FROM supplier_order WHERE EXTRACT(MONTH FROM order_date) = '"+month+"' AND EXTRACT(YEAR FROM order_date) = " +year;
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
	System.out.println("Exception generating supplier order report by month and year : " + e );
	//Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
	}
  }

	@Override
	public void generateFullSupplierOrderReport(String loadPath, String btnInput, String storePath) {
		// TODO Auto-generated method stub
		try {
    		System.out.println("Load Path : " + loadPath );
			con = DBConnect.getDBConnection();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			//get the query
			String query = "SELECT * FROM supplier_order";           
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
			System.out.println("Exception in viewing full supplier order report : " + e );
			//Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
		
	}
	/*---------------------------------------------------------------------------------------------------------------------------------*/
		
	}

