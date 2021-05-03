package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Model.Order;
import Model.OrderProcessor;
import Model.ReportObject;
import Model.Converter.AmazonUploadConverter;
import Model.Importer.AmazonImporter;
import Model.Importer.AnPostImporter;
import Model.Importer.ReportImporter;
import lombok.Getter;

public class AmazonService implements BaseService{
	
	private final AmazonUploadConverter amazonUploadConverter;
	
	private final String workDirectory;
	
	@Getter
	private ArrayList<Order> importedOrders;
	
	public AmazonService(String workDirectory) {
		this.workDirectory = workDirectory;
		this.amazonUploadConverter = new AmazonUploadConverter(workDirectory);
	}
	
	public ArrayList<Order> importOrdersFromFileToMemory(String marketPlace) throws Exception {
		AmazonImporter importer = new AmazonImporter(workDirectory);
		importedOrders = importer.getOrdersFromFile(marketPlace);
		return importedOrders;
	}
	
	public void processOrders() {
		importedOrders = OrderProcessor.setShortNameDispach(importedOrders);
		importedOrders = OrderProcessor.setProductQuantity(importedOrders);
		importedOrders = OrderProcessor.setCustomTariff(importedOrders);
		importedOrders = OrderProcessor.split(importedOrders);
	}
	
	public void createAnpostFiles(ArrayList<Order> orders, String marketPlace) {
		AnPostImporter importer = new AnPostImporter();
		
		List<Order> redLabels = orders.stream()
			.filter(order -> order.getItens().size() == 1)
			.filter(order -> order.getItens().get(0).getDispach().equals("red"))
			.collect(Collectors.toList());
		
		if (redLabels.size() > 0) {
			importer.writeImportFile(redLabels, marketPlace, workDirectory, "red");
			System.out.println(String.format("Market: %s - Red Label: %d", marketPlace, redLabels.size()));
		}
		
		List<Order> blueLabels = orders.stream()
				.filter(order -> order.getItens().size() == 1)
				.filter(order -> order.getItens().get(0).getDispach().equals("blue"))
				.collect(Collectors.toList());
		
		if (blueLabels.size() > 0) {
			importer.writeImportFile(blueLabels, marketPlace, workDirectory, "blue");
			System.out.println(String.format("Market: %s - Blue Label: %d", marketPlace, blueLabels.size()));
		}
		
		List<Order> analiseLabels = orders.stream()
				.filter(order -> order.getItens().size() > 1)
				.filter(order -> order.getItens().get(0).getDispach().equals("analise"))
				.collect(Collectors.toList());
		
		if (analiseLabels.size() > 0) {
			importer.writeImportFile(analiseLabels, marketPlace, workDirectory, "analise");
			System.out.println(String.format("Market: %s - Analise Label: %d", marketPlace, analiseLabels.size()));
		}
	}

	public ArrayList<ReportObject> getTrackNumbersFromReport() {
		ReportImporter importer = new ReportImporter(workDirectory);
		return importer.getAllReportObjects();
	}

	public void writeAmazonDispathFile(ArrayList<Order> amazonOrders, ArrayList<ReportObject> amazonTrackNumbers) { 
		amazonUploadConverter.writeAmazonDispatchFile(amazonOrders, amazonTrackNumbers);
		
	}
}
