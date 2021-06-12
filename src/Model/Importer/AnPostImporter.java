package Model.Importer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import Model.Order;
import Model.Product;

public class AnPostImporter {

	public void writeImportFile(ArrayList<Order> orders, String marketPlace, String workDirectory) {

		try {
			String batch = workDirectory+marketPlace;
			FileWriter autoLinkFile = new FileWriter(workDirectory+"/AnPost/"+marketPlace+batch+"_autoLink.txt");

			String SEPARATOR = "|";

			for (int i = 0; i < orders.size(); i++) {

				// write first line A Address
				StringBuffer sb = new StringBuffer();

				// INDICATOR
				sb.append("A");
				sb.append(SEPARATOR);

				// INVOICE_bach
				sb.append(batch);
				//sb.append(amazonFileList.get(i).getOrderId());
				sb.append(SEPARATOR);

				// CONSIGNEE_idOrder
				//sb.append(amazonFileList.get(i).getRecipientName());
				sb.append(orders.get(i).getOrderId());
				sb.append(SEPARATOR);

				// CONSIGNEE_RECIPEINT_NAME
				sb.append(orders.get(i).getRecipientName());
				sb.append(SEPARATOR);

				// CONSIGNEE_COMPANY_NAME
				sb.append("");
				sb.append(SEPARATOR);

				// CONSIGNEE_ADDR1
				sb.append(orders.get(i).getShipAddress1());
				sb.append(SEPARATOR);

				// CONSIGNEE_ADDR2
				sb.append(orders.get(i).getShipAddress2());
				sb.append(SEPARATOR);

				// CONSIGNEE_ADDR4
				sb.append(orders.get(i).getShipCity());
				sb.append(SEPARATOR);

				// CONSIGNEE_ADDR5
				sb.append(orders.get(i).getShipState());
				sb.append(SEPARATOR);

				// CONSIGNEE_POSTCODE
				sb.append(orders.get(i).getShipPostalCode());
				sb.append(SEPARATOR);

				// CUSTOMER_COUNTRY_CODE
				sb.append(orders.get(i).getShipCountry());
				sb.append(SEPARATOR);

				// EMAIL_ADDRESS
				sb.append(orders.get(i).getBuyerEmail());
				sb.append(SEPARATOR);

				// CONSIGNEE_TELEPHONE_NUMBER
				sb.append(orders.get(i).getBuyerPhoneNumber());
				sb.append(SEPARATOR);

				// CONSIGNEE_MOBILE_NUMBER
				sb.append("");
				sb.append(SEPARATOR);

				// WEIGHT
				sb.append("2");
				sb.append(SEPARATOR);
				

				autoLinkFile.write(sb.toString());

				// Write other lines C
				for (Product item : orders.get(i).getItens()) {
					
					sb = new StringBuffer();
					// INDICATOR
					sb.append("\n");
					sb.append("C");
					sb.append(SEPARATOR);
	
					// INVOICE_WORKSORDER_NO
					sb.append(orders.get(i).getOrderId());
					sb.append(SEPARATOR);
	
					// CONTENTS_PART_NO
					sb.append(item.getPruductId());
					sb.append(SEPARATOR);
	
					// CONTENTS_ITEM_DESCRIPTION
					sb.append(item.getNameWithQuantity());
					sb.append(SEPARATOR);
	
					// CONTENTS_NO_UNITS
					sb.append(item.getQuantity());
					sb.append(SEPARATOR);
	
					// CONTENTS_WEIGHT
					sb.append("2");
					sb.append(SEPARATOR);
	
					// CONTENTS_ITEM_VALUE
					sb.append(getItemValue(orders.get(i).getShipCountry()));
					sb.append(SEPARATOR);
	
					// CONTENTS_CUSTOMS_TARIFF
					sb.append(getCustomTariff(item.getName(), orders.get(i).getShipCountry()));
					sb.append(SEPARATOR);
	
					// CONTENTS_COUNTRY_ORIGIN
					sb.append(orders.get(i).getShipCountry());
					sb.append(SEPARATOR);
	
					// CONTENTS_CURRENCY
					sb.append("");
					sb.append(SEPARATOR);
					sb.append(SEPARATOR);
					sb.append(SEPARATOR);
					sb.append(SEPARATOR);
					sb.append(SEPARATOR);
					sb.append(SEPARATOR);
					sb.append("\n");
				}

				autoLinkFile.write(sb.toString());
			}
			autoLinkFile.close();
		} catch (IOException e) {
			System.out.println("AnPost File coudn't be wrote.");
			e.printStackTrace();
		}
	}
	
	private String getItemValue(String shipCountry) {
		return "10";
	}

	public String getCustomTariff(String productName, String country) {
		
		String FILTERS_GERENAL_CUSTOM_TARIF = "8421.99.0";
		String FILTERS_UK_CUSTOM_TARIF = "842121";
		String TAB_SALT_CUSTOM_TARIFF = "8450.90.00";
		String P_AND_M_CUSTOM_TARIF = "3304.99.0000";
		
		if (productName.toLowerCase().contains("filter")) {
			if (country.toLowerCase().equals("uk") 
				|| country.toLowerCase().equals("gb")) {
				
				return FILTERS_UK_CUSTOM_TARIF;
			}
			else return FILTERS_GERENAL_CUSTOM_TARIF;
		}
		
		if (productName.toLowerCase().contains("p&m")) {
			return P_AND_M_CUSTOM_TARIF;
		}
		
		if (productName.toLowerCase().contains("*miele")) {
			return TAB_SALT_CUSTOM_TARIFF;
		}
		
		return "";
	}
}
