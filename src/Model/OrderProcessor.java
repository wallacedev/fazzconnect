package Model;

import java.util.ArrayList;
import java.util.Optional;

import Util.Util;

public class OrderProcessor {

	public static ArrayList<Order> setShortNameDispach(ArrayList<Order> orders) {
		for (Order order : orders)
			for (Product item : order.getItens()) {
				String sku = item.getSku();
				
				Optional<String> shortName = Optional.ofNullable(Util.getShortName(sku));
				
				if (shortName.isPresent()) {
					item.setShortName(shortName.get());
					item.setDispach("red");
				} 
				
				else if (!shortName.isPresent()) {
					
					shortName = Optional.ofNullable(Util.getShortNameBlue(sku));
					
					if (shortName.isPresent()) {
						item.setShortName(shortName.get());
						item.setDispach("blue");
					}
					else {
						item.setDispach("analise");
						System.out.println("SKU not converted:" + sku);
					}
				}
			}
		return orders;
	}

	public static ArrayList<Order> setCustomTariff(ArrayList<Order> orders) {
		String CUSTOM_TARIF = "8421.99.0";
		String sulfix = "al";
		
		for (Order order : orders)
			for (Product item : order.getItens())
				if (isSulfixMatch(item.getShortName(), sulfix))
					item.setCustomTarif(CUSTOM_TARIF);
				
		return orders;
	}

	private static boolean isSulfixMatch(String shortName, String sulfix) {
		Optional<String> sub = Optional.ofNullable(shortName.substring(0, 1).toLowerCase());
		
		if (sub.isPresent()) {
			if (sulfix.equals(sub.get())) {
				return true;
			}
		} 
		return false;
	}

	public static ArrayList<Order> split(ArrayList<Order> orders) {
		
		ArrayList<Order> newOrders = new ArrayList();
		
		for (Order order : orders) {
			
			if (order.isRedLabel()) {
				
				Optional<Integer> maxQtdToSplit =  Optional.ofNullable(Util.quantityToSplit(order.getItens().get(1).getSku()));
				
				if (maxQtdToSplit.isPresent()) {
					
					int qtdToSend = Integer.parseInt(order.getQuantityPurchased());
					int qtsMax = maxQtdToSplit.get();
					
					if (qtdToSend > qtsMax) {
						
						for (int i = 0; qtdToSend > 0; i++) {
							Order newOrder = (Order) order.clone();
							String shortName = newOrder.getItens().get(1).getShortName();
							newOrder.getItens().get(1).setShortName(i+1 +"/"+  +  " - " +shortName);
							
							
							amazonFileTemp.setProductName(i+1 +"/"+ quantityToSplit +  " - " +productName);
							amazonFileTemp.setOrderItemId(amazonFileTemp.getOrderId()+i);
							amazonFileList.add(amazonFileTemp);
							
						}
						
						
					}
					
				}
			}
			
		}
		

	}
}
