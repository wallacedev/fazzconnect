package Model.Converter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import Model.ReportObject;

public class AmazonUploadConverter {
	
	private String workDirectory;
	
	public void convert(String workDirectory) {
		
		this.workDirectory = workDirectory;
		
		System.out.println("Converting File...");
		
		ArrayList<ReportObject> anPostReportLineList = new ArrayList<ReportObject>();
		
		try {
			File file = new File(workDirectory+"/reportAutoLink.csv");
			Scanner myReader = new Scanner(file);
			anPostReportLineList = new ArrayList<ReportObject>();
			myReader.nextLine();
			myReader.nextLine();
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				anPostReportLineList.add(convertLine(data));
			}
			myReader.close();
			writeFile(anPostReportLineList);
			System.out.println("File convertion successful.");
		} catch (Exception e) {
			System.out.println("File not converted.");
		}
		
	}
	
	private ReportObject convertLine(String data) {
		String[] fields = data.split(",");
		ReportObject reportObject = new ReportObject();
		reportObject.setIdOrder(fields[0]);
		reportObject.setTrackNumber(fields[1]);
		reportObject.setCountry(fields[2]);
		reportObject.setDate(fields[3]);
		reportObject.setBatch(fields[4]);
		return reportObject;
	}
	
	private void writeFile(ArrayList<ReportObject> anPostReportLineList) {
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

			HashSet<String> batches = new HashSet<String>();
			for (int i = 0; i < anPostReportLineList.size(); i++) {
				batches.add(anPostReportLineList.get(i).getBatch());
			}

			StringBuffer[] sbList = new StringBuffer[batches.size()];
			
			int i = 0;
			for (String batch : batches) {
				HashMap<String, ReportObject> reportMap = new HashMap<String, ReportObject>();
				for (ReportObject anPostReportLine : anPostReportLineList) {
					if (anPostReportLine.getBatch().equals(batch)) {
						if (reportMap.get(anPostReportLine.getIdOrder()) != null) {
							ReportObject temp = reportMap.get(anPostReportLine.getIdOrder());
							temp.setTrackNumber(temp.getTrackNumber()+" "+ anPostReportLine.getTrackNumber());
							reportMap.put(anPostReportLine.getIdOrder(), temp);
						}else {
							reportMap.put(anPostReportLine.getIdOrder(), anPostReportLine);
						}
					}
				}
				
				sbList[i] = new StringBuffer();
				Iterator<Entry<String, ReportObject>> hmIterator = reportMap.entrySet().iterator(); 
				while(hmIterator.hasNext()) {
					Map.Entry<String, ReportObject> mapElement = hmIterator.next();
					sbList[i].append(mapElement.getValue().getIdOrder());
					sbList[i].append(SEPARATOR);
					sbList[i].append("");
					sbList[i].append(SEPARATOR);
					sbList[i].append("");
					sbList[i].append(SEPARATOR);
					sbList[i].append(mapElement.getValue().getConvertedDate());
					sbList[i].append(SEPARATOR);
					sbList[i].append("Other");
					sbList[i].append(SEPARATOR);
					sbList[i].append(mapElement.getValue().getCarrier());
					sbList[i].append(SEPARATOR);
					sbList[i].append(mapElement.getValue().getTrackNumber());
					sbList[i].append(SEPARATOR);
					sbList[i].append("");
					sbList[i].append(SEPARATOR);
					sbList[i].append("");
					sbList[i].append(SEPARATOR);
					sbList[i].append("FAZZ LTD");
					sbList[i].append(SEPARATOR);
					sbList[i].append("10C Boeing Road");
					sbList[i].append(SEPARATOR);
					sbList[i].append("Airways Industrial Estate");
					sbList[i].append(SEPARATOR);
					sbList[i].append("");
					sbList[i].append(SEPARATOR);
					sbList[i].append("Dublin 17");
					sbList[i].append(SEPARATOR);
					sbList[i].append("Dublin");
					sbList[i].append(SEPARATOR);
					sbList[i].append("Dublin");
					sbList[i].append(SEPARATOR);
					sbList[i].append("D17 E167");
					sbList[i].append(SEPARATOR);
					sbList[i].append("IE");
					sbList[i].append("\n");
					
				}
				
				FileWriter file = new FileWriter(workDirectory+"/"+batch + ".txt");
				file.write(title.toString());
				file.write(sbList[i].toString());
				file.close();
				i++;
			}
		} catch (Exception e) {
			System.out.println("File not Converted.");
		}

	}
}
