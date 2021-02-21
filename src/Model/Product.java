package Model;

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
	String quantity;
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
}
