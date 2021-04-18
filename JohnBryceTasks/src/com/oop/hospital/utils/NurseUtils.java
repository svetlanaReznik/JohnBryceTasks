package com.oop.hospital.utils;

import static com.oop.hospital.utils.DataUtils.MAX_ROOMS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.oop.hospital.bean.Nurse;

public class NurseUtils {
	public static final int MAX_NUM_OF_NURSE_ROOMS = 2;
	public static List<Integer> listUnassignedRoomsForNurse = generateListUnassignedRooms();
	public static HashMap<Integer, Nurse> saveAdditionalAssignedRoomToNurse = new HashMap<>();

	public static List<Integer> generateListUnassignedRooms() {
		return IntStream.range(1, MAX_ROOMS + 1).boxed().collect(Collectors.toList());
	}

	public static int getRandomRoom() {
		int randomRoom = listUnassignedRoomsForNurse.get(new Random().nextInt(listUnassignedRoomsForNurse.size()));
		removeRoomNumberFromList(randomRoom);
		return randomRoom;
	}

	public static boolean isEnoughEmptyRooms(int size) {
		return listUnassignedRoomsForNurse.size() < size ? false : true;
	}

	public static void removeRoomNumberFromList(int currentRoomNumber) {
		listUnassignedRoomsForNurse.removeIf(num -> num.equals(currentRoomNumber));
	}

	public static HashMap<Integer, Nurse> isRoomAlreadyAssignedToNurse(int roomNumber) {
		return (HashMap<Integer, Nurse>)saveAdditionalAssignedRoomToNurse.entrySet()
				                                        .stream()
				                                        .filter(r -> r.getKey().equals(roomNumber))
				                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
