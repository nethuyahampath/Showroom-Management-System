package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.sun.xml.internal.ws.message.DataHandlerAttachment;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AttendanceModel;
import model.Main;
import model.RepairItemModel;
import model.TempRepairItemModel;
import model.User;
import model.repairOrderFormModel;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import service.IRepairService;
import service.ItemRepairImpl;
import service.TManageService;
import service.TechnicianManageImpl;
import util.Generator;

public class TechnicianManageController implements Initializable {
	Connection conn;
	PreparedStatement preparedStatment = null;
	ResultSet rs = null;
	ObservableList<AttendanceModel> attendData = FXCollections.observableArrayList();
	ObservableList<TempRepairItemModel> tempItemRepairData = FXCollections.observableArrayList();
	ObservableList<RepairItemModel> repairItemData = FXCollections.observableArrayList();
	
	@FXML
	private TextField itemCodetxt;
	@FXML
	private TextField empIDTxt;
	@FXML
	private TextField costTxt;
	@FXML
	private TextField custIDTxt;
	@FXML
	private TextField payIDTxt;
	@FXML
	private Button addButton;
	@FXML
	private Button dltButton;
	@FXML
	private Button updateButton;
	
	@FXML
	private TableView<AttendanceModel> attendanceTable;	
	@FXML
	private TableColumn<?, ?> desigCol;	
	@FXML
	private TableColumn<?, ?> empIdCol;	
	
	@FXML
	private TableView<TempRepairItemModel> tempRepairItemTable;
	@FXML
	private TableColumn<?, ?> itemCodeColtemp;	
	@FXML
	private TableColumn<?, String> empIDColtemp;
	
	@FXML
	private TableView<RepairItemModel> RepairItemTable;
	@FXML
	private TableColumn<?, ?> itemCodeCol;
	@FXML
	private TableColumn<?, ?> empIDCol;
	@FXML
	private TableColumn<?, ?> costCol;
	@FXML
	private TableColumn<?, ?> custIDCol;
	@FXML
	private TableColumn<?, ?> payIDCol;
	
	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
    
    private RepairItemModel repairObj = new RepairItemModel();
    
    public void loadItemCode(RepairItemModel repairItem) {

    	this.repairObj = repairItem;
    	
    	TManageService tManageService = new TechnicianManageImpl();
  
    	itemCodetxt.setText(tManageService.loadICode(repairObj));
    }
    
    	public void getItem(String item) {
		itemCodetxt.setText(item);
	}
	
	/*---------------------------INITIALIZE-------------------------------*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		showEmpAttendanceDetails();
		
		desigCol.setCellValueFactory(new PropertyValueFactory<> ("designation"));
		empIdCol.setCellValueFactory(new PropertyValueFactory<> ("empId"));
		
		showTempItemRepairDetails();
		
		itemCodeColtemp.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
		empIDColtemp.setCellValueFactory(new PropertyValueFactory<>("empID"));
		
		showRepairItemDetails();
		
		itemCodeCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
		empIDCol.setCellValueFactory(new PropertyValueFactory<>("empID"));
		costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
		custIDCol.setCellValueFactory(new PropertyValueFactory<>("custID"));
		payIDCol.setCellValueFactory(new PropertyValueFactory<>("payID"));
		
	}
	
	/*---------------------------SHOW ATTENDANCE DETAILS-------------------------------*/
	private void showEmpAttendanceDetails() {
		
		attendData.clear();
		
		TManageService tManageService = new TechnicianManageImpl();
		
		ArrayList<AttendanceModel> attendList = tManageService.getEmpAttendanceDetails();
		
		for(AttendanceModel attendance : attendList) {
			attendData.add(attendance);
		}
		
		attendanceTable.setItems(attendData);
		
	}
	
	/*--------------------------SHOW ON CLICK ATTENDANCE--------------------------------------*/
	@FXML
	private void showOnClickAttendance() {
		AttendanceModel attendance = attendanceTable.getSelectionModel().getSelectedItem();
		
		empIDTxt.setText(attendance.getEmpId());
	}

	/*--------------------------ADD REPAIR ITEMS-------------------------------*/
	TManageService tManageService = new TechnicianManageImpl();
	@FXML
	public void addTempRepairItems() {
		
		if(validateFields()) {
		
		TempRepairItemModel tempRepairItem = new TempRepairItemModel();
		
		
		tempRepairItem.setItemCode(itemCodetxt.getText());
		tempRepairItem.setEmpID(empIDTxt.getText());
		
		tManageService.addTempRepairItems(tempRepairItem);
		
		clear();
		showTempItemRepairDetails();
		}
	}
	
/*---------------VALIDATION------------------*/
	
	private boolean validateFields() {
		
		Pattern pattern = Pattern.compile("[A-Za-z]+[0-9]+");
		Matcher matcher_empId = pattern.matcher(empIDTxt.getText());
		Matcher matcher_itemCode = pattern.matcher(itemCodetxt.getText());

		//Pattern pattern2 = Pattern.compile("[0-9]+");
		//Matcher matcher_empId = pattern2.matcher(empIDTxt.getText());
		
		if(itemCodetxt.getText().isEmpty()) {
			
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
		
		
		if(empIDTxt.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter an Employee ID..!!");
			alert.showAndWait();
			return false;
		}
		
		if(!matcher_empId.matches()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter a Valid Employee ID..!!");
			alert.showAndWait();
			return false;
		}
		return true;
	}
	
	/*-------CLEAR---------*/
	@FXML
	private void clear() {
		itemCodetxt.setText("");
		empIDTxt.setText("");
	}

	/*-----------------------SHOW TEMPORARY ITEM REPAIR DETAILS-------------------------------------------*/
	private void showTempItemRepairDetails() {
		tempItemRepairData.clear();
		
		TManageService tManageService = new TechnicianManageImpl();
		
		ArrayList<TempRepairItemModel> tempItemList = tManageService.getTempRepairItemDetails();
		
		for(TempRepairItemModel tempRepairItem : tempItemList) {
			tempItemRepairData.add(tempRepairItem);
		}
		
		tempRepairItemTable.setItems(tempItemRepairData);
	}
	
	/*--------------------------SHOW ON CLICK TEMP REPAIR ITEM--------------------------------------*/
	@FXML
	private void showOnClickTempItemRepair() {
		TempRepairItemModel tempItemRepair = tempRepairItemTable.getSelectionModel().getSelectedItem();
		
		itemCodetxt.setText(tempItemRepair.getItemCode());
		empIDTxt.setText(tempItemRepair.getEmpID());
	}
	
	/*----------------------DELETE TEMPORARY ITEM REPAIR--------------------------------*/
	@FXML
	private void deleteTempRepairItems() {
		tManageService.deleteTempRepairItems(itemCodetxt.getText());
		
		showTempItemRepairDetails();
	}
	
	/*------------------------ADD REPAIR ITEMS---------------------------------------------------*/
	@FXML
	private void addRepairItems() {
		RepairItemModel repairItem = new RepairItemModel();
		
		repairItem.setItemCode(itemCodetxt.getText());
		repairItem.setEmpID(empIDTxt.getText());
		
		//System.out.println(itemCodetxt.getText());
		
		tManageService.addRepairItems(repairItem);
		
		clear();
		showRepairItemDetails();
	}
	
	public void addTechDemoDetails(ActionEvent event) {
		costTxt.setText("1000.00");
		custIDTxt.setText("C101");
		payIDTxt.setText("P101");
		}
	
	/*------------------------SHOW ALL REPAIR ITEMS DETAILS---------------------------------------*/
	@FXML
	private void showRepairItemDetails() {
		repairItemData.clear();
		
		TManageService tManageService = new TechnicianManageImpl();
		
		ArrayList<RepairItemModel> repairItemList = tManageService.getRepairItemDetails();
		
		for(RepairItemModel repairItem : repairItemList) {
			repairItemData.add(repairItem);
		}
		
		RepairItemTable.setItems(repairItemData);
		
	}
	
	/*--------------------------SHOW ON CLICK REPAIR ITEM--------------------------------------*/
	@FXML
	private void showOnClickRepairItem() {
		RepairItemModel repairItem = RepairItemTable.getSelectionModel().getSelectedItem();
		
		itemCodetxt.setText(repairItem.getItemCode());
		empIDTxt.setText(repairItem.getEmpID());
	}
	
	/*-------------------------UPDATE REPAIR DETAILS-----------------------------------------*/
	@FXML
	private void updateRepairItems() {
		RepairItemModel repairItem = new RepairItemModel();
		
		repairItem.setItemCode(itemCodetxt.getText());
		repairItem.setEmpID(empIDTxt.getText());
		repairItem.setCost(costTxt.getText());
		repairItem.setCustID(custIDTxt.getText());
		repairItem.setPayID(payIDTxt.getText());
		
		tManageService.updateRepairItems(repairItem);
		
		clear();
		showRepairItemDetails();
	}
	/*-------------------------------------Window Control---------------------------------------------------*/

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
	
	/*public void itemRepairFormScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager")) {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/ItemRepairManage1.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			ItemRepairManage1Controller controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			
			Generator.getAlert("Access Denied", "You don't have access to Item Repair Management");
		}
	}*/
	
	public void technicianManageScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager")) {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/TechnicianManage.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			TechnicianManageController controller = loader.getController();
			controller.loadUser(userObj);
			controller.loadItemCode(repairObj);
			
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
}
