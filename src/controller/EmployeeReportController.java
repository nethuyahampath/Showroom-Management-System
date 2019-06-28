package controller;

import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Main;
import model.User;
import service.CustomerOrderServiceImpl;
import service.EmployeeServiceImpl;
import service.ICustomerOrderService;
import service.IEmployeeService;
import util.Generator;

public class EmployeeReportController implements Initializable{
	
	
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	Connection connection;
	
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
    private TextField barcodeReportPath;

    @FXML
    private TextField barcodeReportSaveAs;
    
    @FXML
    private TextField customerOrderReportPath;
    
    @FXML
    private TextField customerOrderReportSaveAsText;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		customerOrderReportSelectYear.setItems(yearList);
		customerOrderReportSelectMonth.setItems(monthList);
		//default file barcode report file path
		//barcodeReportPath.setText(Generator.generateFilePathsToJavaFormat("E:\\SLIIT\\Y2_SEM 2\\ITP\\Reports\\Bar_Code_Reports\\"));
		customerOrderReportPath.setText(Generator.generateFilePathsToJavaFormat("C:\\Users\\User\\Desktop\\ITP"));
		
	}
    
    
    /*------------------selections--------------------------------------------------*/
    @FXML
    private ComboBox<String> customerOrderReportSelectYear;

    @FXML
    private ComboBox<String> customerOrderReportSelectMonth;
    
    public ObservableList<String> yearList =  FXCollections.observableArrayList("All","2018",  "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028");
	
    public ObservableList<String> monthList =  FXCollections.observableArrayList("All","January",  "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" );
    /*---------------------------------------------------------------------------*/
    
    @FXML
    void generateItemBarCodePDF(ActionEvent event) {
    	
    	String path = barcodeReportPath.getText() +  barcodeReportSaveAs.getText() + ".pdf";
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
    	
    	Generator.generateBarcodePdf(path, empService.getEmpAttendenceList());
    }    
    
    /*--------------------------- Select Report Save paths ---------------------------------------------------*/
    @FXML
    void chooseBarcodeReportPath(ActionEvent event) {
    	
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);

		barcodeReportPath.setText(Generator.generateFilePathsToJavaFormat(selectedDirectory.getAbsolutePath() + "\\"));
		
    }
    
    @FXML
    void chooseCustomerOrderReportPath(ActionEvent event) {
    	
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);

		customerOrderReportPath.setText(Generator.generateFilePathsToJavaFormat(selectedDirectory.getAbsolutePath() + "\\"));
		
    }
    
    /*---------------------------------------------------------------------------------------------------------*/
    
    /*------------------------------ Save As name --------------------------------------------------------------*/
    @FXML
    void onYearSelectCOR(ActionEvent event) {
    	
    	String yearString = customerOrderReportSelectYear.getSelectionModel().getSelectedItem().toString();
    	String monthString = "";
    	
    	if( customerOrderReportSelectMonth.getSelectionModel().getSelectedItem() == null) {
    		
    		monthString = "";
    	 	customerOrderReportSaveAsText.setText("COR-"+yearString);
    	}else {
    		monthString = customerOrderReportSelectMonth.getSelectionModel().getSelectedItem().toString();
    	 	customerOrderReportSaveAsText.setText("COR-"+yearString+"-"+monthString);
    	}	
    	
    }
    
    @FXML
    void onMonthSelectCOR(ActionEvent event) {
    		
    	String yearString = "";
    	String monthString = customerOrderReportSelectMonth.getSelectionModel().getSelectedItem().toString();
    	
    	if( customerOrderReportSelectYear.getSelectionModel().getSelectedItem() == null) {
    		
    		yearString = "";
    	 	customerOrderReportSaveAsText.setText("COR-"+monthString);
    	}else {
    		yearString = customerOrderReportSelectYear.getSelectionModel().getSelectedItem().toString();
    	 	customerOrderReportSaveAsText.setText("COR-"+yearString+"-"+monthString);
    	}	
    }
    
    
    /*----------------------------------------------------------------------------------------------------------*/
    
    /*---------------------------------------- Reports ---------------------------------------------------------*/
    @FXML
    public void viewCustomerOrderReport(ActionEvent event) {
    	
    	IEmployeeService icustOrder = new EmployeeServiceImpl();

    	if( customerOrderReportSelectYear.getSelectionModel().getSelectedItem() == null || customerOrderReportSelectYear.getSelectionModel().getSelectedItem() == null ) {
    		
    		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
    		return;
    	}
    	
    	
    	String monthString = customerOrderReportSelectMonth.getSelectionModel().getSelectedItem().toString();
    	String yearString = customerOrderReportSelectYear.getSelectionModel().getSelectedItem().toString();

    	
    	if( monthString == "All" && yearString == "All" ) {
    		int year = 0;
	    	int month = 0;
	    	icustOrder.generateEAReportByMonthAndYear(COMPILE_REPORT_PATH + "Employee_Attenedance_Report.jrxml", month, year, "view", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );
    	}else
    		if( monthString == "All"  && yearString != "All" ) {
        		int year = Integer.parseInt(customerOrderReportSelectYear.getSelectionModel().getSelectedItem().toString());
    	    	int month = 0;
    	    	icustOrder.generateEAReportByMonthAndYear(COMPILE_REPORT_PATH + "Employee_Attenedance_Report.jrxml", month, year, "view", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );
    		}else
    			if( monthString != "All" && yearString == "All" ) {
            		int year = 0;
        	    	int month = Generator.getMonthIntValue(monthString);
        	    	icustOrder.generateEAReportByMonthAndYear(COMPILE_REPORT_PATH + "Employee_Attenedance_Report.jrxml", month, year, "view", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );
    			}else {
    		    	int year = Integer.parseInt(customerOrderReportSelectYear.getSelectionModel().getSelectedItem().toString());
    		    	int month = Generator.getMonthIntValue(monthString);
    		    	icustOrder.generateEAReportByMonthAndYear(COMPILE_REPORT_PATH + "Employee_Attenedance_Report.jrxml", month, year, "view", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );
    			}

    }
    
    @FXML
    public void saveCustomerOrderReport(ActionEvent event) {
    	
    	IEmployeeService icustOrder = new EmployeeServiceImpl();
    	
    	if( customerOrderReportSaveAsText.getText() == ""  ||  customerOrderReportSaveAsText.getText() == null ) {
    		Generator.getAlert("Error in saving order report", "You must enter a file name to save your pdf");
    		return;
    	}
    	
    	if( customerOrderReportSelectYear.getSelectionModel().getSelectedItem() == null || customerOrderReportSelectYear.getSelectionModel().getSelectedItem() == null ) {
    		
    		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
    		return;
    	}
    	
    	String monthString = customerOrderReportSelectMonth.getSelectionModel().getSelectedItem().toString();
    	String yearString = customerOrderReportSelectYear.getSelectionModel().getSelectedItem().toString();
    	
       	if( monthString == "All" && yearString == "All" ) {
    		int year = 0;
	    	int month = 0;
	    	icustOrder.generateEAReportByMonthAndYear(COMPILE_REPORT_PATH + "Employee_Attenedance_Report.jrxml", month, year, "save", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );
    	}else
    		if( monthString == "All"  && yearString != "All" ) {
        		int year = Integer.parseInt(customerOrderReportSelectYear.getSelectionModel().getSelectedItem().toString());
    	    	int month = 0;
    	    	icustOrder.generateEAReportByMonthAndYear(COMPILE_REPORT_PATH + "Employee_Attenedance_Report.jrxml", month, year, "save", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );
    		}else
    			if( monthString != "All" && yearString == "All" ) {
            		int year = 0;
        	    	int month = Generator.getMonthIntValue(monthString);
        	    	icustOrder.generateEAReportByMonthAndYear(COMPILE_REPORT_PATH + "Employee_Attenedance_Report.jrxml", month, year, "save", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );
    			}else {
    		    	int year = Integer.parseInt(customerOrderReportSelectYear.getSelectionModel().getSelectedItem().toString());
    		    	int month = Generator.getMonthIntValue(monthString);
    		    	icustOrder.generateEAReportByMonthAndYear(COMPILE_REPORT_PATH + "Employee_Attenedance_Report.jrxml", month, year, "save", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );
    			}
    	
    }
   
    
    /*----------------------------------------------------------------------------------------------------------*/

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
		Parent view = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
		Scene scene = new Scene(view);
		scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
		Stage window = Main.getStageObj();
		
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
	

    public void empAttendenceScreen(ActionEvent event) throws Exception {
		
    	if( userObj.getType().equalsIgnoreCase("HR Manager") ) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/EmployeeAttendence.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			EmployeeAttendenceController controller = loader.getController();
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

	  @FXML
	  void empRecordScreen(ActionEvent event) throws Exception {
		  
			if(userObj.getType().equalsIgnoreCase("HR Manager")) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/EmployeeAttendenceReport.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				EmployeeReportController controller = loader.getController();
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
