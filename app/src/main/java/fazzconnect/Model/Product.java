package fazzconnect.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
	
	String sku;
	String assin;
	String pruductId;
	String name;
	String shortName;
	String dispach;
	int quantity;
	String customTarif;
	

	public String getShortName() {
		if (shortName == null) {
			return name;
		}
		else {
			return shortName;
		}
	}

	public String getNameWithQuantity() {
		return String.format("%s %s", this.quantity, this.getShortName());
		
	}
	
	public String getModel() {
		if (hasQtdOnShortName()) {
			return shortName.split(" ")[2];
		} else {
			return shortName.split(" ")[1];
		}
	}
	
	public boolean hasQtdOnShortName() {
		return shortName.split(" ").length == 3;
	}
	
	@Override
	public Object clone() {
		Product product = new Product();
	    try {
	    	product = (Product) super.clone();
	    } catch (CloneNotSupportedException e) {
	        
	    }
	    product.sku = this.sku;
		product.assin = this.assin;
		product.pruductId = this.pruductId;
		product.name = this.name;
		product.shortName = this.shortName;
		product.dispach = this.dispach;
		product.quantity = this.quantity;
		product.customTarif = this.customTarif;
		
		return product;
	}
}
