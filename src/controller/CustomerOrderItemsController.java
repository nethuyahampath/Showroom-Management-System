package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.net.URL;
import util.Generator;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.BrandNewItem;
import model.Cart;
import service.CustomerOrderServiceImpl;
import service.ICustomerOrderService;


public class CustomerOrderItemsController implements Initializable {

	/*----------------Item Table --------------------*/

	@FXML
	public Button closeButton;

	@FXML
	private TableView<BrandNewItem> itemTable;
	
	@FXML
	private TableColumn<BrandNewItem, String> itemCode;

	@FXML
	private TableColumn<BrandNewItem, String> modelName;

	@FXML
	private TableColumn<BrandNewItem , String> itemCondition;

	@FXML
	private TableColumn<BrandNewItem, String> status;

	private ObservableList<BrandNewItem> itemList = FXCollections.observableArrayList();
	
	/*---------------------show item list------------------*/
	public void showItemList() {
		itemList.clear();
		
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		ArrayList<BrandNewItem> items = icustOrder.getItemListForSales();
		
		for( BrandNewItem item : items ) {
			
			itemList.add(item);
		}
		
		itemCode.setCellValueFactory(new PropertyValueFactory<BrandNewItem, String>("itemCode"));
		modelName.setCellValueFactory(new PropertyValueFactory<BrandNewItem, String>("modelName"));
		itemCondition.setCellValueFactory(new PropertyValueFactory<BrandNewItem, String>("itemCondition"));
		status.setCellValueFactory(new PropertyValueFactory<BrandNewItem, String>("itemStatus"));
		
		itemTable.setItems(itemList);
		
	}
	
	/*-------------------initialize----------------------*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		showItemList();
	}

	/*------------------Exit Button---------------------------*/
	public void ExitWindow(ActionEvent event) throws Exception {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}
