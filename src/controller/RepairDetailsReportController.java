package controller;

import javafx.fxml.FXML; 
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;



import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Main;
import model.User;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import service.IRepairService;
import service.ItemRepairImpl;
import util.Generator;

public class RepairDetailsReportController implements Initializable {
	private static final String COMPILE_REPORT_PATH = "C:\\Users\\Ganesh\\Desktop\\SMS_REPORT_NEW\\Showroom_Management_System\\src\\report\\";
	
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Label userLabel;
	@FXML
	private Label repairReportLbl;
	@FXML
	private Label filePathLbl;
	@FXML
	private TextField filePathTxt;
	@FXML
	private Button choosePathBtn;
	@FXML
	private Label selectYrLbl;
	@FXML
	private Label selectMnth;
	@FXML
	private ComboBox selectYrCombo;
	@FXML
	private ComboBox selectMonthCombo;
	
	public ObservableList<String> yearList =  FXCollections.observableArrayList("All","2018",  "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028");
	
    public ObservableList<String> monthList =  FXCollections.observableArrayList("All","January",  "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" );
    
	@FXML
	private TextField pdfNameTxt;
	@FXML
	private Button viewReportBtn;
	@FXML
	private Button saveReportBtn;
	@FXML
	private Button closeButton;
	@FXML
	private Button minButton;
	@FXML
	private Button logOutButton;
	@FXML
	private Button supplierButton;
	@FXML
	private Button inventoryButton;
	@FXML
	private Button customerOrderButton;
	@FXML
	private Button homeButton;
	@FXML
	private Button customerButton;
	@FXML
	private Button employeeButton;
	@FXML
	private Button financeButton;
	@FXML
	private Button itemRepairButton;
	@FXML
	private Button deliveryButton;
	@FXML
	private Button userButton;
	
	private User userObj = new User();
    
    	public void loadUser(User user) {

    		this.userObj = user;
    	
    		IEmployeeService empService = new EmployeeServiceImpl();
  
    		userLabel.setText(empService.loadname(userObj));
    }
    
    
    	
  
   
    /*--------------------------- Select Report Save paths ---------------------------------------------------*/
    @FXML
    void chooseRepairDetailsReportPath(ActionEvent event) {
    	
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);

		filePathTxt.setText(Generator.generateFilePathsToJavaFormat(selectedDirectory.getAbsolutePath() + "\\"));
		
    }
    
    /*------------------------------ Save As name --------------------------------------------------------------*/
    @FXML
    void onYearSelectRepairDetails(ActionEvent event) {
    	
    	String yearString = selectYrCombo.getSelectionModel().getSelectedItem().toString();
    	String monthString = "";
    	
    	if( selectMonthCombo.getSelectionModel().getSelectedItem() == null) {
    		
    		monthString = "";
    	 	pdfNameTxt.setText("RDR-"+yearString);
    	}else {
    		monthString = selectMonthCombo.getSelectionModel().getSelectedItem().toString();
    	 	pdfNameTxt.setText("RDR-"+yearString+"-"+monthString);
    	}	
    	
    }
    
    @FXML
    void onMonthSelectRepairDetails(ActionEvent event) {
    		
    	String yearString = "";
    	String monthString = selectMonthCombo.getSelectionModel().getSelectedItem().toString();
    	
    	if(selectYrCombo.getSelectionModel().getSelectedItem() == null) {
    		
    		yearString = "";
    	 	pdfNameTxt.setText("RDR-"+monthString);
    	}else {
    		yearString = selectYrCombo.getSelectionModel().getSelectedItem().toString();
    	 	pdfNameTxt.setText("RDR-"+yearString+"-"+monthString);
    	}	
    }
    
    /*---------------------------------------- Reports ---------------------------------------------------------*/
    @FXML
    public void viewRepairDetailsReport(ActionEvent event) {
    	
    	IRepairService iRepairService = new ItemRepairImpl();

    	if( selectYrCombo.getSelectionModel().getSelectedItem() == null || selectMonthCombo.getSelectionModel().getSelectedItem() == null ) {
    		
    		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
    		return;
    	}
    	
    	
    	String monthString = selectMonthCombo.getSelectionModel().getSelectedItem().toString();
    	String yearString = selectYrCombo.getSelectionModel().getSelectedItem().toString();

    	
    	if( monthString == "All" && yearString == "All" ) {
    		int year = 0;
	    	int month = 0;
	    	iRepairService.generateRepairReportByMonthAndYear(COMPILE_REPORT_PATH + "Item_repair_report.jrxml", month, year, "view", filePathTxt.getText()+pdfNameTxt.getText()+".pdf" );
	    }else
    		if( monthString == "All"  && yearString != "All" ) {
    			int year = Integer.parseInt(selectYrCombo.getSelectionModel().getSelectedItem().toString());
    	    	int month = 0;
    	    	iRepairService.generateRepairReportByMonthAndYear(COMPILE_REPORT_PATH + "Item_repair_report.jrxml", month, year, "view", filePathTxt.getText()+pdfNameTxt.getText()+".pdf" );
    		}else
    			if( monthString != "All" && yearString == "All" ) {
    				int year = 0;
        	    	int month = Generator.getMonthIntValue(monthString);
        	    	iRepairService.generateRepairReportByMonthAndYear(COMPILE_REPORT_PATH + "Item_repair_report.jrxml", month, year, "view", filePathTxt.getText()+pdfNameTxt.getText()+".pdf" );
    			}else {
    		    	int year = Integer.parseInt(selectYrCombo.getSelectionModel().getSelectedItem().toString());
    		    	int month = Generator.getMonthIntValue(monthString);
    		    	iRepairService.generateRepairReportByMonthAndYear(COMPILE_REPORT_PATH + "Item_repair_report.jrxml", month, year, "view", filePathTxt.getText()+pdfNameTxt.getText()+".pdf" );
    			}

    }
    
    @FXML
    public void saveRepairDetailsReport(ActionEvent event) {
    	
    	IRepairService iRepairService = new ItemRepairImpl();
    	
    	if( pdfNameTxt.getText() == ""  ||  pdfNameTxt.getText() == null ) {
    		Generator.getAlert("Error in saving order report", "You must enter a file name to save your pdf");
    		return;
    	}
    	
    	if( selectYrCombo.getSelectionModel().getSelectedItem() == null || selectMonthCombo.getSelectionModel().getSelectedItem() == null ) {
    		
    		Generator.getAlert("Error in Viewing Report", "You Cannot keep the Year or Month Values Null");
    		return;
    	}
    	
    	String monthString = selectMonthCombo.getSelectionModel().getSelectedItem().toString();
    	String yearString = selectYrCombo.getSelectionModel().getSelectedItem().toString();

    	
    	if( monthString == "All" && yearString == "All" ) {
    		int year = 0;
	    	int month = 0;
    		iRepairService.generateRepairReportByMonthAndYear(COMPILE_REPORT_PATH + "Item_repair_report.jrxml", month, year, "save",  filePathTxt.getText()+pdfNameTxt.getText()+".pdf");
    	}else
    		if( monthString == "All"  && yearString != "All" ) {
    			int year = Integer.parseInt(selectYrCombo.getSelectionModel().getSelectedItem().toString());
    	    	int month = 0;
    	    	iRepairService.generateRepairReportByMonthAndYear(COMPILE_REPORT_PATH + "Item_repair_report.jrxml", month, year, "view", filePathTxt.getText()+pdfNameTxt.getText()+".pdf" );
    		}else
    			if( monthString != "All" && yearString == "All" ) {
    				int year = 0;
        	    	int month = Generator.getMonthIntValue(monthString);
        	    	iRepairService.generateRepairReportByMonthAndYear(COMPILE_REPORT_PATH + "Item_repair_report.jrxml", month, year, "view", filePathTxt.getText()+pdfNameTxt.getText()+".pdf" );
    			}else {
    		    	int year = Integer.parseInt(selectYrCombo.getSelectionModel().getSelectedItem().toString());
    		    	int month = Generator.getMonthIntValue(monthString);
    		    	iRepairService.generateRepairReportByMonthAndYear(COMPILE_REPORT_PATH + "Item_repair_report.jrxml", month, year, "save", filePathTxt.getText()+pdfNameTxt.getText()+".pdf" );
    			}
    	
    }
    
    @FXML
    private TextField recieverEmaiTxt;

    @FXML
    private TextField subjectTxt;

    @FXML
    private TextArea BodyTxt;
    
    public void sendNotification(ActionEvent event) {
    	
    	String rec = recieverEmaiTxt.getText();
    	String sub = subjectTxt.getText();
    	String body = BodyTxt.getText();
    	
    	Generator.sendNotification(rec , sub, body);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	selectYrCombo.setItems(yearList);
		selectMonthCombo.setItems(monthList);
		
		filePathTxt.setText(Generator.generateFilePathsToJavaFormat("F:\\SLIIT\\Year 2\\ITP\\Reports\\Repair_Details_Report\\"));
    		
    }
    
    
    
    	

   /*-----------------------WINDOW CONTROLS--------------------------*/
    	
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
		
	/*public void itemRepairFormScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager")) {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/ItemRepairManage1.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			ItemRepairManage1Controller controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			
			Generator.getAlert("Access Denied", "You don't have access to Item Repair Management");
		}
	}*/
	
	public void technicianManageScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager")) {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/TechnicianManage.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			TechnicianManageController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			
			Generator.getAlert("Access Denied", "You don't have access to Item Repair Management");
		}
	}
	
	public void reportManageScreen( ActionEvent event ) throws Exception{
		if(userObj.getType().equalsIgnoreCase("Stock Manager")) {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/RepairDetailsReport.fxml"));
			Parent parent = loader.load();
			
			Scene scene =  new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			
			RepairDetailsReportController controller = loader.getController();
			controller.loadUser(userObj);
			
			Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
			window.centerOnScreen();
			
		}else {
			
			Generator.getAlert("Access Denied", "You don't have access to Item Repair Management");
		}
	}

	
	
}
