package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class ConectApp {

	private static String batch = "";
	private static int batchSequence = 0;
	private static String workDirectory = "";
	private static ArrayList<String> marketPlaces;
	
	public static void main(String[] args) {
		int option = 0;
		//TODO read it from properties file
		marketPlaces = new ArrayList<String>();
		marketPlaces.add("us");
		marketPlaces.add("uk");
		marketPlaces.add("ca");
		//String workDirectory = new String();
		do {
			if (workDirectory.equals("")) {
				System.out.println("******** FAZZ CONNECT *********");
				System.out.println("*******************************");
				setWorkDirectory();
			}
			
			System.out.println("******** FAZZ CONNECT *********");
			System.out.println("*******************************");
			System.out.println("");
			System.out.println("Chose an option below:");
			System.out.println("1 - Convert Amazon file to AnPOst File.");
			System.out.println("2 - Convert AnPost Repost to Amazon File.");
			System.out.println("3 - Change work directory.");
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
				convertAnPostReportToAmazonFile();
				break;
			case 3:
				setWorkDirectory();
			default:
				break;
			}
		}while(option!=0);
		

	}

	private static void setWorkDirectory() {
		System.out.println("Inform the WorkDirectory:");
		Scanner scanner = new Scanner(System.in);
		workDirectory = scanner.nextLine();
		File directory = new File(workDirectory);
		if(!directory.exists()) {
			directory.mkdir();
			for (String string : marketPlaces) {
				File marketFile = new File(workDirectory+"/"+string);
				marketFile.mkdir();
			}
		}
		
	}

	private static void convertAnPostReportToAmazonFile() {

		String userDirectory = new File("").getAbsolutePath();
		System.out.println(userDirectory);
		System.out.println("Converting File...");
		ArrayList<AnPostReportLine> anPostReportLineList = new ArrayList<AnPostReportLine>();
		try {
			File file = new File(workDirectory+"/reportAutoLink.csv");
			Scanner myReader = new Scanner(file);
			anPostReportLineList = new ArrayList<AnPostReportLine>();
			myReader.nextLine();
			myReader.nextLine();
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				anPostReportLineList.add(convertLineToAnPostReport(data));
				// System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		writeFazzToAmazontFile(anPostReportLineList);
		System.out.println("File convertion successful.");

	}

	private static void writeFazzToAmazontFile(ArrayList<AnPostReportLine> anPostReportLineList) {
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
			title.append("\n");

			HashSet<String> batches = new HashSet<String>();
			for (int i = 0; i < anPostReportLineList.size(); i++) {
				batches.add(anPostReportLineList.get(i).getBatch());
			}

			StringBuffer[] sbList = new StringBuffer[batches.size()];
			int i = 0;
			for (String batch : batches) {
				sbList[i] = new StringBuffer();
				for (AnPostReportLine anPostReportLine : anPostReportLineList) {
					if (anPostReportLine.getBatch().equals(batch)) {
						sbList[i].append(anPostReportLine.getIdOrder());
						sbList[i].append(SEPARATOR);
						sbList[i].append("");
						sbList[i].append(SEPARATOR);
						sbList[i].append("");
						sbList[i].append(SEPARATOR);
						sbList[i].append(anPostReportLine.getConvertedDate());
						sbList[i].append(SEPARATOR);
						sbList[i].append("Other");
						sbList[i].append(SEPARATOR);
						sbList[i].append(anPostReportLine.getCarrier());
						sbList[i].append(SEPARATOR);
						sbList[i].append(anPostReportLine.getTrackNumber());
						sbList[i].append(SEPARATOR);
						sbList[i].append("");
						sbList[i].append(SEPARATOR);
						sbList[i].append("");
						sbList[i].append("\n");
					}
				}
				FileWriter file = new FileWriter(workDirectory+"/"+batch + ".txt");
				file.write(title.toString());
				file.write(sbList[i].toString());
				file.close();
				i++;
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	private static AnPostReportLine convertLineToAnPostReport(String data) {
		String[] fields = data.split(",");
		AnPostReportLine anPostReportLine = new AnPostReportLine();
		anPostReportLine.setIdOrder(fields[0]);
		anPostReportLine.setTrackNumber(fields[1]);
		anPostReportLine.setCountry(fields[2]);
		anPostReportLine.setDate(fields[3]);
		anPostReportLine.setBatch(fields[4]);
		return anPostReportLine;
	}

	private static void convertAmazonToAnPostFile() {
		//String userDirectory = new File("").getAbsolutePath();
		//System.out.println(userDirectory);
		System.out.println("Converting Files...");
		
		for (String marketPlace : marketPlaces) {
			
			
			
			ArrayList<AmazonFile> amazonFileList = new ArrayList<AmazonFile>();
			
			File path = new File(workDirectory+"/"+marketPlace+"/");
			File[] files = path.listFiles();
			
			if (files.length>0) {
				Scanner scanner2 = new Scanner(System.in);
				System.out.println("Inform the batch number for "+marketPlace);
				batch = scanner2.nextLine();
				File file = new File(workDirectory+"/"+marketPlace+"/"+files[0].getName());
				Scanner myReader;
				try {
					myReader = new Scanner(file);
					amazonFileList = new ArrayList<AmazonFile>();
					myReader.nextLine();
					while (myReader.hasNextLine()) {
						String data = myReader.nextLine();
						amazonFileList.add(convertLineToAmazonFile(data));
						// System.out.println(data);
					}
					myReader.close();
					writeAnpostFile(amazonFileList, marketPlace);
					System.out.println("File "+files[0].getName()+" convertion successful.");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else {
				System.out.println("No files to convert on "+marketPlace);
			}
			
			
		}
		

	}

	private static void writeAnpostFile(ArrayList<AmazonFile> amazonFileList, String marketPlace) {

		try {
			FileWriter autoLinkFile = new FileWriter(workDirectory+"/"+marketPlace+"/"+marketPlace+batch+"_autoLink.txt");

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
		amazonFile.setProductName(Util.getShortName(amazonFile.getSku()));
		if (amazonFile.getProductName()!=null) {
			if (amazonFile.getProductName().equals("sku")) {
				amazonFile.setProductName("SKU "+amazonFile.getSku());
			}
		}else {
			amazonFile.setProductName(fields[11]);
		}
		//Try convert name with a short name
//		if (Util.getShortName(amazonFile.getSku())==null) {
//			String newShortName = insertSKU(amazonFile.getSku()); 
//			if (newShortName!=null) {
//				amazonFile.setProductName(newShortName);
//			}else {
//				amazonFile.setProductName(fields[11]);
//			}
//			
//		}else {
//			amazonFile.setProductName(Util.getShortName(amazonFile.getSku()));
//		}
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
		amazonFile.setProductName(amazonFile.getQuantityPurchased() + " x " +amazonFile.getProductName());
		return amazonFile;
	}
//
//	private static String insertSKU(String sku, String productName) {
//		System.out.println("No shortname found for the following product:");
//		System.out.println(productName);
//		System.out.println("Do you want to give a  shortname for it? (y/n)");
//		String option = null;
//		boolean validOption = false;
//		Scanner scanner = new Scanner(System.in);
//		while (validOption==false) {
//			option = scanner.nextLine();
//			option = option.toLowerCase();
//			if (option.equals("y") ||
//					option.equals("yes") ||
//					option.equals("n") ||
//					option.equals("no")) {
//				validOption = true;
//			}
//		}
//		if (option.equals("y") || option.equals("yes")) {
//			
//		}
//		
//	}

}
