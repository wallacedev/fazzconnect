package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
				.filter(order -> order.getItens().size() >= 1 || order.getItens().get(0).getDispach().equals("analise"))
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
		
	}
	
}
