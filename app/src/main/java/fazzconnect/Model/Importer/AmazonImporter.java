package fazzconnect.Model.Importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import fazzconnect.Model.Order;
import fazzconnect.Model.Product;

public class AmazonImporter {
	private final String folder = "amazon";
	private String workDirectory;
	
	public AmazonImporter(String workDirectory) {
		this.workDirectory = workDirectory;
	}
	
	
	public ArrayList <Order> getOrdersFromFile(String marketPlace) throws Exception {
		
		ArrayList <String> lines = readLines(marketPlace);
		ArrayList <Order> orders = getOrders(lines, marketPlace);
		return orders;
	}
	
	private ArrayList <String> readLines (String marketPlace) {
		File file = new File("current/" + folder + "/" + marketPlace + ".txt");

		Scanner myReader;
		ArrayList <String> lines = new ArrayList <String> ();
		try {
			
			myReader = new Scanner(file);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				lines.add(myReader.nextLine());
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File " + file.getName() + " coudn't be found.");
		}
		return lines;
	}
	
	private ArrayList<Order> getOrders(ArrayList<String> lines, String marketPlace) throws Exception {
		ArrayList<Order> orders = new ArrayList<Order>();
		
		Order order;
		
		for (String line : lines) {
			order = new Order();
			
			String[] fields = line.split("\\t");
			
			boolean IsFirstItem = orders
					.stream()
					.filter(myorder -> myorder.getOrderId().equals(fields[0]))
					.findAny().isEmpty();
			
			if (IsFirstItem) {
				
				order.setOrderId(fields[0]);
				
				order.setPurchaseDate(fields[2]);
				
				order.setBuyerEmail(fields[7]);
				
				order.setBuyerName(fields[8]);
				
				order.setBuyerPhoneNumber(fields[9]);
				
				//order.setSku(fields[10]);
				
				//order.setProductName(fields[11]);
				
				order.setQuantityPurchased(Integer.parseInt(fields[12]));
				
				//order.setQuantityShipped(fields[13]);
				
				//order.setQuantityToShip(fields[14]);
				
				order.setRecipientName(fields[16]);
				
				order.setShipAddress1(fields[17]);
				
				order.setShipAddress2(fields[18]);
				
				order.setShipAddress3(fields[19]);
				
				order.setShipCity(fields[20]);
				
				order.setShipState(fields[21]);
				
				order.setShipPostalCode(fields[22]);
				
				order.setShipCountry(fields[23]);
				
				order.setBatch(String.format("%s%s", workDirectory, marketPlace));
				
				Product product = new Product();
				product.setName(fields[11]);
				product.setPruductId(fields[1]);
				product.setSku(fields[10]);
				product.setQuantity(Integer.valueOf(fields[12]));
				
				order.addItem(product);
				
				orders.add(order);
			}
			else {
				Product product = new Product();
				product.setName(fields[11]);
				product.setPruductId(fields[1]);
				product.setSku(fields[10]);
				product.setQuantity(Integer.valueOf(fields[12]));
					
				orders.stream()
						.filter(myOrder -> myOrder.getOrderId().equals(fields[0]))
						.findAny()
						.get().addItem(product);
				
			}
		}
		return orders;
	}
	
}
