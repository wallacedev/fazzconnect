package Model.Converter;

import java.awt.List;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import Model.Order;
import Model.ReportObject;

public class EbayUploadConverter {

	private String workDirectory;
	
	public EbayUploadConverter (String workDirectory) {
		this.workDirectory = workDirectory;
	}

	public void writeEbayDispatchFile(ArrayList<Order> ebayOrders, ArrayList<ReportObject> ebayTrackNumbers) {
		
		try {

			String SEPARATOR = ",";
	
			// Writing file title
			StringBuffer title = new StringBuffer();
			title.append("Action(SiteID=UK|Country=GB|Currency=GBP|Version=941|CC=ISO-8859-1)");
			title.append(SEPARATOR);
			title.append("ItemID");
			title.append(SEPARATOR);
			title.append("TransactionID");
			title.append(SEPARATOR);
			title.append("ShippingStatus");
			title.append(SEPARATOR);
			title.append("ShippingCarrierUsed");
			title.append(SEPARATOR);
			title.append("ShipmentTrackingNumber");
			title.append("\n");
			
			StringBuffer content = new StringBuffer();
			
			for (Order order : ebayOrders) {
				
				content.append("Status");
				content.append(SEPARATOR);
				content.append(order.getItens().get(0).getPruductId());
				content.append(SEPARATOR);
				content.append(order.getIdTransaction());
				content.append(SEPARATOR);
				content.append("1");
				content.append(SEPARATOR);
				content.append(ReportObject.getEbayCarrier());
				content.append(SEPARATOR);
				content.append(getTrackNumbers(order.getOrderId(), ebayTrackNumbers));
				content.append("\n");	
			}
			
			FileWriter file = new FileWriter(String.format("%s/ebayDispatch%s.csv", workDirectory, workDirectory));
			file.write(title.toString());
			file.write(content.toString());
			file.close();
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private String getTrackNumbers (String idOrder, ArrayList<ReportObject> ebayTrackNumbers) throws Exception {
		
		var results =  Optional.ofNullable(
				 ebayTrackNumbers
				 .stream()
				 .filter(ebayTrackNumber -> ebayTrackNumber.getIdOrder().equals(idOrder))
				 .collect(Collectors.toList()))	
		.orElseThrow(() -> new Exception("Track number not found"));
		
		String trackNumbers = "";
		for (ReportObject result : results) {
			trackNumbers += result.getTrackNumber() + " ";
		}
		return trackNumbers;
	}
}
