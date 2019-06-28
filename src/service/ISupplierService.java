package service;

import java.util.ArrayList;

import model.SupplierPayment;
import model.supplier;
import model.supplierOrder;

public interface ISupplierService {

	/*------------------------supplier details---------------------------------------------*/
	public ArrayList<supplier> getAllSupplierDetails();
	public void addSupplierDetails(supplier sup);
	public void updateSupplierDetails(supplier sup);
	public void deleteSupplierDetails(String SID);
	
	/*-------------------------supplier order details--------------------------------------*/
	public ArrayList<supplierOrder> getAllSupplierOrder();
	public void addSupplierOrder(supplierOrder order);
	public void updateSupplierOrder(supplierOrder order);
	public void deleteSupplierOrder(String OID);
		
	/*-------------------------supplier payment details------------------------------------*/
	public ArrayList<SupplierPayment> getAllSupplierPay();
	public void addSupplierPay(SupplierPayment pay);
	public void updateSupplierPay(SupplierPayment pay);
	public void deleteSupplierPay(String PaymentID);

	/*-------------------------Report-------------------------------------------------------*/
	public ArrayList<String> getSupplierOrderDetailsList();
	public void generateSupplierOrderReportByMonthAndYear(String loadPath , int month, int year, String btnInput, String storePath );
	public void generateFullSupplierOrderReport( String loadPath ,  String btnInput, String storePath);
}
