package service;

import Model.Converter.AmazonUploadConverter;

public class AmazonService {
	
	private final AmazonUploadConverter amazonUploadConverter;
	
	private final String workDirectory;
	
	
	public AmazonService(String workDirectory) {
		this.workDirectory = workDirectory;
		this.amazonUploadConverter = new AmazonUploadConverter();
	}

	public void ConvertReportToAmazonUploadFile() {
		amazonUploadConverter.convert(workDirectory);
	}
}
