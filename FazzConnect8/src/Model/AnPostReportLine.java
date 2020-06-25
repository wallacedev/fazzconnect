package Model;

public class AnPostReportLine {
	private String idOrder;
	private String trackNumber;
	private String country;
	private String date;
	private String batch;
	
	public AnPostReportLine() {
		idOrder = "";
		trackNumber = "";
		country = "";
		date = "";
		batch = "";
	}
	
	
	public String getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}
	public String getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}


	public String getCarrier() {
		if (country.contentEquals("US") || country.contentEquals("CA") || country.contentEquals("IE")) {
			return ("AnPost / USPS");
		}else {
			return "Royal Mail";
		}
	}


	public Object getConvertedDate() {
		return date.substring(4, 6) + "-" + date.substring(6, 8)+ "-" + date.substring(0, 4);
	}
	
	
	
}
