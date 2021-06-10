package com.project.ProjectPhase2.exceptions;

public class CompanyNotExistsException extends Exception {

	public CompanyNotExistsException() {
		super("Company not exists based on provided ID.");
	}
}
