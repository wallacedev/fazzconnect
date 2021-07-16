package Model.Converter;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import Model.ReportObject;

public interface BaseUploadConverter {
	
	default String getTrackNumbers (String idOrder, ArrayList<ReportObject> reportObjects) throws Exception {
		var results =  Optional.ofNullable(
				reportObjects
				 .stream()
				 .filter(reportObject -> reportObject.getIdOrder().equals(idOrder))
				 .collect(Collectors.toList()))	
		.orElseThrow(() -> new Exception("Track number not found"));

		String trackNumbers = "";
		for (ReportObject result : results) {
			trackNumbers += result.getTrackNumber() + " ";
		}
		return trackNumbers;
	}	

}
