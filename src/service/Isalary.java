package service;

import model.salary;

import java.util.ArrayList;

import javafx.collections.ObservableList;

public interface Isalary {

	public ObservableList<salary>getAllSalary();
	public void addSalary(salary Salary);
	public void updateSalary(salary Salary);
	public void deleteSalary(String salary);
	
	public ArrayList<String> getsalaryIDList();
	public void generateSALReportByMonthAndYear(String loadPath , int month, int year, String btnInput, String storePath );
	public void generateFullSALReport( String loadPath ,  String btnInput, String storePath);
	
	
}
