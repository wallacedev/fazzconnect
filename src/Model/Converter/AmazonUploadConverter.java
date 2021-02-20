package Model.Converter;

import java.io.FileWriter;
import java.util.ArrayList;

import Model.Order;
import Model.ReportObject;

public class AmazonUploadConverter implements BaseUploadConverter{
	
	private String workDirectory;
	
	public AmazonUploadConverter (String workDirectory) {
		this.workDirectory = workDirectory;
	}
	
	public void writeAmazonDispatchFile(ArrayList<Order> amazonOrders, ArrayList<ReportObject> reportObjects) {
		try {
			String SEPARATOR = "\t";

			// Writing file title
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
			
			StringBuffer content = new StringBuffer();
			
			for (Order order : amazonOrders) {
				
				content.append(order.getOrderId());
				content.append(SEPARATOR);
				
				content.append(order.getItens().get(1).getPruductId());
				content.append(SEPARATOR);
				
				content.append("");
				content.append(SEPARATOR);
				
				content.append(getDate(order.getOrderId(), reportObjects));
				content.append(SEPARATOR);
				
				content.append("Other");
				content.append(SEPARATOR);
				
				content.append(ReportObject.getCarrier(order.getShipCountry()));
				content.append(SEPARATOR);
				
				content.append(getTrackNumber(order.getOrderId(), reportObjects));
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
			}
			
			String batch = amazonOrders.get(1).getBatch();
			FileWriter file = new FileWriter(workDirectory+"/dispatch/"+batch + ".txt");
			file.write(title.toString());
			file.write(content.toString());
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
