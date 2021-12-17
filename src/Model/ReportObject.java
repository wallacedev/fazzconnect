package Model;

import lombok.Data;

@Data
public class ReportObject {
	private String idOrder;
	private String trackNumber;
	private String country;
	private String date;
	private String batch;
	
	public ReportObject() {
		idOrder = "";
		trackNumber = "";
		country = "";
		date = "";
		batch = "";
	}

	public static String getCarrier(String country) {
		if (country.contentEquals("US") || country.contentEquals("CA") || country.contentEquals("IE")) {
			return ("AnPost / USPS");
		}else {
			return "Royal Mail";
		}
	}
	
	public static String getEbayCarrier() {
		return "AnPost";
	}


	public String getConvertedDate() {
		return date.substring(4, 6) + "-" + date.substring(6, 8)+ "-" + date.substring(0, 4);
	}	
}