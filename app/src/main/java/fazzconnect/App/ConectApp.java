package fazzconnect.App;

import fazzconnect.Model.Order;
import fazzconnect.Model.ReportObject;
import fazzconnect.service.AmazonService;
import fazzconnect.service.EbayService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConectApp {

	private static String workDirectory = "";
	private static ArrayList<String> foldersToCreate;
	
	public static void main(String[] args) {
		
		foldersToCreate = new ArrayList<String>();
		foldersToCreate.add("amazon");
		foldersToCreate.add("ebay");
		foldersToCreate.add("anpost");
		foldersToCreate.add("dispatch");
		foldersToCreate.add("ebay-email");
		
		var option = 1;
		
		do {
			if (workDirectory.equals("")) {
				System.out.println("******** FAZZ CONNECT *********");
				System.out.println("*******************************");
				setWorkDirectory();
			}
			
			System.out.println("******** FAZZ CONNECT *********");
			System.out.println("*******************************");
			System.out.println("Work Directory:" + workDirectory);
			System.out.println("");
			System.out.println("Chose an option below:");
			System.out.println("1 - Convert Amazon file to AnPost File.");
			System.out.println("2 - Convert AnPost Repost to Amazon File.");
			System.out.println("3 - Change work directory.");
			System.out.println("4 - Convert Ebay.");
			System.out.println("5 - Convert AnPost Repost to eBay File.");
			System.out.println("6 - Close batch.");
			System.out.println("0 - Exit.");
						
			Scanner scanner = new Scanner(System.in);
			option = scanner.nextInt();
	
			switch (option) {
			case 1:
				try {
					convertAmazonToAnPostFile();
					System.out.println("Amazon Conversion successful.");
				} catch (Exception e) {
					System.out.println("Amazon conversion failed.");
					System.out.println(e.getMessage());
				}
				break;
				
			case 2:
				try { 
					createAllAmazonDispachFile();
					System.out.println("Amazon dispach file creation successful.");
				} catch (Exception e) {
					System.out.println("Amazon dispach file creation failed.");
					System.out.println(e.getMessage());
				}
				break;
				
			case 3:
				setWorkDirectory();
				break;
				
			case 4:
				try {
					convertEbay();
					System.out.println("Ebay Conversion successful.");
				} catch (Exception e) {
					System.out.println("Ebay conversion failed.");
					System.out.println(e.getMessage());
				}
				break;
				
			case 5:
				try {
					createEbayDispachFile();
					System.out.println("Ebay dispach file creation successful.");
				} catch (Exception e) {
					System.out.println("Ebay dispach file creation failed.");
					System.out.println(e.getMessage());
				}
				break;

			case 6:
				closeBatch();
				break;
				
			default:
				break;
			}
			
		} while(option!=0);
	}
	
	private static void createAllAmazonDispachFile() throws Exception {
		for (String marketPlace: getAmazonMarketPlaces()) {
			createAmazonDispachFile(marketPlace);
		}
	}
	
	private static void setWorkDirectory() {
		System.out.println("Inform the WorkDirectory:");
		Scanner scanner = new Scanner(System.in);
		workDirectory = scanner.nextLine();
		File directory = new File("current");
		if(!directory.exists()) {
			directory.mkdir();
			for (String string : foldersToCreate) {
				File marketFile = new File("current/"+string);
				marketFile.mkdir();
			}
		}else {
			System.out.println("The last batch needs to be finished");
		}
	}

	private static void closeBatch() {
		System.out.println("Closing batch");
		File directory = new File("current");
		if (directory.renameTo(new File(workDirectory))) {
			System.out.println("Batch closed successfully");
		} else {
			System.out.println("Failed to close batch");
		}
	}

	private static void createAmazonDispachFile(String marketPlace) throws Exception {
		
		if (marketPlaceExists(marketPlace)) {
			AmazonService amazomService = new AmazonService(workDirectory);
			
			ArrayList <Order> amazonOrders = amazomService.importOrdersFromFileToMemory(marketPlace);
			
			ArrayList <ReportObject> amazonTrackNumbers = amazomService.getTrackNumbersFromReport();
			
			amazomService.writeAmazonDispathFile(amazonOrders, amazonTrackNumbers);
		}
	}

	private static boolean marketPlaceExists(String marketPlace) {
		File file = new File(workDirectory+"/amazon/"+marketPlace+".txt");
		return file.exists();
	}

	private static void createEbayDispachFile() throws Exception {
		EbayService ebayService = new EbayService(workDirectory);
			
		ArrayList <Order> ebayOrders = ebayService.importOrdersFromFileToMemory();
		
		ArrayList <ReportObject> ebayTrackNumbers = ebayService.getTrackNumbersFromReport();
		
		ebayService.writeEbayDispathFile(ebayOrders, ebayTrackNumbers);
	}

	private static void convertEbay() throws Exception {
		EbayService ebayService = new EbayService(workDirectory);
		
		ebayService.importOrdersFromFileToMemory();
		
		System.out.println("Use new name format? (y/n)");
		Scanner scanner = new Scanner(System.in);
		var option = scanner.nextLine();
		
		if (option.toLowerCase().equals("y")) {
			ebayService.newProcessOrders();
		} else {
			ebayService.processOrders();
		}
		
		ebayService.createAnpostFile(ebayService.getImportedOrders(), "eb");
		
	}

	private static void convertAmazonToAnPostFile() throws Exception {
		System.out.println("Converting Files...");
		
		System.out.println("Informe the file name or all:");
		
		Scanner scanner = new Scanner(System.in);
		String marketPlaceOption = scanner.nextLine();
		
		if (marketPlaceOption.equals("all")) {
			convertAllAmazon();
			
		} else {
			convertAmazon(marketPlaceOption);
		}
		
	}
	
	private static void convertAmazon(String marketPlace) throws Exception  {
		AmazonService amazonService = new AmazonService(workDirectory);
		
		amazonService.importOrdersFromFileToMemory(marketPlace);
		
		amazonService.processOrders();
		
		amazonService.createAnpostFiles(amazonService.getImportedOrders(), marketPlace);
	}
	
	private static void convertAllAmazon() throws Exception {
		for (String marketPlace: getAmazonMarketPlaces()) {
			convertAmazon(marketPlace);
		}
		
	}
	
	private static List<String> getAmazonMarketPlaces() {
		File files = new File(String.format("%s/amazon/", workDirectory));

		List<String> markets = new ArrayList<String>();
		
		for (String fileName : files.list()) {
			markets.add(fileName.replace(".txt", ""));
		}
		
		return markets;		
	}
}
