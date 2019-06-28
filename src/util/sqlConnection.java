package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class sqlConnection {
	public static Connection Connector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crowndb?useSSL=false","root","4123");
			return conn;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
	}
}
