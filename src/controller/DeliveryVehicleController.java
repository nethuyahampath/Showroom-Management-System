package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Delivery;
import model.Main;
import model.User;
import model.Vehicle;
import service.DeliveryServiceImpl;
import service.EmployeeServiceImpl;
import service.IDeliveryService;
import service.IEmployeeService;
import util.Generator;

	public class DeliveryVehicleController implements Initializable {

		@FXML
		private Label userLabel;
		
		private User userObj = new User();
	    
	    public void loadUser(User user) {

	    	this.userObj = user;
	    	
	    	IEmployeeService empService = new EmployeeServiceImpl();
	  
	    	userLabel.setText(empService.loadname(userObj));
	    }
		/*-------------------full vehicle table---------------*/
		
		@FXML private TextField deleteVehicleText;
		
		@FXML private TextField txtbDeliveryehicalId;
		@FXML private ComboBox<String> comVehicleType;
		@FXML private ComboBox<String> comVehicleStatus;
		
		@FXML private TableView<Vehicle> fullVehicle;
		
		
		@FXML private TableColumn<Vehicle, String> vehicleId;
		@FXML private TableColumn<Vehicle, String> vehicleType;
		@FXML private TableColumn<Vehicle, String> vehicleStatus;
		
		
		private ObservableList<Vehicle> fullVehicleList = FXCollections.observableArrayList();
		private ObservableList<String> VehicleTypeList = FXCollections.observableArrayList("Medium Truck","Light Truck","Cargo Van","City Truck","Refrig Truck","Flatded Truck");
		private ObservableList<String> VehicleStatusList = FXCollections.observableArrayList("Pending Delivery","On Delivery","Finish Delivery");
		
	
		//private ObservableList<Vehicle> fullDeliveryList = FXCollections.observableArrayList();;
	
		/*------------------clear vehicle -----------------*/
		
		private void clear(ActionEvent event) {
			
			txtbDeliveryehicalId.setText("");
			comVehicleType.setPromptText("");
			comVehicleStatus.setPromptText("");
			
		}
		
		
		/*---------------show full vehicle-----------------*/
		
		private void showFullVehicle() {
			
			
		
			fullVehicleList.clear();
			
			IDeliveryService iDeliveryService = new DeliveryServiceImpl();
			
			ArrayList<Vehicle> vehicleList = iDeliveryService.getAllVehicleDetails(); 
			
			for(Vehicle vehicle : vehicleList) {
				fullVehicleList.add(vehicle);
			}
			
			fullVehicle.setItems(fullVehicleList);
			
		}
		
		

		/*-------------------initialize--------------------*/
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			txtbDeliveryehicalId.setText(Generator.generateVehicleId());
			showFullVehicle();
			
			vehicleId.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleId"));
			vehicleType.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleType"));
			vehicleStatus.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleStatus"));
			
			comVehicleType.setItems(VehicleTypeList);
			comVehicleStatus.setItems(VehicleStatusList);
			
		}
		
		
		
		/*------------------Validation for Delivery Table------------------*/
		
		public boolean vehicleValidation() {
			
			Pattern pattern_vehicle_id = Pattern.compile("[A-za-z]+[0-9]+");
			Matcher matcher_vehicle_id = pattern_vehicle_id.matcher(txtbDeliveryehicalId.getText());
			
			if(txtbDeliveryehicalId.getText().isEmpty()) {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Please Enter A Vehicle Id ");
				alert.showAndWait();
				return false;
			}
			
			
			if(!matcher_vehicle_id .matches()) {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Enter a valid Vehicle Id");
				alert.showAndWait();
				return false;
			}
			
			
			return true;
			
		}
		
	
	
		/*------------------add vehicle details------------------*/
		
		
		IDeliveryService iDeliveryService = new DeliveryServiceImpl();
		
		public void addVehicleDetails(ActionEvent event) {
			
			if(vehicleValidation()) {
			
			Vehicle vehicle = new Vehicle();
			
			vehicle.setVehicleId(txtbDeliveryehicalId.getText());
			vehicle.setVehicleType(comVehicleType.getSelectionModel().getSelectedItem().toString());
			vehicle.setVehicleStatus(comVehicleStatus.getSelectionModel().getSelectedItem().toString());
			
			iDeliveryService.addVehicleDetails(vehicle);
			
			clear(event);
			showFullVehicle();
			txtbDeliveryehicalId.setText(Generator.generateVehicleId());
			}
			
		}
		
		/*--------------------show on click------------------------*/
		
	public void showOnClick() {
		
		Vehicle vehicle = fullVehicle.getSelectionModel().getSelectedItem();
		
		txtbDeliveryehicalId.setText(vehicle.getVehicleId());
		
		comVehicleType.setValue(fullVehicle.getSelectionModel().getSelectedItem().getVehicleType());
		comVehicleStatus.setValue(fullVehicle.getSelectionModel().getSelectedItem().getVehicleStatus());
		
	}
		
		
		/*--------------------update vehicle details----------------*/
		
		public void updateDeliveryVehicleDetails(ActionEvent event) {
			
			Vehicle vehicle = new Vehicle();
			
			vehicle.setVehicleId(txtbDeliveryehicalId.getText());
			vehicle.setVehicleType(comVehicleType.getValue());
			vehicle.setVehicleStatus(comVehicleStatus.getSelectionModel().getSelectedItem().toString());
			
			
			iDeliveryService.updateDeliveryVehicleDetails(vehicle);
			
			clear(event);
			txtbDeliveryehicalId.setText(Generator.generateVehicleId());
			showFullVehicle();
		}
		
		
		
		/*-------------------delete vehicle details----------------*/
		
		public void deleteDeliveryVehicleDetails(ActionEvent event) {
			
			IDeliveryService iDeliveryService=new DeliveryServiceImpl();
			
			iDeliveryService.deleteDeliveryVehicleDetails(deleteVehicleText.getText());
			System.out.println(deleteVehicleText.getText());
			
			showFullVehicle();
			txtbDeliveryehicalId.setText(Generator.generateVehicleId());
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
}