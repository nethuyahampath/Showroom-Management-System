package util;

public class CommonConstants {

	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver"; //Driver name
	public static final String DB_URL = "jdbc:mysql://localhost:3306/crowndb?useSSL=false"; //DB Url
	public static final String DB_USERNAME =  "root"; //DB username
	public static final String DB_PASSWORD = "4123"; //DB password
	
	/*---------------------------------------- Notification------------------------------------------*/
	
	public static final String SENDER_EMAIL = "crown2018itp@gmail.com";
	public static final String SENDER_PASSWORD = "Crown123@";
	
	
	/*---------------------------------------------Queries-------------------------------------------*/
	public static final String QUERY_ALL_CATEGORY_NAMES = "SELECT * FROM category";
	public static final String QUERY_ALL_SUPPLIERS = "SELECT supplier_id FROM supplier WHERE category=?";
	public static final String QUERY_ADD_MODEL="INSERT INTO "
			+ "model(model_name,name_category,id_supplier,unit_price,selling_price,quantity,warranty_years) "
			+ "VALUES (?,?,?,?,?,?,?)";
	
	public static final String QUERY_GET_ALL_MODELS="SELECT * FROM model";
	public static final String QUERY_DELETE_MODEL="DELETE FROM model WHERE model_name = ?";
	public static final String QUERY_UPDATE_MODEL="UPDATE model "
			+ "SET name_category = ?, id_supplier = ?, unit_price = ?, selling_price = ?, quantity = ? , warranty_years= ? "
			+ "WHERE model_name = ?";
	
	public static final String QUERY_ADD_CATEGORY="INSERT INTO category(category_name)"
			+ "VALUES(?)";
	
	public static final String QUERY_DELETE_CATEGORY="DELETE FROM category WHERE category_name = ?";
	
	public static final String QUERY_ADD_ITEM="INSERT INTO "
			+ "item(item_code,model_name,item_condition,item_sold,id_order_supplier) "
			+ "VALUES (?,?,?,?,?)";
	
	public static final String QUERY_GET_ALL_ITEMS="SELECT * FROM item";
	
	public static final String QUERY_UPDATE_ITEM="UPDATE item "
			+ "SET model_name = ?, item_condition = ?, item_sold = ?, id_order_supplier = ? "
			+ "WHERE item_code = ?";
	
	public static final String QUERY_DELETE_ITEM="DELETE FROM item WHERE item_code=?";
	
	public static final String QUERY_ALL_MODELS="SELECT model_name FROM model";
	
	public static final String QUERY_ALL_ORDERS="SELECT order_id FROM supplier_order";
	
	public static final String QUERY_ADD_SUPPLIER_ORDER_REQUEST="INSERT INTO "
			+ "supplier_order_request(sup_order_req_id,name_category,model_name,order_date,quantity,order_status)"
			+ "VALUES(?,?,?,?,?,?)";
	
	
	public static final String QUERY_GET_ALL_SUPPLIER_REQUEST_ORDERS="SELECT * FROM supplier_order_request";
	
	public static final String QUERY_DELETE_SUPPLIER_ORDER_REQUEST="DELETE FROM supplier_order_request WHERE sup_order_req_id=?";
	
	public static final String QUERY_UPDATE_SUPPLIER_REQUEST_ORDER="UPDATE supplier_order_request SET sup_order_req_id = ?, name_category = ?, model_name = ?,order_date=?, quantity = ?, order_status = ? WHERE sup_order_req_id = ?;";

	public static final String QUERY_ADD_BRAND_NEW_ITEM="INSERT INTO "
			+ "brand_new_item(item_code)"
			+ " VALUES(?)";
	
	
	public static final String QUERY_MAX_SUPPLIER_ORDER_REQUEST_ID="SELECT MAX(sup_order_req_id) FROM supplier_order_request";
	
	
	public static final String QUERY_MAX_ITEM_CODE="SELECT MAX(item_code) FROM item";

	/*--------------------------------Customer Order Management -------------------------------------------*/
	public static final String QUERY_ALL_CART_DETAILS = "SELECT * FROM cart"; 
	public static final String QUERY_ALL_CART_DETAILS_BY_CART_ID = "SELECT * FROM cart WHERE cart_id = ?"; 
	public static final String QUERY_ALL_BRAND_NEW_ITEM_DETAILS = "SELECT * FROM cart WHERE cart_id = ?";
	public static final String QUERY_ALL_ITEMS_BY_BRAND_NEW_ITEM_ID = "SELECT i.item_code, i.model_name, i.item_condition, i.item_sold, i.id_order_supplier FROM item i , brand_new_item b WHERE b.item_code = i.item_code AND i.item_sold = ?";
	public static final String QUERY_BRAND_NEW_ITEM_SEARCH = "";
	public static final String QUERY_MODEL_BY_ITEM_CODE = "SELECT model_name FROM item WHERE item_code = ?";
	public static final String QUERY_PRICE_BY_MODEL_NAME= "SELECT selling_price FROM model WHERE model_name = ?";
	public static final String QUERY_MODEL_COUNT_IN_CART = "SELECT COUNT(c.cart_id) FROM cart c, item i WHERE i.model_name = ? AND c.item_code =  i.item_code AND c.cart_id = ?";
	public static final String QUERY_CUSTOMER_ID_BY_NIC = "SELECT cust_id FROM customer WHERE NIC = ?";
	public static final String QUERY_ITEM_CODE_BY_ITEM_CODE = "SELECT i.item_code FROM item i , brand_new_item b WHERE b.item_code = i.item_code AND b.item_code = ?";
	public static final String QUERY_ADD_ITEM_TO_CART = "INSERT INTO cart VALUES(?, ?, ?, ?, ? )";
	public static final String QUERY_ITEM_DETAILS_FOR_SALES = "SELECT i.item_code, i.model_name, i.item_condition, i.item_sold FROM item i , brand_new_item b WHERE b.item_code = i.item_code";
	public static final String QUERY_MODEL_DETAILS_FOR_SALES = "SELECT DISTINCT m.model_name, m.name_category, m.unit_price, m.selling_price,m.quantity, m.warranty_years FROM item i , brand_new_item b , model m WHERE b.item_code = i.item_code AND i.model_name = m.model_name";
	
	public static final String QUERY_MAX_USER_ID = "SELECT MAX(cart_id) FROM cart"; 
	public static final String QUERY_QUANTITY_BY_MODEL_NAME = "SELECT DISTINCT m.quantity FROM item i , brand_new_item b , model m WHERE b.item_code =  i.item_code AND m.model_name = ? ";
	
	public static final String QUERY_REMOVE_CART_DETAILS = "DELETE FROM cart WHERE cart_id = ?";
	public static final String QUERY_REMOVE_ITEM_FROM_CART =  "DELETE FROM cart WHERE item_code = ? AND cart_id = ?"; 	

	
	public static final String QUERY_UPDATE_ITEM_STATUS = "UPDATE item SET item_sold = ? WHERE item_code = ?";
	public static final String QUERY_UPDATE_MODEL_QUANTITY = "UPDATE model SET quantity = ? WHERE model_name = ?";
	
	public static final String QUERY_SOLD_ITEMS_DETAILS = "SELECT item_sold FROM item WHERE  item_code = ?";
	public static final String QUERY_CART_IN_ORDER = "SELECT order_id FROM customer_order WHERE cart_id = ?";

	//
	public static final String QUERY_ADD_CUSTOMER_ORDER_DETAILS = "INSERT INTO customer_order VALUES(?,?,?,?,?,?)";
	public static final String QUERY_GET_ALL_CUSTOMER_ORDER = "SELECT * FROM customer_order";
	public static final String QUERY_MAX_CUSTOMER_ORDER_ID = "SELECT MAX(order_id) FROM customer_order"; 
	public static final String QUERY_ADD_CUSTOMER_PAYMENT_DETAILS = "INSERT INTO customer_payment VALUES(?,?,?,?,?,?)";
	public static final String QUERY_GET_CUSTOMER_PAYMENT_DETAILS = "SELECT * FROM customer_payment";
	public static final String QUERY_MAX_CUSTOMER_PAYMENT_ID = "SELECT MAX(payment_id) FROM customer_payment"; 
	public static final String QUERY_CUSTOMER_ID_BY_CART_ID = "SELECT cust_id FROM cart WHERE cart_id = ?";
	public static final String QUERY_CUSTOMER_SINGLE_CART = "SELECT price,item_code FROM cart WHERE cart_id = ?";
	
	public static final String QUERY_CUSTOMER_EMAIL_BY_ID = "SELECT emai FROM customer WHERE cust_id = ?";
	public static final String QUERY_CUSTOMER_FNAME_BY_ID = "SELECT first_name FROM customer WHERE cust_id = ?";
	public static final String QUERY_CUSTOMER_LNAME_BY_ID = "SELECT last_name FROM customer WHERE cust_id = ?";
	
	/*--------------------------------Report generation queries---------------------------------*/
	public static final String QUERY_GET_ALL_ITEM_CODES = "SELECT item_code FROM item";
	

	/*---------------Delivery Management-----------*/
	
	public static final String QUERY_ADD_DELIVERY_DETAILS = "INSERT INTO delivery VALUES(?,?,?,?,?,?,?)";
	public static final String QUERY_GET_DELIVERY_DETAILS = "SELECT * FROM delivery";
	public static final String QUERY_REMOVE_DELIVERY_DETAILS = "DELETE FROM delivery WHERE delivery_id = ?";
	public static final String QUERY_UPDATE_DELIVERY_DETAILS = "UPDATE delivery SET no = ? , street = ? , city = ? , delivery_date = ? , vehicle_id = ? WHERE delivery_id = ? ";
	public static final String QUERY_MAX_DELIVERY_ID = "SELECT MAX(delivery_id) FROM delivery";

	/*-------------Delivery Vehicle Management--------*/
	
	
	public static final String QUERY_ADD_DELIVERY_VEHICLE_DETAILS = "INSERT INTO vehicle VALUES(?,?,?)";
	public static final String QUERY_GET_DELIVERY_VEHICLE_DETAILS = "SELECT * FROM vehicle";
	public static final String QUERY_REMOVE_DELIVERY_VEHICLE_DETAILS = "DELETE * FROM vehicle WHERE vehicle_id = ?";
	public static final String QUERY_UPDATE_DELIVERY_VEHICLE_DETAILS = "UPDATE vehicle SET vehicle_type = ? , vehicle_status = ? WHERE vehicle_id = ?";
	public static final String QUERY_MAX_VEHICLE_ID = "SELECT MAX(vehicle_id) FROM vehicle";
	
	
	/*-----------------Driver Manage-------------------------------------------------*/
	
	public static final String QUERY_GET_DRIVER_ATTENDANCE_DETAILS = "SELECT e.designation, a.emp_id FROM employee e, employee_attendance a WHERE e.designation = 'Driver' AND a.attendance_date = CURDATE() AND e.emp_id  = a.emp_id ";
	public static final String QUERY_ADD_Delivery_Driver_Details = "INSERT INTO delivery_employee(delivery_id , emp_id ) VALUES(?,?)";
	public static final String QUERY_GET_DRIVER_DETAILS = "SELECT * FROM delivery_employee";
	public static final String QUERY_DELETE_DELIVERY_DETAILS = "DELETE FROM delivery_employee WHERE delivery_id = ?";
	public static final String QUERY_UPDATE_DELIVERY_MANAGE_DETAILS = "UPDATE delivery_employee SET delivery_id = ?,emp_id = ? WHERE delivery_id = ?";
	
	/*--------------------------------Report generation queries---------------------------------*/
	public static final String QUERY_GET_ALL_DELIVERY_CODES = "SELECT delivery_id FROM delivery";


	/*----------------- Employee-----------------------------*/
	
	public static final String QUERY_ADD_EMPLOYEE_DETAILS = "insert into employee (emp_id,first_name,last_name,NIC,address,email,contact_no,designation) values(?,?,?,?,?,?,?,?)";
	public static final String QUERY_UPDATE_EMPLOYEE_DETAILS = "update employee set emp_id = ? ,first_name = ? ,last_name = ?,NIC = ? , address = ? ,email = ? ,contact_no=? ,designation = ? where emp_id = ?";
	public static final String QUERY_DELETE_EMPLOYEE_DETAILS = "delete from employee where emp_id = ?";
	public static final String QUERY_MAX_EMPLOYEE_ID = "SELECT MAX(emp_id) FROM employee"; 
	
	/*--------------------------------Report generation queries---------------------------------*/
	public static final String QUERY_GET_ALL_ATTENDANCE_LIST = "SELECT attendance_date FROM employee_attendance";
	

	/*----------------------------------------Supplier Management----------------------------------*/
	
	public static final String QUERY_ADD_SUPPLIER_DETAILS = "INSERT INTO supplier (supplier_id,supplier_name,telephone,email,address,country,category) VALUES(?,?,?,?,?,?,?)";
	public static final String QUERY_GET_ALL_SUPPLIER_DETAILS = "select * from supplier";
	public static final String QUERY_UPDATE_SUPPLIER_DETAILS = "update supplier set supplier_id = ? , supplier_name = ? , telephone = ? , email = ? , address = ? , country = ? ,category = ? where supplier_id = ?";
	public static final String QUERY_DELETE_SUPPLIER_DETAILS = "delete from supplier where supplier_id = ?";
	public static final String QUERY_ADD_SUPPLIER_ORDER = "INSERT INTO supplier_order (order_id,supplier_id,order_date,order_time,total) VALUES(?,?,?,?,?)";
	public static final String QUERY_GET_ALL_SUPPLIER_ORDER = "select * from supplier_order";
	public static final String QUERY_UPDATE_SUPPLIER_ORDER = "update supplier_order set order_id = ? , supplier_id = ? , order_date = ? , order_time = ? , total = ? where order_id = ?";
	public static final String QUERY_DELETE_SUPPLIER_ORDER = "delete from supplier_order where order_id = ?";
	public static final String QUERY_ADD_SUPPLIER_PAYMENT = "INSERT INTO supplier_payment (payment_id, order_id, type, quantity, payment_date, payment_time, amount) VALUES(?,?,?,?,?,?,?)";
	public static final String QUERY_GET_ALL_SUPPLIER_PAYMENT = "select * from supplier_payment";
	public static final String QUERY_UPDATE_SUPPLIER_PAYMENT = "update supplier_payment set payment_id = ? , order_id = ? , type = ? ,quantity = ?,payment_date = ?, payment_time = ? , amount = ? where payment_id = ?";
	public static final String QUERY_DELETE_SUPPLIER_PAYMENT = "delete from supplier_payment where payment_id = ?";
	public static final String QUERY_MAX_SUPPLIER_ID = "SELECT MAX(supplier_id) FROM supplier";
	public static final String QUERY_MAX_SUPPLIER_ORDER_ID = "SELECT MAX(order_id) FROM supplier_order";
	public static final String QUERY_MAX_SUPPLIER_PAYMENT_ID = "SELECT MAX(payment_id) FROM supplier_payment";
	
	/*---------------------------------------Supplier report generation query-----------------------*/
	
	public static final String QUERY_GET_ALL_SUPPLIER_CODES = "SELECT * FROM supplier_order";
	
		
	
	/*---------------------------------- Item Repair ----------------------------------------------------------------*/
	
	//String date = Generator.getCurrentDate();
	public static final String QUERY_GET_ALL_REPAIR_DETAILS = "SELECT * FROM repair_detail";
	public static final String QUERY_ADD_REPAIR_DETAILS = "INSERT INTO repair_detail(repair_detail_id,order_id, item_code, description, repair_date, repair_stat) values(?,?,?,?,?,?)";
	public static final String QUERY_UPDATE_REPAIR_DETAILS = "update repair_detail set order_id=?, item_code=?, description=?, repair_date=?, repair_stat=? where order_id=?";
	public static final String QUERY_DELETE_REPAIR_DETAILS = "delete from repair_detail where order_id = ?";
	public static final String QUERY_GET_ALL_MODEL_DETAILS = "SELECT * FROM model";
	public static final String QUERY_GET_ORDER_DATE = "SELECT order_date FROM customer_order WHERE oder_id = ?";
	public static final String QUERY_GET_ATTENDANCE_DETAILS = "SELECT e.designation, a.emp_id FROM employee_attendance a, employee e WHERE e.designation = 'Stock Manager' and a.attendance_date = CURDATE() and e.emp_id = a.emp_id"; 
	public static final String QUERY_ADD_TEMP_REPAIR_ITEMS = "INSERT INTO temp_item_repair_table(item_code, emp_id) VALUES(?,?)";
	public static final String QUERY_GET_TEMP_REPAIR_ITEM_DETAILS = "SELECT * FROM temp_item_repair_table";
	public static final String QUERY_DELETE_TEMP_REPAIR_ITEMS = "delete from temp_item_repair_table where item_code = ?";
	public static final String QUERY_ADD_REPAIR_ITEMS = "INSERT INTO repair_item(item_code, cost, cust_id, emp_id, payment_id) VALUES(?,?,?,?,?)";
	public static final String QUERY_GET_REPAIR_ITEM_DETAILS = "SELECT * FROM repair_item";
	public static final String QUERY_UPDATE_REPAIR_ITEMS = "UPDATE repair_item SET item_code=?, cost=?, cust_id=?, emp_id=?, payment_id=? WHERE item_code=?";
	public static final String QUERY_MAX_REPAIR_DETAIL_ID = "SELECT MAX(repair_detail_id) FROM repair_detail";
	/*------------------------------------------Report Generation Queries---------------------------------------*/
	public static final String QUERY_GET_ALL_REPAIR_ID = "SELECT * FROM repair_detail";
	/*---------------------------------------------------------------------------------------------------------------*/
	
	
	/*------------Finance----------------------------*/
	
	
	
	
	public static final String QUERY_GET_ALL_SALARY_DETAILS = "SELECT * FROM salary_nethu";
	public static final String QUERY_ADD_SALARY_DETAILS = "insert into salary_nethu(salary_id , emp_id , name , designation , basic_salary , allowance , OT_hours , OT_rate , deduction,total_salary ,epf,date,etf,ot)  VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?,? )";	
	public static final String QUERY_UPDATE_SALARY = "UPDATE salary_nethu SET salary_id = ?  , emp_id=?, name = ? , designation = ? , basic_salary= ? , allowance = ? , OT_hours = ? , OT_rate = ? , deduction = ?  , total_salary = ?  , epf = ?,date=?,etf=? ,ot = ?  WHERE salary_id = ? "; 
	public static final String QUERY_DELETE_SALARY = "DELETE FROM salary_nethu WHERE salary_id = ?";
	public static final String QUERY_SELECT_EMP_DETAILS = "SELECT * from employee_nethu where emp_id = ?";
	public static final String QUERY_MAX_SALARY_ID = "SELECT MAX(salary_id)from salary_nethu";
	
	public static final String QUERY_ADD_DELIVERY_EXPENSES = "insert into delivery_expense_nethu (expense_id , delivery_id , expense_date , amount , description ) values(?,?,?,?,?)";
	public static final String QUERY_ADD_REPAIRING_EXPENSES = "insert into repair_expense_nethu(expense_id , repair_detail_id , expense_date , amount , description) values(?,?,?,?,?)";
	public static final String QUERY_ADD_SHOWROOM_EXPENSES = "insert into showroom_expense_nethu (expense_id , utility , expense_date , amount , type , description) values(?,?,?,?,?,?)";

	public static final String QUERY_SELECT_DELIVERY_EXPENSES = "select * from delivery_expense_nethu";
	public static final String QUERY_SELECT_REPAIRING_EXPENSES = "select * from repair_expense_nethu";
	public static final String QUERY_SELECT_SHOWROOM_EXPENSES = "select * from showroom_expense_nethu";
	
	public static final String QUERY_UPDATE_DELIVERY_EXPENSES = "UPDATE delivery_expense_nethu set expense_id = ? , delivery_id = ? , expense_date = ? , amount = ? , description = ?  WHERE expense_id = ?";
	public static final String QUERY_UPDATE_REPAIRING_EXPENSES = "UPDATE repair_expense_nethu set expense_id = ? , repair_detail_id = ? , expense_date = ? , amount = ? , description = ? WHERE expense_id = ?";
	public static final String QUERY_UPDATE_SHOWROOM_EXPENSES = "UPDATE showroom_expense_nethu set expense_id = ? , utility  = ? , expense_date = ? , amount = ? , type = ?  , description = ?  WHERE expense_id = ?";
	
	public static final String QUERY_DELETE_DELIVERY_EXPENSES = "DELETE FROM delivery_expense_nethu WHERE expense_id = ?";
	public static final String QUERY_DELETE_REPAIRING_EXPENSES = "DELETE FROM repair_expense_nethu WHERE expense_id = ?";
	public static final String QUERY_DELETE_SHOWROOM_EXPENSES = "DELETE FROM showroom_expense_nethu WHERE expense_id = ?";
	
	public static final String QUERY_GENERATE_DELIVERY_ID = "select max(expense_id) from delivery_expense_nethu";
	public static final String QUERY_GENERATE_REPAIRING_ID = "select max(expense_id) from repair_expense_nethu";
	public static final String QUERY_GENERATE_SHOWROOM_ID = "select max(expense_id) from showroom_expense_nethu";

	
	public static final String QUERY_GET_ALL = "select * from income";
	public static final String QUERY_DELETE_ALL = "DELETE from income where income_id = ?";
	
	
	public static final String QUERY_GET_ALL_SALIDLIST = "SELECT salary_id FROM salary_nethu";
}
