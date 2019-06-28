package controller;

import model.Main;

import model.User;
//import model.repairOrderFormModel;
import model.supplier;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import service.ISupplierService;
import service.SupplierImpl;
import util.sqlConnection;
import javafx.scene.control.TextArea;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Generator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SupplierDetailsReportController implements Initializable{
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
	    private TextField supplierOrderReportPath;
	    
	 @FXML
	    private TextField supplierOrderReportSaveAsText;
	
	 /*------------------selections--------------------------------------------------*/
	    @FXML
	    private ComboBox<String> supplierOrderReportSelectYear;

	    @FXML
	    private ComboBox<String> supplierOrderReportSelectMonth;
	    
	    public ObservableList<String> yearList =  FXCollections.observableArrayList("All","2018",  "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028");
		
	    public ObservableList<String> monthList =  FXCollections.observableArrayList("All","January",  "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" );
	    /*---------------------------------------------------------------------------*/
	
	    /*--------------------------- Select Report Save paths ---------------------------------------------------*/
	   
	     @FXML
	    void chooseSupplierOrderReportPath(ActionEvent event) {
	    	
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(null);

			supplierOrderReportPath.setText(Generator.generateFilePathsToJavaFormat(selectedDirectory.getAbsolutePath() + "\\"));
			
	    }
	    
	     @FXML
	     void onYearSelectSOR(ActionEvent event) {
	     	
	     	String yearString = supplierOrderReportSelectYear.getSelectionModel().getSelectedItem().toString();
	     	String monthString = "";
	     	
	     	if( supplierOrderReportSelectMonth.getSelectionModel().getSelectedItem() == null) {
	     		
	     		monthString = "";
	     	 	supplierOrderReportSaveAsText.setText("SOR-"+yearString);
	     	}else {
	     		monthString = supplierOrderReportSelectMonth.getSelectionModel().getSelectedItem().toString();
	     	 	supplierOrderReportSaveAsText.setText("SOR-"+yearString+"-"+monthString);
	     	}	
	     	
	     }
	     
	     @FXML
	     void onMonthSelectSOR(ActionEvent event) {
	     		
	     	String yearString = "";
	     	String monthString =  supplierOrderReportSelectMonth.getSelectionModel().getSelectedItem().toString();
	     	
	     	if( supplierOrderReportSelectYear.getSelectionModel().getSelectedItem() == null) {
	     		
	     		yearString = "";
	     	 	supplierOrderReportSaveAsText.setText("SOR-"+monthString);
	     	}else {
	     		yearString = supplierOrderReportSelectYear.getSelectionModel().getSelectedItem().toString();
	     	 	supplierOrderReportSaveAsText.setText("SOR-"+yearString+"-"+monthString);
	     	}	
	     }
	     
	     
	     /*----------------------------------------------------------------------------------------------------------*/
	     
	     /*---------------------------------------- Reports ---------------------------------*/
	     
	     @FXML
	     public void viewSupplierOrderReport(ActionEvent event) {
	     	
	     	ISupplierService isupOrder = new SupplierImpl();

	     	if( supplierOrderReportSelectYear.getSelectionModel().getSelectedItem() == null || supplierOrderReportSelectMonth.getSelectionModel().getSelectedItem() == null ) {
	     		
	     		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
	     		return;
	     	}
	     	
	     	
	     	String monthString = supplierOrderReportSelectMonth.getSelectionModel().getSelectedItem().toString();
	     	String yearString = supplierOrderReportSelectYear.getSelectionModel().getSelectedItem().toString();

	     	
	     	if( monthString == "All" && yearString == "All" ) {
	     		int year = 0;
	     		int month = 0;
	     		isupOrder.generateFullSupplierOrderReport(COMPILE_REPORT_PATH + "SupplierOrder.jrxml", "view",  supplierOrderReportPath.getText()+supplierOrderReportSaveAsText.getText()+".pdf");
	     	}else
	     		if( monthString == "All"  && yearString != "All" ) {
	     			int year = Integer.parseInt(supplierOrderReportSelectYear.getSelectionModel().getSelectedItem().toString());
	     			int month = 0;
	     			isupOrder.generateSupplierOrderReportByMonthAndYear(COMPILE_REPORT_PATH + "SupplierOrder.jrxml", month, year, "view", supplierOrderReportPath.getText()+supplierOrderReportSaveAsText.getText()+".pdf");
	     		}else
	     			if( monthString != "All" && yearString == "All" ) {
	     				int year = 0;
	        	    	int month = Generator.getMonthIntValue(monthString);
	        	    	isupOrder.generateSupplierOrderReportByMonthAndYear(COMPILE_REPORT_PATH + "SupplierOrder.jrxml", month, year, "view", supplierOrderReportPath.getText()+supplierOrderReportSaveAsText.getText()+".pdf" );
	     			}else {
	     		    	int year = Integer.parseInt(supplierOrderReportSelectYear.getSelectionModel().getSelectedItem().toString());
	     		    	int month = Generator.getMonthIntValue(monthString);
	     		    	isupOrder.generateSupplierOrderReportByMonthAndYear(COMPILE_REPORT_PATH + "SupplierOrder.jrxml", month, year, "view", supplierOrderReportPath.getText()+supplierOrderReportSaveAsText.getText()+".pdf" );
	     			}

	     }
	     
	     @FXML
	     public void saveSupplierOrderReport(ActionEvent event) {
	     	
	     	ISupplierService isupOrder = new SupplierImpl();
	     	
	     	if( supplierOrderReportSaveAsText.getText() == ""  ||  supplierOrderReportSaveAsText.getText() == null ) {
	     		Generator.getAlert("Error in saving order report", "You must enter a file name to save your pdf");
	     		return;
	     	}
	     	
	     	if( supplierOrderReportSelectYear.getSelectionModel().getSelectedItem() == null || supplierOrderReportSelectYear.getSelectionModel().getSelectedItem() == null ) {
	     		
	     		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
	     		return;
	     	}
	     	
	     	String monthString = supplierOrderReportSelectMonth.getSelectionModel().getSelectedItem().toString();
	     	String yearString = supplierOrderReportSelectYear.getSelectionModel().getSelectedItem().toString();

	     	
	     	if( monthString == "All" && yearString == "All" ) {
	     		int year = 0;
	     		int month = 0;
	     		isupOrder.generateFullSupplierOrderReport(COMPILE_REPORT_PATH + "SupplierOrder.jrxml", "save",  supplierOrderReportPath.getText()+supplierOrderReportSaveAsText.getText()+".pdf");
	     	}else
	     		if( monthString == "All"  && yearString != "All" ) {
	     			int year = Integer.parseInt(supplierOrderReportSelectYear.getSelectionModel().getSelectedItem().toString());
		     		int month = 0;
		     		isupOrder.generateSupplierOrderReportByMonthAndYear(COMPILE_REPORT_PATH + "SupplierOrder.jrxml", month, year, "view", supplierOrderReportPath.getText()+supplierOrderReportSaveAsText.getText()+".pdf");
	     		}else
	     			if( monthString != "All" && yearString == "All" ) {
	     				int year = 0;
	        	    	int month = Generator.getMonthIntValue(monthString);
	        	    	isupOrder.generateSupplierOrderReportByMonthAndYear(COMPILE_REPORT_PATH + "SupplierOrder.jrxml", month, year, "view", supplierOrderReportPath.getText()+supplierOrderReportSaveAsText.getText()+".pdf" );
	     			}else {
	     		    	int year = Integer.parseInt(supplierOrderReportSelectYear.getSelectionModel().getSelectedItem().toString());
	     		    	int month = Generator.getMonthIntValue(monthString);
	     		    	isupOrder.generateSupplierOrderReportByMonthAndYear(COMPILE_REPORT_PATH + "SupplierOrder.jrxml", month, year, "save", supplierOrderReportPath.getText()+supplierOrderReportSaveAsText.getText()+".pdf" );
	     			}
	     	
	     }
	     
	     @FXML
	     public void viewSupplierOrderReportByDate( ActionEvent event ) {
	 		
	     	ISupplierService isupOrder = new SupplierImpl();
	     	
	     	if( supplierOrderReportSaveAsText.getText() == ""  ||  supplierOrderReportSaveAsText.getText() == null ) {
	     		Generator.getAlert("Error in saving order report", "You must enter a file name to save your pdf");
	     		return;
	     	}
	     	
	     	//if( supplierOrderDate.getValue()  == null ) {
	     		//Generator.getAlert("Error in Date","You cannot keep the date null");
	     	//	return;
	     	//}
	     	
	     	//isupOrder.generateSupplierOrderReportByDate(COMPILE_REPORT_PATH + "Customer_Order.jrxml", customerOrderDate.getValue().toString(), "view", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );

	     }
	     
	     @FXML
	     public void saveCustomerOrderReportByDate( ActionEvent event ) {
	 		
	     	ISupplierService isupOrder = new SupplierImpl();
	     	
	     	if( supplierOrderReportSaveAsText.getText() == ""  ||  supplierOrderReportSaveAsText.getText() == null ) {
	     		Generator.getAlert("Error in saving order report", "You must enter a file name to save your pdf");
	     		return;
	     	}
	     	
	     	//if( customerOrderDate.getValue() == null) {
	     		//Generator.getAlert("Error in Date","You cannot keep the date null");
	     	//	return;
	     //	}
	     	
	     //	icustOrder.generateCOReportByDate(COMPILE_REPORT_PATH + "Customer_Order.jrxml", customerOrderDate.getValue().toString(), "save", customerOrderReportPath.getText()+customerOrderReportSaveAsText.getText()+".pdf" );

	     }
	    
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
	     /*----------------------------------------------------------------------------------------------------------*/

	     @Override
	 	public void initialize(URL arg0, ResourceBundle arg1){

	 		supplierOrderReportSelectYear.setItems(yearList);
	 		supplierOrderReportSelectMonth.setItems(monthList);
	 		//default file barcode report file path
	 		//barcodeReportPath.setText(Generator.generateFilePathsToJavaFormat("E:\\SLIIT\\Y2_SEM 2\\ITP\\Reports\\Bar_Code_Reports\\"));
	 		supplierOrderReportPath.setText(Generator.generateFilePathsToJavaFormat("C:\\Users\\DELL\\Music\\ITP\\reports\\supplier_order"));
	 		
	 	}
	 	
	     
	    /*---------------------------------------------------------------------------------------------------------*/
	    /*------------------------------ Save As name --------------------------------------*/
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
	
	public void supplierPaymentScreen( ActionEvent event ) throws Exception{
		Parent view = FXMLLoader.load(getClass().getResource("/view/SupplierPayment.fxml"));

			
					if(userObj.getType().equalsIgnoreCase("Supplier Manager")) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SupplierPayment.fxml"));
						Parent parent = loader.load();
						
						Scene scene =  new Scene(parent);
						scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
						
						SupplierPaymentController controller = loader.getController();
						controller.loadUser(userObj);

						Stage window = Main.getStageObj();
			 
						window.setScene(scene);
						window.show();
						window.centerOnScreen();
					}else {
						
						Generator.getAlert("Access Denied", "You don't have access to Supplier Management");
					
				}
		}
	
	public void supplierOrderScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Supplier Manager")) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/SupplierOrder.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				SupplierOrderController controller = loader.getController();
				controller.loadUser(userObj);
		
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Supplier Management");
			
			}
		}
	
	
	
	//end Screen changing functions
	

}