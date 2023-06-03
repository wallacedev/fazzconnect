package fazzconnect.Model.Converter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fazzconnect.Model.Order;
import fazzconnect.Model.ReportObject;

public class AmazonUploadConverter implements BaseUploadConverter{
	
	private String workDirectory;
	private final String SEPARATOR = "\t";
	private String dispatchdate;
	
	public AmazonUploadConverter (String workDirectory) {
		this.workDirectory = workDirectory;
	}
	
	public void writeAmazonDispatchFile(ArrayList<Order> amazonOrders, ArrayList<ReportObject> reportObjects) {
		try {
			dispatchdate = reportObjects.get(0).getConvertedDate();
			
			StringBuffer content = new StringBuffer();
			
			for (Order order : amazonOrders) {
				
				var trackingNumbers = getTrackNumbersList(order.getOrderId(), reportObjects);
				
				if (!trackingNumbers.isEmpty()) {
					
					if (trackingNumbers.size() == 1) {
						
						content.append(getLine(order, trackingNumbers.get(0)));
					}
					else if (trackingNumbers.size() > 1 && 
						trackingNumbers.size() == order.getQuantityPurchased()) {
							trackingNumbers.forEach(tn -> {
								content.append(getLine(order, tn, true));
							});
					} else {
						content.append(getLine(order, trackingNumbers.get(0)));
						printExtraTrackingNumbers(order, trackingNumbers);
					}
				}
			}
			
			String batch = amazonOrders.get(0).getBatch();
			FileWriter file = new FileWriter("current/dispatch/"+batch + ".txt");
			file.write(getTitle());
			file.write(content.toString());
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printExtraTrackingNumbers(Order order, List<String> trackingNumbers) {
		try {
			FileWriter file = new FileWriter("current/dispatch/extraTracking.txt", true);
			file.write(order.getOrderId());
			file.write(SEPARATOR);	
			file.write(dispatchdate);
			file.write(SEPARATOR);
			String tn = "";
			for (String trackingNumber : trackingNumbers) {
				tn += trackingNumber + " ";
			}
			file.write(tn);
			file.write("\n");
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String getLine(Order order, String trackingNumber) {
		return getLine(order, trackingNumber, false);
	}

	private String getLine(Order order, String trackingNumber, boolean useProductId) {
		StringBuffer content = new StringBuffer();
		content.append(order.getOrderId());
		content.append(SEPARATOR);
		
		//product id
		content.append(useProductId ? order.getItens().get(0).getPruductId() : "");
		content.append(SEPARATOR);
		
		//quantity per label
		content.append(useProductId ? "1" : "");
		content.append(SEPARATOR);
		
		content.append(dispatchdate);
		content.append(SEPARATOR);
		
		content.append("Other");
		content.append(SEPARATOR);
		
		content.append(ReportObject.getCarrier(order.getShipCountry()));
		content.append(SEPARATOR);
		
		content.append(trackingNumber);
		content.append(SEPARATOR);
		
		content.append("");
		content.append(SEPARATOR);
		
		content.append("");
		content.append(SEPARATOR);
		
		content.append("FAZZ LTD");
		content.append(SEPARATOR);
		
		content.append("10C Boeing Road");
		content.append(SEPARATOR);
		
		content.append("Airways Industrial Estate");
		content.append(SEPARATOR);
		
		content.append("");
		content.append(SEPARATOR);
		
		content.append("Dublin 17");
		content.append(SEPARATOR);
		
		content.append("Dublin");
		content.append(SEPARATOR);
		
		content.append("Dublin");
		content.append(SEPARATOR);
		
		content.append("D17 E167");
		content.append(SEPARATOR);
		
		content.append("IE");
		content.append("\n");
		
		return content.toString();
	}
	
	public String getTitle() {
		StringBuffer title = new StringBuffer();
		
		title.append("order-id");
		title.append(SEPARATOR);
		title.append("order-item-id");
		title.append(SEPARATOR);
		title.append("quantity");
		title.append(SEPARATOR);
		title.append("ship-date");
		title.append(SEPARATOR);
		title.append("carrier-code");
		title.append(SEPARATOR);
		title.append("carrier-name");
		title.append(SEPARATOR);
		title.append("tracking-number");
		title.append(SEPARATOR);
		title.append("ship-method");
		title.append(SEPARATOR);
		title.append("transparency_code");
		title.append(SEPARATOR);
		title.append("ship_from_address_name");
		title.append(SEPARATOR);
		title.append("ship_from_address_line1");
		title.append(SEPARATOR);
		title.append("ship_from_address_line2");
		title.append(SEPARATOR);
		title.append("ship_from_address_line3");
		title.append(SEPARATOR);
		title.append("ship_from_address_city");
		title.append(SEPARATOR);
		title.append("ship_from_address_county");
		title.append(SEPARATOR);
		title.append("ship_from_address_state_or_region");
		title.append(SEPARATOR);
		title.append("ship_from_address_postalcode");
		title.append(SEPARATOR);
		title.append("ship_from_address_countrycode");
		title.append("\n");
		
		return title.toString();
	}
}
