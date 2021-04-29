package tests;

import static beanUtil.CompanyUtil.fillCompanyRow;
import static beanUtil.CompanyUtil.getCompanyHeadersList;
import static beanUtil.CouponUtil.fillCouponRow;
import static beanUtil.CouponUtil.getCouponHeadersList;
import static beanUtil.CustomerUtil.fillCustomerRow;
import static beanUtil.CustomerUtil.getCustomerHeadersList;
import static beanUtil.TableGenerator.print;
import static beanUtil.TableGenerator.printHeader;
import static beanUtil.TableGenerator.printRow;
import static util.DateUtils.asDate;
import static util.DateUtils.convert;

import java.sql.SQLException;
import java.time.LocalDate;

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

public class PositiveTests 
{
	public static void positiveFunctionalityTests() throws SQLException, LoginFailedException, EntityAlreadyExistsException, EntityNotFoundException,
														   CouponNotAvailableException, CouponDateExpiredException {
		AdminFacadeTests();
		CompanyFacadeTests();
		CustomerFacadeTests();
	}

	/**
	 * Demonstrates basic actions as: add, get & update for admin, company &
	 * customer users.
	 */
	private static void AdminFacadeTests() throws SQLException, LoginFailedException, EntityAlreadyExistsException, EntityNotFoundException 
	{
		AdminFacade admin = (AdminFacade) LoginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

		testAddCompanies(admin);
		testAddCustomers(admin);
		testUpdateCompany(admin);
		testUpdateCustomer(admin);
		testAllCompanies(admin);
		testAllCustomers(admin);
		testDeleteCompanyAndCustomer(admin);
	}

	private static void CompanyFacadeTests() throws SQLException, LoginFailedException, EntityAlreadyExistsException, EntityNotFoundException 
	{
		CompanyFacade company = (CompanyFacade) LoginManager.login("cccc@cc.com", "cccc123", ClientType.COMPANY);

		testAddCoupons(company);
		testUpdateCoupon(company);
		testCompanyCoupons(company);
		testCouponsByCategory(company);
		testCouponsByMaxPrice(company);
		testDeleteCoupon(company);		
	}

	private static void CustomerFacadeTests() throws SQLException, LoginFailedException, EntityNotFoundException, CouponNotAvailableException,
																  CouponDateExpiredException, EntityAlreadyExistsException 
	{
		CustomerFacade customer = (CustomerFacade) LoginManager.login("moshe@sh.com", "moshe123", ClientType.CUSTOMER);

		testCouponPurchase(customer);
		testCustomerCoupons(customer);
		testCustomerCouponsByCategory(customer);
		testCustomerCouponsByMaxPrice(customer);
	}

	private static void testCustomerCouponsByMaxPrice(CustomerFacade customer)
			throws SQLException, EntityNotFoundException {
		print(getCouponHeadersList(), "CUSTOMER'S COUPONS BY MAX PRICE");
		printHeader(getCouponHeadersList());
		for (Coupon coupon : customer.getCustomerCoupons(400)) {
			printRow(getCouponHeadersList(), fillCouponRow(coupon));
		}
	}

	private static void testCustomerCouponsByCategory(CustomerFacade customer)
			throws SQLException, EntityNotFoundException {
		print(getCouponHeadersList(), "CUSTOMER'S COUPONS BY CATEGORY");
		printHeader(getCouponHeadersList());
		for (Coupon coupon : customer.getCustomerCoupons(Category.RESTAURANT)) {
			printRow(getCouponHeadersList(), fillCouponRow(coupon));
		}
	}

	private static void testCustomerCoupons(CustomerFacade customer) throws SQLException, EntityNotFoundException {
		print(getCouponHeadersList(), "CUSTOMER'S COUPONS");
		printHeader(getCouponHeadersList());
		for (Coupon coupon : customer.getCustomerCoupons()) {
			printRow(getCouponHeadersList(), fillCouponRow(coupon));
		}
	}

	private static void testCouponPurchase(CustomerFacade customer)
			throws SQLException, CouponNotAvailableException, CouponDateExpiredException, EntityAlreadyExistsException {
		print(getCouponHeadersList(), "COUPON PURCHASE");
		printHeader(getCouponHeadersList());
		CouponsDBDAO couponsDB = new CouponsDBDAO();
		Coupon c = couponsDB.getOne(4);
		printRow(getCouponHeadersList(), fillCouponRow(c));
		customer.purchaseCoupon(c);
	}

	private static void testAllCustomers(AdminFacade admin) throws SQLException, EntityNotFoundException {
		print(getCustomerHeadersList(), "ALL CUSTOMERS");
		printHeader(getCustomerHeadersList());
		for (Customer customer : admin.getAllCustomers()) {
			printRow(getCustomerHeadersList(), fillCustomerRow(customer));
		}
	}
	
	private static void testDeleteCompanyAndCustomer(AdminFacade admin) throws SQLException, EntityNotFoundException 
	{
		admin.deleteCompany(1);
		admin.deleteCustomer(2);
		
		print(getCompanyHeadersList(), "COMPANY DELETED");
		printHeader(getCompanyHeadersList());
		for (Company company : admin.getAllCompanies()) {
			printRow(getCompanyHeadersList(), fillCompanyRow(company));
		}
		System.out.println();
		print(getCustomerHeadersList(), "CUSTOMER DELETED");
		printHeader(getCustomerHeadersList());
		for (Customer customer : admin.getAllCustomers()) {
			printRow(getCustomerHeadersList(), fillCustomerRow(customer));
		}
	}

	private static void testAllCompanies(AdminFacade admin) throws SQLException, EntityNotFoundException {
		print(getCompanyHeadersList(), "ALL COMPANIES");
		printHeader(getCompanyHeadersList());
		for (Company company : admin.getAllCompanies()) {
			printRow(getCompanyHeadersList(), fillCompanyRow(company));
		}
	}

	private static void testUpdateCustomer(AdminFacade admin) throws SQLException, EntityNotFoundException {
		print(getCustomerHeadersList(), "UPDATED CUSTOMER");
		printHeader(getCustomerHeadersList());
		Customer cus = admin.getOneCustomer(3);
		printRow(getCustomerHeadersList(), fillCustomerRow(cus));
		cus.setPassword("234");
		admin.updateCustomer(cus);
	}

	private static void testUpdateCompany(AdminFacade admin) throws SQLException, EntityNotFoundException {
		print(getCompanyHeadersList(), "UPDATED COMPANY");
		printHeader(getCompanyHeadersList());
		Company c1 = admin.getOneCompany(2);
		printRow(getCompanyHeadersList(), fillCompanyRow(c1));
		c1.setPassword("123");
		admin.updateCompany(c1);
	}

	private static void testAddCustomers(AdminFacade admin) throws SQLException, EntityAlreadyExistsException {
		print(getCustomerHeadersList(), "NEW ADDED CUSTOMERS");
		printHeader(getCustomerHeadersList());
		admin.addCustomer(new Customer("Ali", "Baba", "ali@ali.com", "ali123"));
		admin.addCustomer(new Customer("Israel", "Israeli", "isl@isl.com", "isl123"));
		admin.addCustomer(new Customer("Gigi", "Gugu", "gigi@al.com", "gugu123"));
		admin.addCustomer(new Customer("Moshe", "Mosh", "moshe@sh.com", "moshe123"));
		admin.addCustomer(new Customer("Mitzi", "Pitzi", "mitzi@sh.com", "mitzi123"));
	}

	private static void testAddCompanies(AdminFacade admin) throws SQLException, EntityAlreadyExistsException {
		print(getCompanyHeadersList(), "NEW ADDED COMPANIES");
		printHeader(getCompanyHeadersList());
		admin.addCompany(new Company("AAAAA", "aaaa@aa.com", "aaaa123"));
		admin.addCompany(new Company("BBBBB", "bbbb@bb.com", "bbbb123"));
		admin.addCompany(new Company("CCCCC", "cccc@cc.com", "cccc123"));
		admin.addCompany(new Company("DDDDD", "dddd@dd.com", "dddd123"));
		admin.addCompany(new Company("EEEEE", "eeee@dd.com", "eeee123"));
	}

	private static void testCouponsByMaxPrice(CompanyFacade company) throws SQLException, EntityNotFoundException {
		print(getCouponHeadersList(), "COUPON/S BY MAX PRICE");
		printHeader(getCouponHeadersList());
		for (Coupon coupon : company.getCompanyCoupons(200)) {
			printRow(getCouponHeadersList(), fillCouponRow(coupon));
		}
	}
	
	private static void testDeleteCoupon(CompanyFacade company) throws SQLException, LoginFailedException, EntityNotFoundException {
		print(getCouponHeadersList(), "COMPANY'S COUPONS");
		printHeader(getCouponHeadersList());
		for (Coupon coupon : company.getCompanyCoupons()) {
			printRow(getCouponHeadersList(), fillCouponRow(coupon));
		}
		company.deleteCoupon(1);
		print(getCouponHeadersList(), "COUPON DELETED");
		printHeader(getCouponHeadersList());
		for (Coupon coupon : company.getCompanyCoupons()) {
			printRow(getCouponHeadersList(), fillCouponRow(coupon));
		}	
	}

	private static void testCouponsByCategory(CompanyFacade company) throws SQLException, EntityNotFoundException {
		print(getCouponHeadersList(), "COUPONS BY CATEGORY");
		printHeader(getCouponHeadersList());
		for (Coupon coupon : company.getCompanyCoupons(Category.BOOKS)) {
			printRow(getCouponHeadersList(), fillCouponRow(coupon));
		}
	}

	private static void testCompanyCoupons(CompanyFacade company) throws SQLException, EntityNotFoundException {
		print(getCouponHeadersList(), "ALL COMPANY'S COUPONS");
		printHeader(getCouponHeadersList());
		for (Coupon coupon : company.getCompanyCoupons()) {
			printRow(getCouponHeadersList(), fillCouponRow(coupon));
		}
	}

	private static void testUpdateCoupon(CompanyFacade company) throws SQLException, EntityNotFoundException {
		print(getCouponHeadersList(), "UPDATED COUPON");
		printHeader(getCouponHeadersList());
		Coupon c = company.getOneCoupon(2);
		c.setPrice(400);
		company.updateCoupon(c);
	}

	private static void testAddCoupons(CompanyFacade company) throws SQLException, LoginFailedException, EntityAlreadyExistsException 
	{
		print(getCouponHeadersList(), "NEW ADDED COUPONS");
		printHeader(getCouponHeadersList());
		company.addCoupon(new Coupon(3, 5, Category.BOOKS, "The sea", "About sea", "url",
				convert(asDate(LocalDate.now())), convert(asDate(LocalDate.now().plusDays(20))), 150.0));
		company.addCoupon(new Coupon(3, 5, Category.BOOKS, "The send", "About send", "url",
				convert(asDate(LocalDate.now())), convert(asDate(LocalDate.now().plusDays(20))), 200.0));
		company.addCoupon(new Coupon(4, 5, Category.BOOKS, "The moon", "About moon", "url",
				convert(asDate(LocalDate.now())), convert(asDate(LocalDate.now().plusDays(30))), 150.0));
		company.addCoupon(new Coupon(5, 5, Category.RESTAURANT, "SOHO", "sushi", "url",
				convert(asDate(LocalDate.now())), convert(asDate(LocalDate.now().plusDays(40))), 300.0));
		company.addCoupon(new Coupon(2, 5, Category.RESTAURANT, "Meat", "Steak", "url",
				convert(asDate(LocalDate.now())), convert(asDate(LocalDate.now().plusDays(60))), 280.0));
	}
}
