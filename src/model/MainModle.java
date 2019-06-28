package model;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

import util.sqlConnection;

public class MainModle {
	Connection connection;
	public MainModle() {
		connection = util.sqlConnection.Connector();
		if(connection == null ) {
			System.out.println("Connection not successful");
			System.exit(1);
		}
	}
	
	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User isLogin1(String user,String pass) throws SQLException{
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		User userObj = new User();
		
		String query = "select * from system_user where username = ?  and password = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				userObj.setUserid(resultSet.getString(1));
				userObj.setUseName(resultSet.getString(2));
				userObj.setType(resultSet.getString(4));
				userObj.setId(resultSet.getString(5));
				
				return userObj;
			}
			else {
				return null;
			}
			
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}finally {
			
			preparedStatement.close();
			resultSet.close();
		}
	}
}
