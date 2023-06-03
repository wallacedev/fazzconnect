package fazzconnect.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class PropertiesReader {
	
	public static final String SHORTNAME_PROPERTIES_FILE = "sku-shortname.properties";
	public static final String SPLIT_PROPERTIES_FILE = "split.properties";
	
	public static String getShortName(String sku) {
		String shortName = readPropertie(SHORTNAME_PROPERTIES_FILE, sku);
		return shortName;
	}
	
	public static String quantityToSplit(String sku) {
		String qtyToSplit = readPropertie(SPLIT_PROPERTIES_FILE, sku);
		return qtyToSplit;
	}
		
	private static String readPropertie(String propertiesFilename, String sku) {
		String propertieValue = "";
		try (InputStream input = new FileInputStream(propertiesFilename)){
			Properties properties = new Properties();
			properties.load(input);
			propertieValue = properties.getProperty(sku);
	        input.close();
		} catch (IOException ex) {
        	System.out.println("File" + propertiesFilename + "couldn't be opened."); 
            ex.printStackTrace();
        }
		return propertieValue;
	}
}