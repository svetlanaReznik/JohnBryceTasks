package com.project.ProjectPhase2.facade;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.project.ProjectPhase2.beans.CategoryType;
import com.project.ProjectPhase2.beans.Company;
import com.project.ProjectPhase2.beans.Coupon;
import com.project.ProjectPhase2.beans.Customer;
import com.project.ProjectPhase2.exceptions.CompanyNotExistsException;
import com.project.ProjectPhase2.exceptions.CouponAlreadyExistsException;
import com.project.ProjectPhase2.exceptions.CouponDateExpiredException;
import com.project.ProjectPhase2.exceptions.CouponNotAvailableException;
import com.project.ProjectPhase2.exceptions.CouponNotExistsException;
import com.project.ProjectPhase2.exceptions.CustomerNotExistsException;
import com.project.ProjectPhase2.exceptions.LoginFailedException;

@Service
public class CustomerFacade extends ClientFacade{

	private int customerID;
	
	public int getCustomerID() {
		return customerID;
	}
	
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	@Override
	public int login(String email, String password) throws SQLException, LoginFailedException 
	{
		Customer customer = customerDB.findCustomersByEmailAndPassword(email, password);
		if(customer != null) {
			System.out.println("You logged in as Customer user with id=" + customer.getId());
			return customer.getId();
		}
		throw new LoginFailedException();
	}
	
	public Coupon getOneCoupon(int couponID) throws CouponNotExistsException {
		Coupon coupon = couponDB.findById(couponID).orElseThrow(() -> new CouponNotExistsException());
		return coupon;
	}
	
	/**
	 * Returns all purchased coupons by customer
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Set<Coupon> getCustomerCoupons() throws SQLException
	{
		Set<Coupon> coupons = couponDB.findPurchasedCouponsByCustomer(customerID);
		if(coupons.isEmpty())
			System.out.println("Customer doesn't have coupons.");
		return coupons;
	}
	
	/**
	 * Returns customer's purchased coupons by category
	 * 
	 * @param category
	 * @return
	 * @throws SQLException
	 */
	public Set<Coupon> getCustomerCoupons(CategoryType category) throws SQLException
	{
		Set<Coupon> coupons = couponDB.findPurchasedCouponsByCategory(category, customerID);
		if(coupons.isEmpty())
			System.out.println("Customer doesn't have coupons by mentioned category.");
		return coupons;
	}

	/**
	 * Returns coupons based on max price.
	 * 
	 * @param maxPrice
	 * @return
	 * @throws SQLException
	 */
	public Set<Coupon> getCustomerCoupons(double maxPrice) throws SQLException
	{
		Set<Coupon> coupons = couponDB.findPurchasedCouponsByMaxPrice(maxPrice, customerID);
		if(coupons.isEmpty())
			System.out.println("Customer doesn't have coupons in this range.");
		return coupons;
	}
	
	/**
	 * Adding coupon to <b>CUSTOMERS_VS_COUPONS</b> as a history of purchase.<br>
	 * Checks cases where coupon cannot be purchased:<br>
	 * - if customer already purchased this coupon.
	 * - if amount of coupon is 0.
	 * - if exp.date already expired for coupon.
	 * Decreases coupon amount by 1.
	 * 
	 * @param couponb
	 * @throws SQLException
	 * @throws CouponAlreadyExistsException
	 * @throws CouponNotAvailableException
	 * @throws CouponDateExpiredException
	 */
	public void purchaseCoupon(Coupon coupon) throws SQLException, CouponAlreadyExistsException, CouponNotAvailableException, CouponDateExpiredException 
	{
		isCouponAlreadyPurchased(coupon);
		isCouponAvailable(coupon);
		isCouponExpired(coupon);
		addCouponPurchase(coupon, customerID);
		updateCouponAmount(coupon);
		System.out.println("Purchased couponID=" + coupon.getId());
	}
	
	private void isCouponAlreadyPurchased(Coupon coupon) throws CouponAlreadyExistsException 
	{
		if(!couponDB.findPurchasedCouponById(coupon.getId(), customerID).isEmpty())
			throw new CouponAlreadyExistsException();
	}
	
	/**
	 * Checks if coupon's amount bigger than 0.
	 * 
	 * @param coupon
	 * @throws CouponNotAvailableException
	 */
	private void isCouponAvailable(Coupon coupon) throws CouponNotAvailableException 
	{
		if(coupon.getAmount() == 0)
			throw new CouponNotAvailableException();
	}

	/**
	 * Checks whether coupon's end date expired or not
	 * 
	 * @param coupon
	 * @throws CouponDateExpiredException
	 */
	private void isCouponExpired(Coupon coupon) throws CouponDateExpiredException {
		Date today = Date.valueOf(LocalDate.now());
		if(coupon.getEndDate().before(today)) {
			throw new CouponDateExpiredException();
		}
	}
	
	void addCouponPurchase(Coupon coupon, int customerID) 
	{
		Customer customer = customerDB.findById(customerID).get();
		customer.addCoupon(coupon);
		customerDB.save(customer);
	}

	private void updateCouponAmount(Coupon coupon) {
		coupon.setAmount(coupon.getAmount()-1);
		couponDB.save(coupon);
	}
	
	/**
	 * Retrieves all customer's data.
	 * 
	 * @return
	 * @throws SQLException
	 * @throws CustomerNotExistsException
	 */
	public Customer getCustomerDetails() throws CustomerNotExistsException, SQLException {
		Customer customer = customerDB.findById(customerID).orElseThrow(() -> new CustomerNotExistsException());
		customer.setCoupons(getCustomerCoupons());
		return customer;
	}
}


