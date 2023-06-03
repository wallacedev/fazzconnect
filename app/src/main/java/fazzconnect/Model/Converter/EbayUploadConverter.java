package fazzconnect.Model.Converter;

import java.io.FileWriter;
import java.util.ArrayList;

import fazzconnect.Model.Order;
import fazzconnect.Model.ReportObject;

public class EbayUploadConverter implements BaseUploadConverter{

	private final String workDirectory;
	
	public EbayUploadConverter (String workDirectory) {
		this.workDirectory = workDirectory;
	}

	public void writeEbayDispatchFile(ArrayList<Order> ebayOrders, ArrayList<ReportObject> ebayTrackNumbers) {
		
		try {

			String SEPARATOR = ",";
	
			// Writing file title
			//Shipping Status	Order Number	Item Number	Item Title	Custom Label	Transaction ID	Shipping Carrier Used	Tracking Number
			
			StringBuffer title = new StringBuffer();
			
			title.append("#INFO");
			title.append("\n");
			
			title.append("Shipping Status");
			title.append(SEPARATOR);
			title.append("Order Number");
			title.append(SEPARATOR);
			title.append("Item Number");
			title.append(SEPARATOR);
			title.append("Item Title");
			title.append(SEPARATOR);
			title.append("Custom Label");
			title.append(SEPARATOR);
			title.append("TransactionID");
			title.append(SEPARATOR);
			title.append("Shipping Carrier Used");
			title.append(SEPARATOR);
			title.append("Tracking Number");
			title.append("\n");
			
			StringBuffer content = new StringBuffer();
			
			for (Order order : ebayOrders) {
				
				if (!getTrackNumbers(order.getOrderId(), ebayTrackNumbers).isEmpty()) {
					content.append("1");
					content.append(SEPARATOR);
					
					content.append(order.getOrderId());
					content.append(SEPARATOR);
					
					content.append(order.getItens().get(0).getPruductId());
					content.append(SEPARATOR);
					
					content.append(String.format("\"%s\"", order.getItens().get(0).getName()));
					content.append(SEPARATOR);
					
					content.append(order.getItens().get(0).getShortName());
					content.append(SEPARATOR);
					
					content.append(order.getIdTransaction());
					content.append(SEPARATOR);
					
					content.append(ReportObject.getEbayCarrier());
					content.append(SEPARATOR);
					
					content.append(getTrackNumbers(order.getOrderId(), ebayTrackNumbers));
					content.append("\n");	
				}
				
				
			}
			
			FileWriter file = new FileWriter(String.format("current/dispatch/ebayDispatch%s.csv", workDirectory));
			
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
			
			FileWriter file = new FileWriter(String.format("current/dispatch/ResumeEbayDispatch-%s.txt", workDirectory));
			
			file.write(title.toString());
			file.write(content.toString());
			file.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
