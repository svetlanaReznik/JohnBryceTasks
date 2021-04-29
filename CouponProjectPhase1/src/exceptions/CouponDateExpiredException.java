package exceptions;

public class CouponDateExpiredException extends Exception {

	public CouponDateExpiredException() {
		super("The coupon is already expired!");
	}
	

}
