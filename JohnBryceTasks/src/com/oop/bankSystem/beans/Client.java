package com.oop.bankSystem.beans;

public abstract class Client extends Person implements Comparable<Client> {

	protected float interestRate;
	protected Account account;
	
	public Client(float interestRate) {
		super();
		setInterestRate(interestRate);
		setAccount(new Account());
	}

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int compareTo(Client other) {
		return this.getId() - other.getId();
	}

	@Override
	public String toString() {
		return super.toString() + ", interestRate=" + interestRate + ", account=" + account.toString();
	}
}
