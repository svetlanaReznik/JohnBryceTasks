package tests;

import java.sql.SQLException;

import db.ConnectionPool;
import db.DBManager;
import dbdao.CouponsDBDAO;
import exceptions.CouponDateExpiredException;
import exceptions.CouponNotAvailableException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;
import exceptions.LoginFailedException;
import job.CouponExpirationDailyJob;
import util.ArtUtil;

import static tests.PositiveTests.*;
import static tests.NegativeTests.*;

public class MainTests 
{
	//private static final CouponExpirationDailyJob job = startJob();
	
	/**
	 * Initiates CouponExpirationDailyJob. <br>
	 * Tests basic, advance and negative tests for admin, company and customer users.<br>
	 * Stops thread and closes connections to DB.
	 * @throws CouponDateExpiredException 
	 * @throws CouponNotAvailableException 
	 */
	public static void testAll() throws SQLException, InterruptedException, LoginFailedException, EntityAlreadyExistsException, EntityNotFoundException, CouponNotAvailableException, CouponDateExpiredException 
	{	
		CouponExpirationDailyJob job = null;
		init(job);
		tests();
		stop(job);
	}

	private static void init(CouponExpirationDailyJob job) throws SQLException {
		DBManager.dropAndCreate();
		job = startJob();
		System.out.println(ArtUtil.COUPON_SYSTEM);
	}
	
	private static CouponExpirationDailyJob startJob() 
	{
		CouponsDBDAO couponsDB = new CouponsDBDAO();
		CouponExpirationDailyJob job = new CouponExpirationDailyJob(couponsDB);
		job.start();
		return job;
	}
	
	private static void tests() throws SQLException, LoginFailedException, EntityAlreadyExistsException, EntityNotFoundException, CouponNotAvailableException, CouponDateExpiredException {
		positiveFunctionalityTests();
		negativeFunctionalityTests();
	}
	
	private static void stop(CouponExpirationDailyJob job) throws InterruptedException, SQLException {
		job.stopRunning();
		ConnectionPool.getInstance().closeAllConnections();
	}
}
