package Model.Importer;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import Model.ReportObject;

public class ReportImporter {

	private String workDirectory;
	
	public ReportImporter (String workDirectory) {
		this.workDirectory = workDirectory;
	}
	
	public ArrayList<ReportObject> getReportObjectsByBatch(String batch){
		ArrayList<ReportObject> reportObjects = getReportObjects();
		return (ArrayList<ReportObject>) reportObjects.stream()
				.filter(object -> object.getBatch().equals(batch))
				.collect(Collectors.toList());
	}
	
	public ArrayList<ReportObject> getAllReportObjects() {
		return getReportObjects();
	}
	
	private ArrayList<ReportObject> getReportObjects() {
		
		ArrayList<ReportObject> reportObjects = new ArrayList<ReportObject>();
		
		try {
			File file = new File(workDirectory+"/reportAutoLink.csv");
			Scanner myReader = new Scanner(file);
			reportObjects = new ArrayList<ReportObject>();
			myReader.nextLine();
			myReader.nextLine();
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				reportObjects.add(convertLine(data));
			}
			myReader.close();
			System.out.println("File convertion successful.");
		} catch (Exception e) {
			System.out.println("File not converted.");
		}
		return reportObjects;
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

}
