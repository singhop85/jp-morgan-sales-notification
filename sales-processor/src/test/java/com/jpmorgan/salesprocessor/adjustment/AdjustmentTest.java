package com.jpmorgan.salesprocessor.adjustment;

import org.junit.Assert;
import org.junit.Test;

import com.jpmorgan.salesprocessor.model.Product;

import util.TestData;

public class AdjustmentTest 
{
	@Test
	public void add()
	{
		Product product = TestData.getProperty("apple",null , 10);
		Adjustment.getOperation(Adjustment.ADD).accept(product,TestData.getPrecession(.50));
		Assert.assertEquals(TestData.getPrecession(5.0), product.getNewPrice());
	}
	@Test
	public void substract()
	{
		Product product = TestData.getProperty("apple",null , 10);
		Adjustment.getOperation(Adjustment.SUBTRACT).accept(product,TestData.getPrecession(.50));
		Assert.assertEquals(TestData.getPrecession(-5.0), product.getNewPrice());
	}
	@Test
	public void multiply()
	{
		Product product = TestData.getProperty("apple",null , 10);
		Adjustment.getOperation(Adjustment.MULTIPLY).accept(product,TestData.getPrecession(.50));
		Assert.assertEquals(TestData.getPrecession(5.0), product.getNewPrice());
	}

	
	
}
