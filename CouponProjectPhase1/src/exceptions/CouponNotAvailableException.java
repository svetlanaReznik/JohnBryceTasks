package exceptions;

public class CouponNotAvailableException extends Exception {

	public CouponNotAvailableException() {
		super("This coupon is not available for purchase.");
	}
	
}
