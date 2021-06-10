package com.project.ProjectPhase2.thread;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.project.ProjectPhase2.beans.Coupon;
import com.project.ProjectPhase2.db.CompanyRepository;
import com.project.ProjectPhase2.db.CouponRepository;
import com.project.ProjectPhase2.facade.CompanyFacade;

/**
 * Thread will be running every day in order to check which coupons already expired and can be deleted. <br>
 * Coupons will be deleted from <b>COUPONS</b> table. <br>
 * Also history of purchase will be deleted from <b>CUSTOMERS_VS_COUPONS</b> table.
 * @author Svetlana Reznik
 */

@Service
public class CouponExpirationDailyJob extends Thread 
{
	private boolean quit = true;

	@Autowired
	private CouponRepository couponDB;
	
	@Autowired
	private CompanyFacade companyF;

	public CouponExpirationDailyJob() {
		super();
	}

	@Override
	public void run() 
	{
		while (quit) {

			try {
				Set<Coupon> coupons = couponDB.findCouponsEndDateBefore(Date.valueOf(LocalDate.now()));

				for (Coupon coupon : coupons) {
						companyF.deleteCoupon(coupon.getId());
				}
				Thread.sleep(24 * 60 * 60 * 1000);
			} catch (InterruptedException | SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void stopRunning() {
		quit = false;
		this.interrupt();
	}
}
