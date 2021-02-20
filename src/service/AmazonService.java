package service;

import java.util.ArrayList;

import Model.Order;
import Model.ReportObject;
import Model.Converter.AmazonUploadConverter;
import Model.Importer.AmazonImporter;
import Model.Importer.AnPostImporter;
import Model.Importer.ReportImporter;

public class AmazonService {
	
	private final AmazonUploadConverter amazonUploadConverter;
	
	private final String workDirectory;
	
	public AmazonService(String workDirectory) {
		this.workDirectory = workDirectory;
		this.amazonUploadConverter = new AmazonUploadConverter(workDirectory);
	}
	
	public ArrayList<Order> importOrdersFromFileToMemory(String marketPlace) throws Exception {
		AmazonImporter importer = new AmazonImporter(workDirectory);
		return importer.getOrdersFromFile(marketPlace);
	}
	
	public void createAnpostFile(ArrayList<Order> orders, String marketPlace) {
		AnPostImporter importer = new AnPostImporter();
		importer.writeImportFile(orders, marketPlace, workDirectory);
	}

	public ArrayList<ReportObject> getTrackNumbersFromReport() {
		ReportImporter importer = new ReportImporter(workDirectory);
		return importer.getAllReportObjects();
	}

	public void writeAmazonDispathFile(ArrayList<Order> amazonOrders, ArrayList<ReportObject> amazonTrackNumbers) { 
		amazonUploadConverter.writeAmazonDispatchFile(amazonOrders, amazonTrackNumbers);
		
	}
}
