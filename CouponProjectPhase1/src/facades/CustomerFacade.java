package facades;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import bean.Category;
import bean.Coupon;
import bean.Customer;
import exceptions.CouponDateExpiredException;
import exceptions.CouponNotAvailableException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;
import exceptions.LoginFailedException;

public class CustomerFacade extends ClientFacade 
{
	private int customerID;
		
	public CustomerFacade() {
		super();}

	public int getCustomerID() {
		return customerID;
	}

	@Override
	public boolean login(String email, String password) throws SQLException, LoginFailedException
	{
		customerID = customerDB.checkAllLoginDetails(email, password);
		if(customerID != 0) {
			System.out.println("\nINFO: You logged in as Customer user with id=" + customerID + "!");
			return true;
		}
		throw new LoginFailedException();
	}
		
	/**
	 * Adding coupon to <b>CUSTOMERS_VS_COUPONS</b> as a history of purchase.<br>
	 * Checks cases where coupon cannot be purchased:<br>
	 * - if customer already purchased this coupon.
	 * - if amount of coupon is 0.
	 * - if exp.date already expired for coupon.
	 * Decreases coupon amount by 1.
	 */
	public void purchaseCoupon(Coupon coupon) throws SQLException, CouponNotAvailableException, CouponDateExpiredException, EntityAlreadyExistsException 
	{
		isCouponAlreadyExists(coupon);
		isCouponAvailable(coupon);
		isCouponExpired(coupon);
		couponDB.addCouponPurchase(customerID, coupon.getId());
	}

	/**
	 * Checks whether coupon's end date expired or not
	 */
	private void isCouponExpired(Coupon coupon) throws CouponDateExpiredException {
		Date today = Date.valueOf(LocalDate.now());
		if(coupon.getEndDate().before(today)) {
			throw new CouponDateExpiredException();
		}
	}
	/**
	 * Checks if coupon's amount bigger than 0.
	 */
	private void isCouponAvailable(Coupon coupon) throws CouponNotAvailableException 
	{
		if(coupon.getAmount() == 0)
			throw new CouponNotAvailableException();
	}

	private void isCouponAlreadyExists(Coupon coupon) throws SQLException, EntityAlreadyExistsException 
	{
		if(customerDB.isCustomerPurchasedCoupon(customerID, coupon.getId()))
			throw new EntityAlreadyExistsException(coupon.getTitle());
	}
	
	/**
	 * Returns all purchased coupons by customer
	 */
	public List<Coupon> getCustomerCoupons() throws SQLException, EntityNotFoundException
	{
		List<Coupon> coupons = customerDB.getAllPurchasedCouponsByCustomer(customerID);
		if(coupons.isEmpty())
			throw new EntityNotFoundException("coupons");
		return coupons;
	}
	
	/**
	 * Returns customer's purchased coupons by category
	 */
	public List<Coupon> getCustomerCoupons(Category category) throws SQLException, EntityNotFoundException
	{
		List<Coupon> coupons = customerDB.getPurchasedCouponsByCustomerAndCategory(customerID, category);
		if(coupons.isEmpty())
			throw new EntityNotFoundException("coupons");
		return coupons;
	}
	/**
	 * Returns coupons based on max price.
	 */
	public List<Coupon> getCustomerCoupons(double maxPrice) throws SQLException, EntityNotFoundException
	{
		List<Coupon> coupons = customerDB.getPurchasedCouponsByCustomerAndMaxPrice(customerID, maxPrice);
		if(coupons.isEmpty())
			throw new EntityNotFoundException("coupons");
		return coupons;
	}
	
	/**
	 * Retrieves all customer's data.
	 */
	public Customer getCustomerDetails() throws SQLException, EntityNotFoundException {
		Customer c = customerDB.getOne(customerID);
		if(c == null)
			throw new EntityNotFoundException("customer with id: " + customerID);
		c.setCoupons(getCustomerCoupons());
		return c;
	}
}
