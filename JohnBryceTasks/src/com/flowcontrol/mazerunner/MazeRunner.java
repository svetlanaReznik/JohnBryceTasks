package com.flowcontrol.mazerunner;

import java.util.Scanner;

public class MazeRunner 
{
	private static Maze myMap = new Maze();
	private static Scanner scan = new Scanner(System.in);
	
	public static int NUMOFMOVES=0;
	public static boolean ISGAMEOVER=false;
	public static final int WARNING=50;
	public static final int ALERT=75;
	public static final int DANGER=90;
	public static final int FINISHED=100;

	public static void main(String[] args) 
	{
		intro();
		run();
		finish();
    	scan.close();
	}

	private static void finish() 
	{
		if(ISGAMEOVER) {
			System.out.println("Sorry, but you didn't escape in time - you lose!");
			return;
		}
		congrats();
	}

	private static void run() 
	{
		while(!myMap.didIWin()) 
		{
			executeMove();	
		
			if(NUMOFMOVES == FINISHED) 
			{
				ISGAMEOVER = true;
				return;
			}
		}
	}

	private static void congrats() {
		System.out.println("Congratulations, you made it out alive!");
		System.out.println(String.format("and you did it in %d moves", NUMOFMOVES));
	}

	private static void executeMove() 
	{
		String direction ="";
		direction = userMove();
		
		if(isTherePit(direction)) 
			 navigatePit(direction);
		else if(!isFreeOfWall(direction)) 
		{
			System.out.println("Sorry, you’ve hit a wall.");
			myMap.printMap();
			return;
		}
		else
			move(direction);
		
		myMap.printMap();
		movesMessage();
	}

	public static void intro() 
	{
		System.out.println("Welcome to Maze Runner!");
		System.out.println("Here is your current position:");
		myMap.printMap();
	}

	public static String userMove() 
	{
		do 
		{
			System.err.println();
			System.out.println("Where would you like to move?");
			System.out.println("Right - R");
			System.out.println("Left - L");
			System.out.println("Up - U");
			System.out.println("Down - D");
			String choice = scan.nextLine();
			NUMOFMOVES++;
			switch (choice) 
			{
				case "R":  case "L":  case "U": case "D":
					return choice;
				default:
					System.out.println("Incorrect answer. Please try again!");
					break;
			}
		}while(true);
	}

	private static boolean isFreeOfWall(String direction) 
	{
		if(direction.equals("R")) return myMap.canIMoveRight();
		if(direction.equals("L")) return myMap.canIMoveLeft();
		if(direction.equals("U")) return myMap.canIMoveUp();
		return myMap.canIMoveDown();
	}

	public static void move(String direction) 
	{
		switch(direction) 
		{
			case "R": myMap.moveRight();
			break;
	
			case "L": myMap.moveLeft();
			break;
	
			case "U": myMap.moveUp();	
			break;
	
			case "D": myMap.moveDown();
			break;
		}
	}

	private static boolean isTherePit(String direction) 
	{
		return myMap.isThereAPit(direction);
	}

	public static void navigatePit(String direction) 
	{
		System.out.println("Watch out! There's a pit ahead, jump it?");
		System.out.println("Yes - Y");
		String answer = scan.nextLine();
		if(answer.startsWith("Y") || answer.startsWith("y"))
			myMap.jumpOverPit(direction);
	}

	private static void movesMessage() 
	{
		switch(NUMOFMOVES) 
		{
			case WARNING:
				System.out.println("Warning: You have made 50 moves, you have 50 remaining before the maze exit closes" + "\n");
				break;
	
			case ALERT:
				System.out.println("Alert! You have made 75 moves, you only have 25 moves left to escape." + "\n");
				break;
	
			case DANGER:
				System.out.println("DANGER! You have made 90 moves, you only have 10 moves left to escape!!" + "\n");
				break;
	
			case FINISHED:
				System.out.println("Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[" );
				return;
		}
	}
}
