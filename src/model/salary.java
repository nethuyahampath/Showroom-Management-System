package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class salary {

	private String salaryId;
	private String EmpNo;
	private String name;
	private String designation;
	private String basicSalary;
	private String date;
	private String allowances;
	private String otHrs;
	private String otRate;
	private String etf;
	private String deductions;
	private String epf;
	private String totSalary;
	private String ot;
	
	
	public String getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(String salaryId) {
		this.salaryId = salaryId;
	}
	
	public String getEmpNo() {
		return EmpNo;
	}
	
	public void setEmpNo(String empNo) {
		EmpNo = empNo;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(String basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAllowances() {
		return allowances;
	}

	public void setAllowances(String allowances) {
		this.allowances = allowances;
	}

	public String getOtHrs() {
		return otHrs;
	}

	public void setOtHrs(String otHrs) {
		this.otHrs = otHrs;
	}

	public String getOtRate() {
		return otRate;
	}

	public void setOtRate(String otRate) {
		this.otRate = otRate;
	}

	public String getEtf() {
		return etf;
	}
	
	public void setEtf(String etf) {
		this.etf = etf;
	}

	public String getDeductions() {
		return deductions;
	}
	
	public void setDeductions(String deductions) {
		this.deductions = deductions;
	}

	public String getEpf() {
		return epf;
	}

	public void setEpf(String epf) {
		this.epf = epf;
	}

	public String getTotSalary() {
		return totSalary;
	}

	public void setTotSalary(String totSalary) {
		this.totSalary = totSalary;
	}

	public String getOt() {
		return ot;
	}

	public void setOt(String ot) {
		this.ot = ot;
	}

	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date txtDate = new Date();
		String newDate = dateFormat.format(txtDate);
		
		return newDate;
	}
	
	
	
}
