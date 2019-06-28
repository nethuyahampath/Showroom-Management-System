package controller;

import model.Main;
import model.User;
import model.Cart;

import java.awt.event.ItemListener;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
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

import model.BrandNewItem;
import service.CustomerOrderServiceImpl;
import service.EmployeeServiceImpl;
import service.ICustomerOrderService;
import service.IEmployeeService;
import util.Generator;

public class CustomerOrderController implements Initializable{
	
	
	/*---------------------------------------------------------------------------------------*/
	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
	
	private Cart cart = new Cart();
	
	/*--------------full cart table-------------------*/
	@FXML 
	private Label cartID;
	
	@FXML
	private Label customerNICFieldError;
	
	@FXML 
	private Label customerID;
	
	@FXML
	private Label fullCartTotal;

	@FXML
	private TextField customerNIC;
	
	@FXML
	private TableView<Cart> fullCart;
	
	@FXML
	private TableColumn<Cart, String> itemCode;
	
	@FXML
	private TableColumn<Cart, String> modelName;
	
	@FXML  
	private TableColumn<Cart, Double> sellingPrice; 
	
	private ObservableList<Cart> fullCartList = FXCollections.observableArrayList();
	
	private double totalAmount = 0;
	
	@FXML
	private TextField deleteTextBox;
	
	@FXML 
	private Label deleteFieldError;
	
	/*------------------Cart Summary------------------*/
	
	@FXML
	private Label currentDate;
	
	@FXML
	private Label discountAmount;
	
	@FXML
	private Label discountAmountError;
	
	@FXML 
	private Label netTotalAmount;
	
	@FXML 
	private TableView<Cart> cartSummary;
	
	@FXML 
	private TableColumn<Cart, Integer> summaryQuantity;
	
	@FXML 
	private TableColumn<Cart, String > summaryModelName;
	
	@FXML
	private TableColumn<Cart, Double> summaryPrice;
	
	private ObservableList<Cart> cartSummaryList = FXCollections.observableArrayList();
	
	@FXML
	private TextField discountTextBox;
	
	/*----------------- Item Search  ----------------*/
	
	private ArrayList<String> searchArray = new ArrayList<String>();
	
	@FXML 
	private TextField itemSearch;
	
	@FXML
	private Label itemSearchFieldError;
	
	private AutoCompletionBinding<String> searchBinding;
	
	/*---------------------------------------------------------------------------------------*/
	
	/*---------------Add Customer ID ------------------------------*/
	public void addCustomerID( ActionEvent event ) throws Exception{

		String NIC = customerNIC.getText();
		
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		if( customerNIC.getText().isEmpty() ) {
			customerNICFieldError.setText("***You cannot keep NIC field empty****");
			return;
		}
		
		if( icustOrder.getCustomerIDByNIC(NIC) != ""  ) {
			
			customerID.setText(icustOrder.getCustomerIDByNIC(NIC));
			customerNICFieldError.setText("");
		}else {
			//print error message
			customerNICFieldError.setText("***Invalid Customer NIC number***");
		}
	}
	
	/*---------------Add Item to cart ---------------------------*/
	public void addItemOnAction(KeyEvent e) throws Exception{
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		if( e.getCode().equals(KeyCode.ENTER)) {
			
			if( icustOrder.cartInOrders(cartID.getText())) {
				Generator.getAlert("Order Error", "This cart is unavailable, please proceed to a new order");
				return;
			}
			
			if( customerID.getText().isEmpty() ) {
				String message = "You must add a customer to the cart in order to proceed";
				String title = "Full Cart Error";
				Generator.getAlert(title, message);
				return;
			}
			
			if( itemSearch.getText().isEmpty()  ) {
				itemSearchFieldError.setText("***You cannot keep the search empty***");
				return;
			}
			
			
			//split the search box text field
			String str = itemSearch.getText();
			String[] splited = str.split("\\s+");
			
			String itCode = splited[0];
			
			//check whether the entered item code is valid
			if( icustOrder.isInvalidSearchInput(itCode )) {
				itemSearchFieldError.setText("***Please enter a valid Item Code***");
				return;
			}
			
			if( icustOrder.ifItemsAreSold(itCode) == true ) {
				
				Generator.getAlert("Item Error", "The Item you entered is not available for selling ");
				itemSearch.setText("");
				doSearchBinding();
				return;
			}
			
			String modelName = icustOrder.getModelNameByItemCode(itCode);
			String currentDate = Generator.getCurrentDate();
			String custID = customerID.getText();
			
			double price = Double.parseDouble(icustOrder.getPriceByModelName(modelName));
			
			Cart cart = new Cart();
			
			cart.setCartID(cartID.getText());
			cart.setSellingPrice(price);
			cart.setCartDate(currentDate);
			cart.setCustomerID(custID);
			cart.setItemCode(itCode);
			
			//get total
			totalAmount += price;
			fullCartTotal.setText(Double.toString(totalAmount));
			
			icustOrder.addCartItem(cart);
			
			//update the item status after adding the item
			icustOrder.updateItemStatus("sold", itCode);
			
			//update the item quantity after adding the item
			icustOrder.decrementQuantityOnAdd(modelName);
			
			//remove all from the itemList search array Array List
			//refresh text binding
			searchArray.clear();
			showItemSearch();
			doSearchBinding(); //to refresh the text binding
			
			discountAmountError.setText("");
			itemSearchFieldError.setText("");
			itemSearch.setText(""); //set the search value to null
			showFullCart(); //refresh the page
			showCartSummary();
			calculateNetTotal();
		}
	}
	
	public void addItemToCart(ActionEvent event) throws Exception{
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		if( icustOrder.cartInOrders(cartID.getText())) {
			Generator.getAlert("Order Error", "This cart is unavailable, please proceed to a new order");
			return;
		}
		
		//validate the customer ID label display
		if( customerID.getText().isEmpty() ) {
			String message = "You must add a customer to the cart in order to proceed";
			String title = "Full Cart Error";
			Generator.getAlert(title, message);
			return;
		}
		
		if( itemSearch.getText().isEmpty()  ) {
			itemSearchFieldError.setText("***You cannot keep the search empty***");
			return;
		}

		
		//split the search box text field
		String str = itemSearch.getText();
		String[] splited = str.split("\\s+");
		
		String itCode = splited[0];
		
		//check whether the entered item code is valid
		if( icustOrder.isInvalidSearchInput(itCode )) {
			itemSearchFieldError.setText("***Please enter a valid Item Code***");
			return;
		}
		
		if( icustOrder.ifItemsAreSold(itCode) == true ) {
			
			Generator.getAlert("Item Error", "The Item you entered is not available for selling ");
			itemSearch.setText("");
			doSearchBinding();
			return;
		}
		
		
		String modelName = icustOrder.getModelNameByItemCode(itCode);
		String currentDate = Generator.getCurrentDate();
		String custID = customerID.getText();
		
		double price = Double.parseDouble(icustOrder.getPriceByModelName(modelName));
		
		Cart cart = new Cart();
		
		cart.setCartID(cartID.getText());
		cart.setSellingPrice(price);
		cart.setCartDate(currentDate);
		cart.setCustomerID(custID);
		cart.setItemCode(itCode);
		
		//get total
		totalAmount += price;
		fullCartTotal.setText(Double.toString(totalAmount));
		
		icustOrder.addCartItem(cart);
		
		//update the item status after adding the item
		icustOrder.updateItemStatus("sold", itCode);
		
		//update the item quantity after adding the item
		icustOrder.decrementQuantityOnAdd(modelName);
		
		//remove all from the itemList search array Array List
		//refresh text binding
		searchArray.clear();
		showItemSearch();
		doSearchBinding(); //to refresh the text binding
		
		discountAmountError.setText("");
		itemSearchFieldError.setText("");
		itemSearch.setText(""); //set the search value to null
		showFullCart(); //refresh the page
		showCartSummary();
		calculateNetTotal();
		
	
	}
	
	/*---------------Show Item search---------------------------*/
	public void showItemSearch() {
		
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		ArrayList<BrandNewItem> itemList = icustOrder.getAllBrandNewItemDetails();

		for( BrandNewItem item : itemList ) {
			searchArray.add( item.getItemCode() + " " + item.getModelName() );
			
		} //end for
	}
	
	/*--------------Search binding ----------------------------*/
	public void doSearchBinding() {
		
		searchBinding.dispose();
		searchBinding = TextFields.bindAutoCompletion(itemSearch, searchArray);
		
	}
	
	/*-------------------show full cart ---------------------- */
	public void showFullCart() {
		
		fullCartList.clear();
		
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		ArrayList<Cart> cartList = icustOrder.getAllCartDetails( cartID.getText());
		
		for( Cart cart : cartList ) {
			fullCartList.add(cart);
		} //end for
		
		itemCode.setCellValueFactory(new PropertyValueFactory<Cart, String>("itemCode"));
		modelName.setCellValueFactory(new PropertyValueFactory<Cart, String>("modelName"));
		sellingPrice.setCellValueFactory(new PropertyValueFactory<Cart, Double>("sellingPrice"));
		fullCart.setItems(fullCartList);
	}
	
	/*-------------------show cart summary---------------------*/
	public void showCartSummary() {
		
		cartSummaryList.clear();
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		ArrayList<String> models = new ArrayList<String>();
		
		for( Cart cart : fullCartList ) {
			
			models.add(cart.getModelName());
		} //end for
		
		String modelsArray[] = new String[models.size()];
		modelsArray = models.toArray(modelsArray);
		
		List<String> list = Arrays.asList(modelsArray);
		
		Set<String> set = new HashSet<String>(list); //remove all the duplicate model names
		
		for( String item : set ) {
			
			Cart sumCart = new Cart();
			
			int q = icustOrder.getModelCountInCart(item, cartID.getText());
			double price = Double.parseDouble(icustOrder.getPriceByModelName(item));
			
			price *=  q;
			
			sumCart.setModelName(item);
			sumCart.setQuantity(q);
			sumCart.setSellingPrice(price);

			cartSummaryList.add(sumCart);
		} //end for
			
		summaryModelName.setCellValueFactory(new PropertyValueFactory<Cart,String>("modelName"));
		summaryQuantity.setCellValueFactory(new PropertyValueFactory<Cart,Integer>("quantity"));
		summaryPrice.setCellValueFactory(new PropertyValueFactory<Cart, Double>("sellingPrice"));
	
		cartSummary.setItems(cartSummaryList);
	}
	
	/*------------------show cart ID on screen ---------------*/
	
	public void showCartID() {
		
		String ID = Generator.generateCartID();
		
		cartID.setText(ID);
	}
	

	
	/*------------------ clear cart  --------------------*/
	public void clearCart(ActionEvent event) throws Exception{
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		if( icustOrder.cartInOrders(cartID.getText())) {
			Generator.getAlert("Order Error", "This cart is unavailable, please proceed to a new order");
			return;
		}
		String title =  "Clear Cart";
		String message  = "Do you want to clear the cart ? ";
		
		if( Generator.getConfirmation(title, message)) {
			
			icustOrder.clearCart(cartID.getText());
			
			//update the item status after clearing the cart
			for( Cart cart : fullCartList ) {
				icustOrder.updateItemStatus("stock", cart.getItemCode());
				icustOrder.incrementQuantityOnRemove(cart.getModelName());
			}
			
			showFullCart();
			
			//refresh the  text binding
			searchArray.clear();
			showItemSearch();
			doSearchBinding(); 
			
			showCartSummary();
			
			totalAmount = 0;
			fullCartTotal.setText(Double.toString(totalAmount));
			discountAmount.setText(Integer.toString(0));
			netTotalAmount.setText(Double.toString(0.0));
		} //end if
	}
	
	/*-------------------add discount---------------------*/
	public void addDiscount(ActionEvent event) throws Exception{
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		if( icustOrder.cartInOrders(cartID.getText())) {
			Generator.getAlert("Order Error", "This cart is unavailable, please proceed to a new order");
			return;
		}
		
		int discountValue = Integer.parseInt(discountTextBox.getText());
		if( discountValue < 0 || discountValue > 100 ) {
			discountAmountError.setText("***Invalid Discount Amount***");
		}else {
			discountAmount.setText(discountTextBox.getText());
			discountAmountError.setText("");
		}
		
		calculateNetTotal(); //calculate the Net total
	}
	
	/*------------------calculate net total------------------*/
	public void calculateNetTotal() {
		
		
		double total = Double.parseDouble(fullCartTotal.getText());
		int discount  = 0;
		
		if( discountAmount.getText() ==  null || discountAmount.getText()  == "") {
			discount  = 0;
		}else {
		 discount = Integer.parseInt(discountAmount.getText());
		}
		
		double netTotal = total - ( total * discount / 100.0 );
		
		netTotalAmount.setText(Double.toString(netTotal));
	}
	
	/*--------------select Item from cart----------------------*/
	public void selectItem() {
		Cart cart = fullCart.getSelectionModel().getSelectedItem();
		
		deleteFieldError.setText("");
		deleteTextBox.setText(cart.getItemCode());
	}
	
	/*--------------delete item from cart -------------------*/
	public void removeItemFromCart(ActionEvent event) throws Exception{
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		if( icustOrder.cartInOrders(cartID.getText())) {
			Generator.getAlert("Order Error", "This cart is unavailable, please proceed to a new order");
			return;
		}
		
		String title = "Remove Item";
		String message = "Are you sure to delete Item : " + deleteTextBox.getText() + " from the cart ? ";
		
		if( deleteTextBox.getText().isEmpty()) {
			deleteFieldError.setText("***Please Select an Item to Delete***");
			return;
		}
		
		if( Generator.getConfirmation(title, message)) {
		
			icustOrder.removeItemFromCart(cartID.getText(), deleteTextBox.getText());
		
			//update the item status after removing the item from the cart
			icustOrder.updateItemStatus("stock", deleteTextBox.getText());
			
			//update the item quantity after removing the item from the cart
			icustOrder.incrementQuantityOnRemove(icustOrder.getModelNameByItemCode(deleteTextBox.getText()));
			
			Double price = Double.parseDouble(icustOrder.getPriceByModelName(icustOrder.getModelNameByItemCode(deleteTextBox.getText())));
		
			totalAmount -= price;
			fullCartTotal.setText(Double.toString(totalAmount));
		
			deleteTextBox.setText(null);
			showFullCart(); 
			//refresh the  text binding
			searchArray.clear();
			showItemSearch();
			doSearchBinding(); 
			showCartSummary();
			calculateNetTotal();
		} //end if
		
	}
	
	
	public void newOrder( ActionEvent event ) throws Exception{
		
		if(userObj.getType().equalsIgnoreCase("Salesman"))
		{
			
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
			Generator.getAlert("Access Denied", "You don't have access to Inventory Management");
		}
	}
	
	
	/*-------------------initialize----------------------*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		currentDate.setText(Generator.getCurrentDate()); //show current date
		showCartID(); //to display the cart ID
		
		calculateNetTotal();
		
		showItemSearch();
		searchBinding = TextFields.bindAutoCompletion(itemSearch, searchArray);
		showFullCart();
		showCartSummary();
		
	}
	
	/*---------------Display Item and Model Windows----------------*/
	public void showAllItems(ActionEvent event ) throws Exception{
		try {
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CustomerOrderItems.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.centerOnScreen();
			stage.setScene(scene);
			stage.show();
			
		}catch(Exception e) {
			System.out.println("Exception showing Items window :  ");
			System.out.println(e);
		}
	}
	
	public void showAllIModelDetails(ActionEvent event) throws Exception{
		try {
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CustomerOrderModelList.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.centerOnScreen();
			stage.setScene(scene);
			stage.show();
			
		}catch(Exception e) {
			System.out.println("Exception showing Items window :  ");
			System.out.println(e);
		}
	}
	
	/*-----------------proceed to checkout---------------------*/
	public void toCheckOut(ActionEvent event)  throws Exception{
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		if( icustOrder.cartInOrders(cartID.getText())) {
			Generator.getAlert("Order Error", "This cart is unavailable, please proceed to a new order");
			return;
		}
		try {
			
			//validate total before checking out
			if( netTotalAmount.getText().isEmpty() || netTotalAmount.getText().equals((Double.toString(0.0)))){
				String title = "Checkout Error";
				String message = "The net total amount cannot be null";
				
				Generator.getAlert(title, message);
				return;
			}
			
			/*------------load the cart object-----------------*/
			cart.setCartID(cartID.getText());
			cart.setCustomerID(customerID.getText());
			cart.setNetCartTotal(Double.parseDouble(netTotalAmount.getText()));
			
			
			/*-------------------------------------------------*/
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CustomerOrderPaymentForm.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			
			Stage stage = new Stage();
			Scene scene = new Scene(root1);
			
			CustomerOderPaymentFormController controller = fxmlLoader.getController();
			controller.loadCart(cart);
			
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.centerOnScreen();
			stage.setScene(scene);
			stage.show();
			
		}catch(Exception e) {
			System.out.println("Exception in showing payment for : " );
			System.out.println(e);
		}
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
		
		if( !netTotalAmount.getText().equals((Double.toString(0.0))) ) {
			Generator.getAlert("Error", "You cannot proceed to other with Items remaining in the cart ");
			return;
		}
		
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
		
		if( !netTotalAmount.getText().equals((Double.toString(0.0))) ) {
			Generator.getAlert("Error", "You cannot proceed to other with Items remaining in the cart ");
			return;
		}
		
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
		
		
		if( !netTotalAmount.getText().equals((Double.toString(0.0))) ) {
			Generator.getAlert("Error", "You cannot proceed to other with Items remaining in the cart ");
			return;
		}
		
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
		
		if( !netTotalAmount.getText().equals((Double.toString(0.0))) ) {
			Generator.getAlert("Error", "You cannot proceed to other with Items remaining in the cart ");
			return;
		}

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
		if( !netTotalAmount.getText().equals((Double.toString(0.0))) ) {
			Generator.getAlert("Error", "You cannot proceed to other with Items remaining in the cart ");
			return;
		}
		
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
		if( !netTotalAmount.getText().equals((Double.toString(0.0))) ) {
			Generator.getAlert("Error", "You cannot proceed to other with Items remaining in the cart ");
			return;
		}

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

		if( !netTotalAmount.getText().equals((Double.toString(0.0))) ) {
			Generator.getAlert("Error", "You cannot proceed to other with Items remaining in the cart ");
			return;
		}

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
