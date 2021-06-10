package com.oop.bankSystem.exceptions;

import java.util.Date;

import com.oop.bankSystem.beans.Client;
import com.oop.bankSystem.util.DataUtils;

public class BankSystemException extends Exception {
	
	private static String message;

	
	public BankSystemException(Error errType) 
	{
		super(String.format(Error.NO_CLIENTS_FOUND.getMessage(), DataUtils.dateFormat.format(new Date())));
	}
	
	public BankSystemException() 
	{
		super(String.format(Error.NO_CLIENTS_FOUND.getMessage(), DataUtils.dateFormat.format(new Date())));
	}
	
	public BankSystemException(Error errType, int clientId) 
	{
		super(String.format(Error.CLIENT_NOT_FOUND.getMessage(), DataUtils.dateFormat.format(new Date()), clientId));
	}
	
	public BankSystemException(Error errType, Client client) 
	{
		super(generateMessage(errType, client));
	}
		
	private static String generateMessage(Error errType, Client client) 
	{
		switch(errType) 
		{
			case WITHDRAW: 
				 message = String.format(Error.WITHDRAW.getMessage(), DataUtils.dateFormat.format(new Date()), 
						   client.getId(),
			   			   client.getName(), 
			   			   client.getAccount().getBalance()); 
			   			   break;
	     				  
		 	case DELETE:
				 message = String.format(Error.DELETE.getMessage(), DataUtils.dateFormat.format(new Date()), 
						   client.getId(),
						   client.getName(), 
						   client.getAccount().getBalance());
						   break;
		}
		return message;
	}
}
