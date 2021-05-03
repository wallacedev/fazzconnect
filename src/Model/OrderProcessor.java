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
	
	public static ArrayList<Order> setGenericDispatch(ArrayList<Order> orders) {
		for (Order order : orders)
			for (Product item : order.getItens()) {
				item.setDispach("red");
			}
		return orders;
	}

	public static ArrayList<Order> setProductQuantity(ArrayList<Order> orders) {
		for (Order order : orders)
			for (Product item : order.getItens()) {
				
				if (!item.getShortName().equals("") && item.shortName.split(" ").length > 2) {
					
					// Considering the short name layout: Brand Model PKQTD
					var packQtd = Optional.ofNullable(Integer.valueOf(item.shortName.split(" ")[2]));
					
					if (packQtd.isPresent()) {
						item.setQuantity(item.getQuantity() * packQtd.get());
					} else {
						item.setQuantity(item.getQuantity() * 1);
					}
					var newName = item.shortName.split(" ");
					item.setShortName(item.getQuantity() + " " + newName[0] + " " + newName[1]);
				}
			}
		return orders;
	}
	
	public static ArrayList<Order> setOldProductQuantity(ArrayList<Order> orders) {
		for (Order order : orders)
			for (Product item : order.getItens()) {
				
				if (item.getQuantity() > 1 ) {
					item.setName(String.format("%d x %s", item.getQuantity(), item.getName()));
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
		Optional<String> sub = Optional.ofNullable(shortName.substring(0, 2).toLowerCase());
		
		if (sub.isPresent()) {
			return sulfix.equals(sub.get());
		} else return false;
	}

	public static ArrayList<Order> split(ArrayList<Order> orders) {
		
		ArrayList<Order> newOrders = new ArrayList<Order>();
		
		for (Order order : orders) {
			
			if (order.isRedLabel()) {
				
				var maxQtdToSplitStr =  Optional.ofNullable(Util.quantityToSplit(order.getItens().get(0).getSku()));
				
				if (maxQtdToSplitStr.isPresent()) {
					
					var maxQtdToSplit = Integer.parseInt(maxQtdToSplitStr.get());
					var qtdToSend = order.getItens().get(0).getQuantity();
					var qtdLeft = qtdToSend;
					var qtsMax = maxQtdToSplit;
					var labelAmount = 0;
					
					if (qtdToSend > qtsMax) {
					
						labelAmount = qtdToSend / qtsMax;
						
						if (qtdToSend % qtsMax != 0) labelAmount += 1;
						
						var shortName = order.getItens().get(0).getShortName();
						
						var brandModel = "";
						if (order.hasQtdOnShortName(0)) {
							brandModel =  String.format("%s %s", shortName.split(" ")[1], shortName.split(" ")[2]);
						} else {
							brandModel =  String.format("%s %s", shortName.split(" ")[0], shortName.split(" ")[1]);
						}
						
						for (int i = 1; i<=labelAmount; i++) {
							
							Order newOrder = (Order) order.clone();
							
							var qtdAtualOnLabel = (qtdLeft - qtsMax >= 0) ? qtsMax : qtdLeft;
							
							newOrder.getItens().get(0).setShortName(String.format("(%d/%d) %d %s ", i, labelAmount, qtdAtualOnLabel, brandModel));
							newOrder.getItens().get(0).setQuantity(qtdAtualOnLabel);
							
							var productId = newOrder.getItens().get(0).getPruductId();
							productId = productId+i;
							newOrder.getItens().get(0).setPruductId(productId);
							
							qtdLeft = qtdLeft - qtdAtualOnLabel;
					
							newOrders.add(newOrder);
						}
					}
					else {
						newOrders.add(order);
					}
				} else {
					newOrders.add(order);
				}
			} else {
				newOrders.add(order);
			}
		}
		return newOrders;
	}
}
