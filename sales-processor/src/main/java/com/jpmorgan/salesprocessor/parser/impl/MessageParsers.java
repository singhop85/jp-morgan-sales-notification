package com.jpmorgan.salesprocessor.parser.impl;

import java.math.BigDecimal;

import com.jpmorgan.salesprocessor.adjustment.Adjustment;
import com.jpmorgan.salesprocessor.model.Message;
import com.jpmorgan.salesprocessor.model.Product;
import com.jpmorgan.salesprocessor.parser.ParseMessage;

public class MessageParsers {

	public static int DEFAULT_PRECESSION = 8;
	
	public static Message getMessage(String msg) 
	{
		if (msg == null || msg.isEmpty()) 
		{
			System.out.println("Message is empty");
			return null;
		}
		String[] messageArray = msg.trim().split("\\s+");
		String firstWord = messageArray[0];
		
		if (firstWord.matches("Add|Subtract|Multiply")) 
		{
			return PARSE_PRODUCT_PRICE_ADJUSTMENT.parse(msg);
		} 
		else if (firstWord.matches("^\\d+")) 
		{
			return PARSE_PRODUCT_QUANTITY_PRICE.parse(msg);
		} 
		else if (messageArray.length == 3 && messageArray[1].contains("at")) 
		{
			return PARSE_PRODUCT_PRICE.parse(msg);
		} 
		else 
		{
			System.out.println("Wrong sales notice");
		}
		return null;
	}
	public static ParseMessage PARSE_PRODUCT_PRICE = (msg) -> 
	{
		if(msg != null && !msg.isEmpty()) 
		{
			String[] messageArray = msg.trim().split("\\s+");
			if (messageArray.length > 3 || messageArray.length < 3)
			{
				return null;
			}
			Message message = new Message();
			message.setMsg(msg);
			String type = parseType(messageArray[0]);
			double price = parsePrice(messageArray[2]);
			Product product = new Product(type,getPrecession(price), 1);
			message.setProduct(product);
			return message;
		}
		return null;
	}; 
	
	public static ParseMessage PARSE_PRODUCT_QUANTITY_PRICE = (msg) ->
	{
		if(msg != null && !msg.isEmpty()) 
		{
			String[] messageArray = msg.trim().split("\\s+");
			if (messageArray.length > 7 || messageArray.length < 7) 
			{
				return null;
			}
			Message message = new Message();
			message.setMsg(msg);
			String type = parseType(messageArray[3]);
			double price = parsePrice(messageArray[5]);
			int quantity = Integer.parseInt(messageArray[0]);
			Product product = new Product(type,getPrecession(price),quantity);
			message.setProduct(product);
			return message;
		}
		return null;
	}; 
	
	public static ParseMessage PARSE_PRODUCT_PRICE_ADJUSTMENT = (msg) -> 
	{
		if(msg != null && !msg.isEmpty()) 
		{
			String[] messageArray = msg.trim().split("\\s+");
			if (messageArray.length > 3 || messageArray.length < 3)
			{
				return null;
			}
			Message message = new Message();
			Product product = new Product();
			String type = parseType(messageArray[2]);
			product.setType(type);
			double price = parsePrice(messageArray[1]);
			product.setAdjustment(getPrecession(price));
			message.setProduct(product);
			message.setPriceAdjustementRequired(true);
			message.setMsg(msg);
			if("add".equalsIgnoreCase(messageArray[0]))
			{
				message.setAdjustment(Adjustment.ADD);
			}
			else if("subtract".equalsIgnoreCase(messageArray[0]))
			{
				message.setAdjustment(Adjustment.SUBTRACT);
			}
			else if("multiply".equalsIgnoreCase(messageArray[0]))
			{
				message.setAdjustment(Adjustment.MULTIPLY);
			}
			else{
				message.setAdjustment(Adjustment.UNKNOWN);
			}
			return message;
		}
		return null;
	};
	
	
	public static String parseType(String rawType) 
	{
		String parsedType = "";
		String typeWithoutLastChar = rawType.substring(0, rawType.length() - 1);
		// enum DEPREC
		if (rawType.endsWith("o")) 
		{
			parsedType = String.format("%soes", typeWithoutLastChar);
		} 
		else if (rawType.endsWith("y"))
		{
			parsedType = String.format("%sies", typeWithoutLastChar);
		} 
		else if (rawType.endsWith("h"))
		{
			parsedType = String.format("%shes", typeWithoutLastChar);
		} 
		else if (!rawType.endsWith("s")) 
		{
			parsedType = String.format("%ss", rawType);
		} 
		else 
		{
			parsedType = String.format("%s", rawType);
		}
		return parsedType.toLowerCase();
	}

	
	// Parse the price and get only the value
	// e.g "20p" will become 0.20
	public static double parsePrice(String rawPrice) 
	{
		double price = Double.parseDouble(rawPrice.replaceAll("Â£|p", ""));
		if (!rawPrice.contains("."))
		{
			price = Double.valueOf(Double.valueOf(price) / Double.valueOf("100"));
		}
		return price;
	}
	public static BigDecimal getPrecession(double value) 
	{
		return new BigDecimal(value).setScale(DEFAULT_PRECESSION, BigDecimal.ROUND_HALF_UP);
	}
}
