package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import Model.Order;
import Model.Product;

public class OrderTest {
	
	@Test
	public void shouldCreateOrderCorrectly() {
		Order order = new Order();
		assertNotEquals(null, order);
		
	}
	
	@Test
	public void shouldGetBlueLabel() {
		Order order = new Order();
		Product item = new Product();
		item.setDispach("blue");
		order.addItem(item);
		
		var result = order.isBlueLabel();
		
		assertEquals(result, true);
	}
	
	@Test
	public void shouldNotGetBlueLabel() {
		Order order = new Order();
		Product item1 = new Product();
		order.addItem(item1);
		order.addItem(item1);
		
		var result = order.isBlueLabel();
		
		assertEquals(result, false);
	}

}
