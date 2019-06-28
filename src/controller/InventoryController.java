package controller;

import model.Category;
import model.Item;
import model.Main;
import model.Model;
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
import javafx.util.converter.DefaultStringConverter;



public class InventoryController implements Initializable{

	//manage model
	@FXML private ComboBox<String> cmbCategory;
	@FXML private ComboBox<String> cmbSupplier;
	@FXML private TextField txtbModel;
	@FXML private TextField txtbUnitPrice;
	@FXML private TextField txtbSellingPrice;
	@FXML private TextField txtbQuantity;
	@FXML private ComboBox<String> cmbWarranty;
	
	@FXML private Button btnAddModel;
	
	@FXML private TableView<Model> tblvModel;
	
	@FXML private TableColumn<? , ?> tblcModel;
	@FXML private TableColumn<? , ?> tblcCategory;
	@FXML private TableColumn<? , ?> tblcSupplier;
	@FXML private TableColumn<? , ?> tblcUnitPrice;
	@FXML private TableColumn<? , ?> tblcSellingPrice;
	@FXML private TableColumn<? , ?> tblcQuantity;
	@FXML private TableColumn<? , ?> tblcWarranty;
	
	
	//manage category
	@FXML private TextField txtbAddNewCategory;
	@FXML private TextField txtbDeleteCategory;
	
	
	private ObservableList<Model> modelList = FXCollections.observableArrayList();
	
	private ObservableList<String> categoryList = FXCollections.observableArrayList();
	private ObservableList<String> supplierList = FXCollections.observableArrayList();
	private ObservableList<String> warrantyList = FXCollections.observableArrayList("1","2","3","4","5","10");
	
	
	
	
	@FXML private TextField txtbSearchItems;
	
	
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
		cmbCategory.setItems(categoryList);
		
		cmbWarranty.setItems(warrantyList);
		
		
		/*supplierList=iInventoryServices.getSuppliers();
		cmbSupplier.setItems(supplierList);*/
		
		modelGetAll();
		
		
		
		
		
		
		tblvModel.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				cmbCategory.setValue(tblvModel.getSelectionModel().getSelectedItem().getCategory());
				cmbSupplier.setValue(tblvModel.getSelectionModel().getSelectedItem().getSupplier());
				txtbModel.setText(tblvModel.getSelectionModel().getSelectedItem().getModel());
				txtbUnitPrice.setText(tblvModel.getSelectionModel().getSelectedItem().getUnitPrice());
				txtbSellingPrice.setText(tblvModel.getSelectionModel().getSelectedItem().getSellingPrice());
				txtbQuantity.setText(tblvModel.getSelectionModel().getSelectedItem().getQuantity());
				cmbWarranty.setValue(tblvModel.getSelectionModel().getSelectedItem().getWarranty());
				
				txtbModel.setEditable(false);
				
			}
		});
		
		TextFields.bindAutoCompletion(txtbDeleteCategory,categoryList );
		
		
		/*FilteredList<Model> filteredModels=new FilteredList<>(modelList,e->true);
		txtbSearchItems.setOnKeyReleased(e-> {
			txtbSearchItems.textProperty().addListener((observableValue,oldValue,newValue)->{
				filteredModels.setPredicate((Predicate<? super Model>) model->{
					
					if(newValue==null||newValue.isEmpty()) {
						return true;
					}
					
					if(model.getModel().contains(newValue)) {
						return true;
					}
					
					return false;
				});
			});
			
			SortedList<Model> sortedModels=new SortedList<>(filteredModels);
			sortedModels.comparatorProperty().bind(tblvModel.comparatorProperty());
			tblvModel.setItems(sortedModels);
			
		});*/
		
		
	}
	
	public void getSupplierList(ActionEvent event) {
		String ctgry=cmbCategory.getSelectionModel().getSelectedItem().toString();
		
		supplierList=iInventoryServices.getSuppliers(ctgry);
		cmbSupplier.setItems(supplierList);
		
	}
	
	
	
	
	
	public void demo(ActionEvent event) {
		
		
		
		cmbCategory.getSelectionModel().select(2);
		txtbModel.setText("ROG STRIX");
		txtbUnitPrice.setText("150000");
		txtbSellingPrice.setText("160000");
		txtbQuantity.setText("5");
		cmbWarranty.getSelectionModel().select(1);
		
		
		
	}
	
	
	
	
	
	
	public void modelUpdate(ActionEvent event) {
		
		
		
		if(modelValidation()) {
			
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Update ?");
			Optional <ButtonType> action= alert.showAndWait();
			
			if(action.get()==ButtonType.OK) {
				
				
				Model model=new Model();
				
				model.setCategory((cmbCategory.getSelectionModel().getSelectedItem()).toString());
				model.setSupplier((cmbSupplier.getSelectionModel().getSelectedItem()).toString());
				model.setModel(txtbModel.getText());
				model.setUnitPrice(txtbUnitPrice.getText());
				model.setSellingPrice(txtbSellingPrice.getText());
				model.setQuantity(txtbQuantity.getText());
				model.setWarranty((cmbWarranty.getSelectionModel().getSelectedItem()).toString());
				
				iInventoryServices.updateModel(model);
				
				clear(event);
				
			}
		}
		
		
		
		modelGetAll();
	}
	
	
	
	public void modelDelete(ActionEvent event) {
		
		ObservableList<Model> selectedModel;
		
		//allItems=itemTable.getItems();
		
		selectedModel=tblvModel.getSelectionModel().getSelectedItems();
		
		if(selectedModel.isEmpty()) {
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Model");
			alert.showAndWait();
			
		}else {
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Delete Selected Model ?");
			Optional <ButtonType> action= alert.showAndWait();
			
			if(action.get()==ButtonType.OK) {
				
				
				String model_name=selectedModel.get(0).getModel().toString();
				
				iInventoryServices.deleteModel(model_name);
				
				clear(event);
			}
		}
		
		
		
		modelGetAll();
	}
	
	public void modelGetAll() {
		
		tblcModel.setCellValueFactory(new PropertyValueFactory<>("model"));
		tblcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
		tblcSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		tblcUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		tblcSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
		tblcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		tblcWarranty.setCellValueFactory(new PropertyValueFactory<>("warranty"));
		
		modelList=iInventoryServices.getAllModels();
		
		tblvModel.setItems(modelList);
	}
	
	
	public boolean modelValidation() {
		

		if((cmbCategory.getSelectionModel().getSelectedItem()==null)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Category ");
			alert.showAndWait();
			return false;
		}
		if((cmbSupplier.getSelectionModel().getSelectedItem()==null)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Supplier ");
			alert.showAndWait();
			return false;
		}
		if(txtbModel.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Model Name ");
			alert.showAndWait();
			return false;
		}
		
		Pattern pattern_model_name = Pattern.compile("[a-zA-Z0-9 ]*");
		Matcher matcher_model_name = pattern_model_name.matcher(txtbModel.getText());
		
		if(!matcher_model_name.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Model Name Can not Contains Special Character ");
			alert.showAndWait();
			return false;
		}
		
		
		Pattern pattern_number = Pattern.compile("[0-9]*");
		Matcher matcher_unit_price = pattern_number.matcher(txtbUnitPrice.getText());
		Matcher matcher_selling_price = pattern_number.matcher(txtbSellingPrice.getText());
		Matcher matcher_quantity_price = pattern_number.matcher(txtbQuantity.getText());
		
		
		
		
		if(txtbUnitPrice.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Unit Price ");
			alert.showAndWait();
			return false;
		}
		
		if(!matcher_unit_price.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Unit Price Can Contains Only Numbers ");
			alert.showAndWait();
			return false;
		}
		
		
		if(txtbSellingPrice.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Selling Price ");
			alert.showAndWait();
			return false;
		}
		
		
		if(!matcher_selling_price.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Selling Price Can Contains Only Numbers ");
			alert.showAndWait();
			return false;
		}
		
		if(txtbQuantity.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter An Quantity ");
			alert.showAndWait();
			return false;
		}
		
		
		
		if(!matcher_quantity_price.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Quantity Can Contains Only Numbers ");
			alert.showAndWait();
			return false;
		}
		
		
		if((cmbWarranty.getSelectionModel().getSelectedItem()==null)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Warranty Years ");
			alert.showAndWait();
			return false;
		}
		
		
		
		
		
		 
	     
	  
	 
		
		return true;
		
		
	}
	
	public void modelAdd(ActionEvent event) {
		
		
		/*if((cmbCategory.getSelectionModel().getSelectedItem()==null)||(cmbSupplier.getSelectionModel().getSelectedItem()==null)||
				txtbModel.getText().isEmpty()||txtbUnitPrice.getText().isEmpty()||txtbSellingPrice.getText().isEmpty()||txtbQuantity.getText().isEmpty()||
				txtbWarranty.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter All Fields");
			alert.showAndWait();
			return;
		}
		
		String exp_model_name="^[&%$##@!~]";
		if(txtbModel.getText().matches(exp_model_name)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Letters And Numbers For Model Name");
			alert.showAndWait();
			return;
		}
		String num_val=" /^[0-9]+$/";
		if(!txtbUnitPrice.getText().matches(num_val)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Numbers For Unit Price");
			alert.showAndWait();
			return;
		}
		
		if(!txtbSellingPrice.getText().matches(num_val)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Numbers For Selling Price");
			alert.showAndWait();
			return;
		}
		
		if(!txtbQuantity.getText().matches(num_val)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Numbers Quantity");
			alert.showAndWait();
			return;
		}
		if(!txtbWarranty.getText().matches(num_val)) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Numbers For Warranty Years");
			alert.showAndWait();
			return;
		}*/
		
		
		if(modelValidation()) {
			
			Model model=new Model();
			
			model.setCategory((cmbCategory.getSelectionModel().getSelectedItem()).toString());
			model.setSupplier((cmbSupplier.getSelectionModel().getSelectedItem()).toString());
			model.setModel(txtbModel.getText());
			model.setUnitPrice(txtbUnitPrice.getText());
			model.setSellingPrice(txtbSellingPrice.getText());
			model.setQuantity(txtbQuantity.getText());
			model.setWarranty((cmbWarranty.getSelectionModel().getSelectedItem()).toString());
			
			String message = iInventoryServices.addModel(model);
		
			if( !message.equalsIgnoreCase("error") ) {
				clear(event);
			}
			
			modelGetAll();
			
			
			txtbModel.setEditable(true);
		}
		
		
		
		
	}
	
	
	
	public void categoryAdd(ActionEvent event) {
		
		
		Pattern pattern_category = Pattern.compile("[a-zA-Z0-9 ]*");
		Matcher matcher_add_category = pattern_category.matcher(txtbAddNewCategory.getText());
		
		
		if(txtbAddNewCategory.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A New Category Name ");
			alert.showAndWait();
			return ;
		}
		
		if(!matcher_add_category.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Category Name Can not Contains Special Character ");
			alert.showAndWait();
			return ;
		}
		
		
		
		iInventoryServices.addNewCategory(txtbAddNewCategory.getText());;
	
		txtbAddNewCategory.setText("");
		
		categoryList=iInventoryServices.getCategoryNames();
		cmbCategory.setItems(categoryList);
		
		modelGetAll();
	
	}

	
	public void categoryDelete(ActionEvent event) {
		
		if(txtbDeleteCategory.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter A Category");
			alert.showAndWait();
		}else {
			
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Delete Selected Model ?");
			Optional <ButtonType> action= alert.showAndWait();
			
			if(action.get()==ButtonType.OK) {
				
				iInventoryServices.deleteCategory(txtbDeleteCategory.getText());;
			}
		}
		
		
	
		txtbDeleteCategory.setText("");
		
		categoryList=iInventoryServices.getCategoryNames();
		
		cmbCategory.setItems(categoryList);
		
		modelGetAll();	
		
	}


	
	
	
	
	
	
	
	public void clear(ActionEvent event) {
		cmbCategory.getSelectionModel().clearSelection();
		cmbSupplier.getSelectionModel().clearSelection();
		txtbModel.setText("");
		txtbUnitPrice.setText("");
		txtbSellingPrice.setText("");
		txtbQuantity.setText("");
		cmbWarranty.getSelectionModel().clearSelection();
		txtbAddNewCategory.setText("");
		txtbDeleteCategory.setText("");
		
		txtbModel.setEditable(true);
		
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
	
	
	
	
	
	//end Screen changing functions

	
	
}
