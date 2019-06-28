package model;

import javafx.beans.property.SimpleStringProperty;

public class ItemRepairManage1Model {
	
	private SimpleStringProperty modelName;
	private SimpleStringProperty nameCategory;
	private SimpleStringProperty idSupplier;
	private SimpleStringProperty unitPrice;
	private SimpleStringProperty sellPrice;
	private SimpleStringProperty quantity;
	private SimpleStringProperty warranty;
	
	public ItemRepairManage1Model(String modelName, String nameCategory, String idSupplier, String unitPrice, String sellPrice, String quantity, String warranty) {
		super();
		this.modelName = new SimpleStringProperty(modelName);
		this.nameCategory = new SimpleStringProperty(nameCategory);
		this.idSupplier = new SimpleStringProperty(idSupplier);
		this.unitPrice = new SimpleStringProperty(unitPrice);
		this.sellPrice = new SimpleStringProperty(sellPrice);
		this.quantity = new SimpleStringProperty(quantity);
		this.warranty = new SimpleStringProperty(warranty);
	}

	public String getModelName() {
		return modelName.get();
	}

	public String getNameCategory() {
		return nameCategory.get();
	}

	public String getIdSupplier() {
		return idSupplier.get();
	}

	public String getUnitPrice() {
		return unitPrice.get();
	}

	public String getSellPrice() {
		return sellPrice.get();
	}

	public String getQuantity() {
		return quantity.get();
	}

	public String getWarranty() {
		return warranty.get();
	}

	
	
	
}
