package controller;

import model.Cart;
import model.CustomerOrder;
import model.Delivery;
import model.Main;
import service.IDeliveryService;
import util.Generator;
import service.CustomerOrderServiceImpl;
import service.DeliveryServiceImpl;
import service.ICustomerOrderService;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.textfield.TextFields;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



import model.Item;
import model.Main;
import model.User;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import service.IInventoryServices;
import service.InventoryServicesImpl;
import util.Generator;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.textfield.TextFields;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class InventoryManageItemsController implements Initializable{
	
	//manage items
	@FXML private TextField txtbItem_ItemCode;
	@FXML private TextField txtbItem_ModelName;
	@FXML private ComboBox<String> cmbItem_ItemCondition;
	@FXML private ComboBox<String> cmbItem_Status;
	@FXML private TextField txtbItem_OrderId;
	
	@FXML private TableView<Item> tblvItemList;
	
	@FXML private TableColumn<? , ?> tblcItemCode_item;
	@FXML private TableColumn<? , ?> tblcModelName_item;
	@FXML private TableColumn<? , ?> tblcItemCondition_item;
	@FXML private TableColumn<? , ?> tblcStatus_item;
	@FXML private TableColumn<? , ?> tblcOrderID_item;
	
	IInventoryServices iInventoryServices =new InventoryServicesImpl();
	
	private ObservableList<Item> itemList=FXCollections.observableArrayList();
	
	private ObservableList<String> itemConditionList = FXCollections.observableArrayList("Brand New","Second Hand","For Repair","For Return");
	private ObservableList<String> itemStatusList = FXCollections.observableArrayList("Stock","Sold","Available","Not Available");
	private ObservableList<String> modelList = FXCollections.observableArrayList();
	private ObservableList<String> ordersList = FXCollections.observableArrayList();

	
	//advanced
	
	@FXML private TextField txtbSearchItems;
	private FilteredList<Item> filter = new FilteredList(itemList, e->true);
	
	
	
	
	

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
		
		itemGetAll();
		//showFullItems();
		
		//tblvItemList.setItems(itemList);
		
		cmbItem_ItemCondition.setItems(itemConditionList);
		cmbItem_Status.setItems(itemStatusList);
		modelList=iInventoryServices.getModels();
		ordersList=iInventoryServices.getOrders();
		
		TextFields.bindAutoCompletion(txtbItem_ModelName,modelList );
		TextFields.bindAutoCompletion(txtbItem_OrderId,ordersList );	
		
		txtbItem_ItemCode.setText(Generator.generateItemCode());
		
		
		
		/*tblcItemCode_item.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
		tblcModelName_item.setCellValueFactory(new PropertyValueFactory<>("modelName"));
		tblcItemCondition_item.setCellValueFactory(new PropertyValueFactory<>("itemCondition"));
		tblcStatus_item.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));
		tblcOrderID_item.setCellValueFactory(new PropertyValueFactory<>("idOrderSupplier"));*/
		
		
		
		tblvItemList.setItems(itemList);
		
		
		
		tblvItemList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				txtbItem_ItemCode.setText(tblvItemList.getSelectionModel().getSelectedItem().getItemCode());
				txtbItem_ModelName.setText(tblvItemList.getSelectionModel().getSelectedItem().getModelName());
				cmbItem_ItemCondition.setValue(tblvItemList.getSelectionModel().getSelectedItem().getItemCondition());
				cmbItem_Status.setValue(tblvItemList.getSelectionModel().getSelectedItem().getItemStatus());				
				txtbItem_OrderId.setText(tblvItemList.getSelectionModel().getSelectedItem().getIdOrderSupplier());
				
				
				txtbItem_ItemCode.setEditable(false);
				
			}
		});
		
		
		
		
		
		
		FilteredList<Item> filteredItems=new FilteredList<>(itemList,e->true);
		txtbSearchItems.setOnKeyReleased(e-> {
			txtbSearchItems.textProperty().addListener((observableValue,oldValue,newValue)->{
				filteredItems.setPredicate((Predicate<? super Item>) item->{
					
					if(newValue==null||newValue.isEmpty()) {
						return true;
					}
					
					if(item.getItemCode().contains(newValue)) {
						return true;
					}
					
					return false;
				});
			});
			
			SortedList<Item> sortedItems=new SortedList<>(filteredItems);
			sortedItems.comparatorProperty().bind(tblvItemList.comparatorProperty());
			tblvItemList.setItems(sortedItems);
			
		});
		
		
		
	}
	
	
	


	
	
	
	@FXML
	private void search(javafx.scene.input.KeyEvent event) {
		
		System.out.println("jdskj");
		
		txtbSearchItems.textProperty().addListener((observable, oldValue, newValue) ->{
			
			filter.setPredicate((Predicate<? super Item>) (Item oList)->{
				
				if(newValue.isEmpty() || newValue == null) {
					return true;
					
				}else if(oList.getItemCode().contains(newValue)){
					
					return true;
					
				}
				
				return false;
			});
			
		});
		
		SortedList sort = new SortedList(filter);
		sort.comparatorProperty().bind(tblvItemList.comparatorProperty());
		tblvItemList.setItems(sort);
	}
	
	
	
	
	
	
	
	
	public void itemCodeGenerate(ActionEvent event) {
		txtbItem_ItemCode.setText(Generator.generateItemCode());
	}
	
	
	
	
	public void demo(ActionEvent event) {
		
		
		
		
		txtbItem_ModelName.setText("ROG STRIX");
		cmbItem_ItemCondition.getSelectionModel().select(0);
		cmbItem_Status.getSelectionModel().select(0);
		
		
		
	}
	
	
	
	public boolean modelValidation() {
		

		if(txtbItem_ItemCode.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Item Code ");
			alert.showAndWait();
			return false;
		}
		
		Pattern pattern_name = Pattern.compile("[a-zA-Z0-9 ]*");
		Matcher matcher_item_code = pattern_name.matcher(txtbItem_ItemCode.getText());
		
		if(!matcher_item_code.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Item Code Can not Contains Special Character");
			alert.showAndWait();
			return false;
		}
		
		
		
		if(txtbItem_ModelName.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Model Name ");
			alert.showAndWait();
			return false;
		}
		
		Matcher matcher_model_name = pattern_name.matcher(txtbItem_ModelName.getText());
		
		if(!matcher_model_name.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Model Name Can not Contains Special Character ");
			alert.showAndWait();
			return false;
		}
		
		if((cmbItem_ItemCondition.getSelectionModel().getSelectedItem()==null)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Item Condition ");
			alert.showAndWait();
			return false;
		}
		if((cmbItem_Status.getSelectionModel().getSelectedItem()==null)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Item Status ");
			alert.showAndWait();
			return false;
		}
		
		if(txtbItem_OrderId.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Order ID ");
			alert.showAndWait();
			return false;
		}
		
		Matcher matcher_order_id = pattern_name.matcher(txtbItem_OrderId.getText());
		
		if(!matcher_order_id.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Order ID Can not Contains Special Character ");
			alert.showAndWait();
			return false;
		}
		
		
		return true;
		
		
	}



	
	public void itemDelete(ActionEvent event) {
		
		ObservableList<Item> selectedModel;
		
		//allItems=itemTable.getItems();
		
		selectedModel=tblvItemList.getSelectionModel().getSelectedItems();
		
		if(selectedModel.isEmpty()) {
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Item");
			alert.showAndWait();
			
		}else {
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Delete Selected Item ?");
			Optional <ButtonType> action= alert.showAndWait();
			
			if(action.get()==ButtonType.OK) {
				
				
				String item_code=selectedModel.get(0).getItemCode().toString();
				
				iInventoryServices.deleteItem(item_code);
			}
		}
		
		clear(event);
		
		itemGetAll();
		txtbItem_ItemCode.setEditable(true);
	}
	
	
	
	public void itemUpdate(ActionEvent event) {
		
		
		
		if(modelValidation()) {
			
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Update ?");
			Optional <ButtonType> action= alert.showAndWait();
			
			if(action.get()==ButtonType.OK) {
				
				
				Item item=new Item();
				
				item.setItemCode(txtbItem_ItemCode.getText());
				item.setModelName(txtbItem_ModelName.getText());
				item.setItemCondition((cmbItem_ItemCondition.getSelectionModel().getSelectedItem()).toString());
				item.setItemStatus((cmbItem_Status.getSelectionModel().getSelectedItem()).toString());				
				item.setIdOrderSupplier(txtbItem_OrderId.getText());
				
				
				iInventoryServices.updateItem(item);
				
				clear(event);
				txtbItem_ItemCode.setEditable(true);
				
			}
		}
		
		
		
		itemGetAll();
		
			
			
		
	}



	public void showFullItems() {
		
		
		itemList.clear();
		
		
		ArrayList<Item> itemlist = iInventoryServices.getAllItemsSearch();
		
		for( Item item : itemlist ) {
			itemList.add(item);
		} //end for
		
		tblvItemList.setItems(itemList);
	}


	
	
	public void itemGetAll() {
		
		
		
		tblcItemCode_item.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
		tblcModelName_item.setCellValueFactory(new PropertyValueFactory<>("modelName"));
		tblcItemCondition_item.setCellValueFactory(new PropertyValueFactory<>("itemCondition"));
		tblcStatus_item.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));
		tblcOrderID_item.setCellValueFactory(new PropertyValueFactory<>("idOrderSupplier"));
		
		itemList=iInventoryServices.getAllItems();
		
		tblvItemList.setItems(itemList);
		
		
	
	}
	
	
	public void itemAdd(ActionEvent event) {
		
		if(modelValidation()) {
			
			Item item = new Item();
			
			item.setItemCode(txtbItem_ItemCode.getText());
			item.setModelName(txtbItem_ModelName.getText());
			item.setItemCondition((cmbItem_ItemCondition.getSelectionModel().getSelectedItem()).toString());
			item.setItemStatus((cmbItem_Status.getSelectionModel().getSelectedItem()).toString());
			item.setIdOrderSupplier(txtbItem_OrderId.getText());
			
			
			String message = iInventoryServices.addItem(item);
		
			if( !message.equalsIgnoreCase("error") ) {
				clear(event);
			}
			itemGetAll();
			
			txtbItem_ItemCode.setEditable(true);
		}
		
		
		
		
		
		
		
		
	}
	
	

	public void clear(ActionEvent event) {
		txtbItem_ItemCode.setText("");
		txtbItem_ModelName.setText("");
		cmbItem_ItemCondition.getSelectionModel().clearSelection();
		cmbItem_Status.getSelectionModel().clearSelection();
		txtbItem_OrderId.setText("");
		
		txtbItem_ItemCode.setEditable(true);
		
		
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
			loader.setLocation(getClass().getResource("/view/CustomerManage.fxml"));
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
