package service;

import javafx.collections.ObservableList;
import model.repairingExpenses;

public interface IRepairingExpenses {

	public void addRepairingExpense(repairingExpenses repairing_expense);
	public ObservableList<repairingExpenses>getAllRepairingExpense();
	public void updateRepairingExpense(repairingExpenses repairing_expense);
	public void deleteRepairingExpense(String repairing_expense);
	
	
}
