package controller;

import javafx.fxml.FXML;



import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

//import com.sun.javafx.css.CssError.PropertySetError;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Attendence;
import model.Emp;
import model.Main;
import model.User;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import util.Generator;
import util.sqlConnection;
import model.MainModle;

public class EmployeeAttendenceController implements Initializable {
	
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	Connection connection;
	
	@FXML
	private Label userLabel;
	
    @FXML
    private TextField attTime;
    
    @FXML
    private TextField leveTime;
	
	private User userObj = new User();
    
    public void loadUser(User user) {
    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    	
    	//String time1 = FindTime();
    	attTime.setText(FindTime());
    	leveTime.setText(FindTime());
    }
    
	public static String FindTime() {
	
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    String t1 = sdf.format(cal.getTime());
	    
	    
	    return t1;
	}
	
   @FXML
    private DatePicker date1;
   
    @FXML
    private TextField eID = new TextField();
    
    @FXML
    private TableView<Attendence> table1;

    @FXML
    private TableColumn<Attendence, String> attDatecol;

    @FXML
    private TableColumn<Attendence, String> attTimecol;

    @FXML
    private TableColumn<Attendence, String> eidcol;

    @FXML
    private TableColumn<Attendence, String> leaveTimecol;
    
    @FXML
    private Button add_btn;
    
    @FXML
    private RadioButton empArrival;

    @FXML
    private RadioButton empDeparture;
    
    @FXML
    private ToggleGroup status;
    
    public ObservableList<Attendence> list = FXCollections.observableArrayList();
    
   @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	   connection = sqlConnection.Connector();
	   
	   table1.setItems(list);
		
	   attDatecol.setCellValueFactory(new PropertyValueFactory<Attendence, String>("attDate"));
	   attTimecol.setCellValueFactory(new PropertyValueFactory<Attendence, String>("attTime"));
	   eidcol.setCellValueFactory(new PropertyValueFactory<Attendence, String>("empID"));
	   leaveTimecol.setCellValueFactory(new PropertyValueFactory<Attendence, String>("levTime"));
		
	   loadAttendencedata();
	}
   
    
  @FXML
    public void insertDb(KeyEvent ek ) throws SQLException {
    	
	  	Attendence attend = new Attendence();
	  	if(ek.getCode().equals(KeyCode.ENTER)) {	
	  		if( empArrival.isSelected() ) {
		  		
		 		   
		 		   String empID = eID.getText();
		 		   System.out.println("Employee ID : " + empID );
		 		   String query = "insert into employee_attendance (attendance_date,attendance_time,emp_id,departure_time) values(?,?,?,?)";
	
		 	   		preparedStatement = null;
		 	   	
		 	   		if(empID == null || empID=="") {
		 	   			return;
		 	   		}
	    	preparedStatement = null;
	    	
	    	try {
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setString(1, Generator.getCurrentDate());
				preparedStatement.setString(2, Generator.getCurrentTime());
				preparedStatement.setString(3, eID.getText());
				preparedStatement.setString(4, null);
				//preparedStatement.setString(5, (date2.getValue()).toString());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}finally {
				preparedStatement.execute();
				preparedStatement.close();
		  	
		    	
		    	attTime.clear();
		    	eID.clear(); 
		    	leveTime.clear();
		    	list.clear();
		    	loadAttendencedata();
			}
	  }else if( empDeparture.isSelected() ){

		    	try {
		    		 String empID = eID.getText();
		    		   
		    		  //Attendence attend = new Attendence();
		    		 preparedStatement = null;
		    		  String query = "update employee_attendance set departure_time = ? where emp_id = ? and attendance_date= ? ";
		    		  
		    		  preparedStatement = connection.prepareStatement(query);
		    		  
		    		  preparedStatement.setString(2, eID.getText());
		    		  preparedStatement.setString(3, Generator.getCurrentDate());
		    		  preparedStatement.setString(1, Generator.getCurrentTime());
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Exception in departure : " + e);
				}finally {
					preparedStatement.execute();
					preparedStatement.close();
					
			    	attTime.clear();
			    	eID.clear(); 
			    	leveTime.clear();
			    	list.clear();
			    	loadAttendencedata();
				}

	  }
    
	  }
	  
	}
   

   @FXML
   void generateEmpBarcodes(ActionEvent event) {

	   ArrayList<String> empIdList = new ArrayList<String>();
	   EmployeeServiceImpl empService = new EmployeeServiceImpl();
	   empIdList = empService.getEmpIDList();
	   
	   Generator.generateBarcodePdf("C:\\Users\\Ganesh\\Desktop\\Reports\\empbarcodes.pdf", empIdList);
	   
   }		
   
   public void loadAttendencedata() {
		
		String query = "select * from employee_attendance";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				
				list.add(new Attendence(
						resultSet.getString("attendance_date"),
						resultSet.getString("attendance_time"),
						resultSet.getString("emp_id"),
						resultSet.getString("departure_time")
						));
			}
			preparedStatement.close();
			resultSet.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} 
	}
   
   public void ShowOnClick() {
   	try {
			Attendence attend = table1.getSelectionModel().getSelectedItem();
			String query = "select * from employee_attendance";
			preparedStatement = connection.prepareStatement(query);
			
			//date1.setText(attend.getAttDate());
			attTime.setText(attend.getAttTime());
			eID.setText(attend.getEmpID());
			leveTime.setText(attend.getLevTime());
			
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
	//end Screen changing functions
}
