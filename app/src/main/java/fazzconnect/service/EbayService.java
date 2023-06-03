package fazzconnect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fazzconnect.Model.Converter.EbayUploadConverter;
import fazzconnect.Model.Importer.AnPostImporter;
import fazzconnect.Model.Importer.EbayImporter;
import fazzconnect.Model.Importer.ReportImporter;
import fazzconnect.Model.Order;
import fazzconnect.Model.OrderProcessor;
import fazzconnect.Model.ReportObject;
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
	
	public void newProcessOrders() {
		importedOrders = OrderProcessor.setGenericDispatch(importedOrders);
		importedOrders = OrderProcessor.setProductQuantity(importedOrders);
		importedOrders = OrderProcessor.setCustomTariff(importedOrders);
		importedOrders = OrderProcessor.split(importedOrders);
	}

	public void createAnpostFile(ArrayList<Order> orders, String marketPlace) {
		AnPostImporter importer = new AnPostImporter();
		System.out.println(String.format("Orders total: %d", orders.size()));
		for (Order order : orders) {
			System.out.println(String.format("order.getOrderId(): %s", order.getOrderId()));
			System.out.println(String.format("order.getItens().size(): %d", order.getItens().size()));
			System.out.println(String.format("order.getItens().get(0).getDispach(): %s", order.getItens().get(0).getDispach()));
		}
		
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
				.filter(order -> order.getItens().size() > 1 
						|| order.getItens().get(0).getDispach().equals("analise"))
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

	public void writeEbayDispathFile(ArrayList<Order> ebayOrders, ArrayList<ReportObject> ebayTrackNumbers) {
		EbayUploadConverter converter = new EbayUploadConverter(workDirectory);
		converter.writeEbayDispatchFile(ebayOrders, ebayTrackNumbers);
		converter.writeEbayResumeDispatch(ebayOrders, ebayTrackNumbers);
		
	}
	
}
