package controller;

import model.SupplierRequestOrder;
import model.User;
import model.supplierOrder;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.textfield.TextFields;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import util.Generator;

public class InventoryOrdersController implements Initializable{

	@FXML private TextField txtbOrderID_Order;
	@FXML private ComboBox<String> cmbCategory_Order;
	@FXML private TextField txtbModel_Order;
	@FXML private TextField txtbQuantity_Order;
	@FXML private ComboBox<String> cmbOrderStatus_Order;
	@FXML private DatePicker dpSupOrderDate;
	@FXML private TextField txtbSearchOrder;

	
	@FXML private TableView<SupplierRequestOrder> tblvOrders_Orders;
	
	@FXML private TableColumn<? , ?> tblcOrderID_Order;
	@FXML private TableColumn<? , ?> tblcCategory_Order;
	@FXML private TableColumn<? , ?> tblcModel_Order;
	@FXML private TableColumn<? , ?> tblcQuantity_Order;
	@FXML private TableColumn<? , ?> tblcOrderStatus_Order;
	@FXML private TableColumn<? , ?> tblcOrderDate_Order;
	
	
	
	private ObservableList<String> categoryList = FXCollections.observableArrayList();
	private ObservableList<SupplierRequestOrder> supplierRequestOrdersList = FXCollections.observableArrayList();
	private ObservableList<String> modelList = FXCollections.observableArrayList();
	private ObservableList<String> orderStatusList = FXCollections.observableArrayList("Unchecked","Checked");
	
	
	private FilteredList<SupplierRequestOrder> filter = new FilteredList(supplierRequestOrdersList, e->true);

	
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
		categoryList=iInventoryServices.getCategoryNames();
		cmbCategory_Order.setItems(categoryList);
		
		modelList=iInventoryServices.getModels();
		TextFields.bindAutoCompletion(txtbModel_Order,modelList );
		
		cmbOrderStatus_Order.setItems(orderStatusList);
		
		
		supplierOrderrequestGetAll();
		
		

		txtbOrderID_Order.setText(Generator.generateSupplierOrderRequestId());
		
		tblvOrders_Orders.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				txtbOrderID_Order.setText(tblvOrders_Orders.getSelectionModel().getSelectedItem().getSupplierReuestOrderId());
				cmbCategory_Order.setValue(tblvOrders_Orders.getSelectionModel().getSelectedItem().getSupplierRequestOrderCategory());
				txtbModel_Order.setText(tblvOrders_Orders.getSelectionModel().getSelectedItem().getSupplierRequestOrderModel());
				
				
				String date=tblvOrders_Orders.getSelectionModel().getSelectedItem().getSupplierRequestOrderDate();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

				String date1 = date;
				
				//convert String to LocalDate
				LocalDate localDate = LocalDate.parse(date1, formatter);
				
				dpSupOrderDate.setValue(localDate);
				
				
				
				txtbQuantity_Order.setText(tblvOrders_Orders.getSelectionModel().getSelectedItem().getSupplierRequestOrderQuantity());
				cmbOrderStatus_Order.setValue(tblvOrders_Orders.getSelectionModel().getSelectedItem().getSupplierRequestOrderStatus());				
				
				cmbOrderStatus_Order.setDisable(false);
				
				
				//txtbOrderID_Order.setEditable(false);
				
			}
		});
		
		
		cmbOrderStatus_Order.getSelectionModel().selectFirst();
		cmbOrderStatus_Order.setDisable(true);
		dpSupOrderDate.setValue(SupplierRequestOrder.getCurrentDate());
		
		
		
		
		
		
		
		FilteredList<SupplierRequestOrder> filteredOrders=new FilteredList<>(supplierRequestOrdersList,e->true);
		txtbSearchOrder.setOnKeyReleased(e-> {
			txtbSearchOrder.textProperty().addListener((observableValue,oldValue,newValue)->{
				filteredOrders.setPredicate((Predicate<? super SupplierRequestOrder>) order->{
					
					if(newValue==null||newValue.isEmpty()) {
						return true;
					}
					
					if(order.getSupplierReuestOrderId().contains(newValue)) {
						return true;
					}
					
					return false;
				});
			});
			
			SortedList<SupplierRequestOrder> sortedOrders=new SortedList<>(filteredOrders);
			sortedOrders.comparatorProperty().bind(tblvOrders_Orders.comparatorProperty());
			tblvOrders_Orders.setItems(sortedOrders);
			
		});
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	public void idGenerate(ActionEvent event) {
		txtbOrderID_Order.setText(Generator.generateSupplierOrderRequestId());
	}
	
	
	
	@FXML
	private void search(javafx.scene.input.KeyEvent event) {
		
		System.out.println("jdskj");
		
		txtbSearchOrder.textProperty().addListener((observableValue,oldValue,newValue)->{
			filter.setPredicate((Predicate<? super SupplierRequestOrder>) order->{
				
				if(newValue==null||newValue.isEmpty()) {
					return true;
				}
				
				if(order.getSupplierReuestOrderId().contains(newValue)) {
					return true;
				}
				
				return false;
			});
		});
		
		SortedList<SupplierRequestOrder> sortedOrders=new SortedList<>(filter);
		sortedOrders.comparatorProperty().bind(tblvOrders_Orders.comparatorProperty());
		tblvOrders_Orders.setItems(sortedOrders);
		
	}
	
	
	public void demo(ActionEvent event) {
		
		
		
		cmbCategory_Order.getSelectionModel().select(2);
		txtbModel_Order.setText("ROG STRIX");
		txtbQuantity_Order.setText("10");
		cmbOrderStatus_Order.getSelectionModel().select(0);
		
		
		
	}
	
	
	public void supplierRequestOrderUpdate(ActionEvent event) {
		
		
		
		if(orderValidation()) {
		
			
			
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Update ?");
			Optional <ButtonType> action= alert.showAndWait();
			
			if(action.get()==ButtonType.OK) {
				
				
				SupplierRequestOrder order=new SupplierRequestOrder();
				
				order.setSupplierReuestOrderId(txtbOrderID_Order.getText());
				order.setSupplierRequestOrderCategory((cmbCategory_Order.getSelectionModel().getSelectedItem()).toString());
				order.setSupplierRequestOrderModel(txtbModel_Order.getText());
				order.setSupplierRequestOrderDate(dpSupOrderDate.getValue().toString());
				order.setSupplierRequestOrderQuantity(txtbQuantity_Order.getText());
				order.setSupplierRequestOrderStatus((cmbOrderStatus_Order.getSelectionModel().getSelectedItem()).toString());				
				order.setSupplierReuestOrderId(txtbOrderID_Order.getText());
				
				
				
				iInventoryServices.updateSupplierRequestOrder(order);
				
				clear(event);
				//txtbItem_ItemCode.setEditable(true);
				
			}
		}
		
		
		
		supplierOrderrequestGetAll();
		
			
			
		
	}




	
	
	
	public void supplierRequestOrderDelete(ActionEvent event) {
		
		ObservableList<SupplierRequestOrder> selectedOrder;
		
		//allItems=itemTable.getItems();
		
		selectedOrder=tblvOrders_Orders.getSelectionModel().getSelectedItems();
		
		if(selectedOrder.isEmpty()) {
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Order");
			alert.showAndWait();
			
		}else {
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Delete Selected Order ?");
			Optional <ButtonType> action= alert.showAndWait();
			
			if(action.get()==ButtonType.OK) {
				
				
				String order_id=selectedOrder.get(0).getSupplierReuestOrderId().toString();
				
				iInventoryServices.deleteSupplierRequestOrder(order_id);
			}
		}
		
		clear(event);
		
		supplierOrderrequestGetAll();
		
	}
	
	
	
	public void supplierOrderrequestGetAll() {
		
		tblcOrderID_Order.setCellValueFactory(new PropertyValueFactory<>("supplierReuestOrderId"));
		tblcCategory_Order.setCellValueFactory(new PropertyValueFactory<>("supplierRequestOrderCategory"));
		tblcModel_Order.setCellValueFactory(new PropertyValueFactory<>("supplierRequestOrderModel"));
		tblcOrderDate_Order.setCellValueFactory(new PropertyValueFactory<>("supplierRequestOrderDate"));
		tblcQuantity_Order.setCellValueFactory(new PropertyValueFactory<>("supplierRequestOrderQuantity"));
		tblcOrderStatus_Order.setCellValueFactory(new PropertyValueFactory<>("supplierRequestOrderStatus"));
		
		
		supplierRequestOrdersList=iInventoryServices.getAllSupplierRequestOrders();
		
		tblvOrders_Orders.setItems(supplierRequestOrdersList);
		
		
	
	}
	



	public boolean orderValidation() {
		

		if(txtbOrderID_Order.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Order ID ");
			alert.showAndWait();
			return false;
		}
		
		Pattern pattern_name = Pattern.compile("[a-zA-Z0-9 ]*");
		Matcher matcher_order_id = pattern_name.matcher(txtbOrderID_Order.getText());
		
		if(!matcher_order_id.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Order ID Can not Contains Special Character");
			alert.showAndWait();
			return false;
		}
		
		
		
		if((cmbCategory_Order.getSelectionModel().getSelectedItem()==null)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Category ");
			alert.showAndWait();
			return false;
		}
		
		if(txtbModel_Order.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Model Name ");
			alert.showAndWait();
			return false;
		}
		
		Matcher matcher_model_name = pattern_name.matcher(txtbModel_Order.getText());
		
		if(!matcher_model_name.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Model Name Can not Contains Special Character ");
			alert.showAndWait();
			return false;
		}
		
		
		if(dpSupOrderDate.getValue()==null) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Order Date ");
			alert.showAndWait();
			return false;
		}
		
		
		
		if(txtbQuantity_Order.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Quantity ");
			alert.showAndWait();
			return false;
		}
		
		Pattern pattern_number = Pattern.compile("[0-9]*");
		
		Matcher matcher_quantity= pattern_number.matcher(txtbQuantity_Order.getText());
		
		if(!matcher_quantity.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Quantity Can Only Contain Numbers ");
			alert.showAndWait();
			return false;
		}
		
		
		if((cmbOrderStatus_Order.getSelectionModel().getSelectedItem()==null)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Order Status ");
			alert.showAndWait();
			return false;
		}
		
		
		return true;
		
		
	}


	
	public void supplierOrderrequestAdd(ActionEvent event) {
		
		if(orderValidation()) {
			
			
			SupplierRequestOrder order=new SupplierRequestOrder();
			
			order.setSupplierReuestOrderId(txtbOrderID_Order.getText());
			order.setSupplierRequestOrderCategory((cmbCategory_Order.getSelectionModel().getSelectedItem()).toString());
			order.setSupplierRequestOrderModel(txtbModel_Order.getText());
			order.setSupplierRequestOrderDate((dpSupOrderDate.getValue()).toString());
			order.setSupplierRequestOrderQuantity(txtbQuantity_Order.getText());
			order.setSupplierRequestOrderStatus((cmbOrderStatus_Order.getSelectionModel().getSelectedItem()).toString());
			
		
			String message = iInventoryServices.addSupplierRequestOrder(order);
		
			if( !message.equalsIgnoreCase("error") ) {
				clear(event);
			}
			supplierOrderrequestGetAll();
			
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	
	public void clear(ActionEvent event) {
		txtbOrderID_Order.setText("");
		cmbCategory_Order.getSelectionModel().clearSelection();
		txtbModel_Order.setText("");
		txtbQuantity_Order.setText("");
		//cmbOrderStatus_Order.getSelectionModel().clearSelection();	
		//cmbCategory_Order.setAccessibleText("");
		cmbOrderStatus_Order.getSelectionModel().selectFirst();
		cmbOrderStatus_Order.setDisable(true);
		dpSupOrderDate.setValue(SupplierRequestOrder.getCurrentDate());
		
		
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
			
			InventoryReportController controller = loader.getController();
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
