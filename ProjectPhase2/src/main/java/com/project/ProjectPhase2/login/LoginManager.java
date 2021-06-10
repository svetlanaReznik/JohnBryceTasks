package com.project.ProjectPhase2.login;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.project.ProjectPhase2.exceptions.LoginFailedException;
import com.project.ProjectPhase2.facade.AdminFacade;
import com.project.ProjectPhase2.facade.ClientFacade;
import com.project.ProjectPhase2.facade.CompanyFacade;
import com.project.ProjectPhase2.facade.CustomerFacade;

@Service
public class LoginManager 
{
	@Autowired
	private AdminFacade adminF;
	@Autowired
	private CompanyFacade companyF;
	@Autowired
	private CustomerFacade customerF;
	
//	@Autowired
//	private ConfigurableApplicationContext ctx;

	/**
	 * Checks the correctness of login details for Admin, Company & Customer users respectively.
	 * 
	 * @param email
	 * @param password
	 * @param clientType
	 * @return
	 * @throws SQLException
	 * @throws LoginFailedException
	 */
	public ClientFacade login(String email, String password, ClientType clientType) throws LoginFailedException, SQLException 
	{
		switch (clientType) 
		{
			case ADMINISTRATOR:
//				AdminFacade adminF = ctx.getBean(AdminFacade.class);
				if (adminF.login(email, password) == 1)
					return adminF;
	
			case COMPANY:
				CompanyFacade companyFacade = companyF;
				companyF.setCompanyID(companyF.login(email, password));
				return companyFacade;
	
			case CUSTOMER:
				CustomerFacade customerFacade = customerF;
				customerF.setCustomerID(customerF.login(email, password));
				return customerFacade;
		}
		throw new LoginFailedException();
	}
}
