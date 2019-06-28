package controller;

import model.Main;
import model.User;
//import model.repairOrderFormModel;
import model.supplier;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import service.ISupplierService;
import service.SupplierImpl;
import util.sqlConnection;
import javafx.collections.transformation.SortedList;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.transformation.FilteredList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Predicate;
import util.Generator;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SupplierController implements Initializable{

	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	Connection connection;
	//public ObservableList<Emp> list = FXCollections.observableArrayList();
	
	
	@FXML
	private Label userLbl;
	
	@FXML
	TableView<supplier> table;
	@FXML
	private TableColumn<?, ?> IDcol;
	@FXML
	private TableColumn<?, ?> namecol;
	@FXML
	private TableColumn<?, ?> telcol;
	@FXML
	private TableColumn<?, ?> emailcol;
	@FXML
	private TableColumn<?, ?> addcol;
	@FXML
	private TableColumn<?, ?> countrycol;
	@FXML
	private TableColumn<?, ?> catcol;
	
	public ObservableList<supplier> list = FXCollections.observableArrayList();
	
	
	@FXML
	private TextField txtID;
	@FXML
	private TextField delID;
	@FXML
	private TextField txtname;
	@FXML
	private TextField txttel;
	@FXML
	private TextField txtemail;
	@FXML
	private TextField txtadd;
	@FXML
	private TextField txtcountry;
	@FXML
	private TextField searchSup;
	@FXML
	private TextField txtcategory;
	
	@FXML
	private Label userLabel;
	
	FilteredList<supplier> filter = new FilteredList(list, e -> true);
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
	
	
	
public void loadDBdata() {
		
		list.clear();
		
		ISupplierService isup = new SupplierImpl();
		
		ArrayList<supplier> supList = isup.getAllSupplierDetails();
		
		for(supplier sup : supList) {
			list.add(sup);
		}
		
		table.setItems(list);
		
	}

	ISupplierService isup = new SupplierImpl();

	@FXML
	public void AddNewEmp(ActionEvent event) throws SQLException {
		// TODO Autogenerated
		
		if(validateFields() || validateSupId()) {
			if(validateSupName()) {
				if(validateSupTel()) {
					if(validateSupAdd()) {
						if(validateSupCountry()) {
		/*if(txtID.getText().isEmpty()) {
			Generator.getAlert("Warning", "Please enter the ID");
			System.out.println("error");
			return;
		}*/
		//boolean ID1 = FormValidation.textFieldNotEmpty(txtID,"ID is required");
		
		supplier sup = new supplier();
		
		sup.setSID(txtID.getText());
		sup.setSName(txtname.getText());
		sup.setSTel(txttel.getText());
		sup.setSEmail(txtemail.getText());
		sup.setSAdd(txtadd.getText());
		sup.setSCountry(txtcountry.getText());
		sup.setName_category(txtcategory.getText());
		
		isup.addSupplierDetails(sup);
		
		clear(event);
		loadDBdata();
		txtID.setText(Generator.generateSupplierId());
						}}}}}
		}
	//static String tmpEmpName;
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		txtID.setText(Generator.generateSupplierId());
		//connection = sqlConnection.Connector(); 
		loadDBdata();
		
		
		IDcol.setCellValueFactory(new PropertyValueFactory<>("sID"));
		namecol.setCellValueFactory(new PropertyValueFactory<>("sName"));
		telcol.setCellValueFactory(new PropertyValueFactory<>("sTel"));
		emailcol.setCellValueFactory(new PropertyValueFactory<>("sEmail"));
		addcol.setCellValueFactory(new PropertyValueFactory<>("sAdd"));
		countrycol.setCellValueFactory(new PropertyValueFactory<>("sCountry"));
		catcol.setCellValueFactory(new PropertyValueFactory<>("name_category"));
		
		//table.setItems(list);
		
		/*table.setOnMouseClicked(p ->{
		
			String query =  "select * from empregister";
			
			try {
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
			
				
				while(resultSet.next())
				{
							txtempname.setText(resultSet.getString("EmpName"));
							txtaddress.setText(resultSet.getString("Address"));
							txtNic.setText(resultSet.getString("NIC"));
							txtdesig.setText(resultSet.getString("Designation"));
							txtTel.setText(resultSet.getString("Telephone"));
							txtemail.setText(resultSet.getString("Email"));
							
					//table.setItems(list);
				}
				preparedStatement.close();
				resultSet.close();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			
	
		});*/
		
	}
	
	private void clear(ActionEvent event) {
		txtID.setText("");
		txtname.setText("");
		txttel.setText("");
		txtemail.setText("");
		txtadd.setText("");
		txtcountry.setText("");
		txtcategory.setText("");
	}
		
	
	static String tempNic;
	@FXML
	public void ShowOnClick() {
		
			supplier sup = table.getSelectionModel().getSelectedItem();
			
			tempNic = sup.getSID();
			
			txtID.setText(sup.getSID());
			txtname.setText(sup.getSName());
			txttel.setText(sup.getSTel());
			txtemail.setText(sup.getSEmail());
			txtadd.setText(sup.getSAdd());
			txtcountry.setText(sup.getSCountry());
			txtcategory.setText(sup.getName_category());
			delID.setText(sup.getSID());
			
			//preparedStatement.close();
			//result.close();
			
		
	}
	
	@FXML
	private void deleteData(ActionEvent event) throws Throwable {
		if(validateDelID()) {
		isup.deleteSupplierDetails(txtID.getText());
		clear(event);
		loadDBdata();
		txtID.setText(Generator.generateSupplierId());
}}
	
	@FXML
    public void Updateuser(ActionEvent event) {
			
		supplier sup = new supplier();
		
		sup.setSID(txtID.getText());
		sup.setSName(txtname.getText());
		sup.setSTel(txttel.getText());
		sup.setSEmail(txtemail.getText());
		sup.setSAdd(txtadd.getText());
		sup.setSCountry(txtcountry.getText());
		sup.setName_category(txtcategory.getText());
		
		isup.updateSupplierDetails(sup);
		
		clear(event);
		loadDBdata();
		txtID.setText(Generator.generateSupplierId());
		
    }
	@FXML
	public void searchSupplier(javafx.scene.input.KeyEvent event) {
		
		searchSup.textProperty().addListener((observable, oldValue, newValue) -> {
			
			filter.setPredicate((Predicate<? super supplier>) (supplier sList) ->{
				if(newValue.isEmpty() || newValue == null) {
					return true;
				}
				else if(sList.getSID().contains(newValue)) {
					return true;
				}
				
				return false;
			});
	});
		
		SortedList sort = new SortedList(filter);
		sort.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(sort);
	
	}
	
	public void demo(ActionEvent event) throws Exception{
		txtID.setText("S008");
		txtname.setText("LG");
		txttel.setText("0112456737");
		txtemail.setText("LG@gmail.com");
		txtadd.setText("Morokko");
		txtcountry.setText("china");
		txtcategory.setText("Oven");
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

	
	public boolean validateFields() {
		
		//Pattern pattern = Pattern.compile("[A-Za-z] +[0-9]+");
		//Matcher matcher_oID = pattern.matcher(input)
		if(txtID.getText().isEmpty() && txtname.getText().isEmpty() && txttel.getText().isEmpty() && txtemail.getText().isEmpty() && txtadd.getText().isEmpty() && txtcountry.getText().isEmpty()) {
			Generator.getAlert("Error", "Do not keep the fields Empty");
			System.out.println("Do not keep the fields Empty");
			return false;
		}
		if(txtID.getText().isEmpty()) {
			Generator.getAlert("Error", "Do not keep the Supplier ID field Empty");
			System.out.println("Do not keep the Supplier ID field Empty");
			return false;
		}
		if(txtname.getText().isEmpty()) {
			Generator.getAlert("Error", "Do not keep the Supplier Name field Empty");
			System.out.println("Do not keep the Supplier Name field Empty");
			return false;
		}
		if(txttel.getText().isEmpty()) {
			Generator.getAlert("Error", "Do not keep the Telephone field Empty");
			System.out.println("Do not keep the Telephone field Empty");
			return false;
		}
		if(txtemail.getText().isEmpty()) {
			Generator.getAlert("Error", "Do not keep the Email field Empty");
			System.out.println("Do not keep the Email field Empty");
			return false;
		}
		if(txtadd.getText().isEmpty()) {
			Generator.getAlert("Error", "Do not keep the Address field Empty");
			System.out.println("Do not keep the Address field Empty");
			return false;
		}
		if(txtcountry.getText().isEmpty()) {
			Generator.getAlert("Error", "Do not keep the Country field Empty");
			System.out.println("Do not keep the Country field Empty");
			return false;
		}	
		return true;
     } 	
	 public boolean validateDelID() {
		if(delID.getText().isEmpty()) {
			Generator.getAlert("Warning", "Enter the Supplier ID");
			System.out.println("Enter the Supplier ID");
			return false;
		}
		return true;
	}
	 private boolean validateSupId() {
			Pattern p = Pattern.compile("[A-Za-z]+[0-9]+");
			Matcher m = p.matcher(txtID.getText());
			if(m.find() && m.group().equals(txtID.getText())) {
				return true;
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Warning...!!!");
				alert.setHeaderText(null);
				alert.setContentText("Enter a Valid Supplier ID");
				alert.showAndWait();
				System.out.println("Boom");
				return false;
			}
			
		}
	 private boolean validateSupName() {
			Pattern p = Pattern.compile("[A-Za-z]+");
			Matcher m = p.matcher(txtname.getText());
			if(m.find() && m.group().equals(txtname.getText())) {
				return true;
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Warning...!!!");
				alert.setHeaderText(null);
				alert.setContentText("Enter a Valid Supplier Name");
				alert.showAndWait();
				System.out.println("Boom1");
				return false;
			}
			
		}
	 private boolean validateSupTel() {
			Pattern p = Pattern.compile("[0-9]+");
			Matcher m = p.matcher(txttel.getText());
			if(m.find() && m.group().equals(txttel.getText())) {
				return true;
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Warning...!!!");
				alert.setHeaderText(null);
				alert.setContentText("Enter a Valid Telephone Number");
				alert.showAndWait();
				System.out.println("Boom2");
				return false;
			}
			
		}
	 private boolean validateSupAdd() {
			Pattern p = Pattern.compile("[A-Za-z]+");
			Matcher m = p.matcher(txtadd.getText());
			if(m.find() && m.group().equals(txtadd.getText())) {
				return true;
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Warning...!!!");
				alert.setHeaderText(null);
				alert.setContentText("Enter a Valid Supplier Address");
				alert.showAndWait();
				System.out.println("Boom3");
				return false;
			}
			
		}
	 private boolean validateSupCountry() {
			Pattern p = Pattern.compile("[A-Za-z]+");
			Matcher m = p.matcher(txtcountry.getText());
			if(m.find() && m.group().equals(txtcountry.getText())) {
				return true;
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Warning...!!!");
				alert.setHeaderText(null);
				alert.setContentText("Enter a Valid Country Name");
				alert.showAndWait();
				System.out.println("Boom4");
				return false;
			}
			
		}
	 
	//end Supplier change Screen changing functions
	 
	 public void supplierOrderScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Supplier Manager")) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/SupplierOrder.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				SupplierOrderController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Supplier Management");
			
			}
		}
		
		public void supplierPaymentScreen( ActionEvent event ) throws Exception{
			Parent view = FXMLLoader.load(getClass().getResource("/view/SupplierPayment.fxml"));

			
					if(userObj.getType().equalsIgnoreCase("Supplier Manager")) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/view/SupplierPayment.fxml"));
						Parent parent = loader.load();
						
						Scene scene =  new Scene(parent);
						scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
						
						SupplierPaymentController controller = loader.getController();
						controller.loadUser(userObj);
						
						Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
						
						window.setScene(scene);
						window.show();
						window.centerOnScreen();
					}else {
						
						Generator.getAlert("Access Denied", "You don't have access to Supplier Management");
					
					}
		}
		
		public void supplierReport(ActionEvent event ) throws Exception{

			if(userObj.getType().equalsIgnoreCase("Supplier Manager")) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/SupplierDetailsReport.fxml"));
				Parent parent = loader.load();
				
				Scene scene =  new Scene(parent);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				
				SupplierDetailsReportController controller = loader.getController();
				controller.loadUser(userObj);
				
				Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(scene);
				window.show();
				window.centerOnScreen();
			}else {
				
				Generator.getAlert("Access Denied", "You don't have access to Supplier Management");
			
			}
		}
}
