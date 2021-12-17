package Model.Importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Order;
import Model.Product;
import Util.Util;

public class EbayImporter {
	private final String folder = "ebay";
	private String workDirectory;
	
	public EbayImporter(String workDirectory) {
		this.workDirectory = workDirectory;
	}
	
	public ArrayList <Order> getOrdersFromFile() throws Exception {
		
		ArrayList <String> lines = readLines();
		ArrayList <Order> orders = getOrders(lines);
		return orders;
	}

	private ArrayList<String> readLines() {
		
		File path = new File(workDirectory+"/"+folder+"/");
		
		File[] files = path.listFiles();
		
		ArrayList<String> lines = new ArrayList<String>();
		if (files.length>0) {
			File file = new File(workDirectory+"/"+folder+"/"+files[0].getName());
			Scanner myReader;
			try {
				myReader = new Scanner(file);
				while (myReader.hasNextLine()) {
					lines.add(myReader.nextLine());
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}else {
			System.out.println("No files to convert on Ebay");
		}
		
		lines.remove(lines.size() - 3);
		lines.remove(lines.size() - 2);
		lines.remove(lines.size() - 1);
		lines.remove(2);
		lines.remove(1);
		lines.remove(0);
		
		return lines;
	}
	
private ArrayList<Order> getOrders(ArrayList<String> lines) throws Exception {
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		for (String line : lines) {
			
			Order order = new Order();
			
			line = line.replace("\",\"", ";");
			line = line.replace(",\"", ";");
			line = line.replace("\",", ";");
			
			String[] fields = line.split(";");
			
			order.setOrderId(sanitize(fields[1]));
			
			order.setBuyerEmail(sanitize(fields[4]));
			
			order.setRecipientName(sanitize(fields[14]));
			
			order.setBuyerPhoneNumber(sanitize(fields[15]));
			
			order.setShipAddress1(sanitize(fields[16]));
			
			order.setShipAddress2(sanitize(fields[17]));
			
			order.setShipCity(sanitize(fields[18]));
			
			order.setShipState(sanitize(fields[19]));
			
			order.setShipPostalCode(sanitize(fields[20]));
			
			order.setShipCountry(Util.getShortCountry(fields[21]));
			
			order.setIdTransaction(fields[60]);
			
			order.setSallesChannel("ebay");
			
			Product product = new Product();
			product.setPruductId(sanitize(fields[22]));
			
			product.setName(fields[23]);
			
			product.setShortName(fields[24]);
						
			product.setQuantity(Integer.valueOf(sanitize(fields[26])));
			
			order.addItem(product);
			
			orders.add(order);
		}
		return orders;
	}
	
	private String getProductName(String shortName, String fullName) throws Exception {
		if (!shortName.equals("")) {
			return shortName;
		}
		else if (!fullName.equals("")) {
			return fullName;
		}
		else throw new Exception("Product name not found");
	}
	
	private String sanitize(String value) {
		return value.replace("\"", "");
	}
	
	
}
