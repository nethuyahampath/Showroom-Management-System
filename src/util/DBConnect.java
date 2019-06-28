package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnect {

	private static Connection connection;

	public static Connection getDBConnection() throws SQLException {

		try {
			Class.forName(CommonConstants.DRIVER_NAME);
			connection = (Connection) DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

		} catch (Exception e) {
			System.out.println("Exception  : " + e);
		}

		return connection;
	}
}
