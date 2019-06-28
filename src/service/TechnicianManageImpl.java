package service;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.AttendanceModel;
import model.RepairItemModel;
import model.TempRepairItemModel;
import util.CommonConstants;
import util.DBConnect;

public class TechnicianManageImpl implements TManageService{
	private static Connection con;
	private static PreparedStatement prepStmt;
	private static ResultSet resultSet;
	
	@Override
	public ArrayList<AttendanceModel> getEmpAttendanceDetails() {
		
		ArrayList<AttendanceModel> attendList = new ArrayList<AttendanceModel>();
		LocalDate date = java.time.LocalDate.now();
		//System.out.println(date);
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_ATTENDANCE_DETAILS);
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				AttendanceModel attendance = new AttendanceModel();
				
				attendance.setDesignation(resultSet.getString(1));
				attendance.setEmpId(resultSet.getString(2));
				
				attendList.add(attendance);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return attendList;
	}

	@Override
	public void addTempRepairItems(TempRepairItemModel tempRepairItem) {
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_ADD_TEMP_REPAIR_ITEMS);
			
			prepStmt.setString(1, tempRepairItem.getItemCode());
			prepStmt.setString(2, tempRepairItem.getEmpID());
			
			prepStmt.execute();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	@Override
	public ArrayList<TempRepairItemModel> getTempRepairItemDetails() {
		
		ArrayList<TempRepairItemModel> tempItemList = new ArrayList<TempRepairItemModel>();
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_TEMP_REPAIR_ITEM_DETAILS);
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				TempRepairItemModel tempRepairItem = new TempRepairItemModel();
				
				tempRepairItem.setItemCode(resultSet.getString(1));
				tempRepairItem.setEmpID(resultSet.getString(2));
				
				tempItemList.add(tempRepairItem);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return tempItemList;
	}

	@Override
	public void deleteTempRepairItems(String itemCode) {
		
		try {
			con = DBConnect.getDBConnection();
			
			prepStmt = con.prepareStatement(CommonConstants.QUERY_DELETE_TEMP_REPAIR_ITEMS);
			
			prepStmt.setString(1, itemCode);
			
			prepStmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	@Override
	public void addRepairItems(RepairItemModel repairItem) {
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_ADD_REPAIR_ITEMS);
			
			
			prepStmt.setString(1, repairItem.getItemCode());
			prepStmt.setString(2, null);
			prepStmt.setString(3, null);
			prepStmt.setString(4, repairItem.getEmpID());
			prepStmt.setString(5, null);
			
			prepStmt.execute();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	@Override
	public ArrayList<RepairItemModel> getRepairItemDetails() {
		
		ArrayList<RepairItemModel> repairItemList = new ArrayList<RepairItemModel>();
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = (PreparedStatement) con.prepareStatement(CommonConstants.QUERY_GET_REPAIR_ITEM_DETAILS);
			resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				RepairItemModel repairItem = new RepairItemModel();
				
				repairItem.setItemCode(resultSet.getString(1));
				repairItem.setEmpID(resultSet.getString(4));
				repairItem.setCost(resultSet.getString(2));
				repairItem.setCustID(resultSet.getString(3));
				repairItem.setPayID(resultSet.getString(5));
				
				repairItemList.add(repairItem);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return repairItemList;
		
	}

	@Override
	public void updateRepairItems(RepairItemModel repairItem) {
		
		try {
			con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(CommonConstants.QUERY_UPDATE_REPAIR_ITEMS);
			
			prepStmt.setString(1, repairItem.getItemCode());
			prepStmt.setString(4, repairItem.getEmpID());
			prepStmt.setString(2, repairItem.getCost());
			prepStmt.setString(3, repairItem.getCustID());
			prepStmt.setString(5, repairItem.getPayID());
			prepStmt.setString(6, repairItem.getItemCode());
			
			prepStmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	@Override
	public String loadICode(RepairItemModel repairItem) {
		String itemCode = "";
    	
    	String query = "select item_code from repair_item where item_code=?";
    	try {
    		con = DBConnect.getDBConnection();
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, repairItem.getItemCode());
			
			resultSet = prepStmt.executeQuery();
			
			
			while(resultSet.next()) {
				itemCode = resultSet.getString(1);
				System.out.println("Emp Name : " + itemCode);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
    	
    	return itemCode;
	
	}

	

}
