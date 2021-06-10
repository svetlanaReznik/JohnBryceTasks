package com.project.ProjectPhase2.facade;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.ProjectPhase2.db.CompanyRepository;
import com.project.ProjectPhase2.db.CouponRepository;
import com.project.ProjectPhase2.db.CustomerRepository;
import com.project.ProjectPhase2.exceptions.LoginFailedException;

public abstract class ClientFacade 
{
	@Autowired
	protected CouponRepository couponDB;

	@Autowired
	protected CompanyRepository companyDB;
	
	@Autowired
	protected CustomerRepository customerDB;
	
	public abstract int login (String email, String password) throws SQLException, LoginFailedException;
}
