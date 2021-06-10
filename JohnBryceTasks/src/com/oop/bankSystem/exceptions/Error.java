package com.oop.bankSystem.exceptions;

public enum Error 
{
	WITHDRAW("%s: Client:id: %d name: %s balance: %.2f can't withdraw money."),
	DELETE("%s: Can not delete client: id: %d name: %s has debt of %.2f"),
	CLIENT_NOT_FOUND("%s: Client id: %d not found."),
	NO_CLIENTS_FOUND("%s: Not found any client. Please insert clients first.");
	
	private final String message;

	private Error(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
