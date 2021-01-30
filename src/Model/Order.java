package Model;

import java.util.ArrayList;

import Util.Util;

public class Order {
	private String orderId;
	private String purchaseDate;
	private String buyerEmail;
	private String buyerName;
	private String buyerPhoneNumber;
	private String sku;
	private String productName;
	private String quantityPurchased;
	private String quantityShipped;
	private String quantityToShip;
	private String recipientName;
	private String shipAddress1;
	private String shipAddress2;	
	private String shipAddress3;	
	private String shipCity;	
	private String shipState;	
	private String shipPostalCode;	
	private String shipCountry;		
	private String sallesChannel;
	private ArrayList<Product> itens;
	private String idTransaction;
	
	public Order() {
		this.itens = new ArrayList<Product>();
	}; 
	
	public Order (String line) {
		createOrderFromLine(line);
	}
	
	private void createOrderFromLine(String line) {
		String[] fields = line.split("\\t");
		this.setOrderId(fields[0]);
		//this.setOrderItemId(fields[1]);
		this.setPurchaseDate(fields[2]);
		//this.setPaymentsDate(fields[3]);
		//this.setReportingDate(fields[4]);
		//this.setPromiseDate(fields[5]);
		//this.setDaysPastPromise(fields[6]);
		//this.setBuyerEmail(fields[7]);
		this.setBuyerName(fields[8]);
		this.setBuyerPhoneNumber(fields[9]);
		this.setSku(fields[10]);
		this.setProductName(Util.getShortName(this.getSku()));
		if (this.getProductName()!=null) {
			if (this.getProductName().equals("sku")) {
				this.setProductName("SKU "+this.getSku());
			}
		}else {
			this.setProductName(fields[11]);
		}
		this.setQuantityPurchased(fields[12]);
		this.setQuantityShipped(fields[13]);
		this.setQuantityToShip(fields[14]);
		//this.setShipServiceLevel(fields[15]);
		this.setRecipientName(fields[16]);
		this.setShipAddress1(fields[17]);
		this.setShipAddress2(fields[18]);
		this.setShipAddress3(fields[19]);
		this.setShipCity(fields[20]);
		this.setShipState(fields[21]);
		this.setShipPostalCode(fields[22]);
		this.setShipCountry(fields[23]);
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerPhoneNumber() {
		return buyerPhoneNumber;
	}
	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		this.buyerPhoneNumber = buyerPhoneNumber;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getQuantityPurchased() {
		return quantityPurchased;
	}
	public void setQuantityPurchased(String quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}
	public String getQuantityShipped() {
		return quantityShipped;
	}
	public void setQuantityShipped(String quantityShipped) {
		this.quantityShipped = quantityShipped;
	}
	public String getQuantityToShip() {
		return quantityToShip;
	}
	public void setQuantityToShip(String quantityToShip) {
		this.quantityToShip = quantityToShip;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getShipAddress1() {
		return shipAddress1;
	}
	public void setShipAddress1(String shipAddress1) {
		this.shipAddress1 = shipAddress1;
	}
	public String getShipAddress2() {
		return shipAddress2;
	}
	public void setShipAddress2(String shipAddress2) {
		this.shipAddress2 = shipAddress2;
	}
	public String getShipAddress3() {
		return shipAddress3;
	}
	public void setShipAddress3(String shipAddress3) {
		this.shipAddress3 = shipAddress3;
	}
	public String getShipCity() {
		return shipCity;
	}
	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}
	public String getShipState() {
		return shipState;
	}
	public void setShipState(String shipState) {
		this.shipState = shipState;
	}
	public String getShipPostalCode() {
		return shipPostalCode;
	}
	public void setShipPostalCode(String shipPostalCode) {
		this.shipPostalCode = shipPostalCode;
	}
	public String getShipCountry() {
		return shipCountry;
	}
	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}
	public String getSallesChannel() {
		return sallesChannel;
	}
	public void setSallesChannel(String sallesChannel) {
		this.sallesChannel = sallesChannel;
	}
	public ArrayList<Product> getItens() {
		return itens;
	}
	public void addItem(Product product) {
		this.itens.add(product);
	}
	public String getIdTransaction() {
		return idTransaction;
	}
	public void setIdTransaction(String idTransaction) {
		this.idTransaction = idTransaction;
	}
}
