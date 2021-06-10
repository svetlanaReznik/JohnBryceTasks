package com.oop.bankSystem.beans;

public class VipClient extends Client {

	private static final float INTEREST_RATE = 0.1f;

	public VipClient() {
		super(INTEREST_RATE);
	}
}
