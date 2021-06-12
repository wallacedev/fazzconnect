package importer;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Model.Importer.AnPostImporter;

public class AnPostImporterTest {
	
	private final String FILTERS_GERENAL_CUSTOM_TARIF = "8421.99.0";
	private final String FILTERS_UK_CUSTOM_TARIF = "842121";
	private final String TAB_SALT_CUSTOM_TARIFF = "8450.90.00";
	private final String P_AND_M_CUSTOM_TARIF = "3304.99.0000";
	
	private AnPostImporter importer;
	
	public AnPostImporterTest() {
		importer = new AnPostImporter();
	}
	
	@Test
	public void shoudReturnCorrectTariffCodeForPANDM () {
		var productName = "P&M Erase Balm Cleanser 100ml";
		var country = "US";
		
		var customTarrif = importer.getCustomTariff(productName, country);
		
		assertEquals(P_AND_M_CUSTOM_TARIF, customTarrif);
	}
	
	@Test
	public void shoudReturnCorrectTariffCodeForGeneralFilters () {
		var productName = "AL1509 Filter (4 pk)";
		var country = "US";
		
		var customTarrif = importer.getCustomTariff(productName, country);
		
		assertEquals(FILTERS_GERENAL_CUSTOM_TARIF, customTarrif);
	}
	
	@Test
	public void shoudReturnCorrectTariffCodeForUKFilters () {
		var productName = "AL1509 Filter (4 pk)";
		var country = "UK";
		
		var customTarrif = importer.getCustomTariff(productName, country);
		
		assertEquals(FILTERS_UK_CUSTOM_TARIF, customTarrif);
	}
	
	@Test
	public void shoudReturnCorrectTariffCodeForGBFilters () {
		var productName = "AL1509 Filter (4 pk)";
		var country = "GB";
		
		var customTarrif = importer.getCustomTariff(productName, country);
		
		assertEquals(FILTERS_UK_CUSTOM_TARIF, customTarrif);
	}
	
	@Test
	public void shoudReturnCorrectTariffCodeForSaltTabs () {
		var productName = "*Miele 60 + salt";
		var country = "GB";
		
		var customTarrif = importer.getCustomTariff(productName, country);
		
		assertEquals(TAB_SALT_CUSTOM_TARIFF, customTarrif);
	}
	
	
	
}
