package com.project.ProjectPhase2.exceptions;

public class CustomerNotExistsException extends Exception {

	public CustomerNotExistsException() {
		super("Customer not exists in the customers table.");
	}
}
