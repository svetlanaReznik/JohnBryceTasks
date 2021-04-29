package tests;

import static beanUtil.CouponUtil.fillCouponRow;
import static beanUtil.CouponUtil.getCouponHeadersList;
import static beanUtil.TableGenerator.print;
import static beanUtil.TableGenerator.printHeader;
import static beanUtil.TableGenerator.printRow;
import static util.DateUtils.asDate;
import static util.DateUtils.convert;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import bean.Category;
import bean.Company;
import bean.Coupon;
import bean.Customer;
import dbdao.CouponsDBDAO;
import exceptions.CouponDateExpiredException;
import exceptions.CouponNotAvailableException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;
import exceptions.LoginFailedException;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import login.ClientType;
import login.LoginManager;

public class NegativeTests {
	/**
	 * Demonstrates all negative cases for admin, company & customer users.
	 * @throws EntityAlreadyExistsException 
	 * @throws EntityNotFoundException 
	 * @throws CouponDateExpiredException 
	 * @throws CouponNotAvailableException 
	 */
	public static void negativeFunctionalityTests() throws SQLException, LoginFailedException, EntityNotFoundException, EntityAlreadyExistsException, CouponNotAvailableException, CouponDateExpiredException {
		AdminFacadeTests();
		CompanyFacadeTests();
		CustomerFacadeTests();
	}

	private static void AdminFacadeTests() throws SQLException, LoginFailedException, EntityNotFoundException, EntityAlreadyExistsException
	{
		AdminFacade admin = (AdminFacade) LoginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

				
		try {	
		    /* Add company: not possible to add company with the same name */
			admin.addCompany(new Company("BBBBB", "brbr@bb.com", "123"));
			
		} catch (EntityAlreadyExistsException e) {
			System.out.println(e.getMessage());	}
		
		
		try {	
			/* Add company: not possible to add company with same email */
			admin.addCompany(new Company("JJJJ", "bbbb@bb.com", "jjjj123"));
			
		} catch (EntityAlreadyExistsException e) {
			System.out.println(e.getMessage());	}
		
		try {	
			/* Add customer: not possible to add customer with same email */
			admin.addCustomer(new Customer("Baba", "Yaga", "ali@ali.com", "ali123"));
			
		} catch (EntityAlreadyExistsException e) {
			System.out.println(e.getMessage());	}
		
		
		try {
			/* GetOneCompany: company not found for */
			System.out.println(admin.getOneCompany(200));  
			
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage()); } 	
		
		
		try {
			/* GetOneCustomer: customer not found */
			System.out.println(admin.getOneCustomer(200)); 
			
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage()); } 	
		
		
		try {
			/* Update company: company not found for update */
			Company c = new Company("Mmmmm", "mmmm@bb.com", "mmmm123"); 
			c.setPassword("123");
			admin.updateCompany(c);
			
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage()); } 		
			
		try {
			/* Update customer: customer not found for update */
			Customer c1 = new Customer("Peppa", "Peppa", "p@ppp.com", "123");
			c1.setPassword("123");
			admin.updateCustomer(c1);
			
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage()); } 		
	}

	private static void CompanyFacadeTests() throws SQLException, EntityAlreadyExistsException, LoginFailedException, EntityNotFoundException
	{
		
		CompanyFacade cus;
		try {
			/* Login is incorrect */
			cus = (CompanyFacade) LoginManager.login("b", "b", ClientType.COMPANY);
		} catch (LoginFailedException e) {
			System.out.println(e.getMessage()); }
		
		CompanyFacade company = (CompanyFacade) LoginManager.login("eeee@dd.com", "eeee123", ClientType.COMPANY);
		
		try {
			 /* Add coupon: not possible to add coupon with same title for same company */
			company.addCoupon(new Coupon(4, 4, Category.BOOKS, "The moon", "About moon", "url",	convert(asDate(LocalDate.now())), convert(asDate(LocalDate.now().plusDays(30))), 150.0));
		} catch (EntityAlreadyExistsException e) {
			System.out.println(e.getMessage());	} 
		
		try {
			/* Add coupon: possible to add coupon with same title of other company */
			print(getCouponHeadersList(), "NEW ADDED COUPONS");
			printHeader(getCouponHeadersList());
			company.addCoupon(new Coupon(4, 3, Category.BOOKS, "The moon", "About moon", "url", convert(asDate(LocalDate.now())), convert(asDate(LocalDate.now().plusDays(30))), 150.0));
		} catch (EntityAlreadyExistsException e) {
			System.out.println(e.getMessage());	}
			
		try {
			/* Update company: company not found for update */
			Coupon c = new Coupon(30, 5, Category.RESTAURANT, "Meat", "Steak", null, convert(asDate(LocalDate.now())), convert(asDate(LocalDate.now().plusDays(50))), 280.0);
			company.updateCoupon(c);
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());	}
				
		try {
			/*Get company coupons*/
			for (Coupon coupon : company.getCompanyCoupons()) {
				System.out.println(coupon);	}
			for (Coupon coupon : company.getCompanyCoupons(Category.BOOKS)) {
				System.out.println(coupon);	}
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());	}
		
		try {
			for (Coupon coupon : company.getCompanyCoupons(200)) {
				System.out.println(coupon);
			}
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());	
		}
	}


	private static void CustomerFacadeTests() throws SQLException, CouponNotAvailableException, CouponDateExpiredException, EntityAlreadyExistsException, EntityNotFoundException, LoginFailedException
	{
		try {
			/* Login is incorrect */
			CustomerFacade cus = (CustomerFacade) LoginManager.login("a@ali.com", "ali123", ClientType.CUSTOMER);
		}catch (LoginFailedException e) {
			System.out.println(e.getMessage());
		}
		
		/* Coupon purchase: not possible to purchase same coupon */
		CustomerFacade customer = (CustomerFacade) LoginManager.login("alisa@al.com", "alisa123", ClientType.CUSTOMER);
		CouponsDBDAO couponsDB = new CouponsDBDAO();
		try {
			customer.purchaseCoupon(couponsDB.getOne(12));
		}catch (EntityAlreadyExistsException e) {
			System.out.println(e.getMessage());	}
		
		/* Coupon purchase: not possible to purchase coupon where his amount is 0 */
		try {
			Coupon c1 = couponsDB.getOne(11);
			c1.setAmount(0);
			customer.purchaseCoupon(c1);
		} catch (CouponNotAvailableException e) {
			System.out.println(e.getMessage());	} 
		
		/* Coupon purchase: not possible to purchase expired coupon */
		try {
			Coupon c2 = couponsDB.getOne(11);
			c2.setEndDate(convert(asDate(LocalDate.now().minusDays(1))));
			customer.purchaseCoupon(c2);
		} catch (CouponDateExpiredException e) {
			System.out.println(e.getMessage());	} 
		
		try {
			for (Coupon coupon : customer.getCustomerCoupons(Category.MOVIES)) {
				System.out.println(coupon);
			}
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			for (Coupon coupon : customer.getCustomerCoupons(300)) {
				System.out.println(coupon);
			}
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
