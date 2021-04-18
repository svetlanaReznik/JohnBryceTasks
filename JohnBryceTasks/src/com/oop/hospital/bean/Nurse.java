package com.oop.hospital.bean;
import static com.oop.hospital.utils.DataUtils.*;
import static com.oop.hospital.utils.NurseUtils.*;

import com.oop.hospital.utils.NurseUtils;

public class Nurse extends Person 
{
	private int[] roomNumber;
	public static int nurseCount = 0;

	public Nurse() {
		super("Nurse");
	}
	
	public static Nurse build() {
		++nurseCount;
		return new Nurse();
	}
	
	public Nurse roomNumber(int roomNumber) 
	{
		setRoomNumber(roomNumber);
		return this;
	}

	public int[] getRoomNumber() 
	{
		return roomNumber;
	}

	public void setRoomNumber(int currentRoomNumber) 
	{
		toDefineNumOfRooms();
		
		if(!NurseUtils.isEnoughEmptyRooms(MAX_NUM_OF_NURSE_ROOMS))
			roomNumber = new int[1];
		
		roomNumber[0] = currentRoomNumber;
		removeRoomNumberFromList(currentRoomNumber);
		
		for (int i = 1; i < roomNumber.length; i++) {
			roomNumber[i] = NurseUtils.getRandomRoom();
			saveAdditionalAssignedRoomToNurse.put(roomNumber[i], this);
		}
	}
	
	private void toDefineNumOfRooms() {
		roomNumber = new int[getRandomValue(1,MAX_NUM_OF_NURSE_ROOMS)];
	}
	
	public String printRooms() {
		if(this.roomNumber == null)
			return ", not assigned to any room yet";
		String print = ", belong to room number " + String.valueOf(roomNumber[0]);
		for (int i = 1; i < roomNumber.length; i++) {
			if(i < roomNumber.length)
				print += " and " + roomNumber[i];
		}
		return print;
	}
	
	@Override
	public String toString() {
		return super.toString() + printRooms();
	}
}
