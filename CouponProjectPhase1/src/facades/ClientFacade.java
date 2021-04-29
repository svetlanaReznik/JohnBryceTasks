package facades;

import java.sql.SQLException;

import dao.CompaniesDAO;
import dao.CouponsDAO;
import dao.CustomersDAO;
import dbdao.CompaniesDBDAO;
import dbdao.CouponsDBDAO;
import dbdao.CustomersDBDAO;
import exceptions.LoginFailedException;

public abstract class ClientFacade 
{
	protected CouponsDAO couponDB;
	protected CompaniesDAO companyDB;
	protected CustomersDAO customerDB;
	
	public ClientFacade() {
		couponDB = new CouponsDBDAO();
		companyDB = new CompaniesDBDAO();
		customerDB = new CustomersDBDAO();
	}
		
	public abstract boolean login (String email, String password) throws SQLException, LoginFailedException;
}
