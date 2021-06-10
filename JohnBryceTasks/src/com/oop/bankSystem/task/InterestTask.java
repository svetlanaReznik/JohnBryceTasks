package com.oop.bankSystem.task;

import java.util.Set;

import com.oop.bankSystem.bank.BankSystem;
import com.oop.bankSystem.beans.Client;
import com.oop.bankSystem.exceptions.BankSystemException;

public class InterestTask extends Thread
{
	private Set<Client> clients;
	private boolean quit = true;
		
	public InterestTask(Set<Client> clients) {
		this.clients = clients;
	}

	@Override
	public void run() 
	{
		while (quit) {

			try {
			
				System.out.println("\nThread started running.");
				updateBalance();
				BankSystem.getInstance().printAll();
				Thread.sleep(1000*60*60*24);
				System.out.println("Thread stopped and went to sleep.");
			} catch (InterruptedException | BankSystemException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void updateBalance() {
		for (Client client : clients) {
			client.getAccount().setBalance(client.getAccount().getBalance() * client.getInterestRate());
		}
	}
	
	public void stopRunning() {
		quit = false;
		this.interrupt();
	}
}
