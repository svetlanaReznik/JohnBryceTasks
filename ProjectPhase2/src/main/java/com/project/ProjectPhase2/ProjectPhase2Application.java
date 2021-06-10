package com.project.ProjectPhase2;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.project.ProjectPhase2.exceptions.CompanyAlreadyExistsWithSameDetailsException;
import com.project.ProjectPhase2.exceptions.CompanyNotExistsException;
import com.project.ProjectPhase2.exceptions.CouponAlreadyExistsException;
import com.project.ProjectPhase2.exceptions.CouponAlreadyExistsWithSameTitleException;
import com.project.ProjectPhase2.exceptions.CouponDateExpiredException;
import com.project.ProjectPhase2.exceptions.CouponNotAvailableException;
import com.project.ProjectPhase2.exceptions.CouponNotExistsException;
import com.project.ProjectPhase2.exceptions.CustomerAlreadyExistsWithSameEmailException;
import com.project.ProjectPhase2.exceptions.CustomerNotExistsException;
import com.project.ProjectPhase2.exceptions.LoginFailedException;
import com.project.ProjectPhase2.tests.Tests;

@SpringBootApplication
public class ProjectPhase2Application 
{
	public static void main(String[] args)
	{
		ConfigurableApplicationContext ctx = SpringApplication.run(ProjectPhase2Application.class, args);

		try {
			Tests tests = ctx.getBean(Tests.class);
			tests.testAll();
		} catch (CompanyAlreadyExistsWithSameDetailsException | LoginFailedException
				| CustomerAlreadyExistsWithSameEmailException | CompanyNotExistsException
				| CustomerNotExistsException | CouponAlreadyExistsWithSameTitleException | CouponNotExistsException
				| CouponAlreadyExistsException | CouponNotAvailableException | CouponDateExpiredException | SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		ctx.close();
	}
}
