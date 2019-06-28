package model;

public class Cart {

	private String cartID;
	
	private Double sellingPrice;

	private Integer quantity;
	
	private String customerID;
	
	private String itemCode;
	
	private String modelName;
	
	private String cartDate;
	
	private Double netCartTotal;
	
	/*Setters*/
	public void setNetCartTotal( double netCartTotal ) {
		this.netCartTotal =  netCartTotal;
	}
	
	public void setCartID( String carID ) {
		this.cartID =  carID;
	}
	
	public void setSellingPrice( Double sellingPrice ) {
		this.sellingPrice = sellingPrice;
	}
	
	public void setQuantity( int quantity ) {
		this.quantity = quantity;
	}
	
	public void setCustomerID( String customerID ) {
		this.customerID = customerID;
	}
	
	public void setItemCode( String itemCode ) {
		this.itemCode = itemCode;
	}
	
	public void setModelName( String modelName ) {
		this.modelName = modelName;
	}
	
	public void setCartDate( String cartDate ) {
		this.cartDate =  cartDate;
	}
	
	/*Getters*/
	public String getCartID() {
		return cartID;
	}
	
	public double getSellingPrice() {
		return sellingPrice;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public String getCustomerID() {
		return customerID;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public String getCartDate() {
		return cartDate;
	}
	
	public Double getNetCartTotal() {
		return netCartTotal;
	}
}
