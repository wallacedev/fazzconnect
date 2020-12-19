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

import Model.AnPostReportLine;

public class AmazonUploadConverter {
	
	private String workDirectory;
	
	public void convert(String workDirectory) {
		
		this.workDirectory = workDirectory;
		
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
				anPostReportLineList.add(convertLine(data));
			}
			myReader.close();
			writeFile(anPostReportLineList);
			System.out.println("File convertion successful.");
		} catch (Exception e) {
			System.out.println("File not converted.");
		}
		
	}
	
	private AnPostReportLine convertLine(String data) {
		String[] fields = data.split(",");
		AnPostReportLine anPostReportLine = new AnPostReportLine();
		anPostReportLine.setIdOrder(fields[0]);
		anPostReportLine.setTrackNumber(fields[1]);
		anPostReportLine.setCountry(fields[2]);
		anPostReportLine.setDate(fields[3]);
		anPostReportLine.setBatch(fields[4]);
		return anPostReportLine;
	}
	
	private void writeFile(ArrayList<AnPostReportLine> anPostReportLineList) {
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
				HashMap<String, AnPostReportLine> reportMap = new HashMap<String, AnPostReportLine>();
				for (AnPostReportLine anPostReportLine : anPostReportLineList) {
					if (anPostReportLine.getBatch().equals(batch)) {
						if (reportMap.get(anPostReportLine.getIdOrder()) != null) {
							AnPostReportLine temp = reportMap.get(anPostReportLine.getIdOrder());
							temp.setTrackNumber(temp.getTrackNumber()+" "+ anPostReportLine.getTrackNumber());
							reportMap.put(anPostReportLine.getIdOrder(), temp);
						}else {
							reportMap.put(anPostReportLine.getIdOrder(), anPostReportLine);
						}
					}
				}
				
				sbList[i] = new StringBuffer();
				Iterator<Entry<String, AnPostReportLine>> hmIterator = reportMap.entrySet().iterator(); 
				while(hmIterator.hasNext()) {
					Map.Entry<String, AnPostReportLine> mapElement = hmIterator.next();
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
