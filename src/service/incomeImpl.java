package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.income;
import util.CommonConstants;
import util.DBConnect;

public class incomeImpl implements Iincome{

	Connection connection;
	PreparedStatement prepareStatement = null;
	ResultSet resultSet = null;

	
	
	@Override
	public ObservableList<income> getAllincomes() {
		ObservableList<income> incomeList = FXCollections.observableArrayList();
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_GET_ALL;
			prepareStatement = connection.prepareStatement(query);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				income in = new income();

				in.setIncome_id(resultSet.getString(1));
				in.setCust_id(resultSet.getString(2));
				in.setName(resultSet.getString(3));
				in.setContact_no(resultSet.getString(4));
				in.setOrder_id(resultSet.getString(5));
				in.setOrder_date(resultSet.getString(4));
				in.setAmount(resultSet.getString(5));
				
				
				incomeList.add(in);
			}

			System.out.println(query);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  get Income List" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In get All  Income List : " + e);
		} finally {
			try {
				prepareStatement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Get All Income List : " + e);
			}

		}

		
	return incomeList;
	}

	@Override
	public void deleteincome(String income) {
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_DELETE_ALL;
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1, income);

			prepareStatement.executeUpdate();

			/*
			 * for(Model model:model_name) { allItems.remove(item); }
			 */

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Income Deleted Successfully");
			alert.showAndWait();
			return;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Delete Incomee" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Delete Income  : " + e);
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


