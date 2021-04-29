package facades;

import static beanUtil.CompanyUtil.*;
import static beanUtil.CustomerUtil.*;
import static beanUtil.TableGenerator.*;

import java.sql.SQLException;
import java.util.List;

import bean.Company;
import bean.Customer;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;

public class AdminFacade extends ClientFacade 
{
	public AdminFacade() {
		super();
	}
	
	/**
	 * Access for admin user with hard-coded details.
	 */
	@Override
	public boolean login(String email, String password) {
		if("admin@admin.com".equals(email) && "admin".equals(password)) {
			System.out.println("\nINFO: You logged in as Administrator!\n");
			return true;
		}
		return false;
	}
	
	/**
	 * Adding new company to <b>COMPANIES</b> table. <br>
	 * Company cannot be added where name and\or email already exists in the DB.<br>
	 * Throws respective exception.<br>
	 * Prints received id of added company.
	 */
	public void addCompany(Company company) throws SQLException, EntityAlreadyExistsException
	{
		isCompanyExists(company);
		int id = companyDB.add(company);
		company.setId(id);
		printRow(getCompanyHeadersList(), fillCompanyRow(company));
	}

	/**
	 * Checks whether company already exists by name or email in the DB.
	 */
	private void isCompanyExists(Company company) throws SQLException, EntityAlreadyExistsException 
	{
		if(companyDB.getCompanyByName(company.getName()) || companyDB.getCompanyByEmail(company.getEmail()))
			throw new EntityAlreadyExistsException(company.getName());
	}
	
	/**
	 * Updates existing company in DB. If not found, thrown respective exception. <br>
	 * Not possible to update company's id and name.
	 */
	public void updateCompany(Company company) throws SQLException, EntityNotFoundException
	{
		if(companyDB.getOne(company.getId()) == null)
				throw new EntityNotFoundException("company with id: " + company.getId());
		companyDB.update(company);
		printRow(getCompanyHeadersList(), fillCompanyRow(company));
	}
	
	/**
	 * Removes all data related to Company.
	 *  - from CUSTOMERS_VS_COUPONS table
	 *  - from COUPONS table
	 *  - from COMPANIES table
	 */
	public void deleteCompany(int companyID) throws SQLException, EntityNotFoundException
	{
		int res1 = companyDB.deleteCouponPurchases(companyID); 
		System.out.println("INFO:");
		if(res1 > 0)
		   System.out.println(res1 + "Coupon/s deleted from CUSTOMERS_VS_COUPONS table.");
		else
			System.out.println("Coupons of company " + companyID + " not purchased by any customer.");
		
		int res2 = companyDB.deleteCouponsFromCouponsTableBasedOnCompanyID(companyID);
		if(res2 > 0)
			System.out.println(res2 + "Coupon/s deleted from COUPONS table.");
		else
			System.out.println("Company " + companyID + " didn't had any coupon.");
		
		int res3 = companyDB.delete(companyID);
		if(res3 > 0)
			System.out.println("Company " + companyID + " deleted from COMPANIES table.");
		else
			throw new EntityNotFoundException("company with id: " + companyID);
	}

	/**
	 * Retrieves and prints list of all existing companies in the <b>COMPANIES</b> table.<br>
	 * Prints respective message in case of empty table.
	 */
	public List<Company> getAllCompanies() throws SQLException, EntityNotFoundException
	{
		List<Company> companies = companyDB.getAll();
		if(companies.isEmpty())
			throw new EntityNotFoundException("No companies in the table");
		return companies;
	}
	
	/**
	 * Retrieves one company by ID.<br>
	 * Throws exception in case company not found in the <b>COMPANIES</b> table.
	 */
	public Company getOneCompany(int companyID) throws SQLException, EntityNotFoundException 
	{
		Company company = companyDB.getOne(companyID);
	    if (company == null) 
	        throw new EntityNotFoundException("company with id: " + companyID);
	    return company;
	}
	
	/**
	 * Adding new customer to <b>CUSTOMERS</b> table. <br>
	 * Customer cannot be added while email already exists in the DB.
	 */
	public void addCustomer(Customer customer) throws SQLException, EntityAlreadyExistsException 
	{
		isCustomerExistsBasedOnEmail(customer);
		int id = customerDB.add(customer);
		customer.setId(id);
		printRow(getCustomerHeadersList(), fillCustomerRow(customer));
	}

	private void isCustomerExistsBasedOnEmail(Customer customer) throws SQLException, EntityAlreadyExistsException 
	{
		if(customerDB.getCustomerByEmail(customer.getEmail())) 
			throw new EntityAlreadyExistsException(customer.getFirstName() + customer.getLastName());
	}	
	
	/**
	 * Updates existing customer in DB. If not found, thrown respective exception. <br>
	 * Not possible to update customer's id.
	 */
	public void updateCustomer(Customer customer) throws SQLException, EntityNotFoundException 
	{
		if(customerDB.getOne(customer.getId()) == null)
			throw new EntityNotFoundException("customer");
		customerDB.update(customer);
		printRow(getCustomerHeadersList(), fillCustomerRow(customer));
	}

	/**
	 * Removes Customer data.<br>
	 * - from <b>CUSTOMERS_VS_COUPONS</b> table (his purchases).<br>
	 * - from <b>CUSTOMERS</b> table.
	 */
	public void deleteCustomer(int customerID) throws SQLException, EntityNotFoundException 
	{
		int res1 = customerDB.deleteCouponPurchases(customerID);
		if(res1 > 0)
			System.out.println(res1 + "purchase/s deleted from CUSTOMERS_VS_COUPONS table.");
		else
			System.out.println("Customer " + customerID + " didn't purchase any coupon.");
		
		int res2 = customerDB.delete(customerID);
		if(res2 > 0)
			System.out.println("Customer " + customerID + " deleted from CUSTOMERS table.");
		else
			throw new EntityNotFoundException("customer with id: " + customerID);
	}
	
	/**
	 * Retrieves and prints list of all existing customers in the <b>CUSTOMERS</b> table.<br>
	 * Prints respective message in case of empty table.
	 */
	public List<Customer> getAllCustomers() throws SQLException, EntityNotFoundException
	{
		List<Customer> customers = customerDB.getAll();
		if(customers.isEmpty())
			throw new EntityNotFoundException("No customers in the table");
		return customers;
	}
	
	/**
	 * Retrieves one customer by ID.<br>
	 * Throws exception in case customer not found in the <b>CUSTOMERS</b> table.
	 */
	public Customer getOneCustomer(int customerID) throws SQLException, EntityNotFoundException 
	{
		Customer customer = customerDB.getOne(customerID);
	    if (customer == null) 
	        throw new EntityNotFoundException("customer"+customerID);
	    return customer;
	}
}
