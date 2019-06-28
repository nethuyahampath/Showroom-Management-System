package model;

import java.sql.Time;
import java.util.Date;

public class Complain {

private String compailn_id ;
private String discription ;
private String compalin_time;
private String compalin_date;	
private String cust_ID;

public Complain(String compailn_id, String discription, String date, String time, String cust_ID) {
	super();
	this.compailn_id = compailn_id;
	this.discription = discription;
	this.compalin_time = date;
	this.compalin_date = time;
	this.cust_ID = cust_ID;
}
public String getCompailn_id() {
	return compailn_id;
}
public void setCompailn_id(String compailn_id) {
	this.compailn_id = compailn_id;
}
public String getDiscription() {
	return discription;
}
public void setDiscription(String discription) {
	this.discription = discription;
}
public String getCompalin_time() {
	return compalin_time;
}
public void setCompalin_time(String compalin_time) {
	this.compalin_time = compalin_time;
}
public String getCompalin_date() {
	return compalin_date;
}
public void setCompalin_date(String compalin_date) {
	this.compalin_date = compalin_date;
}
public String getCust_ID() {
	return cust_ID;
}
public void setCust_ID(String cust_ID) {
	this.cust_ID = cust_ID;
}
}
