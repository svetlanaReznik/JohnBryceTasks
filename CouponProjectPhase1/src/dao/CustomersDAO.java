package dao;

import java.sql.SQLException;
import java.util.List;

import bean.Category;
import bean.Coupon;
import bean.Customer;

import java.util.HashMap;



public interface CustomersDAO extends CRUDInterface<Customer>
{
	boolean isCustomerExists(String email, String password) throws SQLException;
	List<Coupon> getAllPurchasedCouponsByCustomer(int customerID) throws SQLException;
	List<Coupon> getPurchasedCouponsByCustomerAndCategory(int customerID, Category category) throws SQLException;
	List<Coupon> getPurchasedCouponsByCustomerAndMaxPrice(int customerID, double maxPrice) throws SQLException;
	boolean getCustomerByEmail(String email) throws SQLException ;
	int checkAllLoginDetails(String email, String password) throws SQLException;
	boolean isCustomerPurchasedCoupon(int customerID, int couponID) throws SQLException;
}

