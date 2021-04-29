package facades;

import static beanUtil.CouponUtil.fillCouponRow;
import static beanUtil.CouponUtil.getCouponHeadersList;
import static beanUtil.TableGenerator.printRow;

import java.sql.SQLException;
import java.util.List;

import bean.Category;
import bean.Company;
import bean.Coupon;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;
import exceptions.LoginFailedException;

public class CompanyFacade extends ClientFacade 
{
	private int companyID;

	public CompanyFacade() {
		super();}

	public int getCompanyID() {
		return companyID;
	}
	
	/**
	 * Checks the correctness of login details for Company user.
	 */
	@Override
	public boolean login(String email, String password) throws SQLException, LoginFailedException
	{
		companyID = companyDB.checkAllLoginDetails(email, password);
		if(companyID != 0) {
			System.out.println("\nINFO: You logged in as Company user with id=" + companyID + "!");
			return true;
		}
		throw new LoginFailedException();
	}
	
	/**
	 * Adding new coupon to <b>COUPONS<b> table.
	 * Throws exception in case coupon already exists with same title for logged in company.
	 */
	public void addCoupon(Coupon coupon) throws SQLException, EntityAlreadyExistsException 
	{
		isCouponAlreadyExistsWithSameTitle(coupon);
		int id = couponDB.add(coupon);
		coupon.setId(id);
		printRow(getCouponHeadersList(), fillCouponRow(coupon));	
	}

	private void isCouponAlreadyExistsWithSameTitle(Coupon coupon) throws SQLException, EntityAlreadyExistsException 
	{
		List<Coupon> coupons = companyDB.getAllCouponsByCompanyID(companyID);
		for (Coupon c : coupons) 
		{
			if(coupon.getTitle().equals(c.getTitle()) && coupon.getCompanyID() == c.getCompanyID())
				throw new EntityAlreadyExistsException(coupon.getTitle());
		}
	}

	/**
	 * Checks if coupon already exists in <b>COUPONS</b> table.<br>
	 * Otherwise, exception will be thrown.<br>
	 * Coupon's id and companyID cannot be updated.
	 */
	public void updateCoupon(Coupon coupon) throws SQLException, EntityNotFoundException 
	{
		if(couponDB.getOne(coupon.getId()) == null)
			throw new EntityNotFoundException("coupon");
		couponDB.update(coupon);
		printRow(getCouponHeadersList(), fillCouponRow(coupon));
	}
	
	/**
	 * Retrieves coupon's data based on ID from <b>COUPONS</b> table.
	 */
	public Coupon getOneCoupon(int couponID) throws SQLException, EntityNotFoundException {
		Coupon coupon = couponDB.getOne(couponID);
	    if (coupon == null) 
	        throw new EntityNotFoundException("coupon with id: " + couponID);
	    return coupon;
	}
	
	/**
	 * Company removes coupon.<br>
	 * Coupon will be deleted from <b>CUSTOMERS_VS_COUPONS</b> and from <b>COUPONS</b> tables.
	 */
	public void deleteCoupon(int couponID) throws SQLException, EntityNotFoundException 
	{
		int res1 = couponDB.deleteCouponPurchases(couponID);
		System.out.println("INFO:");
		if (res1 > 0)
			System.out.println(res1 + " purchases of " + couponID + " deleted from CUSTOMERS_VS_COUPONS table");
		else
			System.out.println("Coupon not purchased by any customer.");
		
		int res2 = couponDB.delete(couponID);
		if(res2 > 0)
			System.out.println("\nCoupon " + couponID + " deleted from COUPONS table");
		else
			throw new EntityNotFoundException("coupon with id: " + couponID);
	}

	/**
	 * Retrieves all company's coupons by companyID from <b>COUPONS</b> table..
	 */
	public List<Coupon> getCompanyCoupons() throws SQLException, EntityNotFoundException
	{
		List<Coupon> coupons = companyDB.getAllCouponsByCompanyID(companyID);
		if(coupons.isEmpty())
			throw new EntityNotFoundException("coupons");
		return coupons;
	}
	
	/**
	 * Retrieves all company's coupons based on category from <b>COUPONS</b> table.
	 */
	public List<Coupon> getCompanyCoupons(Category category) throws SQLException, EntityNotFoundException
	{
		List<Coupon> coupons = companyDB.getAllCouponsByCategory(category,companyID);
		if(coupons.isEmpty())
			throw new EntityNotFoundException("coupons");
		return coupons;
	}

	/**
	 * Retrieves company's coupons based on max price from <b>COUPONS</b> table.
	 */
	public List<Coupon> getCompanyCoupons(double maxPrice) throws SQLException, EntityNotFoundException
	{
		List<Coupon> coupons = companyDB.getAllCouponsByMaxPrice(maxPrice, companyID);
		if(coupons.isEmpty())
			throw new EntityNotFoundException("coupons");
		return coupons;
	}
	
	/**
	 * Retrieves company's data from <b>COMPANIES</b> and <b>COUPONS</b> tables.
	 */
	public Company getCompanyDetails() throws SQLException, EntityNotFoundException 
	{
		Company c = companyDB.getOne(companyID);
		if(c == null) 
			throw new EntityNotFoundException("company with id: " + companyID);
		c.setCoupons(getCompanyCoupons());
		return c;		
	}
}
