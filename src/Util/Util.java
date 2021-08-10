/**
 * 
 */
package Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * @author dave
 *
 */
public abstract class Util {
	
	public static String getShortName(String sku) {
		Optional<String> shortName = Optional.empty();
		
		sku = sku.strip().replace(' ', '-');
		
		try (InputStream input = new FileInputStream("sku-shortname.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            shortName = Optional.ofNullable(prop.getProperty(sku));
            input.close();
        } catch (IOException ex) {
        	System.out.println("Exception in the function 'getShortName()'."); 
            ex.printStackTrace();
        }
		
		if (shortName.isEmpty()) {
			return null;
		}
		
		return shortName.get();
	}
	
	public static String getShortNameBlue(String sku) {
		Optional<String> shortName = Optional.empty();
		
		sku = sku.strip().replace(' ', '-');
		
		try (InputStream input = new FileInputStream("sku-shortname-blue.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            shortName = Optional.ofNullable(prop.getProperty(sku));
            input.close();
        } catch (IOException ex) {
        	System.out.println("Exception in the function 'getShortName()'."); 
            ex.printStackTrace();
        }
		
		if (shortName.isEmpty()) {
			return null;
		}
		
		return shortName.get();
	}
	
	public static String quantityToSplit(String sku) {
		Optional<String> quantity = Optional.empty();
		try (InputStream input = new FileInputStream("split.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            quantity = Optional.ofNullable(prop.getProperty(sku));
            input.close();
        } catch (IOException ex) {
        	System.out.println("Exception in the function 'quantityToSplit()'."); 
            ex.printStackTrace();
        }
		
		if (quantity.isEmpty()) {
			return null;
		}
		return quantity.get();
	}
	
	public static String quantityToSplitByProduct(String model) {
		Optional<String> quantity = Optional.empty();
		try (InputStream input = new FileInputStream("splitByModel.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            quantity = Optional.ofNullable(prop.getProperty(model));
            input.close();
        } catch (IOException ex) {
        	System.out.println("Exception in the function 'quantityToSplitByProduct()'."); 
            ex.printStackTrace();
        }
		
		if (quantity.isEmpty()) {
			return null;
		}
		return quantity.get();
	}
	
	
	public static String getShortCountry(String country) {
		Optional<String> shortCountry = Optional.empty();
		
		country = country.toUpperCase();
		country = country.strip().replace(' ', '-');
		
		try (InputStream input = new FileInputStream("countries.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            shortCountry = Optional.ofNullable(prop.getProperty(country));
            input.close();
        } catch (IOException ex) {
        	System.out.println("Exception in the function 'getShortCountry()'."); 
            ex.printStackTrace();
        }
		
		if (shortCountry.isEmpty()) {
			System.out.println("Country not converted:" + country);
			return null;
		}
		
		return shortCountry.get();
	}
}
