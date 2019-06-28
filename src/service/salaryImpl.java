package service;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.salary;
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


public class salaryImpl implements Isalary{
	
	Connection connection;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@Override
	public ObservableList<salary> getAllSalary() {
		ObservableList<salary> salaryList = FXCollections.observableArrayList();
		
		try {
			connection = DBConnect.getDBConnection();
			
			if(connection == null) {
				System.out.println("Connection not successfull");
			}
			
			String query = CommonConstants.QUERY_GET_ALL_SALARY_DETAILS;
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				salary salary = new salary();
				
				salary.setSalaryId(resultSet.getString(1));
				salary.setEmpNo(resultSet.getString(2));
				salary.setName(resultSet.getString(3));
				salary.setDesignation(resultSet.getString(4));
				salary.setBasicSalary(resultSet.getString(5));
				salary.setAllowances(resultSet.getString(6));
				salary.setOtHrs(resultSet.getString(7));
				salary.setOtRate(resultSet.getString(8));
				salary.setDeductions(resultSet.getString(9));
				salary.setTotSalary(resultSet.getString(10));
				salary.setEpf(resultSet.getString(11));
				salary.setDate(resultSet.getString(12));
				salary.setEtf(resultSet.getString(13));
				salary.setOt(resultSet.getString(14));
			
				
				
				salaryList.add(salary);
				
			}
			
			System.out.println(query);
			
			
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error : " + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at get salary details"+e.getLocalizedMessage());
			
		}catch(Exception e) {
			System.out.println("Exception in get all salary details" + e);
			
		}finally {
			try {
				preparedStatement.close();
				resultSet.close();
				connection.close();
			}catch(SQLException e) {
				System.out.println("Finally exception in get all salary details" + e);
			}
		}
		
		return salaryList;
	}

	@Override
	public void addSalary(salary Salary) {
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_ADD_SALARY_DETAILS;
			
			preparedStatement = connection.prepareStatement(query);
				
			preparedStatement.setString(1, Salary.getSalaryId());
			preparedStatement.setString(2, Salary.getEmpNo());
			preparedStatement.setString(3, Salary.getName());
			preparedStatement.setString(4, Salary.getDesignation());
			preparedStatement.setDouble(5, Double.parseDouble(Salary.getBasicSalary()));
			preparedStatement.setDouble(6, Double.parseDouble(Salary.getAllowances()));
			preparedStatement.setInt(7, Integer.parseInt(Salary.getOtHrs()));
			preparedStatement.setDouble(8, Double.parseDouble(Salary.getOtRate()));
			preparedStatement.setDouble(9, Double.parseDouble(Salary.getDeductions()));
			preparedStatement.setDouble(10, Double.parseDouble(Salary.getTotSalary()));
			preparedStatement.setDouble(11, Double.parseDouble(Salary.getEpf()));
			preparedStatement.setString(12,Salary.getDate());
			preparedStatement.setDouble(13, Double.parseDouble(Salary.getEtf()));			
			preparedStatement.setDouble(14, Double.parseDouble(Salary.getOt()));

			preparedStatement.execute();

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Salary Added Successfully");
			alert.showAndWait();
			return;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Add salary" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Add salary : " + e);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Add salary : " + e);
			}

		}
		
		
	}

	@Override
	public void updateSalary(salary Salary) {
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_UPDATE_SALARY;

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, Salary.getSalaryId());
			preparedStatement.setString(2, Salary.getEmpNo());
			preparedStatement.setString(3, Salary.getName());
			preparedStatement.setString(4, Salary.getDesignation());
			preparedStatement.setDouble(5, Double.parseDouble(Salary.getBasicSalary()));
			preparedStatement.setDouble(6, Double.parseDouble(Salary.getAllowances()));
			preparedStatement.setInt(7, Integer.parseInt(Salary.getOtHrs()));
			preparedStatement.setDouble(8, Double.parseDouble(Salary.getOtRate()));
			preparedStatement.setDouble(9, Double.parseDouble(Salary.getDeductions()));
			preparedStatement.setDouble(10, Double.parseDouble(Salary.getTotSalary()));
			preparedStatement.setDouble(11, Double.parseDouble(Salary.getEpf()));
			preparedStatement.setString(12,Salary.getDate());
			preparedStatement.setString(13, Salary.getEtf());
			preparedStatement.setDouble(14, Double.parseDouble(Salary.getOt()));
			preparedStatement.setString(15, Salary.getSalaryId());
			

			

			preparedStatement.executeUpdate();

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Salary Updated Successfully");
			alert.showAndWait();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Update Salary" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Update Salary : " + e);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Update Salary : " + e);
			}

		}

	}

		
	

	@Override
	public void deleteSalary(String salary) {
		try {

			connection = DBConnect.getDBConnection();

			if (connection == null) {
				System.out.println("Connection not successful");
			}

			String query = CommonConstants.QUERY_DELETE_SALARY;
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, salary);

			preparedStatement.executeUpdate();

			/*
			 * for(Model model:model_name) { allItems.remove(item); }
			 */

			System.out.println(query);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Salary Deleted Successfully");
			alert.showAndWait();
			return;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at  Delete Salary" + e.getLocalizedMessage());

		} catch (Exception e) {
			System.out.println("Exception In Delete Salary  : " + e);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Finally Exception In Delete Salary : " + e);
			}

		}
		
	}



	@Override
	public ArrayList<String> getsalaryIDList() {

		ArrayList<String> salList = new ArrayList<String>();
		
		try {
			
			connection = DBConnect.getDBConnection();
			
			preparedStatement = (PreparedStatement) connection.prepareStatement(CommonConstants.QUERY_GET_ALL_SALIDLIST);
			
			resultSet = preparedStatement.executeQuery();
			
			while( resultSet.next() ) {
				salList.add(resultSet.getString(1));
			} //end while
			
			
		}catch(Exception e) {
			System.out.println("Exception in getting item list : ");
			System.out.println(e);
		}
		
		return salList;
	}

	@Override
	public void generateSALReportByMonthAndYear(String loadPath, int month, int year, String btnInput,
			String storePath) {
try {
    		
			connection = DBConnect.getDBConnection();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			//get the query
			String query = "SELECT * FROM salary_nethu WHERE EXTRACT(MONTH FROM date) = "+month+" AND EXTRACT(YEAR FROM date) = "+year;           
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
			System.out.println("Exception in viewing full Full Salary report : " + e );
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Duplicate file names.. Please Rename your file !!!");
			Optional <ButtonType> action= alert.showAndWait();
		}
		
	}
		
	

	@Override
	public void generateFullSALReport(String loadPath, String btnInput, String storePath) {
try {
    		
			connection = DBConnect.getDBConnection();
			
			JasperDesign jasperDesign = JRXmlLoader.load(loadPath);
			
			
			
			//get the query
			String query = "SELECT * FROM salary_nethu";           
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
			System.out.println("Exception in viewing full customer order report : " + e );
			Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Duplicate file names.. Please Rename your file !!!");
			Optional <ButtonType> action= alert.showAndWait();
		}
		
		
	}

	
	
	

	
	

}
