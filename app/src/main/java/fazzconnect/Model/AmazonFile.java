package fazzconnect.Model;

public class AmazonFile {

	private String orderId;
	private String orderItemId;
	private String purchaseDate;
	private String paymentsDate;
	private String reportingDate;
	private String promiseDate;
	private String daysPastPromise;
	private String buyerEmail;
	private String buyerName;
	private String buyerPhoneNumber;
	private String sku;
	private String productName;
	private String quantityPurchased;
	private String quantityShipped;
	private String quantityToShip;
	private String shipServiceLevel;
	private String recipientName;
	private String shipAddress1;
	private String shipAddress2;	
	private String shipAddress3;	
	private String shipCity;	
	private String shipState;	
	private String shipPostalCode;	
	private String shipCountry;	
	private String isBusinessOrder;	
	private String purchaseOrderNumber;	
	private String priceDesignation;	
	private String isSoldByAb;
	private String shipMethod;
	

	public String getShipMethod() {
		return shipMethod;
	}

	public void setShipMethod(String shipMethod) {
		this.shipMethod = shipMethod;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return orderId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getPaymentsDate() {
		return paymentsDate;
	}
	public void setPaymentsDate(String paymentsDate) {
		this.paymentsDate = paymentsDate;
	}
	public String getReportingDate() {
		return reportingDate;
	}
	public void setReportingDate(String reportingDate) {
		this.reportingDate = reportingDate;
	}
	public String getPromiseDate() {
		return promiseDate;
	}
	public void setPromiseDate(String promiseDate) {
		this.promiseDate = promiseDate;
	}
	public String getDaysPastPromise() {
		return daysPastPromise;
	}
	public void setDaysPastPromise(String daysPastPromise) {
		this.daysPastPromise = daysPastPromise;
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
	public String getShipServiceLevel() {
		return shipServiceLevel;
	}
	public void setShipServiceLevel(String shipServiceLevel) {
		this.shipServiceLevel = shipServiceLevel;
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
	public String getIsBusinessOrder() {
		return isBusinessOrder;
	}
	public void setIsBusinessOrder(String isBusinessOrder) {
		this.isBusinessOrder = isBusinessOrder;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public String getPriceDesignation() {
		return priceDesignation;
	}
	public void setPriceDesignation(String priceDesignation) {
		this.priceDesignation = priceDesignation;
	}
	public String getIsSoldByAb() {
		return isSoldByAb;
	}
	public void setIsSoldByAb(String isSoldByAb) {
		this.isSoldByAb = isSoldByAb;
	}
	
	@Override
	public Object clone() {
	    AmazonFile amazonFile = new AmazonFile();
	    try {
	    	amazonFile = (AmazonFile) super.clone();
	    } catch (CloneNotSupportedException e) {
	        
	    }
	    amazonFile.orderId = this.orderId;
    	amazonFile.orderItemId = this.orderItemId;
    	amazonFile.purchaseDate = this.purchaseDate;
    	amazonFile.paymentsDate = this.paymentsDate;
    	amazonFile.reportingDate = this.reportingDate;
    	amazonFile.promiseDate = this.promiseDate;
    	amazonFile.daysPastPromise = this.daysPastPromise;
    	amazonFile.buyerEmail = this.buyerEmail;
    	amazonFile.buyerName = this.buyerName;
    	amazonFile.buyerPhoneNumber = this.buyerPhoneNumber;
    	amazonFile.sku = this.sku;
    	amazonFile.productName = this.productName;
    	amazonFile.quantityPurchased = this.quantityPurchased;
    	amazonFile.quantityShipped = this.quantityShipped;
    	amazonFile.quantityToShip = this.quantityToShip;
    	amazonFile.shipServiceLevel = this.shipServiceLevel;
    	amazonFile.recipientName = this.recipientName;
    	amazonFile.shipAddress1 = this.shipAddress1;
    	amazonFile.shipAddress2 = this.shipAddress2;	
    	amazonFile.shipAddress3 = this.shipAddress3;	
    	amazonFile.shipCity = this.shipCity;	
    	amazonFile.shipState = this.shipState;	
    	amazonFile.shipPostalCode = this.shipPostalCode;	
    	amazonFile.shipCountry = this.shipCountry;	
    	amazonFile.isBusinessOrder = this.isBusinessOrder;	
    	amazonFile.purchaseOrderNumber = this.purchaseOrderNumber;	
    	amazonFile.priceDesignation = this.priceDesignation;	
    	amazonFile.isSoldByAb = this.isSoldByAb;
    	amazonFile.shipMethod = this.shipMethod;
	    return amazonFile;
	}
	
	
}
