package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Main;
import model.User;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import util.Generator;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Attendence;
import model.Delivery;
import model.DriverManage;
import model.Main;
import service.DeliveryServiceImpl;
import service.IDeliveryService;
import util.Generator;

public class DeliveryDriverManageController implements Initializable{
	
	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
    
	
	Connection conn;
	PreparedStatement preparedStatment = null;
	ResultSet rs = null;
	ObservableList<Attendence> fullAttendenceList = FXCollections.observableArrayList();
	ObservableList<DriverManage> fullDeliveryManageList = FXCollections.observableArrayList();


	
	@FXML private TextField deliveryIDtxt;
	@FXML private TextField EmpIDtxt;
	
	@FXML private TableView<Attendence> fullAttendence;
	@FXML private TableColumn<Attendence, String> designation;
	@FXML private TableColumn<Attendence, String> empID;
	
	
	@FXML private TableView<DriverManage> fullDeliveryManage;
	@FXML private TableColumn<DriverManage, String> deliveryID;
	@FXML private TableColumn<DriverManage, String> employeeID;
	
	
/*-----------------------------------------------------------------------------------*/
	
	

	/*-------------------show full Attendence ---------------------- */
	public void showFullAttendence() {
		
		
		fullAttendenceList.clear();
		
		
		IDeliveryService iDeliveryService=new DeliveryServiceImpl();
		
		ArrayList<Attendence> attendenceList = iDeliveryService.getEmpAttendanceDetails();
		
		for( Attendence attendence : attendenceList ) {
			fullAttendenceList.add(attendence);
		} //end for
		
		fullAttendence.setItems(fullAttendenceList);
	}
	
	/*-------------------show full Driver Manage ---------------------- */
	public void showFullDriverManage() {
		
		
		fullDeliveryManageList.clear();
		
		
		IDeliveryService iDeliveryService=new DeliveryServiceImpl();
		
		ArrayList<DriverManage> deliveryManageList = iDeliveryService.getDriverManageDetails();
		
		for( DriverManage DriverManage : deliveryManageList ) {
			fullDeliveryManageList.add(DriverManage);
		} //end for
		
		fullDeliveryManage.setItems(fullDeliveryManageList);
	}
	
	

	/*---------------------------INITIALIZE-------------------------------*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		showFullAttendence();
		
		designation.setCellValueFactory(new PropertyValueFactory<Attendence, String>("designation"));
		empID.setCellValueFactory(new PropertyValueFactory<Attendence, String>("empID"));
		
		showFullDriverManage();
		
		deliveryID.setCellValueFactory(new PropertyValueFactory<DriverManage, String>("delivery_id"));
		employeeID.setCellValueFactory(new PropertyValueFactory<DriverManage, String>("emp_id"));
		
	}
	
	
/*--------------Add Attendence-----------------------*/
	
	
	IDeliveryService iDeliveryService=new DeliveryServiceImpl();
	
	
	public void addDriverDetails(ActionEvent event) {
		
	//	if (deliveryValidation()) {
	
		DriverManage driverManage = new DriverManage();
		
		driverManage.setDelivery_id(deliveryIDtxt.getText());
		driverManage.setEmp_id(EmpIDtxt.getText());
		
		iDeliveryService.addDriverDetails(driverManage);
		
		clear(event);
		showFullDriverManage();
		
		}
	//}
	
	
/*-------------------clear Driver Details------------------------*/
	
	private void clear(ActionEvent event) {
		deliveryIDtxt.setText("");
		EmpIDtxt.setText("");
		
	}
	
	
	/*--------------------------SHOW ON CLICK ATTENDANCE--------------------------------------*/
	@FXML
	private void showOnClickAttendance() {
		Attendence attendance = fullAttendence.getSelectionModel().getSelectedItem();
		
		EmpIDtxt.setText(attendance.getEmpID());
	}
	
	/*--------------------------SHOW ON CLICK ATTENDANCE--------------------------------------*/
	@FXML
	private void showOnClickDeliveryDetails() {
		DriverManage driverManage = fullDeliveryManage.getSelectionModel().getSelectedItem();
		
		deliveryIDtxt.setText(driverManage.getDelivery_id());
		EmpIDtxt.setText(driverManage.getEmp_id());
	}


	/*-----------------delete Driver Details-------------*/
	
	public void deleteDriverDetails(ActionEvent event) throws Exception{
		
		IDeliveryService iDeliveryService=new DeliveryServiceImpl();
		
		iDeliveryService.deleteDriverManage(deliveryIDtxt.getText());
		System.out.println(deliveryIDtxt.getText());
		
		showFullDriverManage();
		clear(event);
		
	}
	
/*-----------------update Delivery Details-------------*/
	
	public void updateDeliveryDetails(ActionEvent event) {
		
		DriverManage driverManage = new DriverManage();
		
		driverManage.setDelivery_id(deliveryIDtxt.getText());
		driverManage.setEmp_id(EmpIDtxt.getText());
				
		iDeliveryService.updateDriverManage(driverManage);
		
		clear(event);
		showFullDriverManage();
		
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
	
	public void deliveryDriverManageScreen(ActionEvent event) throws Exception{

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
