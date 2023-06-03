package fazzconnect.service;

import java.util.ArrayList;

import fazzconnect.Model.Order;
import fazzconnect.Model.Importer.UPSImporter;
import fazzconnect.persistence.OrdersPersistence;

public class UPSService {
	
	private ArrayList<Order> orders;
	
	//import and save into database
	public void importOrdersFromFileToDatabase(String workDirectory) {
		UPSImporter importer = new UPSImporter();
		orders = importer.getOrdersFromFile(workDirectory);
		OrdersPersistence persistence = new OrdersPersistence();
		persistence.saveOrdersTXT(orders);
	}
	
	public ArrayList<Order> importOrdersFromFileToMemory(String workDirectory) {
		UPSImporter importer = new UPSImporter();
		return orders = importer.getOrdersFromFile(workDirectory);
	}
	
	public void createUPSFile() {
		
	}
}
