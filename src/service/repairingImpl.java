package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.repairExpenses;
import util.CommonConstants;
import util.DBConnect;

public class repairingImpl implements Irepairing{
	
	Connection connection;
	PreparedStatement prepareStatement = null;
	ResultSet resultSet = null;

	@Override
	public void addRepairingExpense(repairExpenses repairing_expense) {
		try {
			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_ADD_REPAIRING_EXPENSES;
			
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1, repairing_expense.getRepairExpenseId());
			prepareStatement.setString(2, repairing_expense.getRepairId());
			prepareStatement.setString(3, repairing_expense.getDate());
			prepareStatement.setDouble(4, Double.parseDouble(repairing_expense.getAmount()));
			prepareStatement.setString(5, repairing_expense.getDescription());
			
			prepareStatement.execute();

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Repair Expense Added Successfully");
			alert.showAndWait();
			return;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  add Repair Expense" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Add Repair Expense : " + e);
		} finally {
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Add Repair Expense : " + e);
			}

		}
		
		
	}

	@Override
	public ObservableList<repairExpenses> getAllRepairingExpense() {
		ObservableList<repairExpenses> repairList = FXCollections.observableArrayList();
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_SELECT_REPAIRING_EXPENSES;
			prepareStatement = connection.prepareStatement(query);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				repairExpenses repair = new repairExpenses();

				repair.setRepairExpenseId(resultSet.getString(1));
				repair.setRepairId(resultSet.getString(2));
				repair.setDate(resultSet.getString(3));
				repair.setAmount(resultSet.getString(4));
				repair.setDescription(resultSet.getString(5));
				
				repairList.add(repair);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get Repair Expenses" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All  Repair Expenses : " + e);
		} finally {
			try {
				prepareStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get All  Repair Expenses : " + e);
			}

		}

		
	return repairList;
	}

	@Override
	public void updateRepairingExpense(repairExpenses repairing_expense) {
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_UPDATE_REPAIRING_EXPENSES;

			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1,repairing_expense.getRepairExpenseId());
			prepareStatement.setString(2, repairing_expense.getRepairId());
			prepareStatement.setString(3, repairing_expense.getDate());
			prepareStatement.setDouble(4, Double.parseDouble(repairing_expense.getAmount()));	
			prepareStatement.setString(5, repairing_expense.getDescription());
			prepareStatement.setString(6, repairing_expense.getRepairExpenseId());

			prepareStatement.executeUpdate();

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Repair Expense  Updated Successfully");
			alert.showAndWait();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Update Repair Expense" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Update Repair Expense : " + e);
		} finally {
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Update Repair Expense : " + e);
			}

		}

		
	}

	@Override
	public void deleteRepairingExpense(String repairing_expense) {
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_DELETE_REPAIRING_EXPENSES;
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1, repairing_expense);

			prepareStatement.executeUpdate();

			/*
			 * for(Model model:model_name) { allItems.remove(item); }
			 */

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Repair Expense Deleted Successfully");
			alert.showAndWait();
			return;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Delete Repair Expense" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Delete Repair Expense  : " + e);
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
