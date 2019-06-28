package controller;

import java.awt.Dimension;
import java.io.File;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
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
import service.Isalary;
import service.incomeImpl;
import service.salaryImpl;
import util.DBConnect;
import util.Generator;

public class FinanceReportController implements Initializable {
	

	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
    
private static final String COMPILE_REPORT_PATH = "C:\\Users\\Ganesh\\Desktop\\SMS_REPORT_NEW\\Showroom_Management_System\\src\\report\\";
	
	@FXML private TextField txtsalaryReportPath;
	@FXML private TextField 	txtsalaryReportSaveAsText;
	
	
	@FXML private ComboBox<String> salaryReportSelectedYear;
	@FXML private ComboBox<String> salaryReportSelectedMonth;	
	
	public ObservableList<String> yearList = FXCollections.observableArrayList("All","2018",  "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028");
    public ObservableList<String> monthList =  FXCollections.observableArrayList("All","January",  "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" );

   
    
    
	@FXML 
	void ChooseSalaryReportPath(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);
		txtsalaryReportPath.setText(Generator.generateFilePathsToJavaFormat(selectedDirectory.getAbsolutePath() + "\\"));	
	
	}
	
	@FXML
    void onYearSelectSAL(ActionEvent event) {
    	
    	String yearString = salaryReportSelectedYear.getSelectionModel().getSelectedItem().toString();
    	String monthString = "";
    	
    	if( salaryReportSelectedMonth.getSelectionModel().getSelectedItem() == null) {
    		
    		monthString = "";
    		txtsalaryReportSaveAsText.setText("SAL-"+yearString);
    	}else {
    		monthString = salaryReportSelectedMonth.getSelectionModel().getSelectedItem().toString();
    		txtsalaryReportSaveAsText.setText("SAL-"+yearString+"-"+monthString);
    	}	
    	
    }
	@FXML
	  void onMonthSelectSAL(ActionEvent event) {
  		
	    	String yearString = "";
	    	String monthString = salaryReportSelectedMonth.getSelectionModel().getSelectedItem().toString();
	    	
	    	if( salaryReportSelectedYear.getSelectionModel().getSelectedItem() == null) {
	    		
	    		yearString = "";
	    	 	txtsalaryReportSaveAsText.setText("SAL-"+monthString);
	    	}else {
	    		yearString = salaryReportSelectedYear.getSelectionModel().getSelectedItem().toString();
	    		txtsalaryReportSaveAsText.setText("SAL-"+yearString+"-"+monthString);
	    	}	
	    }
	
	
	
	 @FXML
	    public void viewSalaryReport(ActionEvent event) {
	    	
	    	Isalary isalary = new salaryImpl();

	    	if( salaryReportSelectedYear.getSelectionModel().getSelectedItem() == null || salaryReportSelectedMonth.getSelectionModel().getSelectedItem() == null ) {
	    		
	    		Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("You cannot keep year or months value null");
				alert.showAndWait();				
	    		return;
	    	}
	    	 
	    	
	    	
	    	String monthString = salaryReportSelectedMonth.getSelectionModel().getSelectedItem().toString();
	    	String yearString = salaryReportSelectedYear.getSelectionModel().getSelectedItem().toString();

	    	
	    	if( monthString == "All" && yearString == "All" ) {
	    		isalary.generateFullSALReport(COMPILE_REPORT_PATH + "SalaryReport.jrxml", "view",  txtsalaryReportPath.getText()+txtsalaryReportSaveAsText.getText() +".pdf");
	    	}else
	    		if( monthString == "All"  && yearString != "All" ) {
	    			
	    		}else
	    			if( monthString != "All" && yearString == "All" ) {
	    				
	    			}else {
	    		    	int year = Integer.parseInt(salaryReportSelectedYear.getSelectionModel().getSelectedItem().toString());
	    		    	int month = Generator.getMonthIntValue(monthString);
	    		    	isalary.generateSALReportByMonthAndYear(COMPILE_REPORT_PATH + "SalaryReport.jrxml", month, year, "view", txtsalaryReportPath.getText()+txtsalaryReportSaveAsText.getText()+".pdf" );
	    			}

	    }
	    

	 @FXML
	    public void saveCustomerOrderReport(ActionEvent event) {
	    	
	    	Isalary isal = new salaryImpl();
	    	
	    	if( txtsalaryReportSaveAsText.getText() == ""  ||  txtsalaryReportSaveAsText.getText() == null ) {
	    		Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("You must enter a file name to save to a pdf");
				alert.showAndWait();	
	    		return;
	    	}
	    	
	    	if( salaryReportSelectedYear.getSelectionModel().getSelectedItem() == null || salaryReportSelectedYear.getSelectionModel().getSelectedItem() == null ) {
	    		
	    		Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Error in viewing report , You cannont keep year value or month value null ");
				alert.showAndWait();	
	    		return;
	    	}
	    	
	    	String monthString = salaryReportSelectedMonth.getSelectionModel().getSelectedItem().toString();
	    	String yearString = salaryReportSelectedYear.getSelectionModel().getSelectedItem().toString();

	    	
	    	if( monthString == "All" && yearString == "All" ) {
	    		isal.generateFullSALReport(COMPILE_REPORT_PATH + "SalaryReport.jrxml", "save",  txtsalaryReportPath.getText()+txtsalaryReportSaveAsText.getText()+".pdf");
	    	}else
	    		if( monthString == "All"  && yearString != "All" ) {
	    			
	    		}else
	    			if( monthString != "All" && yearString == "All" ) {
	    				
	    			}else {
	    		    	int year = Integer.parseInt(salaryReportSelectedYear.getSelectionModel().getSelectedItem().toString());
	    		    	int month = Generator.getMonthIntValue(monthString);
	    		    	isal.generateSALReportByMonthAndYear(COMPILE_REPORT_PATH + "SalaryReport.jrxml", month, year, "save", txtsalaryReportPath.getText()+txtsalaryReportSaveAsText.getText()+".pdf" );
	    			}
	    	
	    }

	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		salaryReportSelectedYear.setItems(yearList);
		salaryReportSelectedMonth.setItems(monthList);
		txtsalaryReportPath.setText(Generator.generateFilePathsToJavaFormat("C:\\Users\\NETHU\\eclipse-workspace\\fms_emp_salary_new_2\\src\\reports\\"));
		
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
