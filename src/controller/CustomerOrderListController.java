package controller;

import model.Main;
import model.Model;
import model.User;
import model.Cart;
import model.CustomerOrder;
import model.CustomerPayment;

import java.awt.event.ItemListener;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.util.function.Predicate;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import model.BrandNewItem;
import service.CustomerOrderServiceImpl;
import service.EmployeeServiceImpl;
import service.ICustomerOrderService;
import service.IEmployeeService;
import util.Generator;


public class CustomerOrderListController implements Initializable{


	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
	
	
	
    @FXML
    private Label customerIDLabel;

    @FXML
    private Label cartDateLabel;

    @FXML
    private Label cartIDLabel;

    /*-------------------Cart Display----------------*/
    @FXML
    private TableView<Cart> fullCart;

    @FXML
    private TableColumn<Cart, String> itemCode;

    @FXML
    private TableColumn<Cart, Double> sellingPrice;

    @FXML
    private TableColumn<Cart, String> modelName;

    private ObservableList<Cart> fullCartList = FXCollections.observableArrayList();
    
    @FXML
    private Label orderIDLabel;

    @FXML
    private Label userLabel;

    
    /*-------------------Customer Order Display----------------*/

    @FXML
    private TableView<CustomerOrder> orderTable;
    
    @FXML
    private TableColumn<CustomerOrder, String> orderID;

    @FXML
    private TableColumn<CustomerOrder, String> cartID;

    @FXML
    private TableColumn<CustomerOrder, String> orderDate;

    @FXML
    private TableColumn<CustomerOrder, String> orderTime;

    @FXML
    private TableColumn<CustomerOrder, Double> total;

    @FXML
    private TableColumn<CustomerOrder, String> deliveryStatus;

	@FXML 
	private TextField searchOrders;
    
    private ObservableList<CustomerOrder> orderList = FXCollections.observableArrayList();

	private FilteredList<CustomerOrder> filter = new FilteredList(orderList, e->true);
    
    //store cart ID seperatly
    private String customerCartID = "";
    
    /*-------------------- show order table ----------------*/
    public void showOrderTable() {
    	
    	orderList.clear();
    	
    	ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		ArrayList<CustomerOrder> orders = icustOrder.getCustomerOrderDetails();
		
		for( CustomerOrder order : orders ) {
			orderList.add(order);
		} //end for
		
		orderID.setCellValueFactory(new PropertyValueFactory<CustomerOrder, String>("OID"));
		cartID.setCellValueFactory(new PropertyValueFactory<CustomerOrder, String>("cartID"));
		orderDate.setCellValueFactory(new PropertyValueFactory<CustomerOrder, String>("Odate"));
		orderTime.setCellValueFactory(new PropertyValueFactory<CustomerOrder, String>("Otime"));
		total.setCellValueFactory(new PropertyValueFactory<CustomerOrder, Double>("total"));
		deliveryStatus.setCellValueFactory(new PropertyValueFactory<CustomerOrder, String>("deliveryStatus"));
		
		orderTable.setItems(orderList);
    	
    }
    
    /*-------------- show cart details ----------------------*/
    public void showFullCart() {
		
		fullCartList.clear();
		
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		ArrayList<Cart> cartList = icustOrder.getAllCartDetails( customerCartID);
		
		for( Cart cart : cartList ) {
			fullCartList.add(cart);
		} //end for
		
		itemCode.setCellValueFactory(new PropertyValueFactory<Cart, String>("itemCode"));
		modelName.setCellValueFactory(new PropertyValueFactory<Cart, String>("modelName"));
		sellingPrice.setCellValueFactory(new PropertyValueFactory<Cart, Double>("sellingPrice"));
		fullCart.setItems(fullCartList);
	}
    
    /*------------------- select order from payment table ------------------*/
	public void selectOrderRecord() {
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		CustomerOrder order = orderTable.getSelectionModel().getSelectedItem();
		
		orderIDLabel.setText(order.getOID());
		customerCartID = order.getCartID();
		cartIDLabel.setText(order.getCartID());
		customerIDLabel.setText(icustOrder.getCustomerIDByCartID(order.getCartID()));
		cartDateLabel.setText(order.getOdate());
		
		showFullCart();
	}
	
	/*-----------------------------------------Search for orders --------------------------------------*/
	
	@FXML
	public void doSearch(KeyEvent event ) {
		
		searchOrders.textProperty().addListener((observable, oldValue, newValue )->{
			filter.setPredicate((Predicate<? super CustomerOrder>) (CustomerOrder orderList) -> {
				if( newValue.isEmpty() || newValue == null ) {
					return true;
				}else if( orderList.getOID().contains(newValue)) {
					return true;
				}
				
				return false;
			});
		});
		
		SortedList sort = new SortedList(filter);
		sort.comparatorProperty().bind(orderTable.comparatorProperty());
		orderTable.setItems(sort);
	}

	/*-------------------------------------------------------------------------------------------------*/
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		showOrderTable();
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
	
	public void CustomerOrderListScreen( ActionEvent event ) throws Exception{

		
		if(userObj.getType().equalsIgnoreCase("Salesman"))
		{
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/CustomerOrderList.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			CustomerOrderListController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			Generator.getAlert("Access Denied", "You don't have access to Inventory Management");
		}
	}
	
	public void CustomerPaymentScreen( ActionEvent event ) throws Exception{

		if(userObj.getType().equalsIgnoreCase("Salesman"))
		{
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/CustomerOrderPayment.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			CustomerOrderPaymentController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			Generator.getAlert("Access Denied", "You don't have access to Inventory Management");
		}
	}
	
	public void CustomerOrderReportScreen( ActionEvent event ) throws Exception{


		if(userObj.getType().equalsIgnoreCase("Salesman"))
		{
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/CustomerOrderReports.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			CustomerOrderReportsController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			Generator.getAlert("Access Denied", "You don't have access to Inventory Management");
		}
	}
	
	//end Screen changing functions
	
}
