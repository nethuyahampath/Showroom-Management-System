package service;

import model.User;

import java.util.ArrayList;

import model.Delivery;
import model.Emp;

public interface IEmployeeService {

	public String loadname( User userObj);
	
	/*----------------Employee----------*/
	public ArrayList<Emp> getAllEmployeeDetails();
	public void AddNewEmployee(Emp employee);
	public void UpdateEmployee(Emp employee);
	public void delEmployee(String id);
	public ArrayList<String> getEmpIDList();
	
	//Reports 
		public ArrayList<String> getEmpAttendenceList();
		public void generateEAReportByMonthAndYear(String loadPath , int month, int year, String btnInput, String storePath );
		public void generateFullEAReport( String loadPath ,  String btnInput, String storePath);
		public void generateEAReportByDate( String loadPath , String date  , String btnInput, String storePath );
	
}
