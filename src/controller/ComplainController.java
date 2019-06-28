package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Complain;
import model.Main;
import model.User;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import util.DBconnection;
import util.Generator;

public  class ComplainController implements Initializable {


	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
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
	
	
	
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	Connection connection;
	
	
	
	
	@FXML
	TableView<Complain> CusCompTable;
	@FXML
	private TableColumn<?, ?> complainidcoll;
	@FXML
	private TableColumn<?, ?> descripcoll;
	@FXML
	private TableColumn<?, ?> datecoll;
	@FXML
	private TableColumn<?, ?> timecoll;
	@FXML
	private TableColumn<?, ?> custidcoll;
	@FXML
	
	
	public ObservableList<Complain> CompList = FXCollections.observableArrayList();
	
	
	@FXML
	private TextField txtComplain_id;
	@FXML
	private TextField txtDescription;
	@FXML
	private TextField txtDate;
	@FXML
	private TextField txtTime;
	@FXML
	private TextField txtCust_id;

	
	
	
	
	


	
	
	
@FXML	
public void ViewComplainDetails() {
		
		String query =  "select * from customer_complain";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			
			
			while(resultSet.next())
			{
				CompList.add(new Complain(
						
					    resultSet.getString("complain_id"),
						resultSet.getString("description"),
						resultSet.getString("complain_date"),
						resultSet.getString("complain_time"),
						resultSet.getString("cust_id")
						));
					
				
			}
			preparedStatement.close();
			resultSet.close();
		}catch (Exception e) {
			
			System.out.println(e);
		}
	}


	
	@FXML
	public void AddComplain(ActionEvent event) throws SQLException {
		
		if(validateFields()) { 
		
		try {
		
		String Firstname = txtComplain_id.getText();
		String Lastname = txtDescription.getText();
		String NIC = txtDate.getText();
		String Email = txtTime.getText();
		String Contactnum = txtCust_id.getText();
	
		
		String query = "insert into customer_complain (complain_id,description,complain_date,complain_time,cust_id) values(?,?,?,?,?)";
		
		preparedStatement = null;
		
		
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, Firstname);
			preparedStatement.setString(2, Lastname);
			preparedStatement.setString(3, NIC);
			preparedStatement.setString(4, Email);
			preparedStatement.setString(5, Contactnum);
	
		
		
		
		}catch (SQLException e) {
			
			System.out.println(e);
		}finally {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informaion Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Complain Details Inserted");
			alert.showAndWait();
			
			
			preparedStatement.execute();
			preparedStatement.close();
		}
		
		txtComplain_id.clear();
		txtDescription.clear();
		txtDate.clear();
		txtTime.clear();
		txtCust_id.clear();
		
		
		CompList.clear();
		
		ViewComplainDetails();
		
	}
	
	}
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		
connection = DBconnection.getConnection(); 
		
  CusCompTable.setItems(CompList);
		
        complainidcoll.setCellValueFactory(new PropertyValueFactory<>("compailn_id"));
        descripcoll.setCellValueFactory(new PropertyValueFactory<>("discription"));
        datecoll.setCellValueFactory(new PropertyValueFactory<>("compalin_time"));
        timecoll.setCellValueFactory(new PropertyValueFactory<>("compalin_date"));
        custidcoll.setCellValueFactory(new PropertyValueFactory<>("cust_ID"));
		
		
		ViewComplainDetails();
	}
 
	@FXML
	private void DeleteComplain(ActionEvent event) {
		if(ComplainisEmpty()) {
		try {
		String  query = "delete from customer_complain where complain_id = ? " ;
		
		
			
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1,txtComplain_id.getText());
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informaion Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Complain Details Deleted");
			alert.showAndWait();
			
			preparedStatement.executeUpdate();
		
			ViewComplainDetails();
			
			preparedStatement.close();
			
		    }catch (SQLException e) {
			
		           System.out.println(e);
			
		      }
		txtComplain_id.clear();
		txtDescription.clear();
		txtDate.clear();
		txtTime.clear();
		txtCust_id.clear();
		
		
		CompList.clear();
		
		ViewComplainDetails();
		
		
		}
		
		}
	private boolean validateFields() {
		if(txtComplain_id.getText().isEmpty()) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Complain ID");
			alert.showAndWait();
			
			return false;
		}
		
		if(txtDescription.getText().isEmpty()) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Description");
			alert.showAndWait();
			
			return false;
		}
		
		if(txtDate.getText().isEmpty()) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Date");
			alert.showAndWait();
			
			return false;
		}
	if(txtTime.getText().isEmpty()) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Time");
			alert.showAndWait();
			
			return false;
		}		
		
		
		if(txtCust_id.getText().isEmpty()) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning...!!!");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Customer ID");
			alert.showAndWait();
			
			return false;
		}
		return true;
		
	}	
		private boolean ComplainisEmpty() {
			
			if(txtComplain_id.getText().isEmpty()) {
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Warning...!!!");
					alert.setHeaderText(null);
					alert.setContentText("Please Enter Complain ID");
					alert.showAndWait();
					
					return false;
				}		
				 return true;

}
}
