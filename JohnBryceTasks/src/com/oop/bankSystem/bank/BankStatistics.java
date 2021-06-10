package com.oop.bankSystem.bank;

import java.util.Comparator;

import com.oop.bankSystem.beans.Client;
import com.oop.bankSystem.exceptions.BankSystemException;

public class BankStatistics 
{
	public static int countMembers() {
		return BankSystem.getInstance().getClients().size();
	}

	public static double getBankBalance() {
		return BankSystem.getInstance().getClients().stream()
				.mapToDouble(x -> x.getAccount().getBalance())
				.sum();
	}

	public static Client largeMeansClient() throws BankSystemException  {
		return BankSystem.getInstance().getClients().stream()
				.max(Comparator.comparingDouble(c -> c.getAccount().getBalance()))
				.orElseThrow(BankSystemException::new);
	}

	public static Client lessMeansClient() throws BankSystemException  {
		return BankSystem.getInstance().getClients().stream()
				.min(Comparator.comparingDouble(c -> c.getAccount().getBalance()))
				.orElseThrow(BankSystemException::new);
	}
}
