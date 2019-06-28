package service;

import javafx.collections.ObservableList;
import model.showroomExpenses;

public interface IshowroomExpenses {


	public void addShowrromExpenses(showroomExpenses showroom_expenses);
	public ObservableList<showroomExpenses>getAllShowroomExpenses();
	public void updateShowroomExpenses(showroomExpenses showroom_expenses);
	public void deleteShowroomExpenses(String showroom_expenses);
	
}
