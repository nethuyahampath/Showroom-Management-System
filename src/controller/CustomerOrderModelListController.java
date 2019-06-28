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
import model.Model;
import service.CustomerOrderServiceImpl;
import service.ICustomerOrderService;


public class CustomerOrderModelListController implements Initializable {

	/*----------------Model Table --------------------*/

	@FXML
	public Button closeButton;
	
	@FXML
    private TableView<Model> itemTable;

    @FXML
    private TableColumn<Model, String> modelName;

    @FXML
    private TableColumn<Model, String> category;

    @FXML
    private TableColumn<Model, String> unitPrice;

    @FXML
    private TableColumn<Model, String> sellingPrice;

    @FXML
    private TableColumn<Model, String> quantity;

    @FXML
    private TableColumn<Model, String> warrentyYears;

	private ObservableList<Model> modelList = FXCollections.observableArrayList();
	
	/*---------------------show item list------------------*/
	public void showItemList() {
		modelList.clear();
		
		ICustomerOrderService icustOrder = new CustomerOrderServiceImpl();
		
		ArrayList<Model> models = icustOrder.getModelListForSales();
		
		for( Model model : models ) {
			
			modelList.add(model);
		} //end for
		
		modelName.setCellValueFactory(new PropertyValueFactory<Model, String>("model"));
		category.setCellValueFactory(new PropertyValueFactory<Model, String>("category"));
		unitPrice.setCellValueFactory(new PropertyValueFactory<Model, String>("unitPrice"));
		sellingPrice.setCellValueFactory(new PropertyValueFactory<Model, String>("sellingPrice"));
		quantity.setCellValueFactory(new PropertyValueFactory<Model, String>("quantity"));
		warrentyYears.setCellValueFactory(new PropertyValueFactory<Model, String>("warranty"));
		
		itemTable.setItems(modelList);
		
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
