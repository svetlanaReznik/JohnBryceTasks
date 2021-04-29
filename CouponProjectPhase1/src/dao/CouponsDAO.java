package dao;

import java.sql.SQLException;
import java.util.List;

import bean.Coupon;

public interface CouponsDAO  extends CRUDInterface<Coupon>
{
	void addCouponPurchase(int customerID, int couponID) throws SQLException;
	int deleteCouponPurchase(int customerID, int couponID) throws SQLException;
}
