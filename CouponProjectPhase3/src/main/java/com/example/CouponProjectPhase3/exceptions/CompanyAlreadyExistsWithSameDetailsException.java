package com.project.ProjectPhase2.exceptions;

public class CompanyAlreadyExistsWithSameDetailsException extends Exception {

	public CompanyAlreadyExistsWithSameDetailsException() {
		super("Company already exists with same name or email!");
	}
}
