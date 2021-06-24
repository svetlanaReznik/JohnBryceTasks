package com.example.CouponProjectPhase3.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.CouponProjectPhase3.beans.Company;
import com.example.CouponProjectPhase3.beans.Coupon;
import com.example.CouponProjectPhase3.beans.Customer;
import com.example.CouponProjectPhase3.exceptions.CompanyAlreadyExistsWithSameDetailsException;
import com.example.CouponProjectPhase3.exceptions.CompanyNotExistsException;
import com.example.CouponProjectPhase3.exceptions.CustomerAlreadyExistsWithSameEmailException;
import com.example.CouponProjectPhase3.exceptions.CustomerNotExistsException;

@Service
public class AdminFacade extends ClientFacade{

	/**
	 * Access for admin user with hard-coded details.
	 */
	@Override
	public int login(String email, String password) {
		if("admin@admin.com".equals(email) && "admin".equals(password)) {
			System.out.println("You logged in as Administrator.");
			return 1;
		}
		return 0;
	}

	/**
	 * Adding new company to <b>COMPANIES</b> table. <br>
	 * Company cannot be added where name and\or email already exists in the DB.<br>
	 * Throws respective exception.<br>
	 * Prints received id of added company.
	 * 
	 * @param company
	 * @throws SQLException, CompanyAlreadyExistsWithSameDetailsException
	 */
	public void addCompany(Company company) throws SQLException, CompanyAlreadyExistsWithSameDetailsException
	{
		isCompanyExists(company);
		int id = companyDB.save(company).getId();
		System.out.println("Added company " + company.getName() + " with id: " + id);
	}

	/**
	 * Checks whether company already exists by name or email in the DB.
	 * 
	 * @param company
	 * @throws SQLException, CompanyAlreadyExistsWithSameDetailsException
	 */
	private void isCompanyExists(Company company) throws SQLException, CompanyAlreadyExistsWithSameDetailsException 
	{
		if(companyDB.existsCompaniesByNameOrEmail(company.getName(), company.getEmail()))
			throw new CompanyAlreadyExistsWithSameDetailsException();
	}

	/**
	 * Updates existing company in DB. If not found, thrown respective exception. <br>
	 * Not possible to update company's id and name.
	 * @param company
	 * @throws SQLException
	 * @throws CompanyNotExistsException
	 */
	public void updateCompany(Company company) throws SQLException, CompanyNotExistsException
	{
		if(companyDB.existsById(company.getId())) {
			companyDB.save(company);
			System.out.println("Company " + company.getId() + " updated.");
		}
		else
			throw new CompanyNotExistsException();
	}

	/**
	 * Retrieves one company by ID.<br>
	 * Throws exception in case company not found in the <b>COMPANIES</b> table.
	 * 
	 * @param companyID
	 * @return 
	 * @throws SQLException
	 * @throws CompanyNotExistsException
	 */
	public Company getOneCompany(int companyID) throws SQLException, CompanyNotExistsException 
	{
		Company company = companyDB.findById(companyID).orElseThrow(() -> new CompanyNotExistsException());
		return company;
	}

	/**
	 * Retrieves and prints list of all existing companies in the <b>COMPANIES</b> table.<br>
	 * Prints respective message in case of empty table.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Company> getAllCompanies() throws SQLException
	{
		List<Company> companies = companyDB.findAll();
		if(companies.isEmpty())
			System.out.println("No companies in the table.");
		return companies;
	}

	/**
	 * Removes all data related to Company.
	 *  - from CUSTOMERS_VS_COUPONS table
	 *  - from COUPONS table
	 *  - from COMPANIES table
	 * 
	 * @param companyID
	 * @throws SQLException
	 */
	public void deleteCompany(int companyID) throws SQLException
	{
		int purchaseCount = 0;
		int couponsCount = 0;

		Company company = companyDB.findCompaniesById(companyID);
		if (!company.equals(null)) 
		{
			Set<Coupon> compCoupons = company.getCoupons();
			for (Coupon coupon : compCoupons) 
			{
				for (Customer customer : coupon.getCustomers()) 
				{
					deletePurchaseFromCustomersVSCouponsTable(coupon, customer);
					purchaseCount++;
				}
				couponsCount++;
			} 
			couponDB.deleteInBatch(compCoupons);//removes coupons from COUPONS table
			printCompanyDeleteResults(companyID, purchaseCount, couponsCount);
			deleteCompanyFromCompaniesTable(companyID);
		}
		else 
			System.out.println("Company " + companyID + " not found.");
	}

	private void deletePurchaseFromCustomersVSCouponsTable(Coupon coupon, Customer customer) 
	{
		customer.removeCoupon(coupon);
		customerDB.save(customer);
	}

	public void deleteCompanyFromCompaniesTable(int companyID) {
		companyDB.deleteById(companyID);
		System.out.println("Company " + companyID + " deleted from COMPANIES table.");
	}

	public void printCompanyDeleteResults(int companyID, int purchaseCount, int couponsCount) 
	{
		if(purchaseCount > 0)
			System.out.println(purchaseCount + " coupon/s deleted from CUSTOMERS_VS_COUPONS table.");
		else
			System.out.println("Coupons of company " + companyID + " not purchased by any customer.");

		if(couponsCount > 0)
			System.out.println(couponsCount + " coupon/s deleted from COUPONS table.");
		else
			System.out.println("Company " + companyID + " didn't had any coupon.");
	}

	/**
	 * Adding new customer to <b>CUSTOMERS</b> table. <br>
	 * Customer cannot be added while email already exists in the DB.
	 * 
	 * @param customer
	 * @throws SQLException
	 * @throws CustomerAlreadyExistsWithSameEmailException
	 */
	public void addCustomer(Customer customer) throws SQLException, CustomerAlreadyExistsWithSameEmailException 
	{
		isCustomerExistsBasedOnEmail(customer);
		int id = customerDB.save(customer).getId();
		System.out.println("Added customer " + customer.getFirstName() + " " + customer.getLastName() + " with id: " + id);
	}

	private void isCustomerExistsBasedOnEmail(Customer customer) throws SQLException, CustomerAlreadyExistsWithSameEmailException 
	{
		if(customerDB.existsCustomersByEmail(customer.getEmail()))
			throw new CustomerAlreadyExistsWithSameEmailException();
	}	

	/**
	 * Updates existing customer in DB. If not found, thrown respective exception. <br>
	 * Not possible to update customer's id.
	 * @param customer
	 * @throws SQLException
	 * @throws CustomerNotExistsException
	 */
	public void updateCustomer(Customer customer) throws SQLException, CustomerNotExistsException 
	{
		if(customerDB.existsById(customer.getId())) {
			customerDB.save(customer);
			System.out.println("Customer " + customer.getId() + " updated.");
		}
		else
			throw new CustomerNotExistsException();
	}

	/**
	 * Retrieves one customer by ID.<br>
	 * Throws exception in case customer not found in the <b>CUSTOMERS</b> table.
	 * 
	 * @param customerID
	 * @return
	 * @throws SQLException
	 * @throws CustomerNotExistsException
	 */
	public Customer getOneCustomer(int customerID) throws SQLException,CustomerNotExistsException 
	{
		Customer customer = customerDB.findById(customerID).orElseThrow(() -> new CustomerNotExistsException());
		return customer;
	}

	/**
	 * Retrieves and prints list of all existing customers in the <b>CUSTOMERS</b> table.<br>
	 * Prints respective message in case of empty table.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Customer> getAllCustomers() throws SQLException
	{
		List<Customer> customers = customerDB.findAll();
		if(customers.isEmpty())
			System.out.println("No customers in the table.");
		return customers;
	}

	/**
	 * Removes Customer data.<br>
	 * - from <b>CUSTOMERS_VS_COUPONS</b> table (his purchases).<br>
	 * - from <b>CUSTOMERS</b> table.
	 * 
	 * @param customerID
	 * @throws SQLException
	 */
	public void deleteCustomer(int customerID) throws SQLException, CustomerNotExistsException 
	{
		int purchaseCount = 0;

		Customer customer = getOneCustomer(customerID);
		if (!customer.equals(null)) 
		{
			Set<Coupon> custCoupons = ((Customer) customer).getCoupons();
			for (Coupon coupon : custCoupons) 
			{
				deletePurchaseFromCustomersVSCouponsTable(coupon, customer);
				purchaseCount++;
			} 
			printCustomerPurchasesDeleteResults(customerID, purchaseCount);
			deleteCustomerFromCustomersTable(customerID);
		}
		else 
			System.out.println("Customer " + customerID + " not found.");
	}

	public void deleteCustomerFromCustomersTable(int customerID) {
		customerDB.deleteById(customerID);
		System.out.println("Customer " + customerID + " deleted from CUSTOMERS table.");

	}
	
	private void printCustomerPurchasesDeleteResults(int customerID, int purchaseCount) 
	{
		if(purchaseCount > 0)
			System.out.println(purchaseCount + "Coupon/s deleted from CUSTOMERS_VS_COUPONS table.");
		else
			System.out.println("Customer " + customerID + " not purchased any coupon.");
	}
}
