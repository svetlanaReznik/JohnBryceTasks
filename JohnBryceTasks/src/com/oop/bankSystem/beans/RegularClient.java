package com.oop.bankSystem.beans;

public class RegularClient extends Client {

	private static final float INTEREST_RATE = 0.05f;

	public RegularClient() {
		super(INTEREST_RATE);
	}
}
