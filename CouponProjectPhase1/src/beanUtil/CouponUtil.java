package beanUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bean.Coupon;

public class CouponUtil {
public static List<String> couponHeadersList = Arrays.asList("id", "company id", "category", "title", "description", "start date", "end date", "amount", "price", "image");
	
	public static List<String> getCouponHeadersList() {
		return couponHeadersList;
	}
	
	public static List<String> fillCouponRow(Coupon coupon) 
	{
		List<String> rows = new ArrayList<>();
		rows.addAll(Arrays.asList(String.valueOf(coupon.getId()), 
								  String.valueOf(coupon.getCompanyID()), 
								  String.valueOf(coupon.getCategory().toString()), 
								  coupon.getTitle(), 
								  coupon.getDescription(), 
								  String.valueOf(coupon.getStartDate().toString()), 
								  String.valueOf(coupon.getEndDate().toString()), 
								  String.valueOf(coupon.getAmount()), 
								  String.valueOf(coupon.getPrice()), 
								  coupon.getImage()));
		return rows;
	}
}
