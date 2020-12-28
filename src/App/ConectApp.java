package App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;

import Model.AmazonFile;
import Model.AnPostReportLine;
import Model.Order;
import Util.Util;
import service.AmazonService;
import service.EbayService;

public class ConectApp {

	private static String batch = "";
	private static int batchSequence = 0;
	private static String workDirectory = "";
	private static ArrayList<String> marketPlaces;
	private static ArrayList<String> foldersToCreate;
	
	public static void main(String[] args) {
		int option = 0;
		//TODO read it from properties file
		marketPlaces = new ArrayList<String>();
		marketPlaces.add("us");
		marketPlaces.add("uk");
		marketPlaces.add("ca");
		marketPlaces.add("au");
		
		foldersToCreate = new ArrayList<String>();
		foldersToCreate.add("amazon");
		foldersToCreate.add("anpost");
		//String workDirectory = new String();
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
			//System.out.println("4 - Create UPS File.");
			//System.out.println("5 - Convert Ebay.");
			System.out.println("6 - Sort File.");
			System.out.println("0 - Exit.");
			
			Scanner scanner = new Scanner(System.in);
			option = scanner.nextInt();
			
	
			switch (option) {
			case 1:
				convertAmazonToAnPostFile();
				System.out.println("Press Enter to finish.");
				Scanner scanner2 = new Scanner(System.in);
				scanner2.nextLine();
				break;
			case 2:
				AmazonService amazomService = new AmazonService(workDirectory);
				amazomService.ConvertReportToAmazonUploadFile();
				break;
			case 3:
				setWorkDirectory();
			case 4:
				createUPSFile();
			case 5:
				convertEbay();
			case 6:
				sortPy();
			default:
				break;
			}
		}while(option!=0);
		

	}

	private static void sortPy() {
		
		
	}

	private static void convertEbay() {
		// TODO Auto-generated method stub
		EbayService ebayService = new EbayService(workDirectory);
		ArrayList <Order> ebayOrders = ebayService.importOrdersFromFileToMemory();
		ebayService.createAnpostFile(ebayOrders, "eb");
		
	}

	private static void createUPSFile() {
		// TODO Auto-generated method stub
		
	}

	private static void setWorkDirectory() {
		System.out.println("Inform the WorkDirectory:");
		Scanner scanner = new Scanner(System.in);
		workDirectory = scanner.nextLine();
		File directory = new File(workDirectory);
		if(!directory.exists()) {
			directory.mkdir();
			for (String string : foldersToCreate) {
				File marketFile = new File(workDirectory+"/"+string);
				marketFile.mkdir();
			}
		}
		
	}

	private static void convertAmazonToAnPostFile() {
		//String userDirectory = new File("").getAbsolutePath();
		//System.out.println(userDirectory);
		System.out.println("Converting Files...");
		
		System.out.println("Informe the file name or all:");
		
		Scanner scanner = new Scanner(System.in);
		String marketPlaceOption = scanner.nextLine();
		
		if (marketPlaceOption.equals("all")) {
			//ler todos
			convertAllMArketsToAnpost();
			
		}else if (marketPlaces.contains(marketPlaceOption)) {
			// ler apenas opcao inserida
			convertMarketToAmpost(marketPlaceOption);
		}else {
			// opcao invalida
			System.out.println("Invalid option.");
		}
		
	}

	private static void convertMarketToAmpost(String marketPlaceOption) {

		File file = new File(workDirectory + "/" + "amazon" + "/" + marketPlaceOption + ".txt");

		ArrayList<AmazonFile> amazonFileList = new ArrayList<AmazonFile>();

		Scanner scanner2 = new Scanner(System.in);
		batch = workDirectory + file.getName().replace(".txt", "");
		System.out.println("Inform the complement batch number for " + file.getName());

		String batchComplement = scanner2.nextLine().toUpperCase();

		if (!batchComplement.equals("")) {
			batch += batchComplement;
		}

		batch = batch.toUpperCase();
		
		Scanner myReader;
		try {
			myReader = new Scanner(file);
			amazonFileList = new ArrayList<AmazonFile>();
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				AmazonFile amazonFile = convertLineToAmazonFile(data);
				ArrayList<AmazonFile> amazonFileListVerified = verifications(amazonFile);
				if (amazonFileListVerified != null) {
					for (AmazonFile amazonFile2 : amazonFileListVerified) {
						amazonFileList.add(amazonFile2);
					}
				}
				// System.out.println(data);
			}
			myReader.close();
			writeAnpostFile(amazonFileList, file.getName().replace(".txt", ""));
			System.out.println("File " + file.getName() + " convertion successful.");
		} catch (FileNotFoundException e) {
			System.out.println("File " + file.getName() + " coudn't be converted.");
		}

	}

	private static void convertAllMArketsToAnpost() {

		File path = new File(workDirectory + "/" + "amazon" + "/");
		File[] files = path.listFiles();

		for (File file : files) {

			ArrayList<AmazonFile> amazonFileList = new ArrayList<AmazonFile>();

			Scanner scanner2 = new Scanner(System.in);
			batch = workDirectory+file.getName().replace(".txt", "");
			System.out.println("Inform the complement batch number for " + file.getName());
			
			String batchComplement = scanner2.nextLine();
			
			if (!batchComplement.equals("")){
				batch += batchComplement;
			}

			Scanner myReader;
			try {
				myReader = new Scanner(file);
				amazonFileList = new ArrayList<AmazonFile>();
				myReader.nextLine();
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					AmazonFile amazonFile = convertLineToAmazonFile(data);
					ArrayList<AmazonFile> amazonFileListVerified = verifications(amazonFile);
					if (amazonFileListVerified != null) {
						for (AmazonFile amazonFile2 : amazonFileListVerified) {
							amazonFileList.add(amazonFile2);
						}
					}
					// System.out.println(data);
				}
				myReader.close();
				writeAnpostFile(amazonFileList, file.getName().replace(".txt", ""));
				System.out.println("File " + file.getName() + " convertion successful.");
			} catch (FileNotFoundException e) {
				System.out.println("File " + file.getName() + " coudn't be converted.");
			}
		}
	}

	private static void writeAnpostFile(ArrayList<AmazonFile> amazonFileList, String marketPlace) {

		try {
			FileWriter autoLinkFile = new FileWriter(workDirectory+"/anpost/"+batch+"_autoLink.txt");

			String SEPARATOR = "|";

			for (int i = 0; i < amazonFileList.size(); i++) {

				// write first line A Address
				StringBuffer sb = new StringBuffer();

				// INDICATOR
				sb.append("A");
				sb.append(SEPARATOR);

				// INVOICE_bach
				sb.append(batch);
				//sb.append(amazonFileList.get(i).getOrderId());
				sb.append(SEPARATOR);

				// CONSIGNEE_idOrder
				//sb.append(amazonFileList.get(i).getRecipientName());
				sb.append(amazonFileList.get(i).getOrderId());
				sb.append(SEPARATOR);

				// CONSIGNEE_RECIPEINT_NAME
				sb.append(amazonFileList.get(i).getRecipientName());
				//sb.append(amazonFileList.get(i).getBuyerName());
				sb.append(SEPARATOR);

				// CONSIGNEE_COMPANY_NAME
				sb.append("");
				sb.append(SEPARATOR);

				// CONSIGNEE_ADDR1
				sb.append(amazonFileList.get(i).getShipAddress1());
				sb.append(SEPARATOR);

				// CONSIGNEE_ADDR2
				sb.append(amazonFileList.get(i).getShipAddress2());
				sb.append(SEPARATOR);

				// CONSIGNEE_ADDR4
				sb.append(amazonFileList.get(i).getShipCity());
				sb.append(SEPARATOR);

				// CONSIGNEE_ADDR5
				sb.append(amazonFileList.get(i).getShipState());
				sb.append(SEPARATOR);

				// CONSIGNEE_POSTCODE
				sb.append(amazonFileList.get(i).getShipPostalCode());
				sb.append(SEPARATOR);

				// CUSTOMER_COUNTRY_CODE
				sb.append(amazonFileList.get(i).getShipCountry());
				sb.append(SEPARATOR);

				// EMAIL_ADDRESS
				sb.append(amazonFileList.get(i).getBuyerEmail());
				sb.append(SEPARATOR);

				// CONSIGNEE_TELEPHONE_NUMBER
				sb.append(amazonFileList.get(i).getBuyerPhoneNumber());
				sb.append(SEPARATOR);

				// CONSIGNEE_MOBILE_NUMBER
				sb.append("");
				sb.append(SEPARATOR);

				// WEIGHT
				sb.append("1");
				sb.append(SEPARATOR);
				sb.append("\n");

				autoLinkFile.write(sb.toString());

				// Write other lines C
				sb = new StringBuffer();
				// INDICATOR
				sb.append("C");
				sb.append(SEPARATOR);

				// INVOICE_WORKSORDER_NO
				sb.append(amazonFileList.get(i).getOrderId());
				sb.append(SEPARATOR);

				// CONTENTS_PART_NO
				sb.append(amazonFileList.get(i).getOrderItemId());
				sb.append(SEPARATOR);

				// CONTENTS_ITEM_DESCRIPTION
				sb.append(amazonFileList.get(i).getProductName());
				sb.append(SEPARATOR);

				// CONTENTS_NO_UNITS
				sb.append(amazonFileList.get(i).getQuantityToShip());
				sb.append(SEPARATOR);

				// CONTENTS_WEIGHT
				sb.append("1");
				sb.append(SEPARATOR);

				// CONTENTS_ITEM_VALUE
				sb.append(amazonFileList.get(i).getPriceDesignation());
				sb.append(SEPARATOR);

				// CONTENTS_CUSTOMS_TARIFF
				sb.append("");
				sb.append(SEPARATOR);

				// CONTENTS_COUNTRY_ORIGIN
				sb.append(amazonFileList.get(i).getShipCountry());
				sb.append(SEPARATOR);

				// CONTENTS_CURRENCY
				sb.append("");
				sb.append(SEPARATOR);
				sb.append(SEPARATOR);
				sb.append(SEPARATOR);
				sb.append(SEPARATOR);
				sb.append(SEPARATOR);
				sb.append(SEPARATOR);
				sb.append("\n");

				autoLinkFile.write(sb.toString());
			}
			autoLinkFile.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	private static AmazonFile convertLineToAmazonFile(String data) {
		String[] fields = data.split("\\t");
		AmazonFile amazonFile = new AmazonFile();
		amazonFile.setOrderId(fields[0]);
		amazonFile.setOrderItemId(fields[1]);
		amazonFile.setPurchaseDate(fields[2]);
		amazonFile.setPaymentsDate(fields[3]);
		amazonFile.setReportingDate(fields[4]);
		amazonFile.setPromiseDate(fields[5]);
		amazonFile.setDaysPastPromise(fields[6]);
		amazonFile.setBuyerEmail(fields[7]);
		amazonFile.setBuyerName(fields[8]);
		amazonFile.setBuyerPhoneNumber(fields[9]);
		amazonFile.setSku(fields[10]);
		Optional<String> newName = Optional.ofNullable(Util.getShortName(amazonFile.getSku()));
		if (newName.isPresent()) {
			amazonFile.setProductName(newName.get());
		} else {
			amazonFile.setProductName(fields[11]);
		}
		
		amazonFile.setQuantityPurchased(fields[12]);
		amazonFile.setQuantityShipped(fields[13]);
		amazonFile.setQuantityToShip(fields[14]);
		amazonFile.setShipServiceLevel(fields[15]);
		amazonFile.setRecipientName(fields[16]);
		amazonFile.setShipAddress1(fields[17]);
		amazonFile.setShipAddress2(fields[18]);
		amazonFile.setShipAddress3(fields[19]);
		amazonFile.setShipCity(fields[20]);
		amazonFile.setShipState(fields[21]);
		amazonFile.setShipPostalCode(fields[22]);
		amazonFile.setShipCountry(fields[23]);
		//amazonFile.setProductName(amazonFile.getQuantityPurchased() + " x " +amazonFile.getProductName());
		return amazonFile;
	}

	private static ArrayList<AmazonFile> verifications(AmazonFile amazonFile) {
		
		ArrayList<AmazonFile> amazonFileListVerified = new ArrayList<AmazonFile>();  
		
		//delete registry
		//split registry
		amazonFileListVerified = splitOrders(amazonFile);
		return amazonFileListVerified;
	}
	
	private static ArrayList<AmazonFile> splitOrders(AmazonFile amazonFile) {
		String split = Util.quantityToSplit(amazonFile.getSku());
		ArrayList<AmazonFile> amazonFileList = new ArrayList<AmazonFile>(); 
		if(split!=null) {
			int quantity = Integer.parseInt(split);
			if (Integer.parseInt(amazonFile.getQuantityPurchased())>quantity) {
				int quantityToSplit = Integer.parseInt(amazonFile.getQuantityPurchased());
				if(quantity==0) {
					quantityToSplit = quantityToSplit*2;
				}
				amazonFile.setQuantityPurchased("1");
				amazonFile.setQuantityToShip("1");
				String productName = amazonFile.getProductName();
				for (int i = 0; i < quantityToSplit; i++) {
					AmazonFile amazonFileTemp = (AmazonFile) amazonFile.clone();
					amazonFileTemp.setProductName(i+1 +"/"+ quantityToSplit +  " - " +productName);
					amazonFileTemp.setOrderItemId(amazonFileTemp.getOrderId()+i);
					amazonFileList.add(amazonFileTemp);
				}
			}
			else {
				amazonFile.setProductName(amazonFile.getQuantityPurchased() + " x " +amazonFile.getProductName());
				amazonFileList.add(amazonFile);
			}
		}else {
			amazonFile.setProductName(amazonFile.getQuantityPurchased() + " x " +amazonFile.getProductName());
			amazonFileList.add(amazonFile);
		}
		return amazonFileList;
	}
	

}
