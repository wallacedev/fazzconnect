package Model.Importer;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
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
				if (convertLine(data) != null) {
					reportObjects.add(convertLine(data));
				}
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
		if (fields.length == 5) {
			ReportObject reportObject = new ReportObject();
			reportObject.setIdOrder(fields[0]);
			reportObject.setTrackNumber(fields[1]);
			reportObject.setCountry(Optional.ofNullable(fields[2]).orElse(""));
			reportObject.setDate(Optional.ofNullable(fields[3]).orElse(""));
			reportObject.setBatch(Optional.ofNullable(fields[4]).orElse(""));
			return reportObject;
		}
		else return null;
		
	}

}
