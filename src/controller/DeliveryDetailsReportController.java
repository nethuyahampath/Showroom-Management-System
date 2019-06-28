package controller;

import model.Main;
import model.User;
import service.DeliveryServiceImpl;
import service.EmployeeServiceImpl;
import service.IDeliveryService;
import service.IEmployeeService;
import util.Generator;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import javafx.scene.control.TextArea;
import java.util.ResourceBundle;

import java.util.List;
//import groovy.util.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DeliveryDetailsReportController implements Initializable{
	

	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
    
	private static final String COMPILE_REPORT_PATH = "C:\\Users\\Ganesh\\Desktop\\SMS_REPORT_NEW\\Showroom_Management_System\\src\\report\\";
	
	  @FXML
	  private TextField DeliveryDetailsReportPath;
	    
	  @FXML
	  private TextField deliveryDetailsReportSaveAsText;
	  
	  /*------------------selections--------------------------------------------------*/
	  
	  
	  @FXML
	  private ComboBox deliveryDetailsReportSelectYear;

	  @FXML
	  private ComboBox deliveryDetailsReportSelectMonth;
	    
	  public ObservableList<String> yearList = FXCollections.observableArrayList("All","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028"); 
		
	  public ObservableList<String> monthList =  FXCollections.observableArrayList("All","January",  "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" );
	  
	  
	  
	 /*---------------------------------------------------------------------------*/
	  
	  
	 /*--------------------------- Select Report Save paths ---------------------------------------------------*/
	   
	  @FXML
	  void chooseDeliveryDetailsReportPath(ActionEvent event) {
	    	
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(null);

			DeliveryDetailsReportPath.setText(Generator.generateFilePathsToJavaFormat(selectedDirectory.getAbsolutePath() + "\\"));
			
	    }
	  
	  /*---------------------------------------------------------------------------------------------------------*/
	    
	  /*------------------------------ Save As name --------------------------------------------------------------*/
	
	  
	  @FXML
	  void onYearSelectDDR(ActionEvent event) {
	    	
	    	String yearString = deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem().toString();
	    	String monthString = "";
	    	
	    	if( deliveryDetailsReportSelectMonth.getSelectionModel().getSelectedItem() == null) {
	    		
	    		monthString = "";
	    	 	deliveryDetailsReportSaveAsText.setText("DDR-"+yearString);
	    	}else {
	    		monthString = deliveryDetailsReportSelectMonth.getSelectionModel().getSelectedItem().toString();
	    	 	deliveryDetailsReportSaveAsText.setText("DDR-"+yearString+"-"+monthString);
	    	}	
	    	
	    }
	  
	  
	  @FXML
	  void onMonthSelectDDR(ActionEvent event) {
	    		
	    	String yearString = "";
	    	String monthString = deliveryDetailsReportSelectMonth.getSelectionModel().getSelectedItem().toString();
	    	
	    	if( deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem() == null) {
	    		
	    		yearString = "";
	    	 	deliveryDetailsReportSaveAsText.setText("DDR-"+monthString);
	    	}else {
	    		yearString = deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem().toString();
	    	 	deliveryDetailsReportSaveAsText.setText("DDR-"+yearString+"-"+monthString);
	    	}	
	    }
	  
	  
	  /*----------------------------------------------------------------------------------------------------------*/
	    
	  /*---------------------------------------- Reports ---------------------------------------------------------*/
	  
	  @FXML
	    public void viewDeliveryDetailsReport(ActionEvent event) {
	    	
	    	IDeliveryService icustOrder = new DeliveryServiceImpl();

	    	if( deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem() == null || deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem() == null ) {
	    		
	    		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
	    		return;
	    	}
	    	
	    	
	    	String monthString = deliveryDetailsReportSelectMonth.getSelectionModel().getSelectedItem().toString();
	    	String yearString = deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem().toString();

	    	
	    	if( monthString == "All" && yearString == "All" ) {
	    		icustOrder.generateFullDDReport(COMPILE_REPORT_PATH + "Delivery_Report.jrxml", "view",  DeliveryDetailsReportPath.getText()+deliveryDetailsReportSaveAsText.getText()+".pdf");
	    	}else
	    		if( monthString == "All"  && yearString != "All" ) {
	    			
	    		}else
	    			if( monthString != "All" && yearString == "All" ) {
	    				
	    			}else {
	    		    	int year = Integer.parseInt(deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem().toString());
	    		    	int month = Generator.getMonthIntValue(monthString);
	    		    	icustOrder.generateDDReportByMonthAndYear(COMPILE_REPORT_PATH + "Delivery_Report.jrxml", month, year, "view", DeliveryDetailsReportPath.getText()+deliveryDetailsReportSaveAsText.getText()+".pdf" );
	    			}

	    	
	    }
	    
	    
	    
	    @FXML
	    public void saveDeliveryDetailsReport(ActionEvent event) {
	    	
	    	IDeliveryService iDeliveryService = new DeliveryServiceImpl();
	    	
	    	if( deliveryDetailsReportSaveAsText.getText() == ""  ||  deliveryDetailsReportSaveAsText.getText() == null ) {
	    		Generator.getAlert("Error in saving delivery report", "You must enter a file name to save your pdf");
	    		return;
	    	}
	    	
	    	if( deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem() == null || deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem() == null ) {
	    		
	    		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
	    		return;
	    	}
	    	
	    	String monthString = deliveryDetailsReportSelectMonth.getSelectionModel().getSelectedItem().toString();
	    	String yearString = deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem().toString();

	    	
	    	if( monthString == "All" && yearString == "All" ) {
	    		int year=0;
	    		int month=0;
	    		iDeliveryService.generateFullDDReport(COMPILE_REPORT_PATH + "Delivery_Report.jrxml", "save",  DeliveryDetailsReportPath.getText()+deliveryDetailsReportSaveAsText.getText()+".pdf");
	    	}else
	    		if( monthString == "All"  && yearString != "All" ) {
	    			int year = Integer.parseInt(deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem().toString());
	    			int month = 0;
				iDeliveryService.generateDDReportByMonthAndYear(COMPILE_REPORT_PATH + "Customer_Order.jrxml", month, year, "save", DeliveryDetailsReportPath.getText()+deliveryDetailsReportSaveAsText.getText()+".pdf" );
	    		}else
	    			if( monthString != "All" && yearString == "All" ) {
	    				int year = 0;
	        	    	int month = Generator.getMonthIntValue(monthString);
	        	    	iDeliveryService.generateDDReportByMonthAndYear(COMPILE_REPORT_PATH + "Customer_Order.jrxml", month, year, "save", DeliveryDetailsReportPath.getText()+deliveryDetailsReportSaveAsText.getText()+".pdf" );
	    			}else {
	    				int year = Integer.parseInt(deliveryDetailsReportSelectYear.getSelectionModel().getSelectedItem().toString());
	    		    	int month = Generator.getMonthIntValue(monthString);
	    		    	iDeliveryService.generateDDReportByMonthAndYear(COMPILE_REPORT_PATH + "Customer_Order.jrxml", month, year, "save", DeliveryDetailsReportPath.getText()+deliveryDetailsReportSaveAsText.getText()+".pdf" );
	    			}
	    	
	    }
	    
	 /*----------------------------------------------------------------------------------------------------------*/
	
	    
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1){

			deliveryDetailsReportSelectYear.setItems(yearList);
			deliveryDetailsReportSelectMonth.setItems(monthList);
			//default file barcode report file path
			DeliveryDetailsReportPath.setText(Generator.generateFilePathsToJavaFormat("D:\\\\2 Year 2nd sem SLIIT\\\\ITP Backup\\\\bk3\\\\Showroom_Management_System\\\\src\\\\report\\"));
			
		}
	
	    
	 /*----------------------------------------Notification-------------------------------------------*/
	
	    @FXML
	    private TextField recieverEmaiTxt;

	    @FXML
	    private TextField subjectTxt;

	    @FXML
	    private TextArea BodyTxt;
	    
	    public void sendNotification(ActionEvent event) {
	    	
	    	String rec = recieverEmaiTxt.getText();
	    	String sub = subjectTxt.getText();
	    	String body = BodyTxt.getText();
	    	
	    	Generator.sendNotification(rec , sub, body);
	    }
	    
	  
		/*-------------------Window Control  -----------------*/
		public void ExitWindow(ActionEvent event) throws Exception{
			Platform.exit();
		}
		
		public void MinimizeWindow(ActionEvent event) throws Exception{
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());

			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
		}
		
		
		//Functions to change the screens 
		public void homeScreen( ActionEvent event ) throws Exception{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Home.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			HomeController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
		}
		
		public void employeeManageScreen( ActionEvent event ) throws Exception{
			
			if( userObj.getType().equalsIgnoreCase("HR Manager") ) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/EmployeeManage.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				EmployeeController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				Generator.getAlert("Access Denied", "You don't have access to Employee Management");
			}
		}
		
		public void customerOrderCartScreen( ActionEvent event ) throws Exception{
			
			if( userObj.getType().equalsIgnoreCase("Salesman") ) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/CustomerOrderCart.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				CustomerOrderController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Customer Orders");
			}
		}
		
		public void supplierManageScreen( ActionEvent event ) throws Exception{
			
			if(userObj.getType().equalsIgnoreCase("Supplier Manager")) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/SupplierManage.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				SupplierController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Supplier Management");
			}
			
		}
		
		public void inventoryManageScreen( ActionEvent event ) throws Exception{
			
			if(userObj.getType().equalsIgnoreCase("Stock Manager"))
			{
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/InventoryManage.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				InventoryController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
				
			}else {
				Generator.getAlert("Access Denied", "You don't have access to Inventory Management");
			}
		
		}
		
		public void customerManageScreen( ActionEvent event ) throws Exception{
			
			if(userObj.getType().equalsIgnoreCase("Salesman")) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/CustomerManage.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				CustomerController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Customer Management");
			}
			
		}
		
		public void financeManageScreen( ActionEvent event ) throws Exception{
			
			if(userObj.getType().equalsIgnoreCase("Accountant")) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/FinanceManage.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				FinanceController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Finance Management");
			}
			
		}
		
		public void itemRepairManageScreen( ActionEvent event ) throws Exception{
			
			if(userObj.getType().equalsIgnoreCase("Stock Manager")) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/ItemRepairManage.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				ItemRepairController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
				
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Item Repair Management");
			}

		}
		
		public void deliveryManageScreen( ActionEvent event ) throws Exception{
			
			if(userObj.getType().equalsIgnoreCase("Transport Manager")) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/DeliveryManage.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				DeliveryController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Delivery Management");
			}
			
		}
		
		public void userManageScreen( ActionEvent event ) throws Exception{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/UserManage.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			UserController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
		}
		//end Screen changing functions
		public void deliveryVehicleManageScreen( ActionEvent event ) throws Exception{

			if(userObj.getType().equalsIgnoreCase("Transport Manager")) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/DeliveryVehicle.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				DeliveryVehicleController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Delivery Management");
			}
			
		}
		
		public void deliveryDetailsReportManageScreen(ActionEvent event) throws Exception{

			if(userObj.getType().equalsIgnoreCase("Transport Manager")) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/DeliveryDetailsReport.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				DeliveryDetailsReportController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Delivery Management");
			}
		}
		
		public void deliverDriverManageScreen(ActionEvent event) throws Exception{

			if(userObj.getType().equalsIgnoreCase("Transport Manager")) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/DeliverDriverManage.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				DeliveryDriverManageController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Delivery Management");
			}
		}
	
	//end Screen changing functions
	
}
