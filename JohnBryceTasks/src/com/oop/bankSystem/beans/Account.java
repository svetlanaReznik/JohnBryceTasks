package com.oop.bankSystem.beans;

public class Account 
{
	private double balance;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double amount) {
		this.balance += amount;
	}

	@Override
	public String toString() {
		return "balance: " + balance;
	}
}
