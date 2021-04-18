package com.oop.hospital.bean;

import static com.oop.hospital.factory.RoomUtills.*;

public class Hospital 
{
	public static final int NUM_OF_ROOMS = 10;
	private static final String NAME = "Awesome";
	
	private String name;
	private Room[] rooms;
	
	public Hospital() {
		setName();
		setRooms();
	}

	public String getName() {
		return name;
	}
	
	public void setName() {
		this.name = NAME;
	}
	
	public Room[] getRooms() {
		return rooms;
	}

	public void setRooms() {
		rooms = generateRooms(NUM_OF_ROOMS);
	}
	
	public StringBuilder printRooms(Room[] rooms) {
		StringBuilder print = new StringBuilder("");
		for (Room r : rooms) {
			print.append("\n" + r);
		}
		return print;
	}
	
	public double averageTimeToCure() {
		int sum = 0;
		for (Room r : rooms) {
			sum += r.averageTimeToCure();
		}
		return (double)sum/NUM_OF_ROOMS;
	}

	@Override
	public String toString() {
		return "Hospital: name=" + name + "\n\n" + printRooms(rooms);
	}
}
