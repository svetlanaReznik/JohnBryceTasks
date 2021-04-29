package dbdao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Category;
import bean.Coupon;

import dao.CouponsDAO;
import db.DBUtils;

public class CouponsDBDAO implements CouponsDAO
{
	private static final String QUERY_ADD_COUPON = "INSERT INTO coupons(company_id, category_id, title, description, start_date, end_date, amount, price, image)"
							                        + "values(?,?,?,?,?,?,?,?,?)";
	private static final String QUERY_UPDATE_COUPON = "UPDATE coupons SET category_id=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? WHERE id=?";
	private static final String QUERY_DELETE_COUPON_FROM_CUSTOMERS_VS_COUPONS = "DELETE FROM customers_vs_coupons WHERE coupon_id IN (SELECT id FROM coupons WHERE id=?)";
	private static final String QUERY_DELETE_COUPON = "DELETE FROM coupons where id=?";
	private static final String QUERY_GET_ALL_COUPONS = "SELECT * FROM coupons JOIN categories ON coupons.category_id = categories.id";
	private static final String QUERY_GET_ONE = "SELECT * FROM coupons JOIN categories ON coupons.category_id = categories.id WHERE coupons.id=?";
	private static final String QUERY_ADD_PURCHASE = "INSERT INTO customers_vs_coupons(customer_id, coupon_id) VALUES(?,?)";
	private static final String QUERY_GET_COUPON_AMOUNT = "SELECT amount FROM coupons WHERE id=?";
	private static final String QUERY_UPDATE_COUPON_AMOUNT = "UPDATE coupons SET  amount=? WHERE id=?";
	private static final String QUERY_DELETE_COUPON_PURCHASE = "DELETE FROM customers_vs_coupons WHERE customer_id=? AND coupon_id=?";
	
	@Override
	public int add(Coupon coupon) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, coupon.getCompanyID());
		map.put(2, coupon.getCategory().getValue());
		map.put(3, coupon.getTitle());
		map.put(4, coupon.getDescription());
		map.put(5, coupon.getStartDate());
		map.put(6, coupon.getEndDate());
		map.put(7, coupon.getAmount());
		map.put(8, coupon.getPrice());
		map.put(9, coupon.getImage());
		ResultSet resultSet = DBUtils.runQueryWithAutoIncUpdate(QUERY_ADD_COUPON, map);
		return resultSet.getInt(1); //to return auto-generated id
	}

	@Override
	public void update(Coupon coupon) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, coupon.getCategory().getValue());
		map.put(2, coupon.getTitle());
		map.put(3, coupon.getDescription());
		map.put(4, coupon.getStartDate());
		map.put(5, coupon.getEndDate());
		map.put(6, coupon.getAmount());
		map.put(7, coupon.getPrice());
		map.put(8, coupon.getImage());
		map.put(9, coupon.getId());
		DBUtils.runQuery(QUERY_UPDATE_COUPON, map);
	}

	@Override
	public int deleteCouponPurchases(int couponID) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, couponID);
		return DBUtils.runQueryWithQuantityResult(QUERY_DELETE_COUPON_FROM_CUSTOMERS_VS_COUPONS, map);		
	}
	
	@Override
	public int delete(int couponID) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, couponID);
		return DBUtils.runQueryWithQuantityResult(QUERY_DELETE_COUPON, map);
	}

	@Override
	public List<Coupon> getAll() throws SQLException {
		List<Coupon> coupons = new ArrayList<>();
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ALL_COUPONS,new HashMap<Integer, Object>());
		
		while(resultSet.next()) 
		{
			int couponID = resultSet.getInt("id");
			int companyID = resultSet.getInt("company_id");
			String categoryName = resultSet.getString("category_name");// (int)coupons.category_id = (int)categories.id -> categories.name -> contr Coupon: Category category
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			Date startDate = resultSet.getDate("start_date");
			Date endDate = resultSet.getDate("end_date");
			int amount = resultSet.getInt("amount");
			double price = resultSet.getDouble("price");
			String image = resultSet.getString("image");
							
			coupons.add(new Coupon(couponID, companyID, amount, Category.valueOf(categoryName), title, description, image, startDate, endDate, price));
		}
		return coupons;
	}

	@Override
	public Coupon getOne(int couponID) throws SQLException {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, couponID);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_ONE,map);
		
		if(resultSet.next()) 
		{
			int companyID = resultSet.getInt("company_id");
			int amount = resultSet.getInt("amount");
			String categoryName = resultSet.getString("name");
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			String image = resultSet.getString("image");
			Date startDate = resultSet.getDate("start_date");
			Date endDate = resultSet.getDate("end_date");
			double price = resultSet.getDouble("price");
			
			return new Coupon(couponID, companyID, amount, Category.valueOf(categoryName), title, description, image, startDate, endDate, price);
		}
		return null;
	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) throws SQLException {
		addPurchase(customerID, couponID);
	    updateCouponAmount(couponID);
	}
	
	public void addPurchase(int customerID, int couponID) throws SQLException {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, customerID);
		map.put(2, couponID);
		DBUtils.runQuery(QUERY_ADD_PURCHASE, map);
	}

	private void updateCouponAmount(int couponID) throws SQLException {
		int amount = retrieveAmount(couponID);
		updateAmount(couponID, amount);
		System.out.println("INFO: Updated coupon's amount is: " + amount);
	}

	private int retrieveAmount(int couponID) throws SQLException {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, couponID);
		ResultSet resultSet = DBUtils.runQueryWithResult(QUERY_GET_COUPON_AMOUNT,map);
		
		int amount = 0;
		if(resultSet.next()) {
			amount = resultSet.getInt("amount");
		}
		return amount;
	}

	private void updateAmount(int couponID, int amount) throws SQLException {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, amount-1);
		map.put(2, couponID);
		DBUtils.runQuery(QUERY_UPDATE_COUPON_AMOUNT, map);
	}
	
	@Override
	public int deleteCouponPurchase(int customerID, int couponID) throws SQLException {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, customerID);
		map.put(2, couponID);
		return DBUtils.runQueryWithQuantityResult(QUERY_DELETE_COUPON_PURCHASE, map);
	}
}
