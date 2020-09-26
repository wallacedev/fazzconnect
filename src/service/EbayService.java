package service;

import java.util.ArrayList;

import Model.Order;
import Model.Importer.EbayImporter;

public class EbayService {
	public ArrayList<Order> importOrdersFromFileToMemory(String workDirectory) {
		EbayImporter importer = new EbayImporter(workDirectory);
		return importer.getOrdersFromFile();
	}
}
