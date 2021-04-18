package com.oop.hospital.factory;

import com.oop.hospital.bean.Room;

public class RoomUtills 
{
	public static Room[] generateRooms(int numOfRooms) 
	{
		Room[] rooms = new Room[numOfRooms];
		for (int i = 0; i < rooms.length; i++) {
			rooms[i] = new Room();
		}
		return rooms;
	}
}
