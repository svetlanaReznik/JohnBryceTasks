package com.example.CouponProjectPhase3.facade;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.CouponProjectPhase3.db.CompanyRepository;
import com.example.CouponProjectPhase3.db.CouponRepository;
import com.example.CouponProjectPhase3.db.CustomerRepository;
import com.example.CouponProjectPhase3.exceptions.LoginFailedException;

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
