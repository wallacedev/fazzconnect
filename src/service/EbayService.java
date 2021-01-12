package service;

import java.util.ArrayList;

import Model.Order;
import Model.Importer.AnPostImporter;
import Model.Importer.EbayImporter;

public class EbayService {
	
	private final String workDirectory;
	
	public EbayService(String workDirectory) {
		this.workDirectory = workDirectory;
	}
	
	public ArrayList<Order> importOrdersFromFileToMemory() throws Exception {
		EbayImporter importer = new EbayImporter(workDirectory);
		return importer.getOrdersFromFile();
	}

	public void createAnpostFile(ArrayList<Order> orders, String marketPlace) {
		AnPostImporter importer = new AnPostImporter();
		importer.writeImportFile(orders, marketPlace, workDirectory);
	}
	
	
}
