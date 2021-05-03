package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Model.Order;
import Model.OrderProcessor;
import Model.ReportObject;
import Model.Converter.EbayUploadConverter;
import Model.Importer.AnPostImporter;
import Model.Importer.EbayImporter;
import Model.Importer.ReportImporter;
import lombok.Getter;

public class EbayService implements BaseService {
	
	private final String workDirectory;
	
	@Getter
	private ArrayList<Order> importedOrders;
	
	public EbayService(String workDirectory) {
		this.workDirectory = workDirectory;
	}
	
	public ArrayList<Order> importOrdersFromFileToMemory() throws Exception {
		EbayImporter importer = new EbayImporter(workDirectory);
		importedOrders = importer.getOrdersFromFile();
		return importedOrders;
	}
	
	public void processOrders() {
		importedOrders = OrderProcessor.setGenericDispatch(importedOrders);
		importedOrders = OrderProcessor.setOldProductQuantity(importedOrders);
	}

	public void createAnpostFile(ArrayList<Order> orders, String marketPlace) {
		AnPostImporter importer = new AnPostImporter();
		
		List<Order> redLabels = orders.stream()
				.filter(order -> order.getItens().size() == 1)
				.filter(order -> order.getItens().get(0).getDispach().equals("red"))
				.collect(Collectors.toList());
			
		if (redLabels.size() > 0) {
			importer.writeImportFile(redLabels, marketPlace, workDirectory, "red");
			System.out.println(String.format("Market: %s - Red Label: %d", marketPlace, redLabels.size()));
		}
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
