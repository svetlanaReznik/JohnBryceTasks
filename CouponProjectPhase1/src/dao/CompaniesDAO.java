package dao;

import java.sql.SQLException;
import java.util.List;

import bean.Category;
import bean.Company;
import bean.Coupon;

public interface CompaniesDAO extends CRUDInterface<Company>
{
	boolean isCompanyExists(String email, String password) throws SQLException;
	int deleteCouponsFromCouponsTableBasedOnCompanyID(int companyID) throws SQLException;
	boolean getCompanyByName(String name) throws SQLException;
	boolean getCompanyByEmail(String email) throws SQLException;
	int checkAllLoginDetails(String email, String password) throws SQLException;
	List<Coupon> getAllCouponsByCompanyID(int companyID) throws SQLException;
	List<Coupon> getAllCouponsByCategory(Category category, int companyID) throws SQLException;
	List<Coupon> getAllCouponsByMaxPrice(double maxPrice, int companyID) throws SQLException;
}
