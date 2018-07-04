package com.jpmorgan.salesprocessor.parser;

import com.jpmorgan.salesprocessor.model.Message;

@FunctionalInterface
public interface ParseMessage 
{
	public Message parse(String msgString);
}
