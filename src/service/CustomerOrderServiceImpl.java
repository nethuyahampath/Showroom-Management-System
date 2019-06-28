package service;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import com.mysql.jdbc.PreparedStatement;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;

import model.BrandNewItem;
import model.Cart;
import model.Model;
import model.CustomerOrder;
import model.CustomerPayment;
import model.Order;
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

public class CustomerOrderServiceImpl implements ICustomerOrderService{

	private static Connection con;
	private static PreparedStatement prepStmt;
	private static ResultSet resultSet;
	
	@Override
	public ArrayList<Cart> getAllCartDetails( String cartID ){
		
		ArrayList<Cart> cartList = new ArrayList<Cart>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_ALL_CART_DETAILS_BY_CART_ID);
			prepStmt.setString(1, cartID);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				
				Cart cart = new Cart();
				
				cart.setCartID(resultSet.getString(1));
				cart.setSellingPrice(resultSet.getDouble(2));
				cart.setQuantity(resultSet.getInt(3));
				cart.setCustomerID(resultSet.getString(4));
				cart.setItemCode(resultSet.getString(5));
				cart.setModelName(getModelNameByItemCode(resultSet.getString(5)));
				
				cartList.add(cart);
			} //end while
			
		}catch(Exception e) {
			System.out.println("Exception in getting all cart Details : ");
			System.out.println(e);
		}
		
		return cartList;
	}
	
	@Override
	public ArrayList<BrandNewItem> getAllBrandNewItemDetails(){
		
		ArrayList<BrandNewItem> itemList = new ArrayList<BrandNewItem>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_ALL_ITEMS_BY_BRAND_NEW_ITEM_ID);
			prepStmt.setString(1, "stock"); 
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				
				BrandNewItem item = new BrandNewItem();
				
				item.setItemCode(resultSet.getString(1));
				item.setModelName(resultSet.getString(2));
				item.setItemCondition(resultSet.getString(3));
				item.setItemStatus(resultSet.getString(4));
				item.setIdOrderSupplier(resultSet.getString(5));
			
				itemList.add(item);
			} //end while
			
		}catch(Exception e) {
			System.out.println("Exception in getting Brand New Item Details : ");
			System.out.println(e);
		}
		
		return itemList;
	}
	
	@Override
	public String getModelNameByItemCode(String itemCode) {
		
		String modelName = "";
		
		try {
			
			con = DBConnect.getDBConnection();
			
			PreparedStatement prepStmt2 = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MODEL_BY_ITEM_CODE);
			prepStmt2.setString(1,itemCode);
			
			ResultSet resultSet2  = prepStmt2.executeQuery();
			
			while( resultSet2.next()) {
				modelName = resultSet2.getString(1);
			}
			
		}catch(Exception e ) {
			System.out.println("Exception in getting the model name by item code :");
			System.out.println(e);
		}
		
		return modelName;
	}
	
	@Override
	public String getPriceByModelName( String modelName ) {
		
		String price = "";
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_PRICE_BY_MODEL_NAME);
			prepStmt.setString(1, modelName);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {

				price = resultSet.getString(1);
			} //end  while
			
		}catch(Exception e) {
			System.out.println("Exception  in getting price by model name :");
			System.out.println(e);
		}
		
		return price;
		
	}
	
	@Override
	public void addCartItem( Cart cart ) {
		
		try {
			
			con =  DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_ADD_ITEM_TO_CART);
			
			prepStmt.setString(1, cart.getCartID());
			prepStmt.setDouble(2, cart.getSellingPrice());
			prepStmt.setString(3, cart.getCartDate());
			prepStmt.setString(4, cart.getCustomerID());
			prepStmt.setString(5, cart.getItemCode());
			
			prepStmt.execute();
			
		}catch(Exception e) {
			System.out.println("Exception in adding cart details ");
			System.out.println(e);
		}
	}
	
	@Override
	public void clearCart( String cartID ) {
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_REMOVE_CART_DETAILS);
			prepStmt.setString(1, cartID);
			
			prepStmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("Exception in clearing cart : " );
			System.out.println(e);
		}
	}
	
	@Override
	public int getModelCountInCart( String modelName , String cartID ) {
	
		int count = 0;
		
		try {
			con = DBConnect.getDBConnection();
		
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MODEL_COUNT_IN_CART);
			prepStmt.setString(1, modelName );
			prepStmt.setString(2, cartID );
			
			resultSet = prepStmt.executeQuery();
			
			while( resultSet.next()) {
				count  = resultSet.getInt(1);
			}
			
		}catch(Exception e) {
			System.out.println("Exception in getting the cart Summary : ");
		}
		
		return count;
	}
	
	@Override
	public String getCustomerIDByNIC( String NIC ) {
		
		String custID  = "";
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt =  (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_CUSTOMER_ID_BY_NIC);
			prepStmt.setString(1, NIC );
			
			resultSet = prepStmt.executeQuery();
			
			while( resultSet.next() ) {
				custID = resultSet.getString(1);
			}
			
			System.out.println(custID);
			
		}catch(Exception e) {
			System.out.println("Exception in getting customer ID by NIC : ");
			System.out.println(e);
		}
		
		return custID;
	}
	
	@Override 
	public void removeItemFromCart( String cartID , String itemCode ) {
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_REMOVE_ITEM_FROM_CART);
			prepStmt.setString(1, itemCode);
			prepStmt.setString(2, cartID);
			
			prepStmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("Exception in  removing item from cart : ");
			System.out.println(e);
		}
	}
	
	@Override
	public void updateItemStatus( String status , String itemCode) {
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_UPDATE_ITEM_STATUS);
			prepStmt.setString(1, status);
			prepStmt.setString(2, itemCode);
			
			prepStmt.execute();
			
		}catch(Exception e) {
			System.out.println("Exception in updating the item status : ");
			System.out.println(e);
		}
	}
	
	@Override
	public boolean isInvalidSearchInput(String itemNo ) {
		
		String code =  "";
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_ITEM_CODE_BY_ITEM_CODE);
			prepStmt.setString(1, itemNo);
			
			resultSet =  prepStmt.executeQuery();
			
			while(resultSet.next()) {
				code = resultSet.getString(1);
			}
			
		}catch(Exception e ) {
			System.out.println("Exception in validating the Item search box ");
			System.out.println(e);
		}
		
		if( code ==  null || code == "")
			return true;
		else
			return false;
	}
	
	@Override
	public ArrayList<BrandNewItem> getItemListForSales(){
		
		ArrayList<BrandNewItem> itemList = new ArrayList<BrandNewItem>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_ITEM_DETAILS_FOR_SALES);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				
				BrandNewItem item = new BrandNewItem();
				
				item.setItemCode(resultSet.getString(1));
				item.setModelName(resultSet.getString(2));
				item.setItemCondition(resultSet.getString(3));
				item.setItemStatus(resultSet.getString(4));
				
				itemList.add(item);
			} //end while
			
		}catch(Exception e) {
			System.out.println("Exception in getting Item Details for sales: ");
			System.out.println(e);
		}
		
		return itemList;
	}
	
	@Override
	public ArrayList<Model> getModelListForSales(){
		
		ArrayList<Model> modelList = new ArrayList<Model>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MODEL_DETAILS_FOR_SALES);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				
				Model model = new Model();
				
				model.setModel(resultSet.getString(1));
				model.setCategory(resultSet.getString(2));
				model.setUnitPrice(resultSet.getString(3));
				model.setSellingPrice(resultSet.getString(4));
				model.setQuantity(resultSet.getString(5));
				model.setWarranty(resultSet.getString(6));
				
				modelList.add(model);
			} //end while
			
		}catch(Exception e) {
			System.out.println("Exception in getting Model Details for sales: ");
			System.out.println(e);
		}
		
		return modelList;
	}
	
	@Override
	public void decrementQuantityOnAdd( String modelName ) {
		
		try {
			
			int quantity = 0;
			
			con = DBConnect.getDBConnection();
			
			//get the quantity
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_QUANTITY_BY_MODEL_NAME);
			prepStmt.setString(1, modelName);
				
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				quantity = resultSet.getInt(1);
			}
			
			quantity -= 1;
			
			//update the quantity
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_UPDATE_MODEL_QUANTITY);
			prepStmt.setInt(1, quantity);
			prepStmt.setString(2, modelName);
			
			prepStmt.execute();
			
		}catch(Exception e) {
			System.out.println("Exception in decrementing the quantity : ");
			System.out.println(e);
		}
		
	}
	
	@Override
	public void incrementQuantityOnRemove( String modelName ) {
		
		try {
			
			int quantity = 0;
			
			con = DBConnect.getDBConnection();
			
			//get the quantity
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_QUANTITY_BY_MODEL_NAME);
			prepStmt.setString(1, modelName);
				
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				quantity = resultSet.getInt(1);
			}
			
			quantity += 1;
			
			//update the quantity
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_UPDATE_MODEL_QUANTITY);
			prepStmt.setInt(1, quantity);
			prepStmt.setString(2, modelName);
			
			prepStmt.execute();
			
		}catch(Exception e) {
			System.out.println("Exception in incrementing the quantity : ");
			System.out.println(e);
		}
	}
	
	@Override
	public void addCustomerOrderDetails(CustomerOrder order) {
		try {
			
			con =  DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_ADD_CUSTOMER_ORDER_DETAILS);
		
			prepStmt.setString(1, order.getOID());
			prepStmt.setString(2, order.getCartID());
			prepStmt.setString(3, order.getOdate());
			prepStmt.setString(4, order.getOtime());
			prepStmt.setDouble(5, order.getTotal());
			prepStmt.setString(6, order.getDeliveryStatus());
			
			prepStmt.execute();
			
		}catch(Exception e) {
			System.out.println("Exception in adding customer order details ");
			System.out.println(e);
		}
	}
	
	@Override
	public ArrayList<CustomerOrder> getCustomerOrderDetails(){
		
		ArrayList<CustomerOrder> orderList = new ArrayList<CustomerOrder>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_CUSTOMER_ORDER);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				
				CustomerOrder order = new CustomerOrder();
				
				order.setOID(resultSet.getString(1));
				order.setCartID(resultSet.getString(2));
				order.setOdate(resultSet.getString(3));
				order.setOtime(resultSet.getString(4));
				order.setTotal(Double.parseDouble(resultSet.getString(5)));
				order.setDeliveryStatus(resultSet.getString(6));		
				
				orderList.add(order);
			} //end while
			
		}catch(Exception e) {
			System.out.println("Exception in getting Customer Order Details: ");
			System.out.println(e);
		}
		
		return orderList;
	}
	
	@Override
	public void addCustomerPaymentDetails( CustomerPayment payment ) {
		
		try {
			
			con =  DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_ADD_CUSTOMER_PAYMENT_DETAILS);
		
			prepStmt.setString(1, payment.getPaymentID());
			prepStmt.setString(2, payment.getDate());
			prepStmt.setString(3, payment.getTime());
			prepStmt.setString(4, payment.getAmount());
			prepStmt.setString(5, payment.getType());
			prepStmt.setString(6, payment.getOrder_id());
			
			prepStmt.execute();
			
		}catch(Exception e) {
			System.out.println("Exception in adding customer payment details ");
			System.out.println(e);
		}
	}
	
	@Override
	public ArrayList<CustomerPayment> getCustomerPaymentDetails(){
		
		ArrayList<CustomerPayment> paymentList = new ArrayList<CustomerPayment>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_CUSTOMER_PAYMENT_DETAILS);
			
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				
				CustomerPayment payment = new CustomerPayment();
				
				payment.setPaymentID(resultSet.getString(1));
				payment.setDate(resultSet.getString(2));
				payment.setTime(resultSet.getString(3));
				payment.setAmount(resultSet.getString(4));
				payment.setType(resultSet.getString(5));
				payment.setOrder_id(resultSet.getString(6));
				
				paymentList.add(payment);
			} //end while
			
		}catch(Exception e) {
			System.out.println("Exception in getting Customer Payment Details :");
			System.out.println(e);
		}
		
		return paymentList;
	}
	
	@Override
	public String getCustomerIDByCartID( String cartID ) {
		
		String custID = "";
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt =  (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_CUSTOMER_ID_BY_CART_ID);
			prepStmt.setString(1, cartID );
			
			resultSet = prepStmt.executeQuery();
			
			while( resultSet.next() ) {
				custID = resultSet.getString(1);
			}
			
		}catch(Exception e) {
			System.out.println("Exception in getting customer ID by cart ID  : ");
			System.out.println(e);
		}
		return custID;
	}
	
	/*-------------------------------------------------------------Barcode reading---------------------------------------------------*/
	public boolean ifItemsAreSold( String itemCode ) {
		String status = "";
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_SOLD_ITEMS_DETAILS);
			prepStmt.setString(1, itemCode);
			
			resultSet = prepStmt.executeQuery();
			
			
			
			while( resultSet.next() ) {
				status = resultSet.getString(1);
			}
			

			
		}catch(Exception e ) {
			System.out.println("Exception in checkinig for item sold : " );
			System.out.println(e);
		}
		
		if( status.equalsIgnoreCase("sold")) {
			System.out.println("Status  Ret true: " + status );
			return true;
		}else {
			System.out.println("Status Ret false : " + status );
			return false;
		}

	}
	
	public boolean cartInOrders( String cartID ) {
		String orderID = "";
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_CART_IN_ORDER);
			prepStmt.setString(1, cartID);
			
			resultSet = prepStmt.executeQuery();
			
			while( resultSet.next() ) {
				orderID = resultSet.getString(1);
			}

		}catch(Exception  e) {
			System.out.println("Exception in checking cart in Orders :  ");
			System.out.println(e);
		}
		
		if( orderID != "" ) {
			System.out.println("Order ID ret true: " + orderID );
			return true;
		}else {
			System.out.println("Order ID ret false: " + orderID );
			return false;
		}
	}
	/*---------------------------------------------------------------------------------------------------------------------------------*/
	
	/*--------------------------------------------------------------Report Generation -------------------------------------------------*/
	public ArrayList<String> getItemCodeList(){
		
		ArrayList<String> itemList = new ArrayList<String>();
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ALL_ITEM_CODES);
			
			resultSet = prepStmt.executeQuery();
			
			while( resultSet.next() ) {
				itemList.add(resultSet.getString(1));
			} //end while
			
			
		}catch(Exception e) {
			System.out.println("Exception in getting item list : ");
			System.out.println(e);
		}
		
		return itemList;
	}
	
	public void generateCOReportByMonthAndYear(String loadPath ,int month, int year, String btnInput, String storePath ){
	    	try {
	    		
				con = DBConnect.getDBConnection();
				
				JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
					
				HashMap param = new HashMap();
				
				//get the query
				String query = "";
				
				if( month == 0 && year == 0 ) {
					param.put("info", "Full Customer Order Report for all  the years and months : ");
					query = "SELECT * FROM customer_order";
				}else 
					if( month == 0 && year != 0 ) {
						param.put("info", "Customer Order Report for year : " + year + " for all the months");
						query = "SELECT * FROM customer_order WHERE EXTRACT(YEAR FROM order_date) = "+year;  
					}else
						if( month != 0 && year == 0 ) {
							param.put("info", "Customer Order Report for month  : " + Generator.getMonthStringValue(month) + " for all the years" );
							query = "SELECT * FROM customer_order WHERE EXTRACT(MONTH FROM order_date) = "+month;
						}else
							if( month != 0 && year != 0 ) {
								param.put("info", "Customer Order Report for year : " + year + " and  month  : " +  Generator.getMonthStringValue(month) );
								query = "SELECT * FROM customer_order WHERE EXTRACT(MONTH FROM order_date) = "+month+" AND EXTRACT(YEAR FROM order_date) = "+year;  
							}
				         
				JRDesignQuery jrQuery = new JRDesignQuery();
				jrQuery.setText(query);
				jasperDesign.setQuery(jrQuery);

				
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , param , con );
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
	public void generateCOReportByDate( String loadPath , String date  , String btnInput, String storePath ) {
		
		System.out.println(date);
		
		try {
			con = DBConnect.getDBConnection();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			String query = "";
			
			HashMap param = new HashMap();
			param.put("info", "Customer Order Report For the Date  : " + date);
			query = "SELECT * FROM customer_order WHERE order_date = '" + date+"'";
			
			System.out.println(query);
			//get the query
			         
			JRDesignQuery jrQuery = new JRDesignQuery();
			jrQuery.setText(query);
			jasperDesign.setQuery(jrQuery);
	
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , param , con );
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
	
	public void generateCOInvoice( String loadPath, String cartID , String storePath, String orderID, String orderDate, String orderTime, String total, String type, String custID ) {
		try {
			con = DBConnect.getDBConnection();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			String query = "";
			
			HashMap param = new HashMap();
			param.put("orderID", orderID );
			param.put("orderDate", orderDate );
			param.put("custID", custID );
			param.put("orderTime", orderTime );
			param.put("total", total );
			param.put("type", type );
			
			query = "SELECT * FROM cart WHERE cart_id = '"+cartID+"'";

			         
			JRDesignQuery jrQuery = new JRDesignQuery();
			jrQuery.setText(query);
			jasperDesign.setQuery(jrQuery);
	
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , param , con );
			JRViewer viewer = new JRViewer( jasperPrint );
			
			viewer.setOpaque(true);
			viewer.setVisible(true);
			
	
			JFrame frame = new JFrame("Jasper report");
		    frame.add(viewer);
		    frame.setSize(new Dimension(800, 900));
		    frame.setLocationRelativeTo(null);
		    frame.setVisible(true);
		
		    JasperExportManager.exportReportToPdfFile(jasperPrint, storePath );
		    
		}catch(Exception e) {
			System.out.println("Exception generating customer order invoice : " + e );
			//Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
	}
	
	public void generateFullCOReport( String loadPath ,  String btnInput, String storePath) {
		try {
    		
			con = DBConnect.getDBConnection();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			//get the query
			String query = "SELECT * FROM customer_order";           
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
			System.out.println("Exception in viewing full customer order report : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
		
	}
	/*---------------------------------------------------------------------------------------------------------------------------------*/
	public static String generateCartID() {

		String ID = "";

		try {

			con = DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_USER_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "CT100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "CT" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	
	
	/*-------------------------------------------- notification sending-----------------------------------------------------------*/
	@Override
	public String getCustomerEmailByID( String custID ) {
		
		String email = "";
		
		try {
			
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_CUSTOMER_EMAIL_BY_ID);
			prepStmt.setString(1, custID);
			resultSet = prepStmt.executeQuery();
			
			while (resultSet.next()) {
				email = resultSet.getString(1);
			}
			
		}catch(Exception e) {
			System.out.println("Exception in getting customer Email by ID : " + e);
		}
		
		return email;
	}
	
	@Override
	public String getCustomerFirstNameByID( String custID ) {
		
		String fname = "";
		
		try{
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_CUSTOMER_FNAME_BY_ID);
			prepStmt.setString(1, custID);
			resultSet = prepStmt.executeQuery();
			
			while (resultSet.next()) {
				fname = resultSet.getString(1);
			}
		}catch(Exception e) {
			System.out.println("Exception in getting customer First Name By ID : " + e  );
		}
		
		return fname;
	}
	
	@Override
	public String getCustomerLastNameByID(String custID) {
		
		String lname = "";
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_CUSTOMER_LNAME_BY_ID);
			prepStmt.setString(1, custID);
			resultSet = prepStmt.executeQuery();
			
			while (resultSet.next()) {
				lname = resultSet.getString(1);
			}
		}catch(Exception e) {
			System.out.println("Exception in getting customer Last Name By ID : " + e ); 
		}
		
		return lname;
	}
	
}
