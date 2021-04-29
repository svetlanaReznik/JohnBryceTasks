package dbdao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Category;
import bean.Company;
import bean.Coupon;
import dao.CompaniesDAO;
import db.DBUtils;

public class CompaniesDBDAO implements CompaniesDAO
{
	private static final String QUERY_IS_COMPANY_EXISTS = "SELECT * FROM companies WHERE email=? AND password=?";
	private static final String QUERY_ADD_COMPANY = "INSERT INTO companies (`name`, `email`, `password`) VALUES (?, ?, ?)";
	private static final String QUERY_UPDATE_COMPANY = "UPDATE companies SET name=?, email=?, password=? WHERE id=?";
	private static final String QUERY_DELETE_COUPONS_FROM_CUSTOMERS_VS_COUPONS = "DELETE FROM customers_vs_coupons WHERE coupon_id in (SELECT id FROM coupons WHERE company_id=?)";
	private static final String QUERY_DELETE_COUPONS_FROM_COUPONS = "DELETE FROM coupons WHERE company_id=?";
	private static final String QUERY_DELETE_COMPANY = "DELETE FROM companies WHERE id=?";
	private static final String QUERY_GET_ALL_COMPANIES = "SELECT * FROM COMPANIES";
	private static final String QUERY_GET_ONE_COMPANY= "SELECT * FROM companies WHERE id=?";
	private static final String QUERY_GET_COMPANY_BY_NAME = "SELECT * FROM companies WHERE name=?";
	private static final String QUERY_GET_COMPANY_BY_EMAIL = "SELECT * FROM companies WHERE email=?";
	private static final String QUERY_CHECK_LOGIN_DETAILS = "SELECT * FROM companies WHERE email=? AND password=?";
	private static final String QUERY_GET_ALL_COUPONS_BY_COMPANYID = "SELECT * FROM coupons JOIN categories on coupons.category_id = categories.id WHERE company_id=?";
	private static final String QUERY_GET_ALL_COUPONS_BY_CATEGORYID = "SELECT * FROM coupons JOIN categories on coupons.category_id = categories.id WHERE name=? AND company_id=?";
	private static final String QUERY_GET_ALL_COUPONS_BY_MAX_PRICE = "SELECT * FROM coupons JOIN categories on coupons.category_id = categories.id WHERE company_id=? AND price<?";
	
	
	@Override
	public boolean isCompanyExists(String email, String password) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, email);
		map.put(2, password);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_IS_COMPANY_EXISTS, map);
		if(resultSet.next())
			return true;
		return false;
	}
	
	@Override
	public int add(Company company) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, company.getName() );
		map.put(2, company.getEmail());
		map.put(3, company.getPassword());
		ResultSet resultSet = DBUtils.runQueryWithAutoIncUpdate(QUERY_ADD_COMPANY, map);
		return resultSet.getInt(1); //to return auto-generated id
	}

	@Override
	public void update(Company company) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, company.getName());
		map.put(2, company.getEmail());
		map.put(3, company.getPassword());
		map.put(4, company.getId());
		DBUtils.runQuery(QUERY_UPDATE_COMPANY, map);
	}
	
	@Override
	public int deleteCouponPurchases(int companyID) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, companyID);
		return DBUtils.runQueryWithQuantityResult(QUERY_DELETE_COUPONS_FROM_CUSTOMERS_VS_COUPONS, map);
	}
	
	@Override
	public int deleteCouponsFromCouponsTableBasedOnCompanyID(int companyID) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, companyID);
		return DBUtils.runQueryWithQuantityResult(QUERY_DELETE_COUPONS_FROM_COUPONS, map);
	}
	
	@Override
	public int delete(int companyID) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, companyID);
		return DBUtils.runQueryWithQuantityResult(QUERY_DELETE_COMPANY, map);
	}

	@Override
	public List<Company> getAll() throws SQLException {
		List<Company> results = new ArrayList<>();
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ALL_COMPANIES,new HashMap<Integer, Object>());
		
		while(resultSet.next()) 
		{
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			String email = resultSet.getString(3);
			String password = resultSet.getString(4);
			Company company = new Company(id, name, email, password);
			results.add(company);
		}
		return results;
	}

	@Override
	public Company getOne(int companyID) throws SQLException {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, companyID);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ONE_COMPANY,map);
		if(resultSet.next()) 
		{
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			String password = resultSet.getString("password");
			return new Company(companyID, name, email, password);
		}
		return null;
	}
	
	@Override
	public boolean getCompanyByName(String name) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, name);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_COMPANY_BY_NAME, map);
		if(resultSet.next())
			return true;
		return false;
	}

	@Override
	public boolean getCompanyByEmail(String email) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, email);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_COMPANY_BY_EMAIL, map);
		if(resultSet.next())
			return true;
		return false;
	}

	@Override
	public int checkAllLoginDetails(String email, String password) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, email);
		map.put(2, password);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_CHECK_LOGIN_DETAILS, map);
		if(resultSet.next()) { 
			int id = resultSet.getInt(1);
			return id;
		}
		return 0;
	}

	@Override
	public List<Coupon> getAllCouponsByCompanyID(int companyID) throws SQLException {
		List<Coupon> coupons = new ArrayList<>();
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, companyID);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ALL_COUPONS_BY_COMPANYID,map);
		
		while(resultSet.next()) 
		{
			int couponId = resultSet.getInt("id");
			String categoryName = resultSet.getString("name");
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			Date startDate = resultSet.getDate("start_date");
			Date endDate = resultSet.getDate("end_date");
			int amount = resultSet.getInt("amount");
			double price = resultSet.getDouble("price");
			String image = resultSet.getString("image");

			coupons.add(new Coupon(couponId, companyID, amount, Category.valueOf(categoryName) , title, description, image, startDate, endDate, price));
		}
		return coupons;
	}

	@Override
	public List<Coupon> getAllCouponsByCategory(Category category, int companyID) throws SQLException {
		List<Coupon> coupons = new ArrayList<>();
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, category.name());
		map.put(2, companyID);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ALL_COUPONS_BY_CATEGORYID,map);
		
		while(resultSet.next()) 
		{
			int couponId = resultSet.getInt("id");
			int compID = resultSet.getInt("company_id");
			String categoryName = resultSet.getString("name");
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			Date startDate = resultSet.getDate("start_date");
			Date endDate = resultSet.getDate("end_date");
			int amount = resultSet.getInt("amount");
			double price = resultSet.getDouble("price");
			String image = resultSet.getString("image");

			coupons.add(new Coupon(couponId, compID, amount, Category.valueOf(categoryName) , title, description, image, startDate, endDate, price));
		}
		return coupons;
	}

	@Override
	public List<Coupon> getAllCouponsByMaxPrice(double maxPrice, int companyID) throws SQLException {
		List<Coupon> coupons = new ArrayList<>();
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, companyID);
		map.put(2, maxPrice);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ALL_COUPONS_BY_MAX_PRICE,map);
		
		while(resultSet.next()) 
		{
			int couponId = resultSet.getInt("id");
			int compID = resultSet.getInt("company_id");
			String categoryName = resultSet.getString("name");
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			Date startDate = resultSet.getDate("start_date");
			Date endDate = resultSet.getDate("end_date");
			int amount = resultSet.getInt("amount");
			double price = resultSet.getDouble("price");
			String image = resultSet.getString("image");

			coupons.add(new Coupon(couponId, compID, amount, Category.valueOf(categoryName) , title, description, image, startDate, endDate, price));
		}
		return coupons;
	}
}
