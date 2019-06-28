package controller;

import java.awt.Dimension;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Main;
import model.User;
import model.income;
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
import service.Iincome;
import service.incomeImpl;
import util.DBConnect;
import util.Generator;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

public class incomeController implements Initializable{
	

	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
    
	
	@FXML	private TableView <income> tblVIncome;
	@FXML	private TableColumn <?, ?> tblcIncomeId;
	@FXML	private TableColumn <?, ?> tblccust_id;
	@FXML	private TableColumn <?, ?> tblc_firstName;
	@FXML	private TableColumn <?, ?> tblcContact_number;
	@FXML	private TableColumn <?, ?> tblcOrder_id;
	@FXML	private TableColumn <?, ?> tblcOrder_date;
	@FXML	private TableColumn <?, ?> tblc_amount;
	
	Connection connection;
	
	private ObservableList<income> incomeList = FXCollections.observableArrayList();
	
	Iincome iincome = new incomeImpl();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getAllIncome();
		
	}
	
	
	public void getAllIncome() {

		tblcIncomeId.setCellValueFactory(new PropertyValueFactory<>("income_id"));
		tblccust_id.setCellValueFactory(new PropertyValueFactory<>("cust_id"));
		tblc_firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tblcContact_number.setCellValueFactory(new PropertyValueFactory<>("contact_no"));
		tblcOrder_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		tblcOrder_date.setCellValueFactory(new PropertyValueFactory<>("order_date"));
		tblc_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		
		incomeList = iincome.getAllincomes();

		tblVIncome.setItems(incomeList);
	}
	

public void deleteIncome(ActionEvent event) {
		
		ObservableList<income> selectIncomeExpense;
		
		
		selectIncomeExpense = tblVIncome.getSelectionModel().getSelectedItems();
		
			if(selectIncomeExpense.isEmpty()) {
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Income");
			alert.showAndWait();
			
		}else {
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Delete Selected Income ?");
			Optional <ButtonType> action= alert.showAndWait();
			
			if(action.get()==ButtonType.OK) {
				
				
				String income_del = selectIncomeExpense.get(0).getIncome_id().toString();
				
				iincome.deleteincome(income_del);
			}
		}
		
		
		
		
		getAllIncome();
	}


public void reportGenerating(ActionEvent event) throws Exception{		 
	 
	 

   	try {
   		connection = DBConnect. getDBConnection();
   		
   	    		
   	    	//	connection = getDBConnection();
   	    		
   	    		//JasperReport jasperReport = JasperCompileManager.compileReport("E:\\SLIIT\\Java\\JasperWithFX\\Blank_A4.jrxml");
   	    		
   	    		JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\NETHU\\eclipse-workspace\\fms_income\\src\\reports\\income.jrxml");
   	    		
   	    		//get the query
   	    		String query = "SELECT * FROM income";
   	    		JRDesignQuery jrQuery = new JRDesignQuery();
   	    		jrQuery.setText(query);
   	    		jasperDesign.setQuery(jrQuery);
   	    		
   	    		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
   	    		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , null , connection );
   	    		JRViewer viewer = new JRViewer( jasperPrint );
   	    		
   	    		//JRDataSource jrDataSource = new JREmptyDataSource();
   	    		
   	    		//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
   	    		
   	    		JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\NETHU\\Desktop\\reports\\"+".pdf" );
   	    		
   	    	
   	    		//to view the jasper report in the pdf viewer -  (can be used for a button to view the report)
   	    		viewer.setOpaque(true);
   	    		viewer.setVisible(true);
   	    		
   	    		JFrame frame = new JFrame("Income Report");
   	            frame.add(viewer);
   	            frame.setSize(new Dimension(500, 400));
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
	
	
	Parent view = FXMLLoader.load(getClass().getResource("/view/UserManage.fxml"));
	Scene scene = new Scene(view);
	scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
	Stage window = Main.getStageObj();
	
	window.setScene(scene);
	window.show();
	window.centerOnScreen();
}
//end Screen changing functions




@FXML
void SalaryManageScreen(ActionEvent event)  throws Exception{
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


@FXML
void ExpenseManageScreen(ActionEvent event)  throws Exception{
	if(userObj.getType().equalsIgnoreCase("Accountant")) {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Expenses.fxml"));
		Parent parent = loader.load();
		
		Scene scene =  new Scene(parent);
		scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
		
		expensesController controller = loader.getController();
		controller.loadUser(userObj);
		
		Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
		window.centerOnScreen();
	}else {
		
		Generator.getAlert("Access Denied", "You don't have access to Finance Management");
	}
}

@FXML
void IncomeManageScreen(ActionEvent event)  throws Exception{
	if(userObj.getType().equalsIgnoreCase("Accountant")) {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Income.fxml"));
		Parent parent = loader.load();
		
		Scene scene =  new Scene(parent);
		scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
		
		incomeController controller = loader.getController();
		controller.loadUser(userObj);
		
		Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
		window.centerOnScreen();
	}else {
		
		Generator.getAlert("Access Denied", "You don't have access to Finance Management");
	}
}

@FXML
void FinanceReportScreen(ActionEvent event)  throws Exception{
	if(userObj.getType().equalsIgnoreCase("Accountant")) {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/FinanceReport.fxml"));
		Parent parent = loader.load();
		
		Scene scene =  new Scene(parent);
		scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
		
		FinanceReportController controller = loader.getController();
		controller.loadUser(userObj);
		
		Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
		window.centerOnScreen();
	}else {
		
		Generator.getAlert("Access Denied", "You don't have access to Finance Management");
	}
}
	
}
