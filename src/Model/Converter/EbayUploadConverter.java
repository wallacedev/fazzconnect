package Model.Converter;

import java.io.FileWriter;
import java.util.ArrayList;

import Model.Order;
import Model.ReportObject;

public class EbayUploadConverter implements BaseUploadConverter{

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
				
				if (!getTrackNumbers(order.getOrderId(), ebayTrackNumbers).isEmpty()) {
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
				
				
			}
			
			FileWriter file = new FileWriter(String.format("%s/dispatch/ebayDispatch%s.csv", workDirectory, workDirectory));
			
			file.write(title.toString());
			file.write(content.toString());
			file.close();
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void writeEbayResumeDispatch(ArrayList<Order> ebayOrders, ArrayList<ReportObject> ebayTrackNumbers) {
		try {

			String SEPARATOR = "\t";
	
			// Writing file title
			StringBuffer title = new StringBuffer();
			title.append("Order-ID");
			title.append(SEPARATOR);
			title.append("Product-Name");
			title.append(SEPARATOR);
			title.append("Tracking-Number");
			title.append("\n");
			
			StringBuffer content = new StringBuffer();
			
			for (Order order : ebayOrders) {
				
				if (!getTrackNumbers(order.getOrderId(), ebayTrackNumbers).isEmpty()) {
					content.append(order.getOrderId());
					content.append(SEPARATOR);
					content.append(order.getItens().get(0).getShortName());
					content.append(SEPARATOR);
					content.append(getTrackNumbers(order.getOrderId(), ebayTrackNumbers));
					content.append("\n");	
				}
			}
			
			FileWriter file = new FileWriter(String.format("%s/dispatch/ResumeEbayDispatch-%s.txt", workDirectory, workDirectory));
			
			file.write(title.toString());
			file.write(content.toString());
			file.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
