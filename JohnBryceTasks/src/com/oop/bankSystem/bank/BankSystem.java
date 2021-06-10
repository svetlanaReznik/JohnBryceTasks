package com.oop.bankSystem.bank;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.oop.bankSystem.beans.Client;
import com.oop.bankSystem.beans.RegularClient;
import com.oop.bankSystem.beans.VipClient;
import com.oop.bankSystem.exceptions.BankSystemException;
import com.oop.bankSystem.exceptions.Error;
import com.oop.bankSystem.task.InterestTask;
import static com.oop.bankSystem.util.PrintUtils.*;
import static com.oop.bankSystem.bank.BankStatistics.*;

public class BankSystem 
{
	private static BankSystem instance = null;
	private static Scanner scanner;
	private InterestTask task;
	private Set<Client> clients;
	
	private static final String VIP_CLIENT = "v";
	
	private BankSystem() {
		clients = new TreeSet<>();
		scanner = new Scanner(System.in);
		initTask();
	}

	private void initTask() {
		task = new InterestTask(clients);
		task.start();
	}
	
	public Set<Client> getClients() {
		return clients;
	}

	public static BankSystem getInstance() {
		if (instance == null) {
			synchronized (BankSystem.class) {
				if (instance == null) {
					instance = new BankSystem();
				}
			}
		}
		return instance;
	}

	public void startSystem() 
	{
		int userChoice = 0;
		while (userChoice!=9) 
		{
			showMenu();
			userChoice = scanner.nextInt();
			makeUserChoice(userChoice);
			endSystem();
		}
	}

	private void makeUserChoice(int userChoice) {
		try {
				switch (userChoice) 
				{
					case 1: addClient(selectClientType());
					  	    break;
		
					case 2: deleteClient();
						    break;
		
					case 3: withdraw();
						    break;
		
					case 4: deposit();
					        break;
									
					case 5: printAll();
						    break;
									
					case 6: System.out.println(largeMeansClient());
							break;
									
					case 7: System.out.println(lessMeansClient());
							break;
					
					case 8: System.out.println(getBankBalance());
							break;
				}
			} catch (BankSystemException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		    endSystem();
	}

	private Client selectClientType() {
		userChoiceClientTypeToAdd();
		String choice = scanner.nextLine();
		return choice.toLowerCase().equals(VIP_CLIENT) ? new VipClient() : new RegularClient();
	}

	public void addClient(Client client) {
		clients.add(client);
	}
	
	private void deleteClient() throws BankSystemException {
		int clientID = clientIdSelectedByUser();
		deleteClient(clientID);
	}

	private int clientIdSelectedByUser() {
		userChoiceClientId();
		return scanner.nextInt();
	}
	
	private void deleteClient(int clientId) throws BankSystemException {
		Client clientToDelete = getClient(clientId);
		if (clientToDelete == null)
			throw new BankSystemException(Error.CLIENT_NOT_FOUND, clientId);
		if (clientToDelete.getAccount().getBalance() < 0)
			throw new BankSystemException(Error.DELETE, clientToDelete);
		clients.remove(clientToDelete);
	}
	
	private Client getClient(int clientId){
		return clients.stream().filter(x -> x.getId() == clientId).findFirst().orElse(null);
	}
	
	public void endSystem() {
		task.stopRunning();
		scanner.close();
		printEndMsg();
	}

	public void printAll() throws BankSystemException {
		if (clients.isEmpty())
			throw new BankSystemException(Error.NO_CLIENTS_FOUND);
		clients.stream().sorted(Comparator.comparingDouble(c -> ((Client) c).getAccount().getBalance()).reversed())
				        .forEach(System.out::println);
	}
	
	private void withdraw() throws BankSystemException  {
		withdraw(clientIdSelectedByUser(), amountSelectedByUser());
	}
	
	private double amountSelectedByUser() {
		userChoiceAmount();
		return scanner.nextInt();
	}

	private void withdraw(int clientId, double amount) throws BankSystemException {
		Client client = getClient(clientId);
		if (client == null)
			throw new BankSystemException(Error.CLIENT_NOT_FOUND, clientId);
		if (client.getAccount().getBalance() - amount < 0)
			throw new BankSystemException(Error.WITHDRAW, client);
		client.getAccount().setBalance(amount);
	}
	
	private void deposit() throws BankSystemException {
		deposit(clientIdSelectedByUser(), amountSelectedByUser());
	}

	private void deposit(int clientId, double amount) throws BankSystemException {
		Client selectedClient = getClient(clientId);
		if (selectedClient == null)
			throw new BankSystemException(Error.CLIENT_NOT_FOUND, clientId);
		selectedClient.getAccount().setBalance(amount);
	}
}
