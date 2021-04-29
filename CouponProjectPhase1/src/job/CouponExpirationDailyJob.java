package job;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import bean.Coupon;
import dbdao.CouponsDBDAO;

/**
 * Thread will be running every day in order to check which coupons already expired and can be deleted. <br>
 * Coupons will be deleted from <b>COUPONS</b> table. <br>
 * Also history of purchase will be deleted from <b>CUSTOMERS_VS_COUPONS</b> table.
 * @author Svetlana Reznik
 */
public class CouponExpirationDailyJob extends Thread {
	private CouponsDBDAO couponsDB;
	private boolean quit = true;

	public CouponExpirationDailyJob(CouponsDBDAO couponsDB) {
		super();
		this.couponsDB = couponsDB;
	}

	public CouponsDBDAO getCouponsDB() {
		return couponsDB;
	}

	public void setCouponsDB(CouponsDBDAO couponsDB) {
		this.couponsDB = couponsDB;
	}

	@Override
	public void run() 
	{
		while (quit) {

			try {
				List<Coupon> coupons = couponsDB.getAll();
				Date today = Date.valueOf(LocalDate.now());

				for (Coupon coupon : coupons) {
					if (coupon.getEndDate().before(today)) {
						couponsDB.deleteCouponPurchases(coupon.getId());
						couponsDB.delete(coupon.getId());
					}
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
