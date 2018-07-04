package com.jpmorgan.salesprocessor.reporting;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.jpmorgan.salesprocessor.model.Message;
import com.jpmorgan.salesprocessor.model.Product;

public class Report {
	public static void printReport(Collection<Product> products) 
	{
		System.out.println("10 sales appended to log");
		System.out.println("*************** Log Report *****************");
		System.out.println("|Product           |Quantity   |Value      |");
		products.forEach(System.out::println);
		System.out.println("-------------------------------------------");
		System.out.println(String.format("|%-30s|%-11.2f|", "Total Sales", products.stream().collect(Collectors.summingDouble(p -> p.getNewPrice().doubleValue()))));
		System.out.println("-------------------------------------------");
		System.out.println("End\n\n");
	}
	
	public static void printAdjustment(List<Message> message) 
	{
		System.out.println("The following are the adjustment records made;\n");
		// Display all the adjustment reports so far recorded.
		message.forEach(System.out::println);
		System.out.println("\n\n");
	}
	
}
