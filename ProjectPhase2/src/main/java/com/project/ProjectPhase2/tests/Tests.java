package com.project.ProjectPhase2.tests;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.project.ProjectPhase2.beans.CategoryType;
import com.project.ProjectPhase2.beans.Company;
import com.project.ProjectPhase2.beans.Coupon;
import com.project.ProjectPhase2.beans.Customer;
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
import com.project.ProjectPhase2.facade.AdminFacade;
import com.project.ProjectPhase2.facade.CompanyFacade;
import com.project.ProjectPhase2.facade.CustomerFacade;
import com.project.ProjectPhase2.login.ClientType;
import com.project.ProjectPhase2.login.LoginManager;
import com.project.ProjectPhase2.thread.CouponExpirationDailyJob;

@Service
public class Tests 
{
	@Autowired
	private CouponExpirationDailyJob job;

	@Autowired
	private LoginManager loginManager;

	/**
	 * Initiates CouponExpirationDailyJob. <br>
	 * Tests basic, advance and negative tests for admin, company and customer users.<br>
	 * Stops thread and to close connections to DB.
	 * 
	 * @author Svetlana Reznik
	 * @throws LoginFailedException, CompanyAlreadyExistsWithSameDetailsException, SQLException,
	 * @throws CouponAlreadyExistsWithSameTitleException 
	 * @throws CouponNotExistsException 
	 * @throws CouponDateExpiredException 
	 * @throws CouponNotAvailableException 
	 * @throws CouponAlreadyExistsException 
	 * @throws CustomerAlreadyExistsWithSameEmail, CompanyNotExistsException, CustomerNotExistsException
	 */
	public void testAll() throws CompanyAlreadyExistsWithSameDetailsException, LoginFailedException,
	CustomerAlreadyExistsWithSameEmailException, CompanyNotExistsException, 
	CustomerNotExistsException, CouponAlreadyExistsWithSameTitleException, 
	CouponNotExistsException, CouponAlreadyExistsException, 
	CouponNotAvailableException, CouponDateExpiredException, SQLException 
	{
		job.start();

		basicTests();
		advancedTests();
		negativeTests();

		job.stopRunning();
	}

	/**
	 * Demonstrates basic actions as: add, get & update for admin, company &
	 * customer users.
	 * 
	 * @author Svetlana Reznik
	 * @throws LoginFailedException, CompanyAlreadyExistsWithSameDetailsException, SQLException
	 * @throws CompanyNotExistsException, CustomerNotExistsException, CustomerAlreadyExistsWithSameEmail
	 * @throws CouponAlreadyExistsWithSameTitleException 
	 * @throws CouponNotExistsException 
	 * @throws CouponDateExpiredException 
	 * @throws CouponNotAvailableException 
	 * @throws CouponAlreadyExistsException 
	 */
	private void basicTests() throws 
	CompanyAlreadyExistsWithSameDetailsException, LoginFailedException,
	CustomerAlreadyExistsWithSameEmailException, CompanyNotExistsException, 
	CustomerNotExistsException, CouponAlreadyExistsWithSameTitleException, 
	CouponNotExistsException, CouponAlreadyExistsException, CouponNotAvailableException, CouponDateExpiredException 
	{
//		basicFunctionalityAdminFacade();
//		basicFunctionalityCompanyFacade();
//		basicFunctionalityCustomerFacade();
	}

	public void basicFunctionalityAdminFacade() throws CompanyAlreadyExistsWithSameDetailsException, LoginFailedException,
	CustomerAlreadyExistsWithSameEmailException, CompanyNotExistsException, CustomerNotExistsException, SQLException 
	{
		AdminFacade adminF = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

		adminF.addCompany(new Company("NNNN", "abcd@aa.com", "aaaa123"));
		adminF.addCustomer(new Customer("Ugi", "Baba", "ugi@baba.com", "789"));
		adminF.addCompany(new Company("Bbbbb", "bbbb@bb.com", "bbbb123"));
		adminF.addCustomer(new Customer("Y", "Y", "y@y.com", "yyy123"));
		adminF.addCompany(new Company("Ccccc", "cccc@cc.com", "cccc123"));
		adminF.addCustomer(new Customer("Alisa", "Alice", "alisa@al.com", "alisa123"));

		Company comp = adminF.getOneCompany(2);
		System.out.println(comp); 
		comp.setPassword("123");
		System.out.println(comp); 
		adminF.updateCompany(comp);

		Customer cust = adminF.getOneCustomer(1);
		System.out.println(cust);
		cust.setPassword("234");
		adminF.updateCustomer(cust);
		System.out.println(comp); 

		for (Company company : adminF.getAllCompanies()) {
			System.out.println(company);
		}

		for (Customer customer : adminF.getAllCustomers()) {
			System.out.println(customer);
		}
	}

	public void basicFunctionalityCompanyFacade() throws LoginFailedException, CouponAlreadyExistsWithSameTitleException, CouponNotExistsException, CompanyNotExistsException, SQLException 
	{
		CompanyFacade companyF = (CompanyFacade) loginManager.login("cccc@cc.com", "cccc123", ClientType.COMPANY);
		/*Add coupon*/
		companyF.addCoupon(new Coupon("The sea", "About sea", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(20)), 5, 150.0, null, CategoryType.BOOKS, companyF.getOneCompany(1)));
		companyF.addCoupon(new Coupon("The moon", "About moon", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(20)), 5, 170.0, null, CategoryType.BOOKS, companyF.getOneCompany(2)));
		companyF.addCoupon(new Coupon("Chair", "chair", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(30)), 10, 300.0, null, CategoryType.OFFICE, companyF.getOneCompany(2)));
		companyF.addCoupon(new Coupon("Meat", "Steak", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(50)), 10, 280.0, null, CategoryType.RESTAURANT, companyF.getOneCompany(3)));

		/*Update coupon*/
		Coupon c = companyF.getOneCoupon(4);
		System.out.println(c);
		c.setPrice(400);
		System.out.println(c);
		companyF.updateCoupon(c);

		/*Get company coupons*/
		for (Coupon coupon : companyF.getCompanyCoupons()) {
			System.out.println(coupon);
		}

		for (Coupon coupon : companyF.getCompanyCoupons(CategoryType.RESTAURANT)) {
			System.out.println(coupon);
		}

		for (Coupon coupon : companyF.getCompanyCoupons(410)) {
			System.out.println(coupon);
		}

		System.out.println(companyF.getCompanyDetails()); 
		for (Coupon coupon : companyF.getCompanyCoupons()) {
			System.out.println(coupon);
		}
	}

	public void basicFunctionalityCustomerFacade() throws LoginFailedException, CouponAlreadyExistsException, CouponNotAvailableException, CouponDateExpiredException, CustomerNotExistsException, CouponNotExistsException, SQLException 
	{
		CustomerFacade customerF = (CustomerFacade) loginManager.login("y@y.com", "yyy123", ClientType.CUSTOMER);

		Coupon c = customerF.getOneCoupon(2);
		System.out.println("The coupon's amount: " + c.getAmount());
		customerF.purchaseCoupon(c);
		System.out.println("The coupon's amount: " + c.getAmount());

		for (Coupon coupon : customerF.getCustomerCoupons()) {
			System.out.println(coupon);
		}

		for (Coupon coupon : customerF.getCustomerCoupons(CategoryType.BOOKS)) {
			System.out.println(coupon);
		}

		for (Coupon coupon : customerF.getCustomerCoupons(200)) {
			System.out.println(coupon);
		}

		System.out.println(customerF.getCustomerDetails());
	}

	/**
	 * Demonstrates delete actions and their effect on data for admin, company &
	 * customer users.
	 * 
	 * @author Svetlana Reznik
	 * @throws LoginFailedException 
	 * @throws SQLException 
	 */
	private void advancedTests() throws LoginFailedException, SQLException, CustomerNotExistsException 
	{
//		advancedFunctionalityCompanyFacade();
//		advancedFunctionalityAdminFacade();
//		advancedFunctionalityCustomerFacade();
	}

	/**
	 * Company removes coupon.<br>
	 * Coupon will be deleted from <b>CUSTOMERS_VS_COUPONS</b> and from <b>COUPONS</b> table.
	 * 
	 * @throws SQLException
	 * @throws LoginFailedException
	 */
	private void advancedFunctionalityCompanyFacade() throws LoginFailedException, SQLException 
	{
		CompanyFacade companyF = (CompanyFacade) loginManager.login("abcd@aa.com", "aaaa123", ClientType.COMPANY);
		for (Coupon coupon : companyF.getCompanyCoupons()) {
			System.out.println(coupon);
		}
		companyF.deleteCoupon(1);
		for (Coupon coupon : companyF.getCompanyCoupons()) {
			System.out.println(coupon);
		}		
	}

	/**
	 * Demonstrates deletion of Company and Customer by Admin user.
	 * 
	 * @throws SQLException
	 * @throws LoginFailedException
	 */
	private void advancedFunctionalityAdminFacade() throws LoginFailedException, SQLException, CustomerNotExistsException 
	{
		AdminFacade admin = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		System.out.println("Companies:");
		for (Company company : admin.getAllCompanies()) {
			System.out.println(company);
		}

		System.out.println();
		System.out.println("Customers:");
		for (Customer customer : admin.getAllCustomers()) {
			System.out.println(customer);
		}

		admin.deleteCustomer(7);
		admin.deleteCompany(2);

		System.out.println("Companies:");
		for (Company company : admin.getAllCompanies()) {
			System.out.println(company);
		}
		System.out.println();
		System.out.println("Customers:");
		for (Customer customer : admin.getAllCustomers()) {
			System.out.println(customer);
		}
	}

	private void advancedFunctionalityCustomerFacade() throws LoginFailedException, SQLException 
	{
		CustomerFacade customer = (CustomerFacade) loginManager.login("alisa@al.com", "alisa123", ClientType.CUSTOMER);
		for (Coupon coupon : customer.getCustomerCoupons()) {
			System.out.println(coupon);
		} 
	}

	/**
	 * Demonstrates all negative cases in order to attack the system for admin,
	 * company & customer users.
	 * 
	 * @author Svetlana Reznik
	 * @throws CompanyAlreadyExistsWithSameDetailsException
	 * @throws LoginFailedException
	 * @throws SQLException
	 * @throws CompanyNotExistsException
	 * @throws CustomerNotExistsException
	 * @throws CouponAlreadyExistsWithSameTitleException 
	 * @throws CouponNotExistsException 
	 * @throws CouponDateExpiredException 
	 * @throws CouponNotAvailableException 
	 * @throws CouponAlreadyExistsException 
	 * @throws CustomerAlreadyExistsWithSameEmail
	 */
	private void negativeTests() throws SQLException, LoginFailedException, CompanyAlreadyExistsWithSameDetailsException, CustomerAlreadyExistsWithSameEmailException, 
	CompanyNotExistsException, CustomerNotExistsException, CouponAlreadyExistsWithSameTitleException, CouponNotExistsException, 
	CouponAlreadyExistsException, CouponNotAvailableException, CouponDateExpiredException 
	{
//		negaiveFunctionalityAdminFacade();
//		negaiveFunctionalityCompanyFacade();
		negaiveFunctionalityCustomerFacade();
	}

	private void negaiveFunctionalityAdminFacade() throws SQLException, LoginFailedException, CompanyAlreadyExistsWithSameDetailsException, 
	CustomerAlreadyExistsWithSameEmailException, CompanyNotExistsException, CustomerNotExistsException 
	{
		AdminFacade adminF = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

		/* Add company: not possible to add company with the same name */
//		adminF.addCompany(new Company("NNNN", "abcd@aa.com", "aaaa123"));
		/* Add company: not possible to add company with same email */
//		adminF.addCompany(new Company("Da", "cccc@cc.com", "aaaa123"));

		/* Add customer: not possible to add customer with same email */
//		adminF.addCustomer(new Customer("Baba", "Yaga", "ugi@baba.com", "ali123"));

		/* GetOneCompany: company not found for */
//		System.out.println(adminF.getOneCompany(200)); //without coupons

		/* GetOneCustomer: customer not found */
//		System.out.println(adminF.getOneCustomer(200)); //without coupons

		/* Update company: company not found for update */
//		Company c = new Company("Mmmmm", "mmmm@bb.com", "mmmm123");
//		c.setPassword("123");
//		adminF.updateCompany(c);

		/* Update customer: customer not found for update */
//		Customer c1 = new Customer("Peppa", "Peppa", "p@ppp.com", "123");
//		c1.setPassword("123");
//		adminF.updateCustomer(c1);

		/* getAllCompanies returned empty List */
		for (Company company : adminF.getAllCompanies()) {
			System.out.println(company);
		}

		/* getAllCustomers returned empty List */
		for (Customer customer : adminF.getAllCustomers()) {
			System.out.println(customer);
		}		
	}

	private void negaiveFunctionalityCompanyFacade() throws SQLException, LoginFailedException, CouponAlreadyExistsWithSameTitleException, CouponNotExistsException, CompanyNotExistsException 
	{
		/* Login is incorrect */
//		CompanyFacade companyFF = (CompanyFacade) loginManager.login("sss@ss.com", "sss1", ClientType.COMPANY);

		/* Add coupon: not possible to add coupon with same title for same company */
		CompanyFacade companyF = (CompanyFacade) loginManager.login("cccc@cc.com", "cccc123", ClientType.COMPANY);
//		companyF.addCoupon(new Coupon("Meat", "Steak", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(50)), 10, 280.0, null, CategoryType.RESTAURANT, companyF.getOneCompany(3)));

		/* Add coupon: possible to add coupon with same title of other company */
		companyF.addCoupon(new Coupon("The sea", "About sea", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(20)), 5, 150.0, null, CategoryType.BOOKS, companyF.getOneCompany(3)));

		/*Get company coupons*/
		for (Coupon coupon : companyF.getCompanyCoupons()) {
			System.out.println(coupon);
		}

		for (Coupon coupon : companyF.getCompanyCoupons(CategoryType.BOOKS)) {
			System.out.println(coupon);
		}

		for (Coupon coupon : companyF.getCompanyCoupons(200)) {
			System.out.println(coupon);
		}

		/*company without coupons*/
		System.out.println(companyF.getCompanyDetails()); 		

	}

	private void negaiveFunctionalityCustomerFacade() throws SQLException, LoginFailedException, CouponAlreadyExistsException, CouponNotAvailableException, CouponDateExpiredException, CustomerNotExistsException, CouponNotExistsException 
	{
		/* Login is incorrect */
//		CustomerFacade customerFF = (CustomerFacade) loginManager.login("bubu@y.com", "bibi", ClientType.CUSTOMER);

		/* Coupon purchase: not possible to purchase same coupon */
		CustomerFacade customerF = (CustomerFacade) loginManager.login("ugi@baba.com", "234", ClientType.CUSTOMER);
//		customerF.purchaseCoupon(customerF.getOneCoupon(4));

		/* Coupon purchase: not possible to purchase coupon where his amount is 0 */
//		Coupon c = customerF.getOneCoupon(8);
//		c.setAmount(0);
//		customerF.purchaseCoupon(c);

		/* Coupon purchase: not possible to purchase expired coupon */
//		Coupon coup = customerF.getOneCoupon(8);
//		coup.setEndDate(Date.valueOf(LocalDate.now().minusDays(1)));
//		customerF.purchaseCoupon(coup);

		/* Get methods */
		for (Coupon coupon : customerF.getCustomerCoupons()) {
			System.out.println(coupon);
		}

		for (Coupon coupon : customerF.getCustomerCoupons(CategoryType.BOOKS)) {
			System.out.println(coupon);
		}

		for (Coupon coupon : customerF.getCustomerCoupons(200)) {
			System.out.println(coupon);
		}
	}
}
