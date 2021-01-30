package service;

import java.util.ArrayList;

import Model.Order;
import Model.ReportObject;
import Model.Converter.EbayUploadConverter;
import Model.Importer.AnPostImporter;
import Model.Importer.EbayImporter;
import Model.Importer.ReportImporter;

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

	public ArrayList<ReportObject> getTrackNumbersFromReport() {
		ReportImporter importer = new ReportImporter(workDirectory);
		return importer.getAllReportObjects();
	}

	public void writeEbayDispathFile(ArrayList<Order> ebayOrders, ArrayList<ReportObject> ebayTrackNumbers) {
		EbayUploadConverter converter = new EbayUploadConverter(workDirectory);   
		converter.writeEbayDispatchFile(ebayOrders, ebayTrackNumbers);
		
	}
	
}
