package Model;

public class Product {
	String sku;
	String assin;
	String pruductId;
	String name;
	String shortName;
	String defautCarrier;
	String quantity;
	
	public Product () {
		
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getAssin() {
		return assin;
	}

	public void setAssin(String assin) {
		this.assin = assin;
	}

	public String getPruductId() {
		return pruductId;
	}

	public void setPruductId(String pruductId) {
		this.pruductId = pruductId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		if (shortName == null) {
			return name;
		}
		else {
			return shortName;
		}
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDefautCarrier() {
		return defautCarrier;
	}

	public void setDefautCarrier(String defautCarrier) {
		this.defautCarrier = defautCarrier;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getNameWithQuantity() {
		return String.format("%s X %s", this.quantity, this.getShortName());
		
	}
	
	
	
}
