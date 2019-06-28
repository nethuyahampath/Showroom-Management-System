package controller;

import model.Main;
import model.MainModle;
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
import util.DBconnection;

import java.awt.Dimension;
//import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import model.MainModle;


public class MainController{
	public model.MainModle mainModle = new MainModle();

	@FXML
	private Label isConnected;
	 @FXML
	 private TextField txtusername;

	 @FXML
	 private PasswordField txtpassword;
	    

	public void ExitWindow(ActionEvent event) throws Exception{
		Platform.exit();
	}
	
	public void MinimizeWindow(ActionEvent event) throws Exception{
		
		//get the stage from Main class
		Main.getStageObj().setIconified(true);
	}
	
	
	
	public void LogInSuccess( ActionEvent event ){
		
			try {
				
				User userObj = mainModle.isLogin1(txtusername.getText(),txtpassword.getText());
				
				if(  userObj != null ) {
					isConnected.setText("Username and Password is correct");
					
					System.out.println(userObj.getUseName());
					
			    	/*try {
			    		
			    		Connection connection = DBconnection.getConnection();
			    		
			    		//JasperReport jasperReport = JasperCompileManager.compileReport("E:\\SLIIT\\Java\\JasperWithFX\\Blank_A4.jrxml");
			    		
			    		JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\Ganesh\\Desktop\\SMS\\Showroom_Management_System\\src\\report\\Model_Report.jrxml");
			    		
			    		//get the query
			    		String query = "SELECT * FROM model";
			    		JRDesignQuery jrQuery = new JRDesignQuery();
			    		jrQuery.setText(query);
			    		jasperDesign.setQuery(jrQuery);
			    		
			    		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			    		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , null , connection );
			    		JRViewer viewer = new JRViewer( jasperPrint );
			    		
			    		//JRDataSource jrDataSource = new JREmptyDataSource();
			    		
			    		//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
			    		
			    		JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\Ganesh\\Desktop\\Reports\\test.pdf" );
			    		
			    	
			    		//to view the jasper report in the pdf viewer -  (can be used for a button to view the report)
			    		viewer.setOpaque(true);
			    		viewer.setVisible(true);
			    		
			    		JFrame frame = new JFrame("Jasper report");
			            frame.add(viewer);
			            frame.setSize(new Dimension(500, 400));
			            frame.setLocationRelativeTo(null);
			            frame.setVisible(true);
			    		
			    		
			    	}catch(Exception e) {
			    		System.out.println("Exception : " + e );
			    	}*/
	
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/Home.fxml"));
					Parent parent = loader.load();
					
					Scene scene =  new Scene(parent);
					scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
					
					HomeController controller = loader.getController();
					controller.loadUser(userObj);
					
					
					Stage window  = (Stage) ((Node)event.getSource()).getScene().getWindow();
					
					window.setScene(scene);
					window.show();
					window.centerOnScreen();
					
			}else {
				isConnected.setText("Username and Password is not correct");
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				isConnected.setText("Username and Password is not correct");
				
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}

