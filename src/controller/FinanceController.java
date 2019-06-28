package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import model.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.User;
import model.salary;
import service.EmployeeServiceImpl;
import service.IEmployeeService;
import service.Isalary;
import service.salaryImpl;
import util.Generator;


//import util.Generator;

public class FinanceController implements Initializable {


	@FXML
	private Label userLabel;
	
	private User userObj = new User();
    
    public void loadUser(User user) {

    	this.userObj = user;
    	
    	IEmployeeService empService = new EmployeeServiceImpl();
  
    	userLabel.setText(empService.loadname(userObj));
    }
    
    
    
	@FXML	private TextField txtSalId;
	@FXML	private TextField txtempNo;
	@FXML	private TextField txtName;
	@FXML	private TextField txtDesignation;
	@FXML	private TextField txtBasicSalary;
	@FXML	private DatePicker txtDate;
	@FXML	private TextField txtAllowances;
	@FXML	private TextField txtOtHrs;
	@FXML	private TextField txtOtRate;
	@FXML	private TextField txtEtf;
	@FXML	private TextField txtDeductions;
	@FXML	private TextField txtEpf;
	@FXML	private Label AAmount;
	@FXML	private Label DAmount;
	@FXML	private Label TotSal;
	@FXML	private Label Aot;
	@FXML	private Button btnUpdate;

	@FXML	private TableView<salary> tblViewSalary;

	@FXML	private TableColumn <?, ?> tblcSalaryId;
	@FXML	private TableColumn <?, ?> tblcEmpNo;
	@FXML	private TableColumn <?, ?> tblcName;
	@FXML	private TableColumn <?, ?> tblcDesignation;
	@FXML	private TableColumn <?, ?> tblcBasics;
	@FXML	private TableColumn <?, ?> tblcDate;
	@FXML	private TableColumn <?, ?> tblcAllowances;
	@FXML	private TableColumn <?, ?> tblcOtHrs;
	@FXML	private TableColumn <?, ?> tblcOtRate;
	@FXML	private TableColumn <?, ?> tblcEtf;
	@FXML	private TableColumn <?, ?> tblcDeductions;
	@FXML	private TableColumn <?, ?> tblcEpf;
	@FXML	private TableColumn <?, ?> tblcTotalSalary;
	@FXML	private TableColumn <?, ?> tblcOT;

	private ObservableList<salary> salaryList = FXCollections.observableArrayList();
	
	Isalary isalary = new salaryImpl();
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getAllSalary();
		txtSalId.setText(Generator.generateSalaryId());
		txtEpf.setEditable(true);
		txtEtf.setEditable(true);
		txtSalId.setEditable(true);
		txtName.setEditable(true);
		txtDesignation.setEditable(true);
		
		tblViewSalary.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				txtSalId.setText(tblViewSalary.getSelectionModel().getSelectedItem().getSalaryId());
				txtempNo.setText(tblViewSalary.getSelectionModel().getSelectedItem().getEmpNo());
				txtName.setText(tblViewSalary.getSelectionModel().getSelectedItem().getName());
				txtDesignation.setText(tblViewSalary.getSelectionModel().getSelectedItem().getDesignation());
				txtBasicSalary.setText(tblViewSalary.getSelectionModel().getSelectedItem().getBasicSalary());
				
				String d=tblViewSalary.getSelectionModel().getSelectedItem().getDate();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

				String date = d;
				
				//convert String to LocalDate
				LocalDate localDate = LocalDate.parse(date, formatter);
				
				FinanceController.this.txtDate.setValue(localDate);
				
				txtAllowances.setText(tblViewSalary.getSelectionModel().getSelectedItem().getAllowances());
				txtOtHrs.setText(tblViewSalary.getSelectionModel().getSelectedItem().getOtHrs());
				txtOtRate.setText(tblViewSalary.getSelectionModel().getSelectedItem().getOtRate());
				txtEtf.setText(tblViewSalary.getSelectionModel().getSelectedItem().getEtf());
				txtDeductions.setText(tblViewSalary.getSelectionModel().getSelectedItem().getDeductions());
				txtEpf.setText(tblViewSalary.getSelectionModel().getSelectedItem().getEpf());
				TotSal.setText(tblViewSalary.getSelectionModel().getSelectedItem().getTotSalary());
				Aot.setText(tblViewSalary.getSelectionModel().getSelectedItem().getOt());
				
				
				String allowance = tblViewSalary.getSelectionModel().getSelectedItem().getAllowances();
				String otHours = tblViewSalary.getSelectionModel().getSelectedItem().getOtHrs();
				String OtRate = tblViewSalary.getSelectionModel().getSelectedItem().getOtRate();
				
				String etf = tblViewSalary.getSelectionModel().getSelectedItem().getEtf();
				
				double allo = Double.parseDouble(allowance);
				double otH = Double.parseDouble(otHours);
				double otR = Double.parseDouble(OtRate);				
				double etfD = Double.parseDouble(etf);
				
				double otTot = otH*otR;
				
				double allowances = allo + etfD + (otH * otR);
				
				String deduct = tblViewSalary.getSelectionModel().getSelectedItem().getDeductions();
				String sepf = tblViewSalary.getSelectionModel().getSelectedItem().getEpf();
				
				double dedu = Double.parseDouble(deduct);
				double ep = Double.parseDouble(sepf);
				
				double deduc = dedu+ep;
				
				DAmount.setText(Double.toString(deduc));
				AAmount.setText(Double.toString(allowances));
				Aot.setText(Double.toString(otTot));
				
			}
			
			
		});
	}
	
	
	public boolean Salaryvalidation() {
		
		
		Pattern pattern_ID = Pattern.compile("[a-zA-Z0-9]*");
		Matcher matcher_salary_id = pattern_ID.matcher(txtSalId.getText());	
		Matcher matcher_empNo = pattern_ID.matcher(txtempNo.getText());
		
		if(txtSalId.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A Salary ID");
			alert.showAndWait();
			return false;
			
		}
		
		
		if(!matcher_salary_id.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Salary ID cannot contain any special character!");
			alert.showAndWait();
			return false;
		}
		
		if(txtempNo.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter an employee number ");
			alert.showAndWait();
			return false;
			
		}		
		
		if(!matcher_empNo.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Employee number cannot contain any special character!");
			alert.showAndWait();
			return false;
		}
		
		Pattern pattern_name = Pattern.compile("[a-zA-Z]*");
		Matcher matcher_name = pattern_name.matcher(txtName.getText());
		Matcher matcher_designation = pattern_name.matcher(txtDesignation.getText());
		
		
		
		if(txtName.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter name ");
			alert.showAndWait();
			return false;
			
		}	
		
		if(!matcher_name.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Name cannot contain any numbers or special characters!");
			alert.showAndWait();
			return false;
		}
		
		

		if(txtDesignation.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter designation ");
			alert.showAndWait();
			return false;
			
		}	
		
		if(!matcher_designation.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Desigantion cannot contain any numbers or special characters!");
			alert.showAndWait();
			return false;
		}
		
		
		Pattern pattern_number = Pattern.compile("[0-9]*");
		Matcher matcher_basic_salary = pattern_number.matcher(txtBasicSalary.getText());
		Matcher matcher_allowances= pattern_number.matcher(txtAllowances.getText());
		Matcher matcher_otHours = pattern_number.matcher(txtOtHrs.getText());
		Matcher matcher_otRate = pattern_number.matcher(txtOtRate.getText());
		Matcher matcher_deductions= pattern_number.matcher(txtDeductions.getText());
		//Matcher matcher_epf = pattern_number.matcher(txtEpf.getText());
		//Matcher matcher_etf = pattern_number.matcher(txtEtf.getText());
		
		if(txtBasicSalary.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter basic salary");
			alert.showAndWait();
			return false;
			
		}	
		
		if(!matcher_basic_salary.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Basic salary cannot contain any letters or special characters!");
			alert.showAndWait();
			return false;
		}
		
		
		if(txtAllowances.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter allowance");
			alert.showAndWait();
			return false;
			
		}	
		
		if(!matcher_allowances.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Allowance cannot contain any letters or special characters!");
			alert.showAndWait();
			return false;
		}
		
		if(txtOtHrs.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter OT hours");
			alert.showAndWait();
			return false;
			
		}	
		
		if(!matcher_otHours.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("OT hours cannot contain any letters or special characters!");
			alert.showAndWait();
			return false;
		}
		
		if(txtOtRate.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter OT rate");
			alert.showAndWait();
			return false;
			
		}	
		
		if(!matcher_otRate.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("OT rate cannot contain any letters or special characters!");
			alert.showAndWait();
			return false;
		}
		
		if(txtDeductions.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter deductions");
			alert.showAndWait();
			return false;
			
		}	
		
		if(!matcher_deductions.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Deductions cannot contain any letters or special characters!");
			alert.showAndWait();
			return false;
		}
		
		/*if(txtEpf.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter EPF");
			alert.showAndWait();
			return false;
			
		}*/	
		
		/*if(!matcher_epf.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("EPF cannot contain any letters or special characters!");
			alert.showAndWait();
			return false;
		}*/
		
		/*if(txtEtf.getText().isEmpty()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter EPF");
			alert.showAndWait();
			return false;
			
		}*/	
		
		/*if(!matcher_etf.matches()) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("ETF cannot contain any letters or special characters!");
			alert.showAndWait();
			return false;
		}*/
		
		return true;
		
		
		
	}
	
	

	public void salaryAdd(ActionEvent event) {
		
		/*if(txtSalId.getText().isEmpty() ||txtempNo.getText().isEmpty() ||txtName.getText().isEmpty() ||txtDesignation.getText().isEmpty()
				||txtBasicSalary.getText().isEmpty() || txtDate == null || txtAllowances.getText().isEmpty()
				||txtOtHrs.getText().isEmpty() ||txtOtRate.getText().isEmpty() || txtEtf.getText().isEmpty()
				||txtDeductions.getText().isEmpty() || txtEpf.getText().isEmpty() ) {
			
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter All Fields");
			alert.showAndWait();
			return;
		}*/
	
		/*double basic_salary=Double.parseDouble(txtBasicSalary.getText());
		double allowances_=Double.parseDouble(txtAllowances.getText());
		int ot_hours=Integer.parseInt(txtOtHrs.getText());
		double ot_rate=Double.parseDouble(txtOtRate.getText());
		double deductions_=Double.parseDouble(txtDeductions.getText());
		double epf_=Double.parseDouble(txtEpf.getText());
		double etf = Double.parseDouble(txtEtf.getText());*/
/*
		double total_salary=(basic_salary+allowances_+etf +(ot_hours*ot_rate))-(deductions_ + epf_);		
		String total=Double.toString(total_salary);
	
		double ot_ = ot_hours * ot_rate;
		String ot__ = Double.toString(ot_);*/
		
		if(Salaryvalidation()){
		calculate(event);
		
		//String total="0";
		salary salary_obj = new salary();
		
		salary_obj.setSalaryId(txtSalId.getText());
		salary_obj.setEmpNo(txtempNo.getText());
		salary_obj.setName(txtName.getText());
		salary_obj.setDesignation(txtDesignation.getText());
		salary_obj.setBasicSalary(txtBasicSalary.getText());
		salary_obj.setDate(txtDate.getValue().toString());
		salary_obj.setAllowances(txtAllowances.getText());
		salary_obj.setOtHrs(txtOtHrs.getText());
		salary_obj.setOtRate(txtOtRate.getText());
		salary_obj.setEtf(txtEtf.getText());
		salary_obj.setDeductions(txtDeductions.getText());
		salary_obj.setEpf(txtEpf.getText());
		salary_obj.setTotSalary(TotSal.getText());
		salary_obj.setOt(Aot.getText());
		
		isalary.addSalary(salary_obj);
		clear(event);	
		txtSalId.setText(Generator.generateSalaryId());
		getAllSalary();
		txtSalId.setEditable(true);
		txtempNo.setEditable(true);
		txtEpf.setEditable(true);
		}
	}
	
	public void getAllSalary() {
		
		tblcSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
		tblcEmpNo.setCellValueFactory(new PropertyValueFactory<>("EmpNo"));
		tblcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tblcDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
		tblcBasics.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
		tblcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		tblcAllowances.setCellValueFactory(new PropertyValueFactory<>("allowances"));
		tblcOtHrs.setCellValueFactory(new PropertyValueFactory<>("otHrs"));
		tblcOtRate.setCellValueFactory(new PropertyValueFactory<>("otRate"));
		tblcEtf.setCellValueFactory(new PropertyValueFactory<>("etf"));
		tblcDeductions.setCellValueFactory(new PropertyValueFactory<>("deductions"));
		tblcEpf.setCellValueFactory(new PropertyValueFactory<>("epf"));
		tblcTotalSalary.setCellValueFactory(new PropertyValueFactory<>("totSalary"));
		tblcOT.setCellValueFactory(new PropertyValueFactory<>("ot"));
		
		
		salaryList = isalary.getAllSalary();
		tblViewSalary.setItems(salaryList);		
		
	}
	
	public void clear(ActionEvent event) {
		txtSalId.clear();
		txtempNo.clear();
		txtName.clear();
		txtDesignation.clear();
		txtBasicSalary.clear();
		txtDate.setValue(null);
		txtAllowances.clear();
		txtOtHrs.clear();
		txtOtRate.clear();
		txtEtf.clear();
		txtDeductions.clear();
		txtEpf.clear();
		AAmount.textProperty().setValue("Rs.0.00");
		DAmount.textProperty().setValue("Rs.0.00");
		TotSal.textProperty().setValue("Rs.0.00");
		Aot.textProperty().setValue("Rs.0.00");
		
		txtSalId.setText(Generator.generateSalaryId());
		
		
	}
	
	public void salaryUpdate(ActionEvent event) {		
			
	
			/*if(txtSalId.getText().isEmpty()||txtempNo.getText().isEmpty()||txtName.getText().isEmpty()||txtDesignation.getText().isEmpty()||
			txtBasicSalary.getText().isEmpty()||txtDate == null||txtAllowances.getText().isEmpty()||txtOtHrs.getText().isEmpty()||txtOtRate.getText().isEmpty()||txtEtf.getText().isEmpty()
			||txtDeductions.getText().isEmpty() || txtEpf.getText().isEmpty()) {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Please Enter All Fields");
				alert.showAndWait();
				
			}else {*/
		
			if(Salaryvalidation()) {
				
				Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Are You Sure To Update ?");
				Optional <ButtonType> action= alert.showAndWait();
				
				if(action.get()==ButtonType.OK) {
					
					
					Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
					alert1.setHeaderText(null);
					alert1.setContentText("Calculating salary");
					Optional <ButtonType> action1 = alert1.showAndWait();
					
						if(action1.get() == ButtonType.OK ) {
							btnUpdate.setDisable(true);
							
							calculate(event);
						
					
					
					
					
					
					salary sal=new salary();
					
				/*
					double basic_salary=Double.parseDouble(txtBasicSalary.getText());
					double allowances_=Double.parseDouble(txtAllowances.getText());
					int ot_hours=Integer.parseInt(txtOtHrs.getText());
					double ot_rate=Double.parseDouble(txtOtRate.getText());
					double deductions_=Double.parseDouble(txtDeductions.getText());
					double epf_=Double.parseDouble(txtEpf.getText());
					double etf = Double.parseDouble(txtEtf.getText());
	
					double emp_ot = ot_hours*ot_rate;
					double total_salary = (basic_salary+allowances_+ etf + (ot_hours*ot_rate))-(deductions_ + epf_);
					double totA = allowances_+etf+emp_ot;
					double totD = deductions_+epf_;
					double emp_etf = basic_salary * 0.12;
					double emp_epf = basic_salary * 0.08;
					
					String total =Double.toString(total_salary);
					String ot_ = Double.toString(emp_ot);
					String totAllo = Double.toString(totA);
					String totDeduct = Double.toString(totD);
					String totEtf = Double.toString(emp_etf);
					String totEpf = Double.toString(emp_epf);*/
					

					
					sal.setSalaryId(txtSalId.getText());
					sal.setEmpNo(txtempNo.getText());
					sal.setName(txtName.getText());
					sal.setDesignation(txtDesignation.getText());
					sal.setBasicSalary(txtBasicSalary.getText());
					sal.setDate(txtDate.getValue().toString());
					sal.setAllowances(txtAllowances.getText());
					sal.setOtHrs(txtOtHrs.getText());
					sal.setOtRate(txtOtRate.getText());
					sal.setEtf(txtEtf.getText());
					sal.setDeductions(txtDeductions.getText());
					sal.setEpf(txtEpf.getText());
					sal.setTotSalary(TotSal.getText());
					sal.setOt(Aot.getText());
					sal.setSalaryId(txtSalId.getText());
					
					isalary.updateSalary(sal);
					
					
					
					clear(event);
					
					AAmount.setText("");
					DAmount.setText("");
					TotSal.setText("");	
					Aot.setText("");
					txtSalId.setText(Generator.generateSalaryId());
					
					
				}
			}
			
				
			getAllSalary();
		}
	}

	
	public void salaryDelete(ActionEvent event) {
		ObservableList<salary> selectedSalary;
		
		
		selectedSalary = tblViewSalary.getSelectionModel().getSelectedItems();
		
		if(selectedSalary.isEmpty()) {
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Salary");
			alert.showAndWait();
			
		}else {
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Delete Selected salary ?");
			Optional <ButtonType> action= alert.showAndWait();
			
			if(action.get()==ButtonType.OK) {
				
				
				String salary_del=selectedSalary.get(0).getSalaryId().toString();
				
				isalary.deleteSalary(salary_del);
			}
		}
	
		
		clear(event);
		txtSalId.setText(Generator.generateSalaryId());
		AAmount.textProperty().setValue("Rs.0.00");
		DAmount.textProperty().setValue("Rs.0.00");
		TotSal.textProperty().setValue("Rs.0.00");
		Aot.textProperty().setValue("Rs.0.00");
		
		getAllSalary();
	}
	
	
	public void search(ActionEvent event) {
		function f = new function();
		ResultSet rs = null;
		
		//new model
		
		String emp_name = "txtName";
		String emp_designation = "txtDesignation";
		String emp_basicSalary = "txtBasicSalary";
		String emp_allowances = "txtAllowances";
		String emp_deductions = "txtDeductions";
		String emp_Ot_Hrs = "txtOtHrs";
		String emp_otRate = "txtOtRate";
		
		rs = f.find(txtempNo.getText());
		try {
			if (rs.next()) {
				
				txtName.setText(rs.getString("first_name"));
				txtDesignation.setText(rs.getString("designation"));
				txtBasicSalary.setText(rs.getString("basicSalary"));
				txtAllowances.setText(rs.getString("allowances"));
				txtDeductions.setText(rs.getString("deductions"));
				txtOtHrs.setText(rs.getString("OtHrs"));
				txtOtRate.setText(rs.getString("OtRate"));
				
			} else if(txtempNo.getText().isEmpty()){		

				Alert alert=new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Please Enter employee Id");
				alert.showAndWait();
			}else {
				
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Wrong employee Id check and enter again!!!");
				alert.showAndWait();
				
				
				
			}
			// System.out.println( "NULL"+" "+ "No data for this RefNo");

		} catch (Exception ex) {

			System.out.println("Error" + ex);
		}
	} // end of search
		// ***** Get values from the database and print in the textField*****

	public class function {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		public ResultSet find(String s) {
			try {
				// DB db = new DB();

				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crowndb?useSSL=false", "root",
						"4123");
				ps = con.prepareStatement("select * from employee_nethu where emp_id = ?");
				ps.setString(1, s);
				rs = ps.executeQuery();
			} catch (Exception ex) {

			}
			return rs;
		}
	}
	
	
	public void calculate(ActionEvent event ){
		
		if(Salaryvalidation()) {
		
		int otHrs = Integer.parseInt(txtOtHrs.getText());
		double otRate = Double.parseDouble(txtOtRate.getText());		
		double allo = Double.parseDouble(txtAllowances.getText());	
		double emp_basic = Double.parseDouble(txtBasicSalary.getText());
		double deductions = Double.parseDouble(txtDeductions.getText());
		
		
		
		double otTotal = otHrs*otRate;		
		Aot.setText(Double.toString(otTotal));
		
		
		double epf = emp_basic * 0.08;
		txtEpf.setText(Double.toString(epf));
		
		
		double etf = emp_basic * 0.12;
		txtEtf.setText(Double.toString(etf));
		
		double totAllo = allo + etf + (otTotal);
		AAmount.setText(Double.toString(totAllo));
		
		double totDeduct = epf + deductions;
		DAmount.setText(Double.toString(totDeduct));	
		
		double total = (emp_basic + allo + (otHrs * otRate) +etf ) - (deductions + epf);
		TotSal.setText(Double.toString(total));
		
		btnUpdate.setDisable(false);
		
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
