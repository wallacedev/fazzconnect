package Model.Converter;

import java.util.ArrayList;
import java.util.Optional;

import Model.ReportObject;

public interface BaseUploadConverter {
	
	default String getTrackNumber (String idOrder, ArrayList<ReportObject> reportObjects) throws Exception {
		return Optional.ofNullable(reportObjects
					.stream()
					.filter(trackNumber -> trackNumber.getIdOrder().equals(idOrder))
					.findAny()
					.get()
					.getTrackNumber())
							.orElseThrow(() -> new Exception("Track number not found"));
	}
	
	default String getDate (String idOrder, ArrayList<ReportObject> reportObjects) throws Exception {
		return Optional.ofNullable(reportObjects
					.stream()
					.filter(date -> date.getIdOrder().equals(idOrder))
					.findAny()
					.get()
					.getDate())
							.orElseThrow(() -> new Exception("Date not found"));
	}	

}
