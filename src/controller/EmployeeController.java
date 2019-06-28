package controller;

import model.Main;





import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.xml.validation.Validator;
//import javax.xml.validation.ValidatorHandler;

//import com.mysql.cj.protocol.x.ResultCreatingResultListener;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import util.Generator;
import util.sqlConnection;
import model.Emp;
import model.User;
import service.EmployeeServiceImpl;
import service.IEmployeeService;


public class EmployeeController implements Initializable{
	
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	Connection connection;
	
	 @FXML
	 private Label ID;
	 @FXML
	 private Label usertype;

	private User userObj = new User();
	
	public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
	}


	@FXML
    private TextField txtEmpid;

    @FXML
    private TextField txtfName;

    @FXML
    private TextField txtlName;

    @FXML
    private TextField txtnic;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtmail;

    @FXML
    private TextField txtxTel;
   
   @FXML
    private ComboBox<String> combo;
    
    ObservableList<String> list1 = FXCollections.observableArrayList("HR Manager","Salesman","Transport Manager","suplier Manager","stock Manager");
    
    @FXML
    private Button addBtn;

    public ObservableList<Emp> list = FXCollections.observableArrayList();
    
    @FXML
    TableView<Emp> table;

    @FXML
    private TableColumn<Emp, String> Empidcol;

    @FXML
    private TableColumn<Emp, String> Fnamecol;

    @FXML
    private TableColumn<Emp, String> Lnamecol;

    @FXML
    private TableColumn<Emp, String> niccol;

    @FXML
    private TableColumn<Emp, String> addresscol;

    @FXML
    private TableColumn<Emp, String> mailcol;

    @FXML
    private TableColumn<Emp, String> Telecol;
    
    @FXML
    private TableColumn<Emp, String> Designationcol;
    
	@FXML
	private Label userLabel;
	
	

	public  boolean validateEmpId() {
    	//Pattern p = Pattern.compile("[0-9]+");
    	//Matcher m = p.matcher(txtEmpid.getText());
    
    	Pattern p1 = Pattern.compile("[a-zA-Z]+");
    	Matcher m1 = p1.matcher(txtfName.getText());
    	if(txtfName.getText().isEmpty()) {
    		Generator.getAlert("Error", "Enter Employee First Name");
    		return false;
    	}
    	if(!m1.matches()) {
    		Generator.getAlert("Employee First Name field", "Invalid Character !");
    		return false;
    	}
    	if(txtaddress.getText().isEmpty()) {
    		Generator.getAlert("Error", "Enter Employee Address");
    		return false;
    	}
    	if(txtmail.getText().isEmpty()) {
    		Generator.getAlert("Error", "Enter Employee Mail");
    		return false;
    	}
    	
    		
    		return true;
    		
    		
    	}
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		connection = sqlConnection.Connector();
		
		table.setItems(list);
		
		Empidcol.setCellValueFactory(new PropertyValueFactory<Emp, String>("id"));
		Fnamecol.setCellValueFactory(new PropertyValueFactory<Emp, String>("f_name"));
		Lnamecol.setCellValueFactory(new PropertyValueFactory<Emp, String>("l_name"));
		niccol.setCellValueFactory(new PropertyValueFactory<Emp, String>("nic"));
		addresscol.setCellValueFactory(new PropertyValueFactory<Emp, String>("add"));
		mailcol.setCellValueFactory(new PropertyValueFactory<Emp, String>("mail"));
		Telecol.setCellValueFactory(new PropertyValueFactory<Emp, String>("Tel"));
		Designationcol.setCellValueFactory(new PropertyValueFactory<Emp, String>("designation"));
		
		loadDBdata();
		
		combo.setItems(list1);
	}

	public void loadDBdata() {
		
		String query = "select * from employee";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				list.add(new Emp(
						resultSet.getString("emp_id"), 
						resultSet.getString("first_name"), 
						resultSet.getString("last_name"), 
						resultSet.getString("NIC"),
						resultSet.getString("address"), 
						resultSet.getString("email"),
						resultSet.getString("contact_no"),
						resultSet.getString("designation")
						));
			}
			preparedStatement.close();
			resultSet.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} 
	}

	/*-----------------ADD NEW EMPLOYEES---------------*/
	
	IEmployeeService iEmployeeService = new EmployeeServiceImpl();
	
    @FXML
    public void AddNewEmp() throws SQLException {  		
    	
    	
    	Emp emp = new Emp();
    if(validateEmpId()) {	
    	
    	emp.setId(Generator.generateEmployeeID());
    	emp.setF_name(txtfName.getText());
    	emp.setL_name(txtlName.getText());
    	emp.setNic(txtnic.getText());
    	emp.setAdd(txtaddress.getText());
    	emp.setMail(txtmail.getText());
    	emp.setTel(txtxTel.getText());
    	System.out.println("Telephone num in controller : " + txtxTel.getText());
    	emp.setDesignation(combo.getValue());
    	
    	iEmployeeService.AddNewEmployee(emp);
    
	}else {
	    Generator.getAlert("Error", "Invalid Characters");
	}
			
	    	txtEmpid.clear();
	    	txtfName.clear();
	    	txtlName.clear();
	    	txtnic.clear();
	    	txtaddress.clear();
	    	txtmail.clear();
	    	txtxTel.clear();
	    	list.clear();
	    	
	    	loadDBdata();
    	
    }

    static String tempNic;
    @FXML
    public void ShowOnClick() {
    	try {
			Emp emp = table.getSelectionModel().getSelectedItem();
			String query = "select * from employee";
			preparedStatement = connection.prepareStatement(query);
			
			tempNic = emp.getNic();
			
			txtEmpid.setText(emp.getId());
			txtfName.setText(emp.getF_name());
			txtlName.setText(emp.getL_name());
			txtnic.setText(emp.getNic());
			txtaddress.setText(emp.getAdd());
			txtmail.setText(emp.getMail());
			txtxTel.setText(emp.getTel());
			combo.setValue(emp.getDesignation());
			
			preparedStatement.close();
			resultSet.close();
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}
    }

    /*--------------Update Employee-------------*/
    @FXML
    public void UpdateEmp(ActionEvent event) {
    	
    	Emp emp = new Emp();
    	
    	emp.setId(txtEmpid.getText());
    	emp.setF_name(txtfName.getText());
    	emp.setL_name(txtlName.getText());
    	emp.setNic(txtnic.getText());
    	emp.setAdd(txtaddress.getText());
    	emp.setMail(txtmail.getText());
    	emp.setTel(txtxTel.getText());
    	emp.setDesignation(combo.getValue());
    	
    	iEmployeeService.UpdateEmployee(emp);
    				
			txtEmpid.clear();
    		txtfName.clear();
    		txtlName.clear();
    		txtnic.clear();
    		txtaddress.clear();
    		txtmail.clear();
    		txtxTel.clear();
    		list.clear();
    		loadDBdata();
    	
    }
    
    @FXML
    public void delEmp(ActionEvent event) {
    	
    	Emp emp = table.getSelectionModel().getSelectedItem();
    	String emp_id = txtEmpid.getText();
    	
    	iEmployeeService.delEmployee(txtEmpid.getText());
    	
    	txtEmpid.clear();
		txtfName.clear();
		txtlName.clear();
		txtnic.clear();
		txtaddress.clear();
		txtmail.clear();
		txtxTel.clear();
		list.clear();
    	loadDBdata();
    }
	
    
    /*--------------------Demo------------------------------*/
    @FXML
    void addDemo(ActionEvent event) {
    	
    	//String query = "insert into employee (emp_id,first_name,last_name,NIC,address,email,contact_no,designation) values('10','Kevin','Max','955555555V','address11','kevin@gmail.com','0757577777','Salesman')";
    
    	try {
					
			txtEmpid.setText("10");
			txtfName.setText("Kevin");
			txtlName.setText("Max");
			txtnic.setText("955555555V");
			txtaddress.setText("address11");
			txtmail.setText("kevin@gmail.com");
			txtxTel.setText("0757577777");
			combo.setValue("Salesman");
			
			preparedStatement.close();
			resultSet.close();
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}
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
		Parent view = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
		Scene scene = new Scene(view);
		scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
		Stage window = Main.getStageObj();
		
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
	

    public void empAttendenceScreen(ActionEvent event) throws Exception {
		
    	if( userObj.getType().equalsIgnoreCase("HR Manager") ) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/EmployeeAttendence.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			EmployeeAttendenceController controller = loader.getController();
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

	  @FXML
	  void empRecordScreen(ActionEvent event) throws Exception {
		  
			if(userObj.getType().equalsIgnoreCase("HR Manager")) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/EmployeeAttendenceReport.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				EmployeeReportController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Delivery Management");
			}
	  }

}
