package service;


import java.util.ArrayList;

import javafx.collections.ObservableList;
import model.Item;
import model.Model;
import model.SupplierRequestOrder;


public interface IInventoryServices {

	public ObservableList<String> getCategoryNames();
	public ObservableList<String> getSuppliers(String ctgry);
	public ObservableList<String> getModels();
	public ObservableList<String> getOrders();
	public String addModel(Model model);
	public ObservableList<Model> getAllModels();
	public void deleteModel(String model_name);
	public void updateModel(Model model);
	public void addNewCategory(String category);
	public void deleteCategory(String category);
	
	public String addItem(Item item);
	public ObservableList<Item> getAllItems();
	public void updateItem(Item item);
	public void deleteItem(String item_code);
	
	public String addSupplierRequestOrder(SupplierRequestOrder order);
	public ObservableList<SupplierRequestOrder> getAllSupplierRequestOrders();
	public void deleteSupplierRequestOrder(String order_id);
	public void updateSupplierRequestOrder(SupplierRequestOrder order);
	public ArrayList<Item> getAllItemsSearch();
	
	
	
	
	public void generateSORRByMonthAndYear(String loadPath , int month, int year, String btnInput, String storePath );
	public void generateFullSORR( String loadPath ,  String btnInput, String storePath);
	public void generateSORRByDate( String loadPath , String date  , String btnInput, String storePath );
	
	
	
}
