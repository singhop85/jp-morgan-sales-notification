package com.jpmorgan.salesprocessor.reporting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jpmorgan.salesprocessor.model.Message;
import com.jpmorgan.salesprocessor.model.Product;
import com.jpmorgan.salesprocessor.parser.impl.MessageParsers;

public class Repository 
{
	private Map<String, Product> productRepository = new HashMap<String, Product>();
	private List<Message> messageList = new ArrayList<Message>();
	
	public Product getProduct(String type)
	{
		return productRepository.get(type);
	}
	public int getRepositorySize() 
	{
		return messageList.size();
	}
	
	public List getAdjustmentMessages()
	{
		return messageList.stream().filter((msg) -> msg.isPriceAdjustementRequired()).collect(Collectors.toList());
	}
	public Collection<Product> getProducts() 
	{
		return Collections.unmodifiableCollection(productRepository.values());
	}
	public void addMessage(Message message)
	{
		if(message != null)
		{
			messageList.add(message);
			if(!message.isPriceAdjustementRequired()) 
			{
				if(message.getProduct() != null && productRepository.containsKey(message.getProduct().getType())) 
				{
					Product product = productRepository.get(message.getProduct().getType());
					product.setQuantity(product.getQuantity() + message.getProduct().getQuantity());
					product.setType(message.getProduct().getType());
					product.setNewPrice(MessageParsers.getPrecession(product.getNewPrice().doubleValue() + message.getProduct().getQuantity() * message.getProduct().getPrice().doubleValue()));
				} 
				else 
				{
					Product product = new Product();
					product.setQuantity(message.getProduct().getQuantity());
					product.setType(message.getProduct().getType());
					product.setNewPrice(MessageParsers.getPrecession( message.getProduct().getQuantity() * message.getProduct().getPrice().doubleValue()));
					productRepository.put(message.getProduct().getType(), product);
				}
			}
		}
	}
}