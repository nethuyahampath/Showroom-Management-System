package service;

import java.util.ArrayList;

import model.AttendanceModel;
import model.RepairItemModel;
import model.TempRepairItemModel;
import model.User;
import model.repairOrderFormModel;

public interface TManageService {
	
	public ArrayList<AttendanceModel> getEmpAttendanceDetails();
	
	public void addTempRepairItems(TempRepairItemModel tempRepairItem);
	public ArrayList<TempRepairItemModel> getTempRepairItemDetails();
	public void deleteTempRepairItems(String itemCode);
	
	public void addRepairItems(RepairItemModel repairItem);
	public ArrayList<RepairItemModel> getRepairItemDetails();
	public void updateRepairItems(RepairItemModel repairItem);
	
	public String loadICode(RepairItemModel repairItem);

}
