package com.project.ProjectPhase2.exceptions;

public class CouponNotExistsException extends Exception 
{
	public CouponNotExistsException() {
		super("The coupon not exists in the system!");
	}
}
