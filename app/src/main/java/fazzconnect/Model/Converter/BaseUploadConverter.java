package fazzconnect.Model.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fazzconnect.Model.ReportObject;

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
	
	default List<String> getTrackNumbersList (String idOrder, ArrayList<ReportObject> reportObjects) throws Exception {
		return Optional.ofNullable(
				reportObjects
				 .stream()
				 .filter(reportObject -> reportObject.getIdOrder().equals(idOrder))
				 .map(reportObject ->  reportObject.getTrackNumber())
				 .collect(Collectors.toList()))	
		.orElseThrow(() -> new Exception("Track number not found"));

	}

}
