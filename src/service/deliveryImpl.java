package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.deliveryExpenses;
import util.CommonConstants;
import util.DBConnect;

public class deliveryImpl implements IDelivery{

	Connection connection;
	PreparedStatement prepareStatement = null;
	ResultSet resultSet = null;
	
	@Override
	public void addDeliveryExpenses(deliveryExpenses delivery_expense) {
		try {
			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_ADD_DELIVERY_EXPENSES;
			
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1, delivery_expense.getDeliveryExpenseID());
			prepareStatement.setString(2, delivery_expense.getDeliveryId());
			prepareStatement.setString(3, delivery_expense.getDate());
			prepareStatement.setDouble(4, Double.parseDouble(delivery_expense.getAmount()));
			prepareStatement.setString(5, delivery_expense.getDescription());
			
			prepareStatement.execute();

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Delivery Expense Added Successfully");
			alert.showAndWait();
			return;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  add Delivery Expense" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Add Delivery Expense : " + e);
		} finally {
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Add Delivery Expense : " + e);
			}

		}
		
	}

	@Override
	public ObservableList<deliveryExpenses> getAllDeliveryExpenses() {
		ObservableList<deliveryExpenses> delivery_List = FXCollections.observableArrayList();
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_SELECT_DELIVERY_EXPENSES;
			prepareStatement = connection.prepareStatement(query);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				deliveryExpenses delivery = new deliveryExpenses();

				delivery.setDeliveryExpenseID(resultSet.getString(1));
				delivery.setDeliveryId(resultSet.getString(2));
				delivery.setDate(resultSet.getString(3));
				delivery.setAmount(resultSet.getString(4));
				delivery.setDescription(resultSet.getString(5));
				
				delivery_List.add(delivery);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get Delivery Expenses" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All  Delivery Expenses : " + e);
		} finally {
			try {
				prepareStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get All  Delivery Expenses : " + e);
			}

		}

		
	return delivery_List;
	}

	@Override
	public void updateDeliveryExpense(deliveryExpenses delivery_expense) {
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_UPDATE_DELIVERY_EXPENSES;

			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1,delivery_expense.getDeliveryExpenseID());
			prepareStatement.setString(2, delivery_expense.getDeliveryId());
			prepareStatement.setString(3, delivery_expense.getDate());
			prepareStatement.setDouble(4, Double.parseDouble(delivery_expense.getAmount()));
			prepareStatement.setString(5, delivery_expense.getDescription());
			prepareStatement.setString(6,delivery_expense.getDeliveryExpenseID());

			prepareStatement.executeUpdate();

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Delivery Expense  Updated Successfully");
			alert.showAndWait();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Update Delivery Expense" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Update Delivery Expense : " + e);
		} finally {
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Update Delivery Expense : " + e);
			}

		}

		
	}

	@Override
	public void deleteDeliveryExpense(String DeliveryExpenses) {

		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_DELETE_DELIVERY_EXPENSES;
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1, DeliveryExpenses);

			prepareStatement.executeUpdate();

			/*
			 * for(Model model:model_name) { allItems.remove(item); }
			 */

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Delivery Expense Deleted Successfully");
			alert.showAndWait();
			return;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Delete Delivery Expense" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Delete Delivery Expense  : " + e);
		} finally {
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Delete Delivery Expense : " + e);
			}

		}
	}



}
