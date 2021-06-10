package com.project.ProjectPhase2.db;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.ProjectPhase2.beans.CategoryType;
import com.project.ProjectPhase2.beans.Company;
import com.project.ProjectPhase2.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> 
{
	boolean existsCouponsByTitleOrId(String title, String couponID);
	boolean existsCouponsByTitle(String title);
	
	Set<Coupon> findCouponsByCompany(Company company);
	
	Set<Coupon> findCouponsEndDateBefore(Date date);
	
	@Query("select coupon from Company company join company.coupons coupon where coupon.category like ?1 and company.id like ?2")
	Set<Coupon> findCouponsByCategory(CategoryType category,int companyId);

	@Query("select coupon from Company company join company.coupons coupon where coupon.price <= ?1 and company.id like ?2")
	Set<Coupon> findCouponsByMaxPrice(double price, int companyId);
	
	@Query("select coupon from Customer customer join customer.coupons coupon where coupon.category like ?1 and customer.id like ?2")
	Set<Coupon> findPurchasedCouponsByCategory(CategoryType category,int custId);

	@Query("select coupon from Customer customer join customer.coupons coupon where coupon.price <= ?1 and customer.id like ?2")
	Set<Coupon> findPurchasedCouponsByMaxPrice(double price, int custId);
	
	@Query("select coupon from Customer customer join customer.coupons coupon where coupon.id like ?1 and customer.id like ?2")
	Set<Coupon> findPurchasedCouponById(int id, int customerId);
	
	@Query("select coupon from Customer customer join customer.coupons coupon where customer.id like ?1")
	Set<Coupon> findPurchasedCouponsByCustomer(int customerId);
	
	Set<Coupon> findCouponsByTitle(String title);
	
	Coupon findCouponsById(int couponID);
}
