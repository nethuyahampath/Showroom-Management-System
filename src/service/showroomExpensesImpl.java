package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.showroomExpenses;
import util.CommonConstants;
import util.DBConnect;

public class showroomExpensesImpl implements IshowroomExpenses{

	Connection connection;
	PreparedStatement prepareStatement = null;
	ResultSet resultSet = null;
	
	@Override
	public void addShowrromExpenses(showroomExpenses showroom_expenses) {
		try {
			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_ADD_SHOWROOM_EXPENSES;
			
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1, showroom_expenses.getShowroomExpenseId());
			prepareStatement.setString(2, showroom_expenses.getUtility());
			prepareStatement.setString(3, showroom_expenses.getDate());
			prepareStatement.setDouble(4, Double.parseDouble(showroom_expenses.getAmount()));
			
			prepareStatement.execute();

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Showroom Expense Added Successfully");
			alert.showAndWait();
			return;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  add Showroom Expense" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Add Showroom Expense : " + e);
		} finally {
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Add Showroom Expense : " + e);
			}

		}
		
		
	}

	@Override
	public ObservableList<showroomExpenses> getAllShowroomExpenses() {
		ObservableList<showroomExpenses> showroomList = FXCollections.observableArrayList();
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_SELECT_SHOWROOM_EXPENSES;
			prepareStatement = connection.prepareStatement(query);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				showroomExpenses showroom = new showroomExpenses();

				showroom.setShowroomExpenseId(resultSet.getString(1));
				showroom.setUtility(resultSet.getString(2));
				showroom.setDate(resultSet.getString(3));
				showroom.setAmount(resultSet.getString(4));
				
				showroomList.add(showroom);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get Showroom Expenses" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All  Showroom Expenses : " + e);
		} finally {
			try {
				prepareStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get All  Showroom Expenses : " + e);
			}

		}

		
	return showroomList;
	}

	@Override
	public void updateShowroomExpenses(showroomExpenses showroom_expenses) {
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_UPDATE_SHOWROOM_EXPENSES;

			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1,showroom_expenses.getShowroomExpenseId());
			prepareStatement.setString(2, showroom_expenses.getUtility());
			prepareStatement.setString(3, showroom_expenses.getDate());
			prepareStatement.setDouble(4, Double.parseDouble(showroom_expenses.getAmount()));	
			prepareStatement.setString(5,showroom_expenses.getShowroomExpenseId());

			prepareStatement.executeUpdate();

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("showroom Expense  Updated Successfully");
			alert.showAndWait();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Update showroom Expense" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Update showroom Expense : " + e);
		} finally {
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Update showroom Expense : " + e);
			}

		}
		
	}

	@Override
	public void deleteShowroomExpenses(String showroom_expenses) {
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_DELETE_SHOWROOM_EXPENSES;
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1, showroom_expenses);

			prepareStatement.executeUpdate();

			/*
			 * for(Model model:model_name) { allItems.remove(item); }
			 */

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("showrom Expense Deleted Successfully");
			alert.showAndWait();
			return;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Delete showrom Expense" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Delete showrom Expense  : " + e);
		} finally {
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Delete showrom Expense : " + e);
			}

		}
}
		
	}


