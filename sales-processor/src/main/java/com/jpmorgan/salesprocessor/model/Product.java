package com.jpmorgan.salesprocessor.model;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

import com.jpmorgan.salesprocessor.adjustment.Adjustment;

public class Product 
{
	String type;
	BigDecimal price;
	int quantity;
	BigDecimal adjustment;
	BigDecimal newPrice = new BigDecimal(0).setScale(8, BigDecimal.ROUND_HALF_UP);
	Adjustment lastAdjustment;
	
	public Product() 
	{
	}
	public Product(String type , BigDecimal price , int quantity)
	{
		this.type = type;
		this.price = price;
		this.quantity = quantity;
	}
	public BigDecimal getPrice() 
	{
		return price;
	}
	public int getQuantity() 
	{
		return quantity;
	}
	public String getType() 
	{
		return type;
	}
	public BigDecimal getAdjustment() 
	{
		return adjustment;
	}
	public BigDecimal getNewPrice() 
	{
		return newPrice;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	public void setNewPrice(BigDecimal newPrice) 
	{
		this.newPrice = newPrice;
	}
	public void setAdjustment(BigDecimal adjustment) 
	{
		this.adjustment = adjustment;
	}
	public void setLastAdjustment(Adjustment lastAdjustment) 
	{
		this.lastAdjustment = lastAdjustment;
	}
	public Adjustment getLastAdjustment() 
	{
		return lastAdjustment;
	}
	public void setPrice(BigDecimal price) 
	{
		this.price = price;
	}
	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}
	public void applyAdjustment(BiConsumer<Product, BigDecimal> operation, BigDecimal adjustmnet)
	{
		operation.accept(this,adjustmnet);
	}
	@Override
	public String toString() 
	{
		return String.format("|%-18s|%-11d|%-11.2f|", type, quantity,newPrice);
	}
}
