package service;

import javafx.collections.ObservableList;

import model.income;

public interface Iincome {

	public ObservableList<income>getAllincomes();
	public void deleteincome(String income);
	
	
}
