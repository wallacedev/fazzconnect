package Model.Importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Order;

public class UPSImporter {

	public ArrayList<Order> getOrdersFromFile(String workDirectory) {
		File path = new File(workDirectory+"/ups/");
		File[] files = path.listFiles();
		ArrayList<Order> orders = new ArrayList<Order>();
		
		if (files.length>0) {
			
			File file = new File(workDirectory+"/ups/"+files[0].getName());
			Scanner myReader;
			try {
				myReader = new Scanner(file);
				myReader.nextLine();
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					//Order order = new Order(data);
					//orders.add(order);
				}
				myReader.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			System.out.println("No files to convert on UPS");
			orders = null;
		}
		return orders;
	}

}
