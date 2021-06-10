package com.oop.bankSystem.util;

public class PrintUtils 
{
	private static final String END_MESSAGE = "Thank you for your visit and good bye!";
	
	public static void printEndMsg() {
		System.out.println(END_MESSAGE);
	}
	
	public static void showMenu() {
		print("BANK SYSTEM");
		System.out.println("Please select an action: ");
		System.out.println("1 | Add Client.");
		System.out.println("2 | Delete Client.");
		System.out.println("3 | Withdraw.");
		System.out.println("4 | Deposit.");
		System.out.println("5 | Print all clients.");
		System.out.println("6 | Client with large meals.");
		System.out.println("7 | Client with less meals.");
		System.out.println("8 | Bank balance.");
		System.out.println("9 | Exit.");
	}

	public static void print(String str) {
		System.out.println("-------------------------------------------------------------");
		System.out.println("                          " + str + "                        ");
		System.out.println("-------------------------------------------------------------\n");
	}
	
	public static void userChoiceClientTypeToAdd() {
		System.out.println("Which type of user you prefer to Add?");
		System.out.println("R | Regular client.");
		System.out.println("V | VIP client.");
	}
	
	public static void userChoiceClientId() {
		System.out.println("What Client ID you prefer?");
	}
	
	public static void userChoiceAmount() {
		System.out.println("What amount you prefer?");
	}

}
