package util;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import service.CustomerOrderServiceImpl;
import service.ICustomerOrderService;
import javafx.scene.control.ButtonType;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;

public class Generator {

	private static Connection con;
	private static PreparedStatement prepStmt;
	private static ResultSet resultSet;
	
	/*-------------------Generate Current Date -----------------*/
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String newDate = dateFormat.format(date);
		
		return newDate;
	}
	
	/*-------------------Generate Current Time -----------------*/
	public static String getCurrentTime() {
		
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    
	     return (sdf.format(cal.getTime()));
	}
	
	/*-------------------Alert Box for errors ----------------*/
	public static void getAlert(String title,  String message ) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	/*-------------Confirmation box---------------------------*/
	public static boolean getConfirmation(String title,  String message ) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);	
		alert.setContentText(message);
		
		Optional <ButtonType> action =  alert.showAndWait();
		
		if(action.get() == ButtonType.OK ) {
			return true;
		}else {
			return false;
		}
	}
	
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
	
	public static String generateDeliveryID() {

		String ID = "";

		try {

			con = (Connection) DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_DELIVERY_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "D100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "D" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	
	public static String generateSupplierOrderRequestId() {

		String ID = "";

		try {

			con = DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_SUPPLIER_ORDER_REQUEST_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "SRO100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "SRO" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception in SRO Generating : " + e);
		}

		return ID;
	}
	public static String generateSupplierId() {

		String ID = "";

		try {

			con = DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_SUPPLIER_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "S000";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "S00" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception in sup Generating : " + e);
		}

		return ID;
	}
	
	public static String generateVehicleId() {
		
		String ID = "";
		
		try {
			
			con = (Connection) DBConnect.getDBConnection();
			
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_VEHICLE_ID);
			
			resultSet = prepStmt.executeQuery();
			
			while (resultSet.next()) {
				ID = resultSet.getString(1);	
			}
			
			if(ID == null) {
				ID = "V100";
			}else {
				String newString = ID.replaceAll("[^0-9]","");
				int num = Integer.parseInt(newString);
				++num;
				
				ID = "V" + num;
			}
			
		}catch(Exception e) {
			System.out.println("Exception  : " + e);
		}
		
		
		return ID;
		
		
	}

	public static String generateEmployeeID() {

		String ID = "";

		try {

			con = (Connection) DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_EMPLOYEE_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "E100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "E" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	
	
	public static String generateItemCode() {

		String ID = "";

		try {

			con = DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_ITEM_CODE);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "I100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "I" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception in ITM Generating : " + e);
		}

		return ID;
	}
	
	public static String generateOrderID() {

		String ID = "";

		try {

			con = DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_CUSTOMER_ORDER_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "O100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "O" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	
	public static String generatePaymentID() {

		String ID = "";

		try {

			con = DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_CUSTOMER_PAYMENT_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "P100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "P" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	
	
	/*-------------------------- Directory path change --------------------------------*/
	public static String generateFilePathsToJavaFormat( String path ) {
		
		String newPath = "";
		
		newPath = path.replace("/", "\\");
		newPath = newPath.replace("\\", "\\" + "\\");
		System.out.println(newPath);
		return newPath;
	
	}
	
	/*--------------------- To generate the barcode pdf -------------------------------------*/
	public static void generateBarcodePdf( String filePath, ArrayList<String> codeList) {
	    Document doc = new Document();
	    PdfWriter docWriter = null;
	    try {
	        docWriter = PdfWriter.getInstance(doc, new FileOutputStream(filePath));
	        doc.addAuthor("BarCode");
	        doc.addCreationDate();
	        doc.addProducer();
	        doc.addCreator("Crowns PVT LTD");
	        doc.addTitle("Crown PVT LTD Barcodes");
	        doc.setPageSize(PageSize.LETTER);
	        doc.open();
	        PdfContentByte cb = docWriter.getDirectContent();

	        ArrayList<String> codes = new ArrayList<String>();
	        
	        codes = codeList;
	        
	        int count = 1;
	        
	        int XPosition = 100;
	        int YPosition = 700;
	        
	        for(String code : codes ) {
	        	
	        	if( count == 2 ) {
	        		XPosition += 150; 
	        	}
	        	
	        	if( count == 3 ) {
	        		XPosition  += 150;
	        	}
	        	
	        	if( count ==  4 ) {
	        		count = 1;
	        		XPosition = 100;
	        		YPosition -= 100;
	        	}
	        	
		        Barcode128 code128 = new Barcode128();
		        code128.setCode(code.trim());
		        code128.setCodeType(Barcode128.CODE128);
		        Image code128Image = code128.createImageWithBarcode(cb, null, null);
		        code128Image.setAbsolutePosition(XPosition, YPosition);
		        code128Image.scalePercent(200);
		        doc.add(code128Image);
		        
		        ++count;
		        
	        } //end for
	        /*
	        BarcodeEAN codeEAN = new BarcodeEAN();
	        codeEAN.setCode(myString.trim());
	        codeEAN.setCodeType(BarcodeEAN.EAN13);      
	        Image codeEANImage = code128.createImageWithBarcode(cb, null, null);
	        codeEANImage.setAbsolutePosition(10, 600);
	        codeEANImage.scalePercent(125);
	        doc.add(codeEANImage);
	        */
	    } catch (Exception e) {
	        System.out.println("Exception in generateItemCodeBarcodePdf : " + e );
	    } finally {
	        if (doc != null) {
	            doc.close();
	        }
	        if (docWriter != null) {
	            docWriter.close();
	        }
	    }
	}
	
	/*------------------------------- Generate month integer value -------------------------------*/
	public static int getMonthIntValue( String monthString ) {
		
		int month = 0;
    	//switch to get the month values
    	switch(monthString) {
    	
    	case "January"	: 	month = 1;
    						break;
    					
    	case "February"	: 	month = 2;
    						break;
    					
    	case "March"	:	month = 3;
    						break;
    						
    	case "April"	: 	month = 4;
    						break;
    						
    	case "May"		: 	month = 5;
    						break;
    						
    	case "June"		: 	month = 6;
    						break;
    						
    	case "July" 	: 	month = 7;
    						break;
    	
    	case "August"	: 	month = 8;
    						break;
    						
    	case "September":	month = 9;
    						break;
    						
    	case "October" 	: 	month = 10;
    						break;
    						
    	case "November"	: 	month = 11;
    						break;
    						
    	case "December"	: 	month = 12;
    						break;
    	
    	} //end switch
    	
    	return month;
	}
	
	public static String getMonthStringValue( int monthInt ) {
		
		String month = "";
    	//switch to get the month values
    	switch(monthInt) {
    	
    	case 1	: 	month = "January";
							break;
	
		case 2	: 	month = "February";
							break;
			
		case 3	:	month = "March";
							break;
				
		case 4	: 	month = "April";
							break;
				
		case 5	: 	month = "May";
							break;
				
		case 6		: 	month = "June";
							break;
				
		case 7 	: 	month = "July";
							break;
		
		case 8	: 	month = "August";
							break;
				
		case 9:	month = "September";
							break;
				
		case 10 	: 	month = "October";
							break;
				
		case 11	: 	month = "November";
							break;
				
		case 12	: 	month = "December";
							break;
    	
    	} //end switch
    	
    	return month;
	}
	
	
	public static String generateSalaryId() {

		String ID = "";

		try {

			con = (Connection) DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_SALARY_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "SAL100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "SAL" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	
	
	
	public static String generateDeliveryExpenseId() {

		String ID = "";

		try {

			con = (Connection) DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GENERATE_DELIVERY_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "D100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "D" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	
	
	public static String generateRepairingExpensesID() {

		String ID = "";

		try {

			con = (Connection) DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GENERATE_REPAIRING_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "R100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "R" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	
	public static String generateShowroomExpensesId() {

		String ID = "";

		try {

			con = (Connection) DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GENERATE_SHOWROOM_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "S100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "S" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	
	
	public static String generateRepairDetailID() {

		String ID = "";

		try {

			con = (Connection) DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_REPAIR_DETAIL_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "RD100";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "RD" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}

		return ID;
	}
	

	public static void sendNotification( String receiver , String subjectEmail , String bodyEmail ) {
		
		try {
			String host = "smtp.gmail.com";
			String user = CommonConstants.SENDER_EMAIL;
			String pass = CommonConstants.SENDER_PASSWORD;
			String to = receiver;
			String from = CommonConstants.SENDER_EMAIL;
			String subject = subjectEmail;
			String messageText = bodyEmail;
			boolean sessionDebug = false;
			
			Properties props = System.getProperties();
			
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");
			
			
			Session mailSession = Session.getDefaultInstance(props,null);
			mailSession.setDebug(sessionDebug);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));
			InternetAddress address = (new InternetAddress(to));
			msg.setRecipient(Message.RecipientType.TO, address);
			msg.setSubject(subject);msg.setSentDate(new Date());
			msg.setText(messageText);
			
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, user, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			System.out.println("message send successfully");
			
			Generator.getAlert("Notification Success", "Message Send Successfully");
			}catch(MessagingException ex) {
				System.out.println(ex);
				System.out.println("unsuccessful");
			}
	}
	
	
	public static String generateSupplierPayId() {

		String ID = "";

		try {

			con = DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_SUPPLIER_PAYMENT_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "P000";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "P00" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception in pay Generating : " + e);
		}

		return ID;
	}
	public static String generateSupplierOrderId() {

		String ID = "";

		try {

			con = DBConnect.getDBConnection();

			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_MAX_SUPPLIER_ORDER_ID);

			resultSet = prepStmt.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString(1);
			}

			if (ID == null) {
				ID = "O000";
			} else {
				String newString = ID.replaceAll("[^0-9]", "");
				int num = Integer.parseInt(newString);
				++num;

				ID = "O00" + num;
			}

		} catch (Exception e) {
			System.out.println("Exception in ord Generating : " + e);
		}

		return ID;
	}

	
}
