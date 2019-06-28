package service;

import javafx.collections.ObservableList;
import model.deliveryExpenses;

public interface IDelivery {

	public void addDeliveryExpenses(deliveryExpenses delivery_expense);
	public ObservableList<deliveryExpenses>getAllDeliveryExpenses();
	public void updateDeliveryExpense(deliveryExpenses delivery_expense);
	public void deleteDeliveryExpense(String DeliveryExpenses);
	
}
