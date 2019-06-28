package service;

import javafx.collections.ObservableList;
import model.repairExpenses;


public interface Irepairing {

	public void addRepairingExpense(repairExpenses repairing_expense);
	public ObservableList<repairExpenses>getAllRepairingExpense();
	public void updateRepairingExpense(repairExpenses repairing_expense);
	public void deleteRepairingExpense(String repairing_expense);
	
	
}
