package controller;

import model.SupplierRequestOrder;
import model.User;
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

import java.awt.Dimension;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.controlsfx.control.textfield.TextFields;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Item;
import model.Main;
import model.Model;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import service.IInventoryServices;
import service.InventoryServicesImpl;
import util.DBconnection;
import util.Generator;





import java.io.File;
import java.util.ArrayList;


import java.time.format.DateTimeFormatterBuilder;
import model.CustomerPayment;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import service.CustomerOrderServiceImpl;
import service.ICustomerOrderService;
import util.DBConnect;
import javafx.stage.DirectoryChooser;








public class InventoryReportController implements Initializable{
	
	private static final String COMPILE_REPORT_PATH = "C:\\Users\\Ganesh\\Desktop\\SMS_REPORT_NEW\\Showroom_Management_System\\src\\report\\";
	
	@FXML
    private TextField txtbSORRselecPath;
	@FXML
    private TextField txtbSORRsaveAs;
	@FXML
    private ComboBox<String> cmbSORRselectYear;
	@FXML
    private ComboBox<String> cmbSORRselectMonth;
	@FXML
    private ComboBox<String> cmbSORRselectDate;
	@FXML
    private DatePicker dpSORRselectDate;
	
	
	
	
	
	
	 public ObservableList<String> yearList =  FXCollections.observableArrayList("All","2018",  "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028");
		
	 public ObservableList<String> monthList =  FXCollections.observableArrayList("All","January",  "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" );

	 
	 @FXML
	    void chooseSupplierOrderRequestReportPath(ActionEvent event) {
	    	
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(null);

			txtbSORRselecPath.setText(Generator.generateFilePathsToJavaFormat(selectedDirectory.getAbsolutePath() + "\\"));
			
	    }
	 
	 
	 
	    /*---------------------------------------------------------------------------------------------------------*/
	    
	    /*------------------------------ Save As name --------------------------------------------------------------*/
	    @FXML
	    void onYearSelectSORR(ActionEvent event) {
	    	
	    	String yearString = cmbSORRselectYear.getSelectionModel().getSelectedItem().toString();
	    	String monthString = "";
	    	System.out.println(yearString);
	    	if( cmbSORRselectMonth.getSelectionModel().getSelectedItem() == null) {
	    		
	    		monthString = "";
	    		txtbSORRsaveAs.setText("SORR-"+yearString);
	    	}else {
	    		monthString = cmbSORRselectMonth.getSelectionModel().getSelectedItem().toString();
	    		txtbSORRsaveAs.setText("SORR-"+yearString+"-"+monthString);
	    	}	
	    	
	    }
	    
	    @FXML
	    void onMonthSelectSORR(ActionEvent event) {
	    		
	    	String yearString = "";
	    	String monthString = cmbSORRselectMonth.getSelectionModel().getSelectedItem().toString();
	    	
	    	if( cmbSORRselectYear.getSelectionModel().getSelectedItem() == null) {
	    		
	    		yearString = "";
	    		txtbSORRsaveAs.setText("SORR-"+monthString);
	    	}else {
	    		yearString = cmbSORRselectYear.getSelectionModel().getSelectedItem().toString();
	    		txtbSORRsaveAs.setText("SORR-"+yearString+"-"+monthString);
	    	}	
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    /*----------------------------------------------------------------------------------------------------------*/
	    
	    /*---------------------------------------- Reports ---------------------------------------------------------*/
	    @FXML
	    public void viewSupplierOrderRequestReport(ActionEvent event) {
	    	
	    	IInventoryServices iSupOrderReq = new InventoryServicesImpl();

	    	if( cmbSORRselectYear.getSelectionModel().getSelectedItem() == null || cmbSORRselectMonth.getSelectionModel().getSelectedItem() == null ) {
	    		
	    		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
	    		return;
	    	}
	    	
	    	
	    	String monthString = cmbSORRselectMonth.getSelectionModel().getSelectedItem().toString();
	    	String yearString = cmbSORRselectYear.getSelectionModel().getSelectedItem().toString();

	    	
	    	if( monthString == "All" && yearString == "All" ) {
	    		int year = 0;
		    	int month = 0;
		    	iSupOrderReq.generateSORRByMonthAndYear(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", month, year, "view", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );
	    	}else
	    		if( monthString == "All"  && yearString != "All" ) {
	        		int year = Integer.parseInt(cmbSORRselectYear.getSelectionModel().getSelectedItem().toString());
	    	    	int month = 0;
	    	    	iSupOrderReq.generateSORRByMonthAndYear(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", month, year, "view", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );
	    		}else
	    			if( monthString != "All" && yearString == "All" ) {
	            		int year = 0;
	        	    	int month = Generator.getMonthIntValue(monthString);
	        	    	iSupOrderReq.generateSORRByMonthAndYear(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", month, year, "view", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );
	    			}else {
	    		    	int year = Integer.parseInt(cmbSORRselectYear.getSelectionModel().getSelectedItem().toString());
	    		    	int month = Generator.getMonthIntValue(monthString);
	    		    	iSupOrderReq.generateSORRByMonthAndYear(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", month, year, "view", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );
	    			}

	    }
	    
	    @FXML
	    public void saveSupplierOrderRequestReport(ActionEvent event) {
	    	
	    	IInventoryServices iSupOrderReq = new InventoryServicesImpl();
	    	
	    	if( txtbSORRsaveAs.getText() == ""  ||  txtbSORRsaveAs.getText() == null ) {
	    		Generator.getAlert("Error in saving order report", "You must enter a file name to save your pdf");
	    		return;
	    	}
	    	
	    	if( cmbSORRselectYear.getSelectionModel().getSelectedItem() == null || cmbSORRselectYear.getSelectionModel().getSelectedItem() == null ) {
	    		
	    		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
	    		return;
	    	}
	    	
	    	String monthString = cmbSORRselectMonth.getSelectionModel().getSelectedItem().toString();
	    	String yearString = cmbSORRselectYear.getSelectionModel().getSelectedItem().toString();
	    	
	       	if( monthString == "All" && yearString == "All" ) {
	    		int year = 0;
		    	int month = 0;
		    	iSupOrderReq.generateSORRByMonthAndYear(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", month, year, "save", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );
	    	}else
	    		if( monthString == "All"  && yearString != "All" ) {
	        		int year = Integer.parseInt(cmbSORRselectYear.getSelectionModel().getSelectedItem().toString());
	    	    	int month = 0;
	    	    	iSupOrderReq.generateSORRByMonthAndYear(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", month, year, "save", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );
	    		}else
	    			if( monthString != "All" && yearString == "All" ) {
	            		int year = 0;
	        	    	int month = Generator.getMonthIntValue(monthString);
	        	    	iSupOrderReq.generateSORRByMonthAndYear(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", month, year, "save", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );
	    			}else {
	    		    	int year = Integer.parseInt(cmbSORRselectYear.getSelectionModel().getSelectedItem().toString());
	    		    	int month = Generator.getMonthIntValue(monthString);
	    		    	iSupOrderReq.generateSORRByMonthAndYear(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", month, year, "save", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );
	    			}
	       	
	       	
	       	Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Report Saved Successfully");
			alert.showAndWait();

	    	
	    }
	    
	    
	    @FXML
	    void onDateSelectSORR(ActionEvent event) {
	    	txtbSORRsaveAs.setText("SORR-"+dpSORRselectDate.getValue().toString());
	    }
	    
	    @FXML
	    public void viewSupplierOrderRequestReportByDate( ActionEvent event ) {
			
	    	IInventoryServices iSupOrderReq = new InventoryServicesImpl();
	    	
	    	if( txtbSORRsaveAs.getText() == ""  ||  txtbSORRsaveAs.getText() == null ) {
	    		Generator.getAlert("Error in saving order report", "You must enter a file name to save your pdf");
	    		return;
	    	}
	    	
	    	if( dpSORRselectDate.getValue()  == null ) {
	    		Generator.getAlert("Error in Date","You cannot keep the date null");
	    		return;
	    	}
	    	
	    	iSupOrderReq.generateSORRByDate(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", dpSORRselectDate.getValue().toString(), "view", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );

	    }
	    
	    @FXML
	    public void saveSupplierOrderRequestReportByDate( ActionEvent event ) {
			
	    	IInventoryServices iSupOrderReq = new InventoryServicesImpl();
	    	
	    	if( txtbSORRsaveAs.getText() == ""  ||  txtbSORRsaveAs.getText() == null ) {
	    		Generator.getAlert("Error in saving order report", "You must enter a file name to save your pdf");
	    		return;
	    	}
	    	
	    	if( dpSORRselectDate.getValue() == null) {
	    		Generator.getAlert("Error in Date","You cannot keep the date null");
	    		return;
	    	}
	    	
	    	iSupOrderReq.generateSORRByDate(COMPILE_REPORT_PATH + "Supplier_Request_Order.jrxml", dpSORRselectDate.getValue().toString(), "save", txtbSORRselecPath.getText()+txtbSORRsaveAs.getText()+".pdf" );
	    	
	    	
	    	
	    	Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Report Saved Successfully");
			alert.showAndWait();

	    }
	   
	    
	    /*----------------------------------------------------------------------------------------------------------*/
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	 
	 
	@FXML private TextField txtbOrderID_Order;
	@FXML private ComboBox<String> cmbCategory_Order;
	@FXML private TextField txtbModel_Order;
	@FXML private TextField txtbQuantity_Order;
	@FXML private ComboBox<String> cmbOrderStatus_Order;
	@FXML private DatePicker dpSupOrderDate;
	
	
	@FXML private TableView<SupplierRequestOrder> tblvOrders_Orders;
	
	@FXML private TableColumn<? , ?> tblcOrderID_Order;
	@FXML private TableColumn<? , ?> tblcCategory_Order;
	@FXML private TableColumn<? , ?> tblcModel_Order;
	@FXML private TableColumn<? , ?> tblcQuantity_Order;
	@FXML private TableColumn<? , ?> tblcOrderStatus_Order;
	@FXML private TableColumn<? , ?> tblcOrderDate_Order;
	
	
	
	
	
	IInventoryServices iInventoryServices=new InventoryServicesImpl();

	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
    
    
    
    
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cmbSORRselectYear.setItems(yearList);
		cmbSORRselectMonth.setItems(monthList);
		txtbSORRselecPath.setText(Generator.generateFilePathsToJavaFormat("C:\\Users\\Ganesh\\Desktop\\Reports\\"));

	}
	
	
	

	
	public void getModelReport(ActionEvent event) {
		
		
		
		
		try {
    		
    		Connection connection = DBconnection.getConnection();
    		
    		//JasperReport jasperReport = JasperCompileManager.compileReport("E:\\SLIIT\\Java\\JasperWithFX\\Blank_A4.jrxml");
    		
    		JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\Ganesh\\Desktop\\SMS_REPORT\\Showroom_Management_System\\src\\report\\Model_Report.jrxml");
    		
    		//get the query
    		String query = "SELECT * FROM model";
    		JRDesignQuery jrQuery = new JRDesignQuery();
    		jrQuery.setText(query);
    		jasperDesign.setQuery(jrQuery);
    		
    		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , null , connection );
    		JRViewer viewer = new JRViewer( jasperPrint );
    		
    		//JRDataSource jrDataSource = new JREmptyDataSource();
    		
    		//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
    		
    		JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\Ganesh\\Desktop\\Reports\\test.pdf" );
    		
    	
    		//to view the jasper report in the pdf viewer -  (can be used for a button to view the report)
    		viewer.setOpaque(true);
    		viewer.setVisible(true);
    		
    		JFrame frame = new JFrame("Jasper report");
            frame.add(viewer);
            frame.setSize(new Dimension(500, 400));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            
            
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Report Saved Successfully");
			alert.showAndWait();
    		
    	}catch(Exception e) {
    		System.out.println("Exception : " + e );
    	}
	}
	
	
	
	
	
	
	
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
		
		
		Parent view = FXMLLoader.load(getClass().getResource("/view/UserManage.fxml"));
		Scene scene = new Scene(view);
		scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
		Stage window = Main.getStageObj();
		
		window.setScene(scene);
		window.show();
		window.centerOnScreen();
	}
	//end Screen changing functions

	
	public void inventoryManageItemScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager"))
		{
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/InventoryItems.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			InventoryManageItemsController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			Generator.getAlert("Access Denied", "You don't have access to Inventory Management");
		}
	
	}
	
	
	public void inventoryOrderRequestsScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager"))
		{
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/InventoryOrders.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			InventoryOrdersController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			Generator.getAlert("Access Denied", "You don't have access to Inventory Management");
		}
	
	}
	
	public void inventoryNotificationsScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager"))
		{
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/InventoryNotification.fxml"));
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
	
	public void inventoryReportsScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager"))
		{
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/InventoryReport.fxml"));
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
	
}
