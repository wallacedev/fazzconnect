package service;

import java.util.ArrayList;

import Model.Order;
import Model.Importer.UPSImporter;
import persistence.OrdersPersistence;

public class UPSService {
	
	private ArrayList<Order> orders;
	
	public void importOrdersFromFile(String workDirectory) {
		UPSImporter importer = new UPSImporter();
		orders = importer.getOrdersFromFile(workDirectory);
		OrdersPersistence persistence = new OrdersPersistence();
		persistence.saveOrdersTXT(orders);
	}
	
	public void createUPSFile() {
		
	}
}
