package Model.Importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Order;
import Model.Product;

public class EbayImporter {
	private final String folder = "ebay";
	private String workDirectory;
	
	public EbayImporter(String workDirectory) {
		this.workDirectory = workDirectory;
	}
	
	public ArrayList <Order> getOrdersFromFile() {
		
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
			System.out.println("No files to convert on UPS");
		}
		lines.remove(0);
		lines.remove(1);
		lines.remove(2);
		lines.remove(lines.size()-1);
		lines.remove(lines.size()-2);
		lines.remove(lines.size()-3);
		return lines;
	}
	
	private ArrayList<Order> getOrders(ArrayList<String> lines) {
		
		for (String line : lines) {
			Order order = new Order();
			String[] fields = line.split("\\t");
			order.setOrderId(fields[1]);
			order.setBuyerName(fields[12]);
			order.setBuyerPhoneNumber(fields[13]);
			order.setShipAddress1(fields[14]);
			order.setShipAddress2(fields[15]);
			order.setShipCity(fields[16]);
			order.setShipState(fields[17]);
			order.setShipPostalCode(fields[18]);
			order.setShipCountry(fields[19]);
			Product product = new Product();
			product.setPruductId(fields[20]);
			product.setName(fields[21]);
			order.addItem(product);
			order.setSallesChannel("ebay");
		}
		return null;
	}
	
	
}
