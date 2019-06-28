package service;

import java.awt.Dimension;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import model.Emp;
import model.User;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import util.sqlConnection;
import util.CommonConstants;
import util.Generator;

//import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class EmployeeServiceImpl implements IEmployeeService{

	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	Connection connection;
	
	@Override
	public String loadname(User userObj) {
    	String empName = "";
    	
    	String query = "select e.first_name from employee e ,system_user s where e.emp_id= s.emp_id and e.emp_id=?";
    	try {
    		connection = util.sqlConnection.Connector();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userObj.getId());
			
			resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next()) {
				empName = resultSet.getString(1);
				System.out.println("Emp Name : " + empName);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
    	
    	return empName;
    	
    }
							
	@Override
	public void AddNewEmployee(Emp employee) {
		// TODO Auto-generated method stub
		
			try {
				connection = sqlConnection.Connector();
		
				preparedStatement = connection.prepareStatement(CommonConstants.QUERY_ADD_EMPLOYEE_DETAILS);
		
				preparedStatement.setString(1, employee.getId());
				preparedStatement.setString(2, employee.getF_name());
				preparedStatement.setString(3, employee.getL_name());
				preparedStatement.setString(4, employee.getNic());
				preparedStatement.setString(5, employee.getAdd());
				preparedStatement.setString(6, employee.getMail());
				preparedStatement.setString(7, employee.getTel());
				
				System.out.println("Telephone :"+employee.getTel());
				preparedStatement.setString(8, employee.getDesignation());
				
				preparedStatement.execute();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
				
	}

	@Override
	public void UpdateEmployee(Emp employee) {
		// TODO Auto-generated method stub
		try {
			connection = sqlConnection.Connector();
			preparedStatement = connection.prepareStatement(CommonConstants.QUERY_UPDATE_EMPLOYEE_DETAILS);
	
			preparedStatement.setString(1, employee.getId());
			preparedStatement.setString(2, employee.getF_name());
			preparedStatement.setString(3, employee.getL_name());
			preparedStatement.setString(4, employee.getNic());
			preparedStatement.setString(5, employee.getAdd());
			preparedStatement.setString(6, employee.getMail());
			preparedStatement.setString(7, employee.getTel());
			preparedStatement.setString(8, employee.getDesignation());
			preparedStatement.setString(9, employee.getId());
			
			preparedStatement.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}

	@Override
	public void delEmployee(String id) {
		// TODO Auto-generated method stub
		try {
    		//Emp emp = table.getSelectionModel().getSelectedItem();
    		//String query = "delete from employee where emp_id = ?";
			connection = sqlConnection.Connector();
			preparedStatement = connection.prepareStatement(CommonConstants.QUERY_DELETE_EMPLOYEE_DETAILS);
		
			preparedStatement.setString(1, id);
			//String emp_id = txtEmpid.getText();
			 
			preparedStatement.executeUpdate();
			preparedStatement.close();

			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}
	
	
	public ArrayList<String> getEmpIDList(){
		
		ArrayList<String> empList = new ArrayList<String>();
		
		try {
			
			connection = sqlConnection.Connector();
			
			preparedStatement = connection.prepareStatement("select emp_id from employee");
			
			resultSet = preparedStatement.executeQuery();
			
			while( resultSet.next() ) {
				empList.add(resultSet.getString(1));
			} //end while
			
			
		}catch(Exception e) {
			System.out.println("Exception in getting item list : ");
			System.out.println(e);
		}
		
		return empList;
	}
	
public ArrayList<String> getEmpAttendenceList(){
		
		ArrayList<String> itemList = new ArrayList<String>();
		
		try {
			
			connection = sqlConnection.Connector();
			
			preparedStatement = (PreparedStatement) connection.prepareStatement(CommonConstants.QUERY_GET_ALL_ITEM_CODES);
			
			resultSet = preparedStatement.executeQuery();
			
			while( resultSet.next() ) {
				itemList.add(resultSet.getString(1));
			} //end while
			
			
		}catch(Exception e) {
			System.out.println("Exception in getting item list : ");
			System.out.println(e);
		}
		
		return itemList;
	}
	
	public void generateEAReportByMonthAndYear(String loadPath ,int month, int year, String btnInput, String storePath ){
	    	try {
	    		
	    		connection = sqlConnection.Connector();
				
				JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
					
				HashMap param = new HashMap();
				
				//get the query
				String query = "";
				
				if( month == 0 && year == 0 ) {
					param.put("info", "Full Customer Order Report for all  the years and months : ");
					query = "SELECT * FROM employee_attendance";
				}else 
					if( month == 0 && year != 0 ) {
						param.put("info", "Customer Order Report for year : " + year + " for all the months");
						query = "SELECT * FROM employee_attendance WHERE EXTRACT(YEAR FROM attendance_date) = "+year;  
					}else
						if( month != 0 && year == 0 ) {
							param.put("info", "Customer Order Report for month  : " + Generator.getMonthStringValue(month) + " for all the years" );
							query = "SELECT * FROM employee_attendance WHERE EXTRACT(MONTH FROM attendance_date) = "+month;
						}else
							if( month != 0 && year != 0 ) {
								param.put("info", "Customer Order Report for year : " + year + " and  month  : " +  Generator.getMonthStringValue(month) );
								query = "SELECT * FROM employee_attendance WHERE EXTRACT(MONTH FROM attendance_date) = "+month+" AND EXTRACT(YEAR FROM attendance_date) = "+year;  
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
			System.out.println("Exception generating employee Attendance report by month and year : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
	}
	
	@Override
	public void generateEAReportByDate( String loadPath , String date  , String btnInput, String storePath ) {
		
		System.out.println(date);
		
		try {
			connection = sqlConnection.Connector();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			String query = "";
			
			HashMap param = new HashMap();
			param.put("info", "Employee Attendance Report For the Date  : " + date);
			query = "SELECT * FROM employee_attendance WHERE attendance_date = '" + date+"'";
			
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
			System.out.println("Exception generating Employee Attendance report by month and year : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
	}
	
	public void generateFullEAReport( String loadPath ,  String btnInput, String storePath) {
		try {
    		
			connection = sqlConnection.Connector();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			//get the query
			String query = "SELECT * FROM employee_attendance";           
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
			System.out.println("Exception in viewing full Employee Attendance report : " + e );
			Generator.getAlert("Duplicate file names", "There is a pdf with the name you entered, please change your file name");
		}
		
	}
	/*---------------------------------------------------------------------------------------------------------------------------------*/

	@Override
	public ArrayList<Emp> getAllEmployeeDetails() {
		// TODO Auto-generated method stub
		return null;
	}


}
