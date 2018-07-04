package com.jpmorgan.salesprocessor;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

import com.jpmorgan.salesprocessor.impl.SalesNotificationImpl;

/**
 * Integration test for SalesAPI.
 */
public class SalesNotificationIntegrationTest 
{
	@Test
	public void parseMessage() 
	{
		SalesNotification salesNotification = new SalesNotificationImpl();
		// Read inputs from test file
		try 
		{
			String line;
			BufferedReader inputFile = new BufferedReader(new FileReader("src/test/resources/sales-input.txt"));
			while ((line = inputFile.readLine()) != null) 
			{
				// process message for each sale notification
				salesNotification.acceptNotification(line);
			}
		} 
		catch (java.io.IOException e)
		{
			e.printStackTrace();
		}
	}
}