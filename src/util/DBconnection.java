package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {
	public static   Connection getConnection()
	 {
	 Connection conne = null;
	 String url = "jdbc:mysql://localhost:3306/crowndb?useSSL=false"; 
	 String username = "root"; 
	 String password = "4123"; 
	 
	 try 
	 {
	 try 
	 {
	 Class.forName("com.mysql.jdbc.Driver");  
	 } 
	 catch (ClassNotFoundException e)
	 {
	 e.printStackTrace();
	 }
	 
	 conne = DriverManager.getConnection(url, username, password); //attempting to connect to MySQL database
	 System.out.println("print connection  "+conne);
	 } 
	 catch (Exception e) 
	 {
	 e.printStackTrace();
	 }
	 
	 return conne; 
	 }


}
