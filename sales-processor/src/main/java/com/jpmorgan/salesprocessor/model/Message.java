package com.jpmorgan.salesprocessor.model;

import com.jpmorgan.salesprocessor.adjustment.Adjustment;

public class Message 
{
	Product product;
	private Boolean priceAdjustementRequired = false;
	Adjustment adjustment; 
	String msg = "";
	
	public Adjustment getAdjustment() 
	{
		return adjustment;
	}
	public Product getProduct() 
	{
		return product;
	}
	public void setAdjustment(Adjustment adjustment) 
	{
		this.adjustment = adjustment;
	}
	public void setProduct(Product product)
	{
		this.product = product;
	}
	public void setPriceAdjustementRequired(Boolean priceAdjustementRequired)
	{
		this.priceAdjustementRequired = priceAdjustementRequired;
	}
	public Boolean isPriceAdjustementRequired()
	{
		return priceAdjustementRequired;
	}
	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	public String getMsg() 
	{
		return msg;
	}
	@Override
	public String toString() 
	{
		return "Performed "+adjustment + " " + product.getAdjustment().setScale(2) + " to " + product.getQuantity()+ " " 
		+ product.getType()  +" and price adjusted from " + product.getPrice().setScale(2) + " to " + product.getNewPrice().setScale(2); 
	}
}
