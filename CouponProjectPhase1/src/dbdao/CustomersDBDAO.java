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
import bean.Customer;
import dao.CustomersDAO;
import db.DBUtils;

public class CustomersDBDAO implements CustomersDAO
{
	private static final String QUERY_IS_CUSTOMER_EXISTS = "SELECT * FROM customers WHERE email=? AND password=?";
	private static final String QUERY_ADD_CUSTOMER = "INSERT INTO customers(first_name, last_name, email, password) VALUES(?, ?, ?, ?);";
	private static final String QUERY_UPDATE_CUSTOMER = "UPDATE customers SET first_name=?, last_name=?, email=?, password=? WHERE id=?";
	private static final String QUERY_DELETE_CUSTOMER_FROM_CUSTOMERS_VS_COUPONS = "DELETE FROM customers_vs_coupons WHERE customer_id IN (SELECT id FROM customers WHERE customer_id=?)";
	private static final String QUERY_DELETE_CUSTOMER = "DELETE FROM customers WHERE id=?";
	private static final String QUERY_GET_ALL_CUSTOMERS = "SELECT * FROM customers";
	private static final String QUERY_GET_ONE_CUSTOMER= "SELECT * FROM customers WHERE id=?";
	private static final String QUERY_GET_ALL_PURCHASED_COUPONS = "SELECT * FROM coupons JOIN categories ON coupons.category_id = categories.id WHERE coupons.id IN (SELECT coupon_id FROM customers_vs_coupons WHERE customer_id=?)";
	private static final String QUERY_GET_PURCHASED_COUPONS_BY_CUSTOMER_AND_CATEGORY = "SELECT * FROM coupons JOIN categories ON coupons.category_id = categories.id "   
	                                                                                    + "WHERE coupons.id IN (SELECT coupon_id FROM customers_vs_coupons WHERE customer_id=?) AND coupons.category_id=?";
	private static final String QUERY_GET_PRURCHASED_COUPONS_BY_CUSTOMER_AND_MAXPRICE = "SELECT * FROM coupons JOIN categories ON coupons.category_id = categories.id WHERE coupons.id IN (SELECT coupon_id FROM customers_vs_coupons WHERE customer_id=?) AND price<?";
	private static final String QUERY_GET_CUSTOMER_BY_EMAIL = "SELECT * FROM customers WHERE email=?";
	private static final String QUERY_CHECK_ALL_LOGIN_DETAILS = "SELECT * FROM customers WHERE email=? AND password=?";
	private static final String QUERY_IS_CUSTOMER_PURCHASED_COUPON = "SELECT * FROM customers_vs_coupons WHERE customer_id=? AND coupon_id=?";
	
	@Override
	public boolean isCustomerExists(String email, String password) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, email);
		map.put(2, password);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_IS_CUSTOMER_EXISTS, map);
		if(resultSet.next())
			return true;
		return false;
	}

	@Override
	public int add(Customer customer) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, customer.getFirstName());
		map.put(2, customer.getLastName());
		map.put(3, customer.getEmail());
		map.put(4, customer.getPassword());
		ResultSet resultSet = DBUtils.runQueryWithAutoIncUpdate(QUERY_ADD_CUSTOMER, map);
		return resultSet.getInt(1); //to return auto-generated id
	}

	@Override
	public void update(Customer customer) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, customer.getFirstName());
		map.put(2, customer.getLastName());
		map.put(3, customer.getEmail());
		map.put(4, customer.getPassword());
		map.put(5, customer.getId());
		
		DBUtils.runQuery(QUERY_UPDATE_CUSTOMER, map);
	}


	@Override
	public int deleteCouponPurchases(int customerID) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, customerID);
		return DBUtils.runQueryWithQuantityResult(QUERY_DELETE_CUSTOMER_FROM_CUSTOMERS_VS_COUPONS, map);
	}

	@Override
	public int delete(int customerID) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, customerID);
		return DBUtils.runQueryWithQuantityResult(QUERY_DELETE_CUSTOMER, map);
	}
	
	@Override
	public List<Customer> getAll() throws SQLException {
		List<Customer> customers = new ArrayList<>();
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ALL_CUSTOMERS,new HashMap<Integer, Object>());
		
		while(resultSet.next()) 
		{
			int id = resultSet.getInt("id");
			String firstName = resultSet.getString("first_name");
			String last_name = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			String password = resultSet.getString("password");
			customers.add(new Customer(id, firstName, last_name, email, password));
		}
		return customers;
	}

	@Override
	public Customer getOne(int customerID) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, customerID);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ONE_CUSTOMER,map);
		
		if(resultSet.next()) 
		{
			String first_name = resultSet.getString("first_name");
			String last_name = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			String password = resultSet.getString("password");
			return new Customer(customerID, first_name, last_name, email, password);
		}
		return null;
	}

	@Override
	public List<Coupon> getAllPurchasedCouponsByCustomer(int customerID) throws SQLException {
		List<Coupon> allPurchasedCouponsByCustomer = new ArrayList<>();
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, customerID);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ALL_PURCHASED_COUPONS,map);
		
		while(resultSet.next()) 
		{
			int couponId = resultSet.getInt("id");
			int companyID = resultSet.getInt("company_id");
			String categoryName = resultSet.getString("name");
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			Date startDate = resultSet.getDate("start_date");
			Date endDate = resultSet.getDate("end_date");
			int amount = resultSet.getInt("amount");
			double price = resultSet.getDouble("price");
			String image = resultSet.getString("image");
			allPurchasedCouponsByCustomer.add(new Coupon(couponId, companyID, amount, Category.valueOf(categoryName) , title, description, image, startDate, endDate, price));
		} 
		return allPurchasedCouponsByCustomer;
	}

	@Override
	public List<Coupon> getPurchasedCouponsByCustomerAndCategory(int customerID, Category category) throws SQLException {
		List<Coupon> allPurchasedCouponsByCustomer = new ArrayList<>();
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, customerID);
		map.put(2, category.getValue());
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_PURCHASED_COUPONS_BY_CUSTOMER_AND_CATEGORY,map);
		
		while(resultSet.next()) 
		{
			int couponId = resultSet.getInt("id");
			int companyID = resultSet.getInt("company_id");
			String categoryName = resultSet.getString("name");
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			Date startDate = resultSet.getDate("start_date");
			Date endDate = resultSet.getDate("end_date");
			int amount = resultSet.getInt("amount");
			double price = resultSet.getDouble("price");
			String image = resultSet.getString("image");

			allPurchasedCouponsByCustomer.add(new Coupon(couponId, companyID, amount, Category.valueOf(categoryName) , title, description, image, startDate, endDate, price));
		} 
		return allPurchasedCouponsByCustomer;
	}

	@Override
	public List<Coupon> getPurchasedCouponsByCustomerAndMaxPrice(int customerID, double maxPrice) throws SQLException {
		List<Coupon> allPurchasedCouponsByCustomer = new ArrayList<>();
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, customerID);
		map.put(2, maxPrice);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_PRURCHASED_COUPONS_BY_CUSTOMER_AND_MAXPRICE,map);
		
		while(resultSet.next()) 
		{
			int couponId = resultSet.getInt("id");
			int companyID = resultSet.getInt("company_id");
			String categoryName = resultSet.getString("name");
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			Date startDate = resultSet.getDate("start_date");
			Date endDate = resultSet.getDate("end_date");
			int amount = resultSet.getInt("amount");
			double price = resultSet.getDouble("price");
			String image = resultSet.getString("image");

			allPurchasedCouponsByCustomer.add(new Coupon(couponId, companyID, amount, Category.valueOf(categoryName) , title, description, image, startDate, endDate, price));
		} 
		return allPurchasedCouponsByCustomer;
	}

	@Override
	public boolean getCustomerByEmail(String email) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, email);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_CUSTOMER_BY_EMAIL,map);
		
		if(resultSet.next()) 
			return true;
		return false;
	}

	@Override
	public int checkAllLoginDetails(String email, String password) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, email);
		map.put(2, password);
		
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_CHECK_ALL_LOGIN_DETAILS,map);
		if(resultSet.next()) { 
			int id = resultSet.getInt(1);
			return id;
		}
		return 0;
	}

	@Override
	public boolean isCustomerPurchasedCoupon(int customerID, int couponID) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, customerID);
		map.put(2, couponID);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_IS_CUSTOMER_PURCHASED_COUPON,map);
		
		if(resultSet.next()) 
			return true;
		return false;
	}
}
