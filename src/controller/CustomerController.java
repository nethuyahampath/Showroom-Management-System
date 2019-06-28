package controller;

import model.Main;
import model.User;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import model.Customer;
import util.DBconnection;
import util.Generator;

import java.awt.Dimension;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomerController implements Initializable{

	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
   
    public void customerReport(ActionEvent event)throws Exception{

    	try {
    		
    		connection = DBconnection.getConnection();
    		
    		
    		
    		JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\Ganesh\\Desktop\\SMS_REPORT_NEW\\Showroom_Management_System\\src\\report\\Cherry_Landscape.jrxml");
    		
    		//get the query
    		String query = "SELECT * FROM customer";
    		JRDesignQuery jrQuery = new JRDesignQuery();
    		jrQuery.setText(query);
    		jasperDesign.setQuery(jrQuery);
    		
    		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , null , connection );
    		JRViewer viewer = new JRViewer( jasperPrint );

    		
    		JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\Ganesh\\Desktop\\Reports\\customerReport.pdf" );
    		
    	
    		//to view the jasper report in the pdf viewer -  (can be used for a button to view the report)
    		viewer.setOpaque(true);
    		viewer.setVisible(true);
    		
    		JFrame frame = new JFrame("Jasper report");
            frame.add(viewer);
            frame.setSize(new Dimension(800, 500));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
    	}catch(Exception e) {
    		System.out.println("Exception : " + e );
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
	
	public void complaintManageScreen( ActionEvent event ) throws Exception{
		
		if(userObj.getType().equalsIgnoreCase("Salesman")) {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Complainview.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			ComplainController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
		}else {
			
			Generator.getAlert("Access Denied", "You don't have access to Customer Management");
		}
		
	}
	//end Screen changing functions
	
	
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	Connection connection;
	
	
	
	
	@FXML
	TableView<Customer> CusTable;
	@FXML
	private TableColumn<?, ?> Fnamecoll;
	@FXML
	private TableColumn<?, ?> Lnamecoll;
	@FXML
	private TableColumn<?, ?> NICcoll;
	@FXML
	private TableColumn<?, ?> Emailcoll;
	@FXML
	private TableColumn<?, ?> TeleNmcoll;
	@FXML
	private TableColumn<?, ?> Addresscoll;
	@FXML
	private TableColumn<?, ?> Custidcoll;
	
	public ObservableList<Customer> CusList = FXCollections.observableArrayList();
	
	
	@FXML
	private TextField txtFirstname;
	@FXML
	private TextField txtLastname;
	@FXML
	private TextField txtNIC;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtTelnum;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField txtCustID;
	
	
	
	
	
	


	
	
	
@FXML	
public void ViewCustomerDetails() {
		
		String query =  "select * from customer";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			
			
			while(resultSet.next())
			{
				CusList.add(new Customer(
						
						resultSet.getString("cust_id"),
					    resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("NIC"),
						resultSet.getString("emai"),
						resultSet.getString("contact_no"),
						resultSet.getString("address")
						//resultSet.getString("cust_id")
						));
				
			}
			preparedStatement.close();
			resultSet.close();
		}catch (Exception e) {
			
			System.out.println(e);
		}
	}


	
	@FXML
	public void AddCustomerDetails(ActionEvent event) throws SQLException {
		
	if(validateFname() & validateNumber()&validateNIC() &
			validateLname() &validateFields()) { 
		
		try {
		
		String ID = txtCustID.getText();
		String Firstname = txtFirstname.getText();
		String Lastname = txtLastname.getText();
		String NIC = txtNIC.getText();
		String Email = txtEmail.getText();
		String Contactnum = txtTelnum.getText();
		String Address = txtAddress.getText();
		
		String query = "insert into customer (cust_id,first_name,last_name,NIC,emai,contact_no,address) values(?,?,?,?,?,?,?)";
		
		preparedStatement = null;
		
		
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, ID);
			preparedStatement.setString(2, Firstname);
			preparedStatement.setString(3, Lastname);
			preparedStatement.setString(4, NIC);
			preparedStatement.setString(5, Email);
			preparedStatement.setString(6, Contactnum);
			preparedStatement.setString(7, Address);
	
		
		
		
		}catch (SQLException e) {
			System.out.println("Adding customers : "+ e);
			System.out.println(e);
		}finally {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informaion Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Customer Details Inserted");
			alert.showAndWait();
			
			
			preparedStatement.execute();
			preparedStatement.close();
		}
		
		txtFirstname.clear();
		txtLastname.clear();
		txtNIC.clear();
		txtEmail.clear();
		txtTelnum.clear();
		txtAddress.clear();
		
		CusList.clear();
		
		ViewCustomerDetails();
		
	}
	
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		connection = DBconnection.getConnection(); 
		
		CusTable.setItems(CusList);
		
		Fnamecoll.setCellValueFactory(new PropertyValueFactory<>("First_Name"));
		Lnamecoll.setCellValueFactory(new PropertyValueFactory<>("Last_Name"));
		NICcoll.setCellValueFactory(new PropertyValueFactory<>("NIC"));
		Emailcoll.setCellValueFactory(new PropertyValueFactory<>("Email"));
		TeleNmcoll.setCellValueFactory(new PropertyValueFactory<>("Contact_Num"));
		Addresscoll.setCellValueFactory(new PropertyValueFactory<>("Address"));
		Custidcoll.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		
		ViewCustomerDetails();
		
		
	
	}
	
	@FXML
    void UpdateCustomerDetails(ActionEvent event) {
		if(validateFname() & validateNumber() &validateNIC() & validateLname()) { 
             try {		
			String query = "update customer set first_name = ? ,NIC = ?, last_name = ?, address = ?,  contact_no = ? , emai = ? where NIC= '"+txtNIC.getText()+"' ";
		 
			
		
			
			String Firstname = txtFirstname.getText();
			String Lastname = txtLastname.getText();
			String NIC = txtNIC.getText();
			String Email = txtEmail.getText();
			String Contactnum = txtTelnum.getText();
			String Address = txtAddress.getText();
			
			
			preparedStatement = connection.prepareStatement(query);
			
			
			preparedStatement.setString(1, Firstname);
			preparedStatement.setString(2, Lastname);
			preparedStatement.setString(3, NIC);
			preparedStatement.setString(4, Email);
			preparedStatement.setString(5, Contactnum);
			preparedStatement.setString(6, Address);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informaion Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Customer Details Has Benn Updated");
			alert.showAndWait();
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			}catch(SQLException e){
				System.out.println(e);
		}
		txtFirstname.clear();
		txtLastname.clear();
		txtNIC.clear();
		txtEmail.clear();
		txtTelnum.clear();
		txtAddress.clear();
		
		CusList.clear();
		
		
			ViewCustomerDetails();
	}	
	}

@FXML
private void DeleteCustomer(ActionEvent event) {
	if(validateNIC()) {
	try {
	String  query = "delete from customer where NIC = ? " ;
	
	
		
		
		preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, txtNIC.getText());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informaion Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Customer Details Deleted");
		alert.showAndWait();
		
		preparedStatement.executeUpdate();
	
		ViewCustomerDetails();
		
		preparedStatement.close();
		
	    }catch (SQLException e) {
		
	           System.out.println(e);
		
	      }
	           txtFirstname.clear();
	           txtLastname.clear();
	           txtNIC.clear();
	           txtEmail.clear();
	           txtTelnum.clear();
	           txtAddress.clear();
	
	CusList.clear();
	
	ViewCustomerDetails();
	
	
	}
	
	}

@FXML
public void SearchViewCustomerDetails(ActionEvent event) {
	
	if(validateNIC()) {
	try {
		
	String query =  "select * from customer where NIC = ? ";
	
	
		
		preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, txtNIC.getText());
		
		resultSet = preparedStatement.executeQuery();
		
		
		
		
		
		while(resultSet.next())
		{
			CusList.add(new Customer(
					
					resultSet.getString("cust_id"),
				    resultSet.getString("first_name"),
					resultSet.getString("last_name"),
					resultSet.getString("NIC"),
					resultSet.getString("emai"),
					resultSet.getString("contact_no"),
					resultSet.getString("address")
					//resultSet.getString("cust_id")
					));
			
		}
		preparedStatement.close();
		resultSet.close();
	}catch (Exception e) {
		
		System.out.println(e);
	
	}

	txtFirstname.clear();
    txtLastname.clear();
    txtNIC.clear();
    txtEmail.clear();
    txtTelnum.clear();
    txtAddress.clear();
}   

	}




private boolean validateFields() {
	if(txtFirstname.getText().isEmpty()) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Warning...!!!");
		alert.setHeaderText(null);
		alert.setContentText("Please Enter First Name");
		alert.showAndWait();
		
		return false;
	}
	
	if(txtLastname.getText().isEmpty()) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Warning...!!!");
		alert.setHeaderText(null);
		alert.setContentText("Please Enter Last Name");
		alert.showAndWait();
		
		return false;
	}
	
	if(txtNIC.getText().isEmpty()) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Warning...!!!");
		alert.setHeaderText(null);
		alert.setContentText("Please Enter NIC");
		alert.showAndWait();
		
		return false;
	}
if(txtAddress.getText().isEmpty()) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Warning...!!!");
		alert.setHeaderText(null);
		alert.setContentText("Please Enter Address");
		alert.showAndWait();
		
		return false;
	}		
	
	
	if(txtEmail.getText().isEmpty()) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Warning...!!!");
		alert.setHeaderText(null);
		alert.setContentText("Please Enter Email");
		alert.showAndWait();
		
		return false;
	}
	
	if(txtTelnum.getText().isEmpty()) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Warning...!!!");
		alert.setHeaderText(null);
		alert.setContentText("Please Enter Contact Number");
		alert.showAndWait();
		
		return false;
	}		
	return true;

}
private boolean validateFname() {
	Pattern p = Pattern.compile("[a-zA-Z]+");
	Matcher m = p.matcher(txtFirstname.getText());
	if(m.find()&&m.group().equals(txtFirstname.getText())) {
		return true;
	}else {
		 
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Validate Warning...!!!");
				alert.setHeaderText(null);
				alert.setContentText("Enter Valid First Name");
				alert.showAndWait();
				
				return false;
		
	}
	}
private boolean validateLname() {
	Pattern p = Pattern.compile("[a-zA-Z]+");
	Matcher m = p.matcher(txtLastname.getText());
	if(m.find()&&m.group().equals(txtLastname.getText())) {
		return true;
	}else {
		 
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Validate Warning...!!!");
				alert.setHeaderText(null);
				alert.setContentText("Enter Valid Last Name");
				alert.showAndWait();
				
				return false;
		
	}
	}


private boolean validateNumber() {
	Pattern p = Pattern.compile("[0-9]+");
	Matcher m = p.matcher(txtTelnum.getText());
	if(m.find() &&m.group().equals(txtTelnum.getText())) {
		return true;
	
	}else {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Validate Warning...!!!");
		alert.setHeaderText(null);
		alert.setContentText("Enter Valid Number");
		alert.showAndWait();
		
		return false;
	}
     
}



public boolean validateNIC() {
	Pattern p = Pattern.compile("[0-9]{9}[V]+");
	Matcher m = p.matcher(txtNIC.getText());
	if(m.find() && m.group().equals(txtNIC.getText())){
		return true;
	}else {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Validate Warning...!!!");
		alert.setHeaderText(null);
		alert.setContentText("Enter Valid NIC");
		alert.showAndWait();
		
		return false;
	}

	
}
}
