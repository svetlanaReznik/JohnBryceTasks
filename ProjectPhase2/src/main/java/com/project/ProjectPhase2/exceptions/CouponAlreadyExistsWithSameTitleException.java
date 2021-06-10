package com.project.ProjectPhase2.exceptions;

public class CouponAlreadyExistsWithSameTitleException extends Exception {

	public CouponAlreadyExistsWithSameTitleException() {
		super("Coupon already exists with same title!");
	}

}
