package util;

import java.math.BigDecimal;

import com.jpmorgan.salesprocessor.model.Product;

public class TestData 
{
	public static Product getProperty(String type , BigDecimal price , int quantity){
		Product product = new Product(type, price, quantity);
		return product;
	}
	public static BigDecimal getPrecession(double value) 
	{
		return new BigDecimal(value).setScale(8, BigDecimal.ROUND_HALF_UP);
	}
}
