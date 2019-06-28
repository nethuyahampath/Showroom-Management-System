package controller;

import model.Main;
import model.User;
import model.repairOrderFormModel;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import service.IRepairService;
import service.ItemRepairImpl;
import util.Generator;
import util.sqlConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.modelmbean.RequiredModelMBean;



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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ItemRepairController implements Initializable{
		
	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
	
	/*public void setOdrID(String oderID) {
		 
		this.orderIDtxt.setText(oderID);
	}*/
	
	Connection conn;
	PreparedStatement preparedStatment = null;
	ResultSet rs = null;
	ObservableList<repairOrderFormModel> data = FXCollections.observableArrayList();
	
	@FXML
	private Label orderFOrmLbl;
	@FXML
	private Label orderID;
	@FXML
	private Label itemCode;
	@FXML
	private Label des;
	@FXML
	private Label date;
	@FXML
	private Label statlbl;
	@FXML
	private TextField orderIDtxt;		
	@FXML
	private TextField desTxt;	
	@FXML
	private TextField itemCodeTxt;	
	@FXML
	private Button createBttn;
	@FXML
	private Button deleteBtn;
	@FXML
	private DatePicker datePick;
	@FXML
	private ComboBox<String> statchooser;
	ObservableList<String> statlist = FXCollections.observableArrayList("Repairing","Completed");	
	@FXML
	private TableView<repairOrderFormModel> table;	
	@FXML
	private TableColumn<?, ?> itemCol;	
	@FXML
	private TableColumn<?, ?> orderIDCol;	
	@FXML
	private TableColumn<?, ?> faultDesCol;	
	@FXML
	private TableColumn<?, String> dateCol;
	@FXML
	private TableColumn<?, ?> statCol;

    //private  repairOrderFormModel repairObj = new repairOrderFormModel();
    
    /*public void getOdrID(repairOrderFormModel repair) {
    	this.repairObj = repair;
    	IRepairService repairService = new ItemRepairImpl();
    	orderIDtxt.setText(repairService.loadOdrID(repairObj));
    }*/
//------------------------------------------------------------------------------------------------------------	
 /*-------------ADD REPAIR DETAILS----------------*/
	
    IRepairService iRepairService = new ItemRepairImpl();
	
	public void addRepairData(ActionEvent event) throws SQLException {
		
		if(validateFields()) {
		
		repairOrderFormModel repair = new repairOrderFormModel();
		
		repair.setRepairDetID(Generator.generateRepairDetailID());
		repair.setOrderID(orderIDtxt.getText());
		repair.setItemCode(itemCodeTxt.getText());
		repair.setDescription(desTxt.getText());
		repair.setDate((datePick.getValue()).toString());
		repair.setStat(statchooser.getValue());
		
		iRepairService.addItemRepairDetails(repair);
		
		clear(event);
		showRepairDetails();
		}
		
	}
	
	/*-------------------INITIALIZE---------------------*/
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		
		statchooser.setItems(statlist);
		
		showRepairDetails();
		
		orderIDCol.setCellValueFactory(new PropertyValueFactory<> ("orderID"));
		itemCol.setCellValueFactory(new PropertyValueFactory<> ("itemCode"));
		faultDesCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		statCol.setCellValueFactory(new PropertyValueFactory<>("stat"));	
		
	}
    
   
	
	/*------CLEAR REPAIR FORM------------*/
	
	private void clear(ActionEvent event) {
		orderIDtxt.setText("");
		itemCodeTxt.setText(null);
		desTxt.setText("");
		datePick.setValue(null);
		statchooser.setValue(null);
		
	}
	
	
	/*---------SHOW ALL REPAIR DETAILS----------*/
	private void showRepairDetails() {
		
		data.clear();
		
		IRepairService iRepairService = new ItemRepairImpl();
		
		ArrayList<repairOrderFormModel> repairList = iRepairService.getAllItemRepairDetails();
		
		for(repairOrderFormModel repair : repairList) {
			data.add(repair);
		}
		
		table.setItems(data);
		
	}
	
	/*------------SHOW ON CLICK-------------*/
	String odrID;
	@FXML
	public void showOnClick() {
		repairOrderFormModel repair = table.getSelectionModel().getSelectedItem();
		
		odrID = repair.getOrderID();
		
		orderIDtxt.setText(repair.getOrderID());
		itemCodeTxt.setText(repair.getItemCode());
		desTxt.setText(repair.getDescription());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = repair.getDate();
		LocalDate localDate = LocalDate.parse(date, formatter);
		datePick.setValue(localDate);
		
		statchooser.setValue(repair.getStat());
		
	}

	/*------------DELETE REPAIR DATA-------------*/

	@FXML
	private void deleteRepairData(ActionEvent event) {
		
		iRepairService.deleteItemRepairDetails(orderIDtxt.getText());
		clear(event);
		showRepairDetails();
	}
	
	/*--------UPDATE REPAIR DETAILS----------*/
	
	@FXML
	private void updateRepairData(ActionEvent event) {
		
		repairOrderFormModel repair = new repairOrderFormModel();
		
		repair.setOrderID(orderIDtxt.getText());
		repair.setItemCode(itemCodeTxt.getText());
		repair.setDescription(desTxt.getText());
		repair.setDate((datePick.getValue()).toString());
		repair.setStat(statchooser.getValue());
		
		iRepairService.updateItemRepairDetails(repair);
		
		clear(event);
		showRepairDetails();
	}
	
public void addDemoDetails(ActionEvent event) {
	orderIDtxt.setText("O106");
	itemCodeTxt.setText("I456");
	desTxt.setText("USB port not working");
	datePick.setValue(java.time.LocalDate.now());
	statchooser.setValue("Repairing");
	}
	
	
	/*---------------VALIDATION------------------*/
	
	private boolean validateFields() {
		
		Pattern pattern = Pattern.compile("[A-Za-z]+[0-9]+");
		Matcher matcher_oID = pattern.matcher(orderIDtxt.getText());
		Matcher matcher_itemCode = pattern.matcher(itemCodeTxt.getText());

		
		if(orderIDtxt.getText().isEmpty()) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Oder ID..!!");
			alert.showAndWait();
			return false;
		}
		
		if(!matcher_oID.matches()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter a Valid Oder ID..!!");
			alert.showAndWait();
			return false;
		}
		
		
		if(itemCodeTxt.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter an Item Code..!!");
			alert.showAndWait();
			return false;
		}
		
		if(!matcher_itemCode.matches()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter a Valid Item Code..!!");
			alert.showAndWait();
			return false;
		}
		
		if(desTxt.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Description..!!");
			alert.showAndWait();
			
			return false;
		}
		
		if(statchooser.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Choose an Item Status..!!");
			alert.showAndWait();
			
			return false;
		}		
		
		return true;
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

	
	
	public void technicianManageScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager")) {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/TechnicianManage.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			TechnicianManageController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			
			Generator.getAlert("Access Denied", "You don't have access to Item Repair Management");
		}
	}
	
	
	public void reportManageScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager")) {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/RepairDetailsReport.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			RepairDetailsReportController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			
			Generator.getAlert("Access Denied", "You don't have access to Item Repair Management");
		}
	}
}
