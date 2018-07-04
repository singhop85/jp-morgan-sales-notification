package com.jpmorgan.salesprocessor.adjustment;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

import com.jpmorgan.salesprocessor.model.Product;
import com.jpmorgan.salesprocessor.parser.impl.MessageParsers;

//types of Adjustments and adjustment algos

public enum Adjustment {
	ADD,SUBTRACT,MULTIPLY,UNKNOWN;
	
	public static BiConsumer<Product , BigDecimal> add = (Product product, BigDecimal adjustmentPrice) -> 
	{
		double newPrice = product.getNewPrice().doubleValue() + product.getQuantity() * adjustmentPrice.doubleValue();
		product.setNewPrice(MessageParsers.getPrecession(newPrice));
	};  
	
	public static BiConsumer<Product , BigDecimal> substract = (Product product, BigDecimal adjustmentPrice) -> 
	{
		double newPrice = product.getNewPrice().doubleValue() - product.getQuantity() * adjustmentPrice.doubleValue();
		product.setNewPrice(MessageParsers.getPrecession(newPrice));
	};  
	
	public static BiConsumer<Product , BigDecimal> multiply = (Product product, BigDecimal adjustmentPrice) -> 
	{
		double newPrice = 0.0;
		if(product.getNewPrice().doubleValue() != 0.0)
		{
			newPrice = product.getNewPrice().doubleValue() * product.getQuantity() * adjustmentPrice.doubleValue();
		}
		else
		{
			newPrice = product.getQuantity() * adjustmentPrice.doubleValue();
		}
		product.setNewPrice(MessageParsers.getPrecession(newPrice));
	};  
	
	public static BiConsumer<Product , BigDecimal> unknown = (Product product, BigDecimal adjustmentPrice) -> {};  

	public static BiConsumer<Product , BigDecimal> getOperation(Adjustment adjustment) 
	{
		if(ADD.equals(adjustment)) 
		{
			return add;
		}
		else if(SUBTRACT.equals(adjustment)) 
		{
			return substract;
		}
		else if(MULTIPLY.equals(adjustment)) 
		{
			return multiply;
		}
		else 
		{
			return unknown;
		}
	}
}