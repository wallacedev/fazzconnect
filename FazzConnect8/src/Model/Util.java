/**
 * 
 */
package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author dave
 *
 */
public abstract class Util {
	
	public static String getShortName(String sku) {
		String shortName="";
		try (InputStream input = new FileInputStream("sku-shortname.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            shortName = prop.getProperty(sku);
            input.close();
        } catch (IOException ex) {
        	System.out.println("Exception in the function 'getShortName()'."); 
            ex.printStackTrace();
        }
		return shortName;
	}
	
//	public static boolean insertNewShortName(String SKU, String shortName) {
//		
//		PropertiesConfiguration conf = new PropertiesConfiguration("sku-shortname.properties");
//		Properties prop = new Properties();
//        prop.setProperty(SKU, shortName);
//		conf.save();    
//		
//		try (OutputStream output = new FileOutputStream("sku-shortname.properties")) {
//            Properties prop = new Properties();
//            prop.setProperty(SKU, shortName);
//            prop.setProperty("db.user", "mkyong");
//
//            // save properties to project root folder
//            prop.store(output, null);
//
//            System.out.println(prop);
//
//        } catch (IOException io) {
//        	System.out.println("Exception in the function 'insertNewShortName()'."); 
//            io.printStackTrace();
//        }
//	}
	
	
}
