package controller;

import model.Cart;
import model.CustomerOrder;
import model.Delivery;
import model.Main;
import model.Order;
import model.User;
import service.IDeliveryService;
import service.IEmployeeService;
import util.Generator;
import service.CustomerOrderServiceImpl;
import service.DeliveryServiceImpl;
import service.EmployeeServiceImpl;
import service.ICustomerOrderService;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import java.util.function.Predicate;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.textfield.TextFields;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

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
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DeliveryController implements Initializable{
	
	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
	
	/*--------------full delivery table---------------*/
	
	@FXML private TextField txtbDeliveryID;
	@FXML private TextField txtbDeliveryNo;
	@FXML private TextField txtbDeliveryStreet;
	@FXML private TextField txtbDeliveryCity;
	@FXML private TextField txtbDeliveryehicalId;
	@FXML private TextField deleteDeliveryText;
	@FXML private DatePicker txtbDeliveryDate;

	@FXML private TableView<Delivery> fullDelivery;
	@FXML private TableColumn<Delivery, String> deliveryId;
	@FXML private TableColumn<Delivery, Integer> no;
	@FXML private TableColumn<Delivery, String> street;
	@FXML private TableColumn<Delivery, String> city;
	@FXML private TableColumn<Delivery, String> deliveryDate;
	@FXML private TableColumn<Delivery, String> vehicalId;
	@FXML private TableColumn<Delivery, String> orderIDDel;
	@FXML private TextField delSearch;
	
	/*-----------------full customer order table----------------------*/
	
	@FXML private TableView<CustomerOrder> fullCustomerOrder;
	@FXML private TableColumn<CustomerOrder, String> orderId;
	@FXML private TableColumn<CustomerOrder, String> orderDate;
	@FXML private TableColumn<CustomerOrder, String> deliveryStatus;
	
	@FXML private TextField txtOrderId;
	
	
	private ObservableList<Delivery> fullDeliveryList = FXCollections.observableArrayList();;
	private ObservableList<CustomerOrder> fullCustomerOrderList = FXCollections.observableArrayList();;
	
	
	private FilteredList<Delivery> filter = new FilteredList(fullDeliveryList, e->true);
	
	/*----------------------------------------------------------------------------------------------------------------*/
	

	
	/*-------------------show full delivery ---------------------- */
	public void showFullDelivery() {
		
		
		fullDeliveryList.clear();
		
		
		IDeliveryService iDeliveryService=new DeliveryServiceImpl();
		
		ArrayList<Delivery> deliveryList = iDeliveryService.getAllDeliveryDetails();
		
		for( Delivery delivery : deliveryList ) {
			fullDeliveryList.add(delivery);
		} //end for
		
		fullDelivery.setItems(fullDeliveryList);
	}
	
	
	/*----------------show full customer order--------------------*/
	
	public void showFullCustomerOrder() {
		
		fullCustomerOrderList.clear();
		
		ICustomerOrderService iCustomerOrderService = new CustomerOrderServiceImpl();
		
		ArrayList<CustomerOrder> customerOrderList = iCustomerOrderService.getCustomerOrderDetails();
		
		
		for(CustomerOrder customerOrder : customerOrderList ) {
			fullCustomerOrderList.add(customerOrder);
		}
		
		fullCustomerOrder.setItems(fullCustomerOrderList);
		
	}
	

	/*------------------initialize------------------*/
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		txtbDeliveryID.setText(Generator.generateDeliveryID());
		showFullDelivery();
		
		deliveryId.setCellValueFactory(new PropertyValueFactory<Delivery, String>("deliveryId"));
		no.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("no"));
		street.setCellValueFactory(new PropertyValueFactory<Delivery, String>("street"));
		city.setCellValueFactory(new PropertyValueFactory<Delivery, String>("city"));
		deliveryDate.setCellValueFactory(new PropertyValueFactory<Delivery, String>("deliveryDate"));
		vehicalId.setCellValueFactory(new PropertyValueFactory<Delivery, String>("vehicalId"));
		orderIDDel.setCellValueFactory(new PropertyValueFactory<Delivery, String>("orderID"));
		fullDelivery.setItems(fullDeliveryList);
		 
		showFullCustomerOrder();
		orderId.setCellValueFactory(new PropertyValueFactory<CustomerOrder, String>("OID"));
		orderDate.setCellValueFactory(new PropertyValueFactory<CustomerOrder, String>("Odate"));
		deliveryStatus.setCellValueFactory(new PropertyValueFactory<CustomerOrder, String>("deliveryStatus"));
		fullCustomerOrder.setItems(fullCustomerOrderList);
		
		

	}
	
	
	/*-------------------Search Delivery ID-----------------------------*/
	
	@FXML
	private void search(javafx.scene.input.KeyEvent event) {
		
		delSearch.textProperty().addListener((observable, oldValue, newValue) ->{
			
			filter.setPredicate((Predicate<? super Delivery>) (Delivery oList)->{
				
				if(newValue.isEmpty() || newValue == null) {
					return true;
					
				}else if(oList.getDeliveryId().contains(newValue)){
					
					return true;
					
				}
				
				return false;
			});
			
		});
		
		SortedList sort = new SortedList(filter);
		sort.comparatorProperty().bind(fullDelivery.comparatorProperty());
		fullDelivery.setItems(sort);
	}
	
	
	/*------------------Validation for Delivery Table------------------*/
	
	public boolean deliveryValidation() {
		
		Pattern pattern_delivery_id = Pattern.compile("[A-za-z]+[0-9]+");
		Matcher matcher_delivery_id = pattern_delivery_id.matcher(txtbDeliveryID.getText());
		
		if(txtbDeliveryID.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Delivery Id ");
			alert.showAndWait();
			return false;
		}
		
		
		if(!matcher_delivery_id .matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Enter a valid delivery id");
			alert.showAndWait();
			return false;
		}
		
		

		Pattern pattern_no = Pattern.compile("[0-9]+");
		Matcher matcher_no = pattern_no.matcher(txtbDeliveryNo.getText());
		
		
		if(txtbDeliveryNo.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Delivery Id ");
			alert.showAndWait();
			return false;
		}
		
		
		if(!matcher_no .matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Enter a valid delivery no");
			alert.showAndWait();
			return false;
		}
		
		Pattern pattern_street = Pattern.compile("[A-Z a-z]+");
		Pattern pattern_city = Pattern.compile("[A-Z a-z]+");
		Matcher matcher_street = pattern_street.matcher(txtbDeliveryStreet.getText());
		Matcher matcher_city = pattern_city.matcher(txtbDeliveryCity.getText());
		
		if(txtbDeliveryStreet.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Delivery Street ");
			alert.showAndWait();
			return false;
		}
		
		
		if(!matcher_street .matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Enter a valid delivery Street");
			alert.showAndWait();
			return false;
		}
		
		
		if(txtbDeliveryCity.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Delivery City ");
			alert.showAndWait();
			return false;
		}
		
		
		if(!matcher_city .matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Enter a valid delivery City");
			alert.showAndWait();
			return false;
		}
		
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
			alert.setContentText("Enter a valid Vehicle id");
			alert.showAndWait();
			return false;
		}
		
		
		
		return true;
	}
	
	
	
	
	
/*--------------Add Delivery Details-----------------------*/
	
	
	IDeliveryService iDeliveryService=new DeliveryServiceImpl();
	
	
	public void AddDelivery(ActionEvent event) {
		
		if (deliveryValidation()) {
	
		Delivery delivery = new Delivery();
		
		delivery.setDeliveryId(txtbDeliveryID.getText());
		delivery.setNo(Integer.parseInt(txtbDeliveryNo.getText())); 
		delivery.setStreet(txtbDeliveryStreet.getText());
		delivery.setCity(txtbDeliveryCity.getText());
		delivery.setDeliveryDate((txtbDeliveryDate.getValue()).toString());
		delivery.setVehicalId(txtbDeliveryehicalId.getText());
		delivery.setOrderID(txtOrderId.getText());
		
		
		iDeliveryService.addDeliveryDetails(delivery);
		
		clear(event);
		showFullDelivery();
		txtbDeliveryID.setText(Generator.generateDeliveryID());
		}
	}
	
	/*-------------------clear delivery------------------------*/
	
	private void clear(ActionEvent event) {
		txtbDeliveryID.setText("");
		txtbDeliveryNo.setText("");
		txtbDeliveryStreet.setText("");
		txtbDeliveryCity.setText("");
		txtbDeliveryDate.setValue(null);
		txtbDeliveryehicalId.setText("");
		
	}
	
	
	/*-----------------show on click-----------------------*/
	
	public void showOnClick() {
		
		Delivery delivery = fullDelivery.getSelectionModel().getSelectedItem();
		
		txtbDeliveryID.setText(delivery.getDeliveryId());
		txtbDeliveryNo.setText(Integer.toString(delivery.getNo()));
		txtbDeliveryStreet.setText(delivery.getStreet());
		txtbDeliveryCity.setText(delivery.getCity());
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		
		String date = delivery.getDeliveryDate();
		
		//convert String to LocalDate
		LocalDate localDate = LocalDate.parse(date, formatter);
		
		txtbDeliveryDate.setValue(localDate);
		txtbDeliveryehicalId.setText(delivery.getVehicalId());
			
	}

	public void showOnClickCustomerOrder() {
		
		Order order = fullCustomerOrder.getSelectionModel().getSelectedItem();
		
		
		txtOrderId.setText(order.getOID());
	}
	
	/*-------------------------demo button------------------------*/
	
	public void demo() {
		
		txtbDeliveryID.setText("D667");
		txtbDeliveryNo.setText("85");
		txtbDeliveryStreet.setText("Ganemulla");
		txtbDeliveryCity.setText("Kadawatha");
		txtbDeliveryDate.setValue(null);
		txtbDeliveryehicalId.setText("V111");
		
	}
	
	
	/*-----------------update Delivery Details-------------*/
	
	public void updateDeliveryDetails(ActionEvent event) {
		
		Delivery delivery = new Delivery();
		
		delivery.setDeliveryId(txtbDeliveryID.getText());
		delivery.setNo(Integer.parseInt(txtbDeliveryNo.getText())); 
		delivery.setStreet(txtbDeliveryStreet.getText());
		delivery.setCity(txtbDeliveryCity.getText());
		delivery.setDeliveryDate((txtbDeliveryDate.getValue()).toString());
		delivery.setVehicalId(null);
		
		
		
		
		iDeliveryService.updateDeliveryDetails(delivery);
		
		clear(event);
		showFullDelivery();
		txtbDeliveryID.setText(Generator.generateDeliveryID());
		}
	
		
	
	
	
	/*-----------------delete Delivery Details-------------*/
		
	public void deleteDeliveryDetails(ActionEvent event) throws Exception{
		
		IDeliveryService iDeliveryService=new DeliveryServiceImpl();
		
		iDeliveryService.deleteDeliveryDetails(deleteDeliveryText.getText());
		System.out.println(deleteDeliveryText.getText());
		
		showFullDelivery();
		txtbDeliveryID.setText(Generator.generateDeliveryID());
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
