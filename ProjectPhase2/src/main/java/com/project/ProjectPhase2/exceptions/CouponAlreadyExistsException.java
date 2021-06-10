package com.project.ProjectPhase2.exceptions;

public class CouponAlreadyExistsException extends Exception 
{
	public CouponAlreadyExistsException() {
		super("The coupon already purchased by customer!");
	}
	

}
