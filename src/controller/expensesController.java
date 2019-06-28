package controller;

/*import java.awt.Dimension;*/
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;

/*import javax.swing.JFrame;*/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Main;
import model.User;
import model.deliveryExpenses;
import model.repairExpenses;
import model.showroomExpenses;
import service.EmployeeServiceImpl;
/*import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;*/
import service.IDelivery;
import service.IEmployeeService;
import service.Irepairing;
import service.Ishowroom;
import service.deliveryImpl;
import service.repairingImpl;
import service.showroomImpl;
/*import util.DBConnect;*/
import util.Generator;

public class expensesController implements Initializable {

	@FXML
	private Label userLabel;

	private User userObj = new User();

	public void loadUser(User user) {

		this.userObj = user;

		IEmployeeService empService = new EmployeeServiceImpl();

		userLabel.setText(empService.loadname(userObj));
	}

	@FXML
	private TextField txtDExpenseId;
	@FXML
	private TextField txtDDid;
	@FXML
	private DatePicker txtDDate;
	@FXML
	private TextField txtDAmount;
	@FXML
	private TextField txtDDescription;

	@FXML
	private TextField txtRExpenseId;
	@FXML
	private TextField txtRId;
	@FXML
	private DatePicker txtRdate;
	@FXML
	private TextField txtRamount;
	@FXML
	private TextField txtRdescription;

	@FXML
	private TextField txtSExpensesId;
	@FXML
	private ComboBox<String> cmbSType;
	@FXML
	private TextField txtSutility;
	@FXML
	private DatePicker txtSdate;
	@FXML
	private TextField txtSamount;
	@FXML
	private TextField txtSdescription;

	@FXML
	private TableView<deliveryExpenses> tblVdelivery;
	@FXML
	private TableView<repairExpenses> tblVRepaire;
	@FXML
	private TableView<showroomExpenses> tblVShowroom;
	@FXML
	private TableColumn<?, ?> tblcDExId;
	@FXML
	private TableColumn<?, ?> tblcDDid;
	@FXML
	private TableColumn<?, ?> tblcDdate;
	@FXML
	private TableColumn<?, ?> tblcDamount;
	@FXML
	private TableColumn<?, ?> tblcDdescription;
	@FXML
	private TableColumn<?, ?> tblcRExpenseId;
	@FXML
	private TableColumn<?, ?> tblcRid;
	@FXML
	private TableColumn<?, ?> tblcRdate;
	@FXML
	private TableColumn<?, ?> tblcRamount;
	@FXML
	private TableColumn<?, ?> tblcRdescription;
	@FXML
	private TableColumn<?, ?> tblcSExpenseId;
	@FXML
	private TableColumn<?, ?> tblcStype;
	@FXML
	private TableColumn<?, ?> tblcSdate;
	@FXML
	private TableColumn<?, ?> tblcSUtility;
	@FXML
	private TableColumn<?, ?> tblcSamount;
	@FXML
	private TableColumn<?, ?> tblcSdescription;

	private ObservableList<deliveryExpenses> deliveryList = FXCollections.observableArrayList();
	private ObservableList<repairExpenses> repairList = FXCollections.observableArrayList();
	// private ObservableList<showroomExpenses> showroomList =
	// FXCollections.observableArrayList();
	private ObservableList<String> showroomExpenseTypeList = FXCollections.observableArrayList("Water Bill",
			"Electricity Bill", "Stationary Items", "Maintanance", "Other");

	// private ObservableList<String> reportItems =
	// FXCollections.observableArrayList("All","Delivery Expenses","Repairing
	// Expenses","Showroom Expenses" );

	private ObservableList<showroomExpenses> ExpenseTypeList = FXCollections.observableArrayList();

	IDelivery idelivery = new deliveryImpl();
	Irepairing irepairing = new repairingImpl();
	Ishowroom showroomService = new showroomImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbSType.setItems(showroomExpenseTypeList);
		txtDExpenseId.setText(Generator.generateDeliveryExpenseId());
		txtRExpenseId.setText(Generator.generateRepairingExpensesID());
		txtSExpensesId.setText(Generator.generateShowroomExpensesId());
		txtRExpenseId.setEditable(false);
		txtDExpenseId.setEditable(false);
		txtSExpensesId.setEditable(false);

		getAllDelivery();
		getAllRepairingExpenses();
		getAllShowroomDetails();

		tblVdelivery.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				txtDExpenseId.setText(tblVdelivery.getSelectionModel().getSelectedItem().getDeliveryExpenseID());
				txtDDid.setText(tblVdelivery.getSelectionModel().getSelectedItem().getDeliveryId());

				String d = tblVdelivery.getSelectionModel().getSelectedItem().getDate();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

				String date = d;

				// convert String to LocalDate
				LocalDate localDate = LocalDate.parse(date, formatter);

				expensesController.this.txtDDate.setValue(localDate);

				// txtDDate.setValue(tblVdelivery.getSelectionModel().getSelectedItem().getDate());
				txtDAmount.setText(tblVdelivery.getSelectionModel().getSelectedItem().getAmount());
				txtDDescription.setText(tblVdelivery.getSelectionModel().getSelectedItem().getDescription());

				txtDExpenseId.setEditable(false);
				txtDDid.setEditable(false);

			}
		});

		tblVRepaire.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				txtRExpenseId.setText(tblVRepaire.getSelectionModel().getSelectedItem().getRepairExpenseId());
				txtRId.setText(tblVRepaire.getSelectionModel().getSelectedItem().getRepairId());

				String d = tblVRepaire.getSelectionModel().getSelectedItem().getDate();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

				String date = d;

				// convert String to LocalDate
				LocalDate localDate = LocalDate.parse(date, formatter);

				expensesController.this.txtRdate.setValue(localDate);

				// txtRdate.setPromptText(tblVRepaire.getSelectionModel().getSelectedItem().getDate());
				txtRamount.setText(tblVRepaire.getSelectionModel().getSelectedItem().getAmount());
				txtRdescription.setText(tblVRepaire.getSelectionModel().getSelectedItem().getDescription());

				txtRExpenseId.setEditable(false);
				txtRId.setEditable(false);

			}
		});

		tblVShowroom.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				txtSExpensesId.setText(tblVShowroom.getSelectionModel().getSelectedItem().getShowroomExpenseId());
				cmbSType.setValue(tblVShowroom.getSelectionModel().getSelectedItem().getType());

				String d = tblVShowroom.getSelectionModel().getSelectedItem().getDate();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

				String date = d;

				// convert String to LocalDate
				LocalDate localDate = LocalDate.parse(date, formatter);

				expensesController.this.txtSdate.setValue(localDate);

				// txtSdate.setPromptText(tblVShowroom.getSelectionModel().getSelectedItem().getDate());
				txtSutility.setText(tblVShowroom.getSelectionModel().getSelectedItem().getUtility());
				txtSamount.setText(tblVShowroom.getSelectionModel().getSelectedItem().getAmount());
				txtSdescription.setText(tblVShowroom.getSelectionModel().getSelectedItem().getDescription());

				txtSExpensesId.setEditable(false);

			}
		});

	}

	// showroomList = showroomService.g;

	public void getAllDelivery() {

		tblcDExId.setCellValueFactory(new PropertyValueFactory<>("deliveryExpenseID"));
		tblcDDid.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
		tblcDdate.setCellValueFactory(new PropertyValueFactory<>("date"));
		tblcDamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		tblcDdescription.setCellValueFactory(new PropertyValueFactory<>("description"));

		deliveryList = idelivery.getAllDeliveryExpenses();

		tblVdelivery.setItems(deliveryList);
	}

	public void getAllRepairingExpenses() {

		tblcRExpenseId.setCellValueFactory(new PropertyValueFactory<>("repairExpenseId"));
		tblcRid.setCellValueFactory(new PropertyValueFactory<>("repairId"));
		tblcRdate.setCellValueFactory(new PropertyValueFactory<>("date"));
		tblcRamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		tblcRdescription.setCellValueFactory(new PropertyValueFactory<>("description"));

		repairList = irepairing.getAllRepairingExpense();

		tblVRepaire.setItems(repairList);
	}

	public void getAllShowroomDetails() {

		tblcSExpenseId.setCellValueFactory(new PropertyValueFactory<>("showroomExpenseId"));
		tblcStype.setCellValueFactory(new PropertyValueFactory<>("type"));
		tblcSUtility.setCellValueFactory(new PropertyValueFactory<>("utility"));
		tblcSdate.setCellValueFactory(new PropertyValueFactory<>("date"));
		tblcSamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		tblcSdescription.setCellValueFactory(new PropertyValueFactory<>("description"));

		ExpenseTypeList = showroomService.getAllShowroomExpenses();

		tblVShowroom.setItems(ExpenseTypeList);
	}

	public boolean deliveryExpenses_validation() {
		Pattern pattern_ID = Pattern.compile("[a-zA-Z0-9]*");
		Matcher matcher_delivery_expense_id = pattern_ID.matcher(txtDExpenseId.getText());
		Matcher matcher_delivery_id = pattern_ID.matcher(txtDDid.getText());
		Matcher matcher_description = pattern_ID.matcher(txtDDescription.getText());

		if (txtDExpenseId.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A Delivery Expense ID");
			alert.showAndWait();
			return false;
		}

		if (!matcher_delivery_expense_id.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Delivery Expense  ID cannot contain any special charanter!");
			alert.showAndWait();
			return false;
		}

		if (txtDDid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A Delivery ID");
			alert.showAndWait();
			return false;
		}

		if (!matcher_delivery_id.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Delivery ID cannot contain any special charanter!");
			alert.showAndWait();
			return false;
		}

		Pattern pattern_amount = Pattern.compile("[0-9]*");
		Matcher matcher_delivery_amount = pattern_amount.matcher(txtDAmount.getText());

		if (txtDAmount.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A amount");
			alert.showAndWait();
			return false;
		}

		if (!matcher_delivery_amount.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Amount cannot contain letters or any special charanter!");
			alert.showAndWait();
			return false;
		}

		if (txtDDescription.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A Description ");
			alert.showAndWait();
			return false;
		}

		if (!matcher_description.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Description cannot contain any special charanter!");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public boolean repairing_expenses_validation() {

		Pattern pattern_ID = Pattern.compile("[a-zA-Z0-9]*");
		Matcher matcher_repairing_expense_id = pattern_ID.matcher(txtRExpenseId.getText());
		Matcher matcher_repair_id = pattern_ID.matcher(txtRId.getText());
		Matcher matcher_description = pattern_ID.matcher(txtDDescription.getText());

		if (txtRExpenseId.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A Repair Expense ID");
			alert.showAndWait();
			return false;
		}

		if (!matcher_repairing_expense_id.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Repair Expense  ID cannot contain any special charanter!");
			alert.showAndWait();
			return false;
		}

		if (txtRId.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A Repair ID");
			alert.showAndWait();
			return false;
		}

		if (!matcher_repair_id.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Reapir ID cannot contain any special charanter!");
			alert.showAndWait();
			return false;
		}

		Pattern pattern_amount = Pattern.compile("[0-9]*");
		Matcher matcher_repair_amount = pattern_amount.matcher(txtRamount.getText());

		if (txtRamount.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A amount");
			alert.showAndWait();
			return false;
		}

		if (!matcher_repair_amount.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Amount cannot contain letters or any special charanter!");
			alert.showAndWait();
			return false;
		}

		if (txtRdescription.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A description");
			alert.showAndWait();
			return false;
		}

		if (!matcher_description.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Description cannot contain any special charanter!");
			alert.showAndWait();
			return false;
		}
		return true;

	}

	public boolean showroom_expenses_validation() {

		Pattern pattern_ID = Pattern.compile("[a-zA-Z0-9]*");
		Matcher matcher_showroom_expense_id = pattern_ID.matcher(txtSExpensesId.getText());
		Matcher matcher_showrrom_id = pattern_ID.matcher(txtSutility.getText());
		Matcher matcher_showroom_description = pattern_ID.matcher(txtSdescription.getText());

		if (txtSExpensesId.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A Showroom Expense ID");
			alert.showAndWait();
			return false;

		}

		if (!matcher_showroom_expense_id.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Showrrom Expense  ID cannot contain any special charanter!");
			alert.showAndWait();
			return false;
		}

		if (cmbSType.getValue().isEmpty()) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A Type");
			alert.showAndWait();
			return false;

		}

		if (txtSutility.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A Utility");
			alert.showAndWait();
			return false;

		}

		if (!matcher_showrrom_id.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Utility cannot contain any special charanter!");
			alert.showAndWait();
			return false;
		}

		Pattern pattern_amount = Pattern.compile("[0-9]*");
		Matcher matcher_showroom_amount = pattern_amount.matcher(txtSamount.getText());

		if (txtSamount.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A amount");
			alert.showAndWait();
			return false;

		}

		if (!matcher_showroom_amount.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Amount cannot contain letters or any special charanter!");
			alert.showAndWait();
			return false;
		}

		if (txtSdescription.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please enter A description");
			alert.showAndWait();
			return false;

		}

		if (!matcher_showroom_description.matches()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Description cannot contain  any special charanter!");
			alert.showAndWait();
			return false;
		}

		return true;

	}

	@FXML
	public void addDeliveryExpenses(ActionEvent event) {

		if (deliveryExpenses_validation()) {

			deliveryExpenses delivery = new deliveryExpenses();

			delivery.setDeliveryExpenseID(txtDExpenseId.getText());
			delivery.setDeliveryId(txtDDid.getText());
			delivery.setDate(txtDDate.getValue().toString());
			delivery.setAmount(txtDAmount.getText());
			delivery.setDescription(txtDDescription.getText());

			System.out.println("dsdsdd");
			idelivery.addDeliveryExpenses(delivery);
			clearD(event);
			txtDExpenseId.setText(Generator.generateDeliveryExpenseId());
			getAllDelivery();
			// txtDExpenseId.setEditable(false);

		}
	}

	public void addRepairingExpenses(ActionEvent event) {

		if (repairing_expenses_validation()) {

			repairExpenses repairing = new repairExpenses();

			repairing.setRepairExpenseId(txtRExpenseId.getText());
			repairing.setRepairId(txtRId.getText());
			repairing.setDate(txtRdate.getValue().toString());
			repairing.setAmount(txtRamount.getText());
			repairing.setDescription(txtRdescription.getText());

			irepairing.addRepairingExpense(repairing);

			clearR(event);
			txtRExpenseId.setText(Generator.generateRepairingExpensesID());

			getAllRepairingExpenses();

			txtRExpenseId.setEditable(false);
			txtRId.setEditable(true);

		}
	}

	public void addShowroomExpenses(ActionEvent event) {

		if (showroom_expenses_validation()) {

			showroomExpenses showroom_expenses = new showroomExpenses();

			showroom_expenses.setShowroomExpenseId(txtSExpensesId.getText());
			showroom_expenses.setUtility(txtSutility.getText());
			showroom_expenses.setDate(txtSdate.getValue().toString());
			showroom_expenses.setAmount(txtSamount.getText());
			showroom_expenses.setType(cmbSType.getSelectionModel().getSelectedItem().toString());
			showroom_expenses.setDescription(txtSdescription.getText());

			showroomService.addShowrromExpenses(showroom_expenses);

			clearS(event);
			txtSExpensesId.setText(Generator.generateShowroomExpensesId());

			getAllShowroomDetails();
			txtSExpensesId.setEditable(false);

		}
	}

	@FXML
	public void DeliveryExpenseUpdate(ActionEvent event) {

		if (deliveryExpenses_validation()) {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Update ?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK) {

				deliveryExpenses delivery = new deliveryExpenses();

				delivery.setDeliveryExpenseID(txtDExpenseId.getText());
				delivery.setDeliveryId(txtDDid.getText());
				delivery.setDate(txtDDate.getValue().toString());
				delivery.setAmount(txtDAmount.getText());
				delivery.setDescription(txtDDescription.getText());
				delivery.setDeliveryExpenseID(txtDExpenseId.getText());

				idelivery.updateDeliveryExpense(delivery);

				clearD(event);
				txtDExpenseId.setText(Generator.generateDeliveryExpenseId());
				txtDExpenseId.setEditable(false);
			}
		}

		getAllDelivery();
	}

	public void RepairingExpenseUpdate(ActionEvent event) {

		if (repairing_expenses_validation()) {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Update ?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK) {
				repairExpenses repairing = new repairExpenses();

				repairing.setRepairExpenseId(txtRExpenseId.getText());
				repairing.setRepairId(txtRId.getText());
				repairing.setDate(txtRdate.getValue().toString());
				repairing.setAmount(txtRamount.getText());
				repairing.setDescription(txtRdescription.getText());
				repairing.setRepairExpenseId(txtRExpenseId.getText());

				irepairing.updateRepairingExpense(repairing);

				clearR(event);
				txtRExpenseId.setText(Generator.generateRepairingExpensesID());

				txtRExpenseId.setEditable(false);
				txtRId.setEditable(true);
			}
		}

		getAllRepairingExpenses();
	}

	public void showroomExpenseUpdate(ActionEvent event) {

		if (showroom_expenses_validation()) {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Update ?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK) {

				showroomExpenses showroom_expense = new showroomExpenses();

				showroom_expense.setShowroomExpenseId(txtSExpensesId.getText());
				showroom_expense.setType(cmbSType.getSelectionModel().getSelectedItem().toString());
				showroom_expense.setUtility(txtSutility.getText());
				showroom_expense.setDate(txtSdate.getValue().toString());
				showroom_expense.setAmount(txtSamount.getText());
				showroom_expense.setDescription(txtSdescription.getText());
				showroom_expense.setShowroomExpenseId(txtSExpensesId.getText());

				showroomService.updateShowroomExpenses(showroom_expense);

				clearS(event);

				txtSExpensesId.setText(Generator.generateShowroomExpensesId());

				txtSExpensesId.setEditable(false);
			}
		}
		getAllShowroomDetails();
	}

	@FXML
	public void DeleteDeliveryExpense(ActionEvent event) {

		ObservableList<deliveryExpenses> selectDelivery;

		selectDelivery = tblVdelivery.getSelectionModel().getSelectedItems();

		if (selectDelivery.isEmpty()) {

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Expense");
			alert.showAndWait();

		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Delete Selected Expense ?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK) {

				String salary_del = selectDelivery.get(0).getDeliveryExpenseID().toString();

				idelivery.deleteDeliveryExpense(salary_del);
			}
		}

		clearD(event);
		txtDExpenseId.setText(Generator.generateDeliveryExpenseId());
		txtDExpenseId.setEditable(false);

		getAllDelivery();
	}

	public void deleteRepairingExpense(ActionEvent event) {

		ObservableList<repairExpenses> selectRepairingExpense;

		selectRepairingExpense = tblVRepaire.getSelectionModel().getSelectedItems();

		if (selectRepairingExpense.isEmpty()) {

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Expense");
			alert.showAndWait();

		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Delete Selected Expense ?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK) {

				String repaire_del = selectRepairingExpense.get(0).getRepairExpenseId().toString();

				irepairing.deleteRepairingExpense(repaire_del);
				;
			}
		}

		clearR(event);
		txtRExpenseId.setText(Generator.generateRepairingExpensesID());
		txtRExpenseId.setEditable(false);
		txtRId.setEditable(true);

		getAllRepairingExpenses();
	}

	public void deleteShowroomExpenses(ActionEvent event) {

		ObservableList<showroomExpenses> selectShowroomExpense;

		selectShowroomExpense = tblVShowroom.getSelectionModel().getSelectedItems();

		if (selectShowroomExpense.isEmpty()) {

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please Select A Expense");
			alert.showAndWait();

		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Are You Sure To Delete Selected Expense ?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK) {

				String showroom_del = selectShowroomExpense.get(0).getShowroomExpenseId().toString();

				showroomService.deleteShowroomExpenses(showroom_del);
			}
		}

		clearS(event);

		txtDExpenseId.setText(Generator.generateShowroomExpensesId());

		txtDExpenseId.setEditable(false);

		getAllShowroomDetails();
	}

	@FXML
	public void clearD(ActionEvent event) {

		txtDExpenseId.setText("");
		txtDDid.setText("");
		txtDDate.setValue(null);
		txtDAmount.setText("");
		txtDDescription.setText("");

		txtDExpenseId.setEditable(false);
		txtDDid.setEditable(true);

		txtDExpenseId.setText(Generator.generateDeliveryExpenseId());

	}

	public void clearR(ActionEvent event) {

		txtRExpenseId.setText("");
		txtRId.setText("");
		txtRdate.setValue(null);
		txtRamount.setText("");
		txtRdescription.setText("");

		txtRExpenseId.setText(Generator.generateRepairingExpensesID());
		txtRExpenseId.setEditable(false);
		txtRId.setEditable(true);

	}

	public void clearS(ActionEvent event) {

		txtSExpensesId.setText("");
		cmbSType.setValue("");
		txtSutility.setText("");
		txtSdate.setValue(null);
		txtSamount.setText("");
		txtSdescription.setText("");

		txtSExpensesId.setText(Generator.generateShowroomExpensesId());

		txtSExpensesId.setEditable(false);

	}

	/*
	 * @FXML public void reportGenerating(ActionEvent event) throws Exception{
	 * 
	 * try { connection = DBConnect. getDBConnection();
	 * 
	 * if(cmbRType.getSelectionModel().getSelectedItem() == null) { Alert alert =
	 * new Alert(Alert.AlertType.ERROR); alert.setHeaderText(null);
	 * alert.setContentText("Please Select A Type"); alert.showAndWait(); } else
	 * if(txtDId.getText().isEmpty()) { Alert alert = new
	 * Alert(Alert.AlertType.CONFIRMATION); alert.setHeaderText(null);
	 * alert.setContentText("Dissmiss the Delivery ID.. Press OK to proceed!!!");
	 * Optional<ButtonType> action = alert.showAndWait(); if (action.get() ==
	 * ButtonType.OK) {
	 * 
	 * JasperDesign jasperDesign = JRXmlLoader.load(
	 * "C:\\Users\\NETHU\\eclipse-workspace\\fms_emp_expenses_new1\\src\\report\\DeliveryExpense.jrxml"
	 * ); String query = "SELECT * FROM delivery_expense"; JRDesignQuery jrQuery =
	 * new JRDesignQuery(); jrQuery.setText(query); jasperDesign.setQuery(jrQuery);
	 * 
	 * JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	 * JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , null ,
	 * connection ); JRViewer viewer = new JRViewer( jasperPrint );
	 * 
	 * //JRDataSource jrDataSource = new JREmptyDataSource();
	 * 
	 * //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
	 * connection);
	 * 
	 * JasperExportManager.exportReportToPdfFile(jasperPrint,
	 * "C:\\Users\\NETHU\\Desktop\\reports"+".pdf" );
	 * 
	 * 
	 * //to view the jasper report in the pdf viewer - (can be used for a button to
	 * view the report) viewer.setOpaque(true); viewer.setVisible(true);
	 * 
	 * JFrame frame = new JFrame("Delivery Report"); frame.add(viewer);
	 * frame.setSize(new Dimension(500, 500)); frame.setLocationRelativeTo(null);
	 * frame.setVisible(true); } }
	 * 
	 * String value1 = txtDId.getText().toString(); JasperDesign jasperDesign =
	 * JRXmlLoader.load(
	 * "C:\\Users\\NETHU\\eclipse-workspace\\fms_emp_expenses_new1\\src\\report\\DeliveryExpense.jrxml"
	 * ); String query =
	 * "SELECT * FROM delivery_expense WHERE total = '"+value1+"'"; JRDesignQuery
	 * jrQuery = new JRDesignQuery(); jrQuery.setText(query);
	 * jasperDesign.setQuery(jrQuery);
	 * 
	 * JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	 * JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , null ,
	 * connection ); JRViewer viewer = new JRViewer( jasperPrint );
	 * 
	 * //JRDataSource jrDataSource = new JREmptyDataSource();
	 * 
	 * //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
	 * connection);
	 * 
	 * JasperExportManager.exportReportToPdfFile(jasperPrint,
	 * "C:\\Users\\NETHU\\Desktop\\reports"+".pdf" );
	 * 
	 * 
	 * //to view the jasper report in the pdf viewer - (can be used for a button to
	 * view the report) viewer.setOpaque(true); viewer.setVisible(true);
	 * 
	 * JFrame frame = new JFrame("Delivery Report"); frame.add(viewer);
	 * frame.setSize(new Dimension(500, 500)); frame.setLocationRelativeTo(null);
	 * frame.setVisible(true);
	 * 
	 * 
	 * }catch(Exception e) { System.out.println("Exception : " + e ); } }
	 */
	

	



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
