package com.project.ProjectPhase2.facade;

import java.sql.SQLException;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.project.ProjectPhase2.beans.CategoryType;
import com.project.ProjectPhase2.beans.Company;
import com.project.ProjectPhase2.beans.Coupon;
import com.project.ProjectPhase2.beans.Customer;
import com.project.ProjectPhase2.exceptions.CompanyNotExistsException;
import com.project.ProjectPhase2.exceptions.CouponAlreadyExistsWithSameTitleException;
import com.project.ProjectPhase2.exceptions.CouponNotExistsException;
import com.project.ProjectPhase2.exceptions.LoginFailedException;

@Service
public class CompanyFacade extends ClientFacade{

	private int companyID;

	public int getCompanyID() {
		return companyID;
	}
	
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	/**
	 * Checks the correctness of login details for Company user.
	 * 
	 */
	@Override
	public int login(String email, String password) throws SQLException, LoginFailedException
	{
		Company company = companyDB.findCompaniesByEmailAndPassword(email, password);
		if(company != null) {
			System.out.println("You logged in as Company user with id=" + company.getId());
			return company.getId();
		}
		throw new LoginFailedException();
	}
	
	/**
	 * Adding new coupon to <b>COUPONS<b> table.
	 * Throws exception in case coupon already exists with same title for logged in company.
	 * 
	 * @param coupon
	 * @throws SQLException
	 * @throws CouponAlreadyExistsWithSameTitleException
	 */
	public void addCoupon(Coupon coupon) throws SQLException, CouponAlreadyExistsWithSameTitleException 
	{
		isCouponAlreadyExists(coupon);
		int id = couponDB.save(coupon).getId();
		System.out.println("Added coupon " + coupon.getTitle() + " with id: " + id);
	}

	private void isCouponAlreadyExists(Coupon coupon) throws SQLException, CouponAlreadyExistsWithSameTitleException 
	{
		Set<Coupon> existingCouponsInDB = couponDB.findCouponsByTitle(coupon.getTitle());
		if(!existingCouponsInDB.isEmpty()) {
			for (Coupon c : existingCouponsInDB) 
			{
				if(c.getCompany().getId() == coupon.getCompany().getId())
					throw new CouponAlreadyExistsWithSameTitleException();
			}
		}
	}
	
	/**
	 * Checks if coupon already exists in <b>COUPONS</b> table.<br>
	 * Otherwise, exception will be thrown.<br>
	 * Coupon's id and companyID cannot be updated.
	 * 
	 * @param coupon
	 * @throws SQLException
	 * @throws CouponNotExistsException
	 */
	public void updateCoupon(Coupon coupon) throws SQLException, CouponNotExistsException 
	{
		if(couponDB.existsById(coupon.getId())) {
			couponDB.save(coupon);
			System.out.println("Coupon " + coupon.getId() + " updated.");
		}
		else	
			throw new CouponNotExistsException();
	}
	
	/**
	 * Retrieves coupon's data based on ID from <b>COUPONS</b> table.
	 * 
	 * @param couponID
	 * @return
	 * @throws SQLException
	 */
	public Coupon getOneCoupon(int couponID) throws SQLException, CouponNotExistsException {
		Coupon coupon = couponDB.findById(couponID).orElseThrow(() -> new CouponNotExistsException());
		return coupon;
	}
	
	/**
	 * Retrieves all company's coupons by companyID from <b>COUPONS</b> table..
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Set<Coupon> getCompanyCoupons() throws SQLException
	{
		Set<Coupon> coupons = couponDB.findCouponsByCompany(companyDB.findById(companyID).get());
		if(coupons.isEmpty())
			System.out.println("Company doesn't have coupons.");
		return coupons;
	}
	
	/**
	 * Retrieves all company's coupons based on category from <b>COUPONS</b> table.
	 * 
	 * @param category
	 * @return
	 * @throws SQLException
	 */
	public Set<Coupon> getCompanyCoupons(CategoryType category) throws SQLException
	{
		Set<Coupon> coupons = couponDB.findCouponsByCategory(category, companyID);
		if(coupons.isEmpty())
			System.out.println("Company doesn't have coupons by mentioned category.");
		return coupons;
	}
	
	/**
	 * Retrieves company's coupons based on max price from <b>COUPONS</b> table.
	 * 
	 * @param maxPrice
	 * @return
	 * @throws SQLException
	 */
	public Set<Coupon> getCompanyCoupons(double maxPrice) throws SQLException
	{
		Set<Coupon> coupons = couponDB.findCouponsByMaxPrice(maxPrice, companyID);
		if(coupons.isEmpty())
			System.out.println("Company doesn't have coupons.");
		return coupons;
	}
	
	/**
	 * Retrieves company's data from <b>COMPANIES</b> and <b>COUPONS</b> tables.
	 * 
	 * @return
	 * @throws SQLException
	 * @throws CompanyNotExistsException 
	 */
	public Company getCompanyDetails() throws CompanyNotExistsException, SQLException 
	{
		Company company = companyDB.findById(companyID).orElseThrow(() -> new CompanyNotExistsException());
		company.setCoupons(getCompanyCoupons());
		return company;		
	}
	
	public Company getOneCompany(int companyID) throws SQLException,CompanyNotExistsException 
	{
		Company company = companyDB.findById(companyID).orElseThrow(() -> new CompanyNotExistsException());
		return company;
	}
	
	/**
	 * Company removes coupon.<br>
	 * Coupon will be deleted from <b>CUSTOMERS_VS_COUPONS</b> and from <b>COUPONS</b> tables.
	 * 
	 * @param couponID
	 * @throws SQLException
	 */
	public void deleteCoupon(int couponID) throws SQLException 
	{
		int purchaseCount = 0;

		Coupon coupon = couponDB.findCouponsById(couponID);
		if (!coupon.equals(null)) 
		{
			Set<Customer> customers = coupon.getCustomers();
			for (Customer customer : customers) 
			{
				deletePurchaseFromCustomersVSCouponsTable(coupon, customer);
				purchaseCount++;

			} 
			printCouponDeleteResults(couponID, purchaseCount);
			deleteCouponFromCouponsTable(couponID);
		}
		else 
			System.out.println("Coupon " + couponID + " not found.");
	}

	public void deletePurchaseFromCustomersVSCouponsTable(Coupon coupon, Customer customer) 
	{
		customer.removeCoupon(coupon);
		customerDB.save(customer);
	}

	public void deleteCouponFromCouponsTable(int couponID) {
		couponDB.deleteById(couponID);
		System.out.println("Coupon " + couponID + " deleted from COUPONS table.");

	}
	
	private void printCouponDeleteResults(int couponID, int purchaseCount) 
	{
		if(purchaseCount > 0)
			System.out.println(purchaseCount + " coupon/s deleted from CUSTOMERS_VS_COUPONS table.");
		else
			System.out.println("Coupon " + couponID + " not purchased by any customer.");
	}
}
