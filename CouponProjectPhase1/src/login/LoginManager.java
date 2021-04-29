package login;

import java.sql.SQLException;

import exceptions.LoginFailedException;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;

public class LoginManager 
{
	private static LoginManager instance;

	private LoginManager() {}

	public static LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}

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
	public static ClientFacade login(String email, String password, ClientType clientType) throws SQLException, LoginFailedException 
	{
		switch (clientType) 
		{
			case ADMINISTRATOR:
				ClientFacade admin = new AdminFacade();
				
				if (new AdminFacade().login(email, password))
					return admin;
	
			case COMPANY:
				ClientFacade company = new CompanyFacade();
	
				if (company.login(email, password))
					return company;
	
			case CUSTOMER:
				ClientFacade customer = new CustomerFacade();
				
				if (customer.login(email, password))
						return customer;
		}
		throw new LoginFailedException();
	}
}
