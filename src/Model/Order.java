package Model;

import java.util.ArrayList;

import lombok.Data;

@Data
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
	private String batch;
	
	public Order() {
		this.itens = new ArrayList<Product>();
	}; 
	
	public void addItem(Product product) {
		this.itens.add(product);
	}
	
	public boolean isRedLabel () {
		if (itens.size() == 1) {
			if (itens.get(0).getDispach().equals("red")) {
				return true;
			}	
		}
		return false;
	}
	
	public boolean isBlueLabel () {
		if (itens.size() == 1) {
			if (itens.get(0).getDispach().equals("blue")) {
				return true;
			}	
		}
		return false;
	}
	
	public boolean isAnaliseLabel () {
		if (itens.size() > 1) {
			return true;
		}
		else {
			if (itens.get(0).getDispach().equals("analise")) {
				return true;
			}	
			return false;
		}
	}
	
	@Override
	public Object clone() {
	    Order order = new Order();
	    try {
	    	order = (Order) super.clone();
	    } catch (CloneNotSupportedException e) {
	        
	    }
	    
	    order.orderId = this.orderId;
	    order.purchaseDate = this.purchaseDate;
	    order.buyerEmail = this.buyerEmail;
	    order.buyerName = this.buyerName;
	    order.buyerPhoneNumber = this.buyerPhoneNumber;
	    order.sku = this.sku;
	    order.productName = this.productName;
	    order.quantityPurchased = this.quantityPurchased;
	    order.quantityShipped = this.quantityShipped;
	    order.quantityToShip = this.quantityToShip;
	    order.recipientName = this.recipientName;
	    order.shipAddress1 = this.shipAddress1;
	    order.shipAddress2 = this.shipAddress2;	
	    order.shipAddress3 = this.shipAddress3;	
	    order.shipCity = this.shipCity;	
	    order.shipState = this.shipState;	
	    order.shipPostalCode = this.shipPostalCode;	
	    order.shipCountry = this.shipCountry;	
	    order.sallesChannel = this.sallesChannel;
		order.idTransaction = this.idTransaction;
		order.batch = this.batch;
		
		ArrayList<Product> itens = new ArrayList<Product>();
		this.itens.forEach(i -> itens.add((Product)i.clone()));
		
		order.itens = itens;
	    return order;
	}
}
