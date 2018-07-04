package com.jpmorgan.salesprocessor.parser.impl;

import org.junit.Assert;
import org.junit.Test;

import com.jpmorgan.salesprocessor.adjustment.Adjustment;
import com.jpmorgan.salesprocessor.model.Message;

import util.TestData;

public class MessageParsersTest 
{
	@Test
	public void getMessage1()
	{
		String msg = "apple at 10p";
		Message message = MessageParsers.getMessage(msg);
		Assert.assertEquals(1, message.getProduct().getQuantity());
		Assert.assertEquals(TestData.getPrecession(0.10), message.getProduct().getPrice());
	}
	@Test
	public void getMessage2()
	{
		String msg = "20 sales of apples at 10p each";
		Message message = MessageParsers.getMessage(msg);
		Assert.assertEquals(20, message.getProduct().getQuantity());
		Assert.assertEquals(TestData.getPrecession(0.10), message.getProduct().getPrice());
	}
	@Test
	public void getMessage3()
	{
		String msg = "Add 20p apples";
		Message message = MessageParsers.getMessage(msg);
		Assert.assertEquals(TestData.getPrecession(0.20), message.getProduct().getAdjustment());
		Assert.assertEquals(Adjustment.ADD, message.getAdjustment());
	}
	@Test
	public void parseType()
	{
		String msg = "apple";
		String message = MessageParsers.parseType(msg);
		Assert.assertEquals("apples", message);
	}
	@Test
	public void parsePrice()
	{
		String msg = "50p";
		double price = MessageParsers.parsePrice(msg);
		Assert.assertEquals(TestData.getPrecession(.5), TestData.getPrecession(price));
	}
	
}
