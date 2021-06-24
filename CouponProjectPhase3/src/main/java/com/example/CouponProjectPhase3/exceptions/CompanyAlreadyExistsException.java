package com.project.ProjectPhase2.exceptions;

public class CompanyAlreadyExistsException extends Exception {

	public CompanyAlreadyExistsException() {
		super("The company name was found in the system!");
	}
}
