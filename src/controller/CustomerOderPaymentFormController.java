package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Cart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import util.Generator;
import model.CustomerOrder;
import model.Order;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import model.CustomerPayment;
import model.Payment;
import service.CustomerOrderServiceImpl;
import service.ICustomerOrderService;

public class CustomerOderPaymentFormController implements Initializable{

	public static final String REPORT_LOAD_PATH  = "C:\\Users\\Ganesh\\Desktop\\SMS_REPORT_NEW\\Showroom_Management_System\\src\\report\\CustomerOrderInvoice.jrxml";
	
	public static final String REPORT_STORE_PATH = "C:\\Users\\Ganesh\\Desktop\\Reports\\";
	
	private Cart cart;

	@FXML
	public Button closeButton;

	@FXML
	private ComboBox<String> paymentType;

	@FXML
	private Label currentDate;

	@FXML
	private Label currentTime;

	@FXML
	private Label amount;
	
    @FXML
    private RadioButton deliveryTrue;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton deliveryFalse;

	
	public ObservableList<String> list =  FXCollections.observableArrayList("Cash",  "Card");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		paymentType.setItems(list);
	}

	public void loadCart(Cart cart) {

		this.cart =  cart;
		
		currentDate.setText(Generator.getCurrentDate());
		currentTime.setText(Generator.getCurrentTime());
		amount.setText(Double.toString(cart.getNetCartTotal()));
	}

	/*------------------Exit Button---------------------------*/
	public void ExitWindow(ActionEvent event) throws Exception {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
	
    @FXML
    void confirmPayment(ActionEvent event) {

    	if( paymentType.getSelectionModel().getSelectedItem() == ""  || paymentType.getSelectionModel().getSelectedItem() == null ) {
    		Generator.getAlert("Payment For Error", "Please Select a Payment Type");
    		return;
    	}
    	
    	System.out.println("Cart ID : " + cart.getCartID());
    	
    	ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
    	
    	String customerOrderID = Generator.generateOrderID();
    	//record the order details 
    	CustomerOrder order = new CustomerOrder();
    	
    	order.setOID(customerOrderID);
    	order.setCartID(cart.getCartID());
    	order.setOtime(currentTime.getText());
    	order.setTotal(Double.parseDouble(amount.getText()));
    	order.setOdate(currentDate.getText());
	
    	if( deliveryTrue.isSelected() ) {
    		order.setDeliveryStatus("true");
    	}else if( deliveryFalse.isSelected() ){
    		order.setDeliveryStatus("false");
    	}
    	
    	order.setDeliveryID(null);
    	
    	icustOrder.addCustomerOrderDetails(order);
    	
    	//record payment details
    	CustomerPayment payment = new CustomerPayment();
    	
    	String paymentTime = Generator.getCurrentTime();
    	
    	payment.setPaymentID(Generator.generatePaymentID());
    	payment.setDate(Generator.getCurrentDate());
    	payment.setTime(paymentTime);
    	payment.setAmount(amount.getText());
    	payment.setType(paymentType.getValue());
    	payment.setOrder_id(customerOrderID);
    	
    	String cartID = cart.getCartID();
    	String orderDate = Generator.getCurrentDate();
    	String totalAmount = amount.getText();
    	String type = paymentType.getValue();
    	
    	icustOrder.addCustomerPaymentDetails(payment);
    	
    	Generator.getAlert("Success", "1 Order record recorded");
    	Generator.getAlert("Success", "1 Payment record recorded");
    	
    	//--------------------------- Send Notification  ----------------------------//
    	
    	String customerEmail = icustOrder.getCustomerEmailByID(cart.getCustomerID());
    	String customerFName = icustOrder.getCustomerFirstNameByID(cart.getCustomerID());
    	String customerLName = icustOrder.getCustomerLastNameByID(cart.getCustomerID());
    	
    	String title =  "Order Successfull - Crown Investments (Pvt) Ltd";
    	String body = "Dear Customer " +customerFName + " "+ customerLName +" Your order : " + customerOrderID + " has been placed successfully . On "+orderDate+" at " + paymentTime + " with a Payment Amount of Rs : " + totalAmount +" --- Thank You !";
    	
    	Generator.sendNotification(customerEmail, title, body);
    	//--------------------Generate customer Order Report ------------------------//
    	icustOrder.generateCOInvoice(REPORT_LOAD_PATH, cartID, REPORT_STORE_PATH+customerOrderID+"-Invoice.pdf", customerOrderID, orderDate, paymentTime, totalAmount, type, cart.getCustomerID());
    	
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
    }

}
