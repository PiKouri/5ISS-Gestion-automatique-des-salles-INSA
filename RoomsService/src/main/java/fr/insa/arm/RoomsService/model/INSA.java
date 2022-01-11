package fr.insa.arm.RoomsService.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class INSA {
	
	private final Map<Integer,Room> rooms;
	
	public INSA() {
		rooms = new HashMap<>();
	}
	
	public ArrayList<Integer> getIds() {
		return new ArrayList<>(rooms.keySet());
	}
	
	public int addRoom(String name) {
		Room room = new Room(name);
		rooms.put(room.getId(), room);
		return room.getId();
	}
	
	public Room removeRoom(int id) {
		return rooms.remove(id);
	}
	
	public String getRoomName(int id) {
		Room room = rooms.get(id);
		return room == null ? null : room.getName();
	}
	
	public boolean affectDevice(int idRoom, String type, int idDevice) {
		Room room = rooms.get(idRoom);
		if (room == null) {
			return false;
		} else {
			room.affectDevice(type, idDevice);
			return true;
		}
	}
	
	public boolean removeDevice(int idRoom, String type, int idDevice) {
		Room room = rooms.get(idRoom);
		return room != null && room.removeDevice(type, idDevice);
	}
	
	public ArrayList<Integer> getIdsDevice(int idRoom, String type) {
		return rooms.get(idRoom).getIdsDevice(type);
	}
}
