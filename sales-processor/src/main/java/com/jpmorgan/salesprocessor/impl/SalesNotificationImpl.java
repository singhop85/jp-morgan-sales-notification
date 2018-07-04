package com.jpmorgan.salesprocessor.impl;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

import com.jpmorgan.salesprocessor.SalesNotification;
import com.jpmorgan.salesprocessor.adjustment.Adjustment;
import com.jpmorgan.salesprocessor.model.Message;
import com.jpmorgan.salesprocessor.model.Product;
import com.jpmorgan.salesprocessor.parser.impl.MessageParsers;
import com.jpmorgan.salesprocessor.reporting.Report;
import com.jpmorgan.salesprocessor.reporting.Repository;

public class SalesNotificationImpl implements SalesNotification 
{
	private Repository repository = new Repository();
	
	@Override
	public void acceptNotification(String msg) 
	{
		Message message = MessageParsers.getMessage(msg);
		if(message != null) 
		{
			repository.addMessage(message);
			if(message.isPriceAdjustementRequired()) 
			{
				BiConsumer<Product , BigDecimal> operation = Adjustment.getOperation(message.getAdjustment());
				Product product = repository.getProduct(message.getProduct().getType());
				message.getProduct().setPrice(product.getNewPrice());
				product.applyAdjustment(operation, message.getProduct().getAdjustment());
				message.getProduct().setQuantity(product.getQuantity());
				message.getProduct().setNewPrice(product.getNewPrice());
			}
		} 
		else 
		{
			System.err.println("invalid sales notice -> " + msg);
		}
		if(repository.getRepositorySize() % 10 == 0) 
		{
			Report.printReport(repository.getProducts());
		}
		if(repository.getRepositorySize() % 50 == 0) 
		{
			Thread thread = new Thread(() -> 
			{
				System.out.println("Application reached 50 messages and Application will pause till it print all adjustments.");
				Report.printAdjustment(repository.getAdjustmentMessages());
			});
			thread.start();
			try 
			{
				thread.join();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
