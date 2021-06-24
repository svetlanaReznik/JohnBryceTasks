package com.project.ProjectPhase2.exceptions;

public class CustomerAlreadyExistsWithSameEmailException extends Exception {

	public CustomerAlreadyExistsWithSameEmailException() {
		super("Customer already exists with same email!");
	}
	

}
