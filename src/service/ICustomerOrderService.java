package service;

import java.util.ArrayList;
import model.BrandNewItem;
import model.Cart;
import model.Model;
import model.CustomerOrder;
import model.Order;
import model.CustomerPayment;
import model.Payment;

public interface ICustomerOrderService {

	public ArrayList<Cart> getAllCartDetails( String cartID );
	public ArrayList<BrandNewItem> getAllBrandNewItemDetails();
	public String getModelNameByItemCode(String itemCode);
	public String getPriceByModelName( String modelName );
	public void addCartItem( Cart cart );
	public void removeItemFromCart( String cartID , String itemCode );
	public void clearCart( String cartID );
	public int getModelCountInCart( String modelName, String cartID  );
	public void updateItemStatus(String status, String itemCode);
	public String getCustomerIDByNIC( String NIC );
	public boolean isInvalidSearchInput(String itemNo );
	public ArrayList<BrandNewItem> getItemListForSales();
	public ArrayList<Model> getModelListForSales();
	public void decrementQuantityOnAdd( String modelName );
	public void incrementQuantityOnRemove( String modelName );
	public void addCustomerOrderDetails(CustomerOrder order);
	public ArrayList<CustomerOrder> getCustomerOrderDetails();
	public void addCustomerPaymentDetails( CustomerPayment payment );
	public ArrayList<CustomerPayment> getCustomerPaymentDetails();
	public String getCustomerIDByCartID( String cartID );
	public boolean ifItemsAreSold( String itemCode );
	public boolean cartInOrders( String cartID );
	
	//Reports 
	public ArrayList<String> getItemCodeList();
	public void generateCOReportByMonthAndYear(String loadPath , int month, int year, String btnInput, String storePath );
	public void generateFullCOReport( String loadPath ,  String btnInput, String storePath);
	public void generateCOReportByDate( String loadPath , String date  , String btnInput, String storePath );
	public void generateCOInvoice( String loadPath, String cartID , String storePath, String orderID, String orderDate, String orderTime, String total, String type, String custID );
	
	//notification
	public String getCustomerEmailByID( String custID );
	public String getCustomerFirstNameByID( String custID );
	public String getCustomerLastNameByID(String custID);
}
