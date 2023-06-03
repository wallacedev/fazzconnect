package fazzconnect.Model.Converter;

import java.util.List;

import fazzconnect.Model.Order;

public class UPSConverter {
	public void generateUpsFile(List<Order> orders) {
		for (Order order : orders) {
			writteOrderToFile(order);
		}
	}

	private void writteOrderToFile(Order order) {
		//write each line to ups file
		
	}
	
	
}
