package service;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Item;
import model.Model;
import model.SupplierRequestOrder;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import util.DBConnect;
import util.DBconnection;
import util.CommonConstants;



import java.awt.Dimension;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;



import model.BrandNewItem;
import model.Cart;
import model.Model;
import model.CustomerOrder;
import model.CustomerPayment;
import model.Order;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import util.CommonConstants;
import util.DBConnect;
import util.Generator;

public class InventoryServicesImpl implements IInventoryServices{

	
	Connection connection;
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public ObservableList<String> getCategoryNames() {
		
		
		ObservableList<String> categoryNameList=FXCollections.observableArrayList();
		
				
		try {
			
			
			connection = DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}		
				
			String query = CommonConstants.QUERY_ALL_CATEGORY_NAMES;
			
				
			preparedStatement=connection.prepareStatement(query);
				
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {	
				
				categoryNameList.add(resultSet.getString(1));
	
			}
				
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Get Category Names" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In Get Category Names : " + e);	
			System.out.println("Exception In at  Get Category Names" + e.getLocalizedMessage());	
		}finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get Category Names : " + e);
			}
			
			
		}
		
		return categoryNameList;
		
		
	}
	
	
	
	
	
	
	
	
	@Override
	public ObservableList<String> getSuppliers(String ctgry) {
		
		
		
		ObservableList<String> supplierNameList=FXCollections.observableArrayList();
		
				
		try {
			
			
			connection = DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}		
				
			String query = CommonConstants.QUERY_ALL_SUPPLIERS;
			
				
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, ctgry);
				
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {	
				
				supplierNameList.add(resultSet.getString(1));
	
			}
				
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Get Suppliers" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In Get Suppliers : " + e);	
			System.out.println("Exception In at  Get Suppliers" + e.getLocalizedMessage());	
		}finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get Suppliers : " + e);
			}
			
			
		}
		
		return supplierNameList;
		
		
	}









	@Override
	public String addModel(Model model) {	
			
		String out = "";
		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = CommonConstants.QUERY_ADD_MODEL;
			
			preparedStatement =connection.prepareStatement(query);	
			
			preparedStatement.setString(1, model.getModel());
			preparedStatement.setString(2, model.getCategory());
			preparedStatement.setString(3, model.getSupplier());
			preparedStatement.setDouble(4,  Double.parseDouble(model.getUnitPrice()));
			preparedStatement.setDouble(5,  Double.parseDouble(model.getSellingPrice()));
			preparedStatement.setInt(6, Integer.parseInt(model.getQuantity()));
			preparedStatement.setInt(7, Integer.parseInt(model.getWarranty()));
			
			preparedStatement.execute();
			
			System.out.println(query);
			
			//itemTable.getItems().add(item);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Model Added Successfully");
			alert.showAndWait();
			return "";
			
			
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Add Model" + e.getLocalizedMessage());
			out = "error";
		} catch (Exception e) {
			System.out.println("Exception In Add Model : " + e);	
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Add Model : " + e);
			}		
			
			
		
		}
	return out;
		
	
	}








	@Override
	public ObservableList<Model> getAllModels() {	
		
		ObservableList<Model> modelList=FXCollections.observableArrayList();
			
			try {
				
				connection=DBConnect.getDBConnection();
				
				if(connection==null) {
					System.out.println("Connection not successful");
				}
				
				String query = CommonConstants.QUERY_GET_ALL_MODELS;
				
				preparedStatement=connection.prepareStatement(query);
				
				resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					Model model = new Model();
					
					model.setModel(resultSet.getString(1));
					model.setCategory(resultSet.getString(2));
					model.setSupplier(resultSet.getString(3));
					model.setUnitPrice(resultSet.getString(4));
					model.setSellingPrice(resultSet.getString(5));
					model.setQuantity(resultSet.getString(6));
					model.setWarranty(resultSet.getString(7));
					
					
					modelList.add(model);
				}
				
				System.out.println(query);
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
				System.out.println("Exception at  get All Models" + e.getLocalizedMessage());	
				
			} catch (Exception e) {
				System.out.println("Exception In get All Models : " + e);	
			}finally {
				try {
					preparedStatement.close();
					resultSet.close();
					connection.close();
				} catch (SQLException e) {
					System.out.println("Finally Exception In Get All Models : " + e);
				}
				
				
			}
			
		return modelList;
	}








	@Override
	public void deleteModel(String model_name) {
		
		try {		
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
				
			String query = CommonConstants.QUERY_DELETE_MODEL;
			preparedStatement = connection.prepareStatement(query);
					
			
			
			preparedStatement.setString(1, model_name);
			
			preparedStatement.executeUpdate();
			
			/*for(Model model:model_name) {
				allItems.remove(item);
			}
			*/
			
			
			System.out.println(query);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Model Deleted Successfully");
			alert.showAndWait();
			return;
			
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Delete Model" + e.getLocalizedMessage());
			
		} catch (Exception e) {
			System.out.println("Exception In Delete Model  : " + e );
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Delete Model : " + e);
			}		
			
		}
		
		
		
		
		
	}








	@Override
	public void updateModel(Model model) {
			
		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
	
			String query=CommonConstants.QUERY_UPDATE_MODEL;
			
			preparedStatement = connection.prepareStatement(query);
					
			preparedStatement.setString(1, model.getCategory());
			preparedStatement.setString(2, model.getSupplier());
			preparedStatement.setDouble(3,  Double.parseDouble(model.getUnitPrice()));
			preparedStatement.setDouble(4,  Double.parseDouble(model.getSellingPrice()));
			preparedStatement.setInt(5, Integer.parseInt(model.getQuantity()));
			preparedStatement.setInt(6, Integer.parseInt(model.getWarranty()));
			preparedStatement.setString(7, model.getModel());
				
			preparedStatement.executeUpdate();
			
			System.out.println(query);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Model Updated Successfully");
			alert.showAndWait();
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Update Model" + e.getLocalizedMessage());
			
		} catch (Exception e) {
			System.out.println("Exception In Update Model : " + e);	
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Update Model : " + e);
			}		
			
		}
		
		
		
	}








	@Override
	public void addNewCategory(String category) {


		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = CommonConstants.QUERY_ADD_CATEGORY;
			
			preparedStatement =connection.prepareStatement(query);	
			
			preparedStatement.setString(1, category);
			
			
			preparedStatement.execute();
			
			System.out.println(query);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Category Added Successfully");
			alert.showAndWait();
			return;
			
			
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Add Category" + e.getLocalizedMessage());
				
		} catch (Exception e) {
			System.out.println("Exception In Add Category : " + e);	
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Add Category : " + e);
			}		
					
		}
		
	}








	@Override
	public void deleteCategory(String category) {
	
		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = CommonConstants.QUERY_DELETE_CATEGORY;
			
			preparedStatement =connection.prepareStatement(query);	
			
			preparedStatement.setString(1, category);
			
			
			preparedStatement.execute();
			
			System.out.println(query);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Category Deleted Successfully");
			alert.showAndWait();
			return;
			
			
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Delete Category" + e.getLocalizedMessage());
				
		} catch (Exception e) {
			System.out.println("Exception In Delete Category : " + e);	
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Delete Category : " + e);
			}		
			
			
		
		}

		
	}








	@Override
	public String addItem(Item item) {


		String out = "";
		
		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = CommonConstants.QUERY_ADD_ITEM;
			String query2 = CommonConstants.QUERY_ADD_BRAND_NEW_ITEM;
			
			preparedStatement =connection.prepareStatement(query);	
			
			
			preparedStatement.setString(1, item.getItemCode());
			preparedStatement.setString(2, item.getModelName());
			preparedStatement.setString(3, item.getItemCondition());
			preparedStatement.setString(4, item.getItemStatus());
			preparedStatement.setString(5, item.getIdOrderSupplier());
			preparedStatement.execute();

			if(item.getItemStatus()=="stock") {
				PreparedStatement p=connection.prepareStatement(query2);
				p.setString(1, item.getItemCode());
				
				
				p.execute();
			}
			
			
			System.out.println(query);
			System.out.println(query2);
			
			//itemTable.getItems().add(item);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Item Added Successfully");
			alert.showAndWait();
			return "";
			
			
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Add Item" + e.getLocalizedMessage());
			out="error";	
		} catch (Exception e) {
			System.out.println("Exception In Add Item : " + e);	
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Add Item : " + e);
			}		
			
			
		
		}
		
		return out;
		
	}








	@Override
	public ObservableList<Item> getAllItems() {


		ObservableList<Item> itemList=FXCollections.observableArrayList();
		
		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = CommonConstants.QUERY_GET_ALL_ITEMS;
			
			preparedStatement=connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Item item = new Item();
				
				item.setItemCode(resultSet.getString(1));
				item.setModelName(resultSet.getString(2));
				item.setItemCondition(resultSet.getString(3));
				item.setItemStatus(resultSet.getString(4));
				item.setIdOrderSupplier(resultSet.getString(5));
				
				
				itemList.add(item);
			}
			
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Items" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All Items : " + e);	
		}finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get All Items : " + e);
			}
			
			
		}
		
	return itemList;
		
	}








	@Override
	public void updateItem(Item item) {


		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
	
			String query=CommonConstants.QUERY_UPDATE_ITEM;
			
			preparedStatement = connection.prepareStatement(query);
					
			preparedStatement.setString(1, item.getModelName());
			preparedStatement.setString(2, item.getItemCondition());
			preparedStatement.setString(3, item.getItemStatus());
			preparedStatement.setString(4, item.getIdOrderSupplier());
			preparedStatement.setString(5, item.getItemCode());
				
			preparedStatement.executeUpdate();
			
			System.out.println(query);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Item Updated Successfully");
			alert.showAndWait();
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Update Item" + e.getLocalizedMessage());
			
		} catch (Exception e) {
			System.out.println("Exception In Update Item : " + e);	
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Update Item : " + e);
			}		
			
		}


		
	}








	@Override
	public void deleteItem(String item_code) {



		try {		
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
				
			String query = CommonConstants.QUERY_DELETE_ITEM;
			preparedStatement = connection.prepareStatement(query);
					
			
			
			preparedStatement.setString(1, item_code);
			
			preparedStatement.executeUpdate();
			
			/*for(Model model:model_name) {
				allItems.remove(item);
			}
			*/
			
			
			System.out.println(query);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Item Deleted Successfully");
			alert.showAndWait();
			return;
			
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Delete Item" + e.getLocalizedMessage());
			
		} catch (Exception e) {
			System.out.println("Exception In Delete Item  : " + e );
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Delete Item : " + e);
			}		
			
		}
		
		
	}








	@Override
	public ObservableList<String> getModels() {
		
		ObservableList<String> modelList=FXCollections.observableArrayList();
		
		
		try {
			
			
			connection = DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}		
				
			String query = CommonConstants.QUERY_ALL_MODELS;
			
				
			preparedStatement=connection.prepareStatement(query);
				
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {	
				
				modelList.add(resultSet.getString(1));
	
			}
				
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Get Models" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In Get Models : " + e);	
			System.out.println("Exception In at  Get Models" + e.getLocalizedMessage());	
		}finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get Models : " + e);
			}
			
			
		}
		
		return modelList;
		
		
	}








	@Override
	public ObservableList<String> getOrders() {


		ObservableList<String> orderList=FXCollections.observableArrayList();
		
		
		try {
			
			
			connection = DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}		
				
			String query = CommonConstants.QUERY_ALL_ORDERS;
			
				
			preparedStatement=connection.prepareStatement(query);
				
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {	
				
				orderList.add(resultSet.getString(1));
	
			}
				
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Get Models" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In Get Models : " + e);	
			System.out.println("Exception In at  Get Models" + e.getLocalizedMessage());	
		}finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get Models : " + e);
			}
			
			
		}
		
		return orderList;
		
	}








	@Override
	public String addSupplierRequestOrder(SupplierRequestOrder order) {


		String out = "";
		
		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = CommonConstants.QUERY_ADD_SUPPLIER_ORDER_REQUEST;
			
			preparedStatement =connection.prepareStatement(query);	
			
			preparedStatement.setString(1, order.getSupplierReuestOrderId());
			preparedStatement.setString(2, order.getSupplierRequestOrderCategory());
			preparedStatement.setString(3, order.getSupplierRequestOrderModel());
			preparedStatement.setString(4, order.getSupplierRequestOrderDate());
			preparedStatement.setInt(5,Integer.parseInt (order.getSupplierRequestOrderQuantity()));
			preparedStatement.setString(6, order.getSupplierRequestOrderStatus());
			
			
			
			preparedStatement.execute();
			
			System.out.println(query);
			
			//itemTable.getItems().add(item);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Order Request Added Successfully");
			alert.showAndWait();
			return "";
			
			
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Add SupplierRequestOrder" + e.getLocalizedMessage());
			out="error";	
		} catch (Exception e) {
			System.out.println("Exception In Add SupplierRequestOrder : " + e);	
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Add SupplierRequestOrder : " + e);
			}		
			
			
		
		}
		
		return out;
		
		
	}








	@Override
	public ObservableList<SupplierRequestOrder> getAllSupplierRequestOrders() {


		ObservableList<SupplierRequestOrder> orderList=FXCollections.observableArrayList();
		
		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
			
			String query = CommonConstants.QUERY_GET_ALL_SUPPLIER_REQUEST_ORDERS;
			
			preparedStatement=connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				SupplierRequestOrder order = new SupplierRequestOrder();
				
				order.setSupplierReuestOrderId(resultSet.getString(1));
				order.setSupplierRequestOrderCategory(resultSet.getString(2));
				order.setSupplierRequestOrderModel(resultSet.getString(3));
				order.setSupplierRequestOrderDate(resultSet.getString(4));
				order.setSupplierRequestOrderQuantity(resultSet.getString(5));
				order.setSupplierRequestOrderStatus(resultSet.getString(6));
				
				
				
				orderList.add(order);
			}
			
			System.out.println(query);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get All Supplier Request Orders" + e.getLocalizedMessage());	
			
		} catch (Exception e) {
			System.out.println("Exception In get All Supplier Request Orders : " + e);	
		}finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get All Supplier Request Orders : " + e);
			}
			
			
		}
		
	return orderList;
	
	
		
		
	}








	@Override
	public void deleteSupplierRequestOrder(String order_id) {


		
		try {		
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
				
			String query = CommonConstants.QUERY_DELETE_SUPPLIER_ORDER_REQUEST;
			preparedStatement = connection.prepareStatement(query);
					
			
			
			preparedStatement.setString(1, order_id);
			
			preparedStatement.executeUpdate();
			
			/*for(Model model:model_name) {
				allItems.remove(item);
			}
			*/
			
			
			System.out.println(query);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Order Request Deleted Successfully");
			alert.showAndWait();
			return;
			
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Delete SupplierRequestOrder" + e.getLocalizedMessage());
			
		} catch (Exception e) {
			System.out.println("Exception In Delete SupplierRequestOrder  : " + e );
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Delete SupplierRequestOrder : " + e);
			}		
			
		}



		
	}








	@Override
	public void updateSupplierRequestOrder(SupplierRequestOrder order) {


		try {
			
			connection=DBConnect.getDBConnection();
			
			if(connection==null) {
				System.out.println("Connection not successful");
			}
	
			String query=CommonConstants.QUERY_UPDATE_SUPPLIER_REQUEST_ORDER;
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, order.getSupplierReuestOrderId());
			preparedStatement.setString(2, order.getSupplierRequestOrderCategory());
			preparedStatement.setString(3, order.getSupplierRequestOrderModel());
			preparedStatement.setString(4, order.getSupplierRequestOrderDate());
			preparedStatement.setString(5, order.getSupplierRequestOrderQuantity());
			preparedStatement.setString(6, order.getSupplierRequestOrderStatus());
			preparedStatement.setString(7, order.getSupplierReuestOrderId());
				
			preparedStatement.executeUpdate();
			
			System.out.println(query);
			
			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Order Updated Successfully");
			alert.showAndWait();
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Update Order" + e.getLocalizedMessage());
			
		} catch (Exception e) {
			System.out.println("Exception In Update Order : " + e);	
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Update Order : " + e);
			}		
			
		}
	}








	@Override
	public ArrayList<Item> getAllItemsSearch() {


		ArrayList<Item> itemList = new ArrayList<Item>();
		
		
		try {
			
			connection = DBconnection.getConnection();
			
			preparedStatement =connection.prepareStatement(CommonConstants.QUERY_GET_ALL_ITEMS);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				Item item = new Item();
				
				item.setItemCode(resultSet.getString(1));
				item.setModelName(resultSet.getString(2));
				item.setItemStatus(resultSet.getString(3));
				item.setItemCondition(resultSet.getString(3));
				item.setIdOrderSupplier(resultSet.getString(3));
				
				
				itemList.add(item);
		
			}
			
		}catch(Exception e) {
			System.out.println("Exception in getting Item Deatials  :");
			System.out.println(e);
		}
		return itemList;
		
		
		
	}



	
	
	
	
	
	
	
	
		
	public void generateSORRByMonthAndYear(String loadPath ,int month, int year, String btnInput, String storePath ){
	    try {
	    		
	    	connection = DBconnection.getConnection();
				
				JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
					
				HashMap param = new HashMap();
				
				//get the query
				String query = "";
				
				if( month == 0 && year == 0 ) {
					param.put("info", "Full Supplier Order Request Report for all  the years and months : ");
					query = "SELECT * FROM supplier_order_request";
				}else 
					if( month == 0 && year != 0 ) {
						param.put("info", "Supplier Order Request Report for year : " + year + " for all the months");
						query = "SELECT * FROM supplier_order_request WHERE EXTRACT(YEAR FROM order_date) = "+year;  
					}else
						if( month != 0 && year == 0 ) {
							param.put("info", "Supplier Order Request Report for month  : " + Generator.getMonthStringValue(month) + " for all the years" );
							query = "SELECT * FROM supplier_order_request WHERE EXTRACT(MONTH FROM order_date) = "+month;
						}else
							if( month != 0 && year != 0 ) {
								param.put("info", "Supplier Order Request Report for year : " + year + " and  month  : " +  Generator.getMonthStringValue(month) );
								query = "SELECT * FROM supplier_order_request WHERE EXTRACT(MONTH FROM order_date) = "+month+" AND EXTRACT(YEAR FROM order_date) = "+year;  
							}
				         
				JRDesignQuery jrQuery = new JRDesignQuery();
				jrQuery.setText(query);
				jasperDesign.setQuery(jrQuery);
	
				
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , param , connection );
				JRViewer viewer = new JRViewer( jasperPrint );
				
				viewer.setOpaque(true);
				viewer.setVisible(true);
				
				if( btnInput == "view") {
					JFrame frame = new JFrame("Jasper report");
			        frame.add(viewer);
			        frame.setSize(new Dimension(800, 900));
			        frame.setLocationRelativeTo(null);
			        frame.setVisible(true);
				}else
					if( btnInput == "save" ) {
						JasperExportManager.exportReportToPdfFile(jasperPrint, storePath );
					}
	
	        
		}catch(Exception e) {
			System.out.println("Exception generating Supplier Order Request Report by month and year : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
	}
	
	@Override
	public void generateSORRByDate( String loadPath , String date  , String btnInput, String storePath ) {
		
		System.out.println(date);
		
		try {
			connection = DBconnection.getConnection();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			String query = "";
			
			HashMap param = new HashMap();
			param.put("info", "Supplier Order Request Report For the Date  : " + date);
			query = "SELECT * FROM supplier_order_request WHERE order_date = '" + date+"'";
			
			System.out.println(query);
			//get the query
			         
			JRDesignQuery jrQuery = new JRDesignQuery();
			jrQuery.setText(query);
			jasperDesign.setQuery(jrQuery);
	
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , param , connection );
			JRViewer viewer = new JRViewer( jasperPrint );
			
			viewer.setOpaque(true);
			viewer.setVisible(true);
			
			if( btnInput == "view") {
				JFrame frame = new JFrame("Jasper report");
		        frame.add(viewer);
		        frame.setSize(new Dimension(800, 900));
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);
		}else
			if( btnInput == "save" ) {
				JasperExportManager.exportReportToPdfFile(jasperPrint, storePath );
			}
	
	
		}catch(Exception e) {
			System.out.println("Exception generating Supplier Order Request Report by month and year : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
	}
	
	public void generateFullSORR( String loadPath ,  String btnInput, String storePath) {
		try {
			
			connection = DBconnection.getConnection();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			//get the query
			String query = "SELECT * FROM supplier_order_request";           
			JRDesignQuery jrQuery = new JRDesignQuery();
			jrQuery.setText(query);
			jasperDesign.setQuery(jrQuery);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , null , connection );
			JRViewer viewer = new JRViewer( jasperPrint );
			
			viewer.setOpaque(true);
			viewer.setVisible(true);
			
			if( btnInput == "view") {
				JFrame frame = new JFrame("Jasper report");
		        frame.add(viewer);
		        frame.setSize(new Dimension(800, 900));
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);
			}else
				if( btnInput == "save" ) {
					JasperExportManager.exportReportToPdfFile(jasperPrint, storePath );
				}
	
		}catch(Exception e) {
			System.out.println("Exception in viewing full Supplier Order Request Report : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
		
	}
	/*---------------------------------------------------------------------------------------------------------------------------------*/











	
	
	
	
	
	
	






}
