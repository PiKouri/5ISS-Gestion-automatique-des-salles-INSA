package fr.insa.arm.RoomsService.model;

import java.util.ArrayList;

public class Room {

	private static class MyTimer {
		private long start = 0L;

		public MyTimer() { }

		public void start() {
			start = System.currentTimeMillis();
		}

		public void stop() {
			start = 0L;
		}

		public long getElapsedTime() {
			long end = System.currentTimeMillis();
			return end - start;
		}
	}

	static int compteur = 0;
	private final int id;
	private final String name;
	private final ArrayList<Integer> temperatureSensors;
	private final ArrayList<Integer> lightSensors;
	private final ArrayList<Integer> co2Sensors;
	private final ArrayList<Integer> gazSensors;
	private final ArrayList<Integer> studentCounters;
	private final ArrayList<Integer> doorActuator;
	private final ArrayList<Integer> windowActuator;
	private final ArrayList<Integer> alarmActuator;
	private final ArrayList<Integer> heatingActuator;
	private final ArrayList<Integer> climActuator;
	private final ArrayList<Integer> lightActuator;
	private Integer countPeople = 0;
	private final MyTimer timer = new MyTimer();

	public Room(String name) {
		this.id = Room.compteur;
		Room.compteur++;
		this.name = name;
		temperatureSensors = new ArrayList<>();
		lightSensors = new ArrayList<>();
		co2Sensors = new ArrayList<>();
		gazSensors = new ArrayList<>();
		studentCounters = new ArrayList<>();
		doorActuator = new ArrayList<>();
		windowActuator = new ArrayList<>();
		alarmActuator = new ArrayList<>();
		heatingActuator = new ArrayList<>();
		climActuator = new ArrayList<>();
		lightActuator = new ArrayList<>();
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void affectDevice(String type, int id) {
		switch (type) {
		case "TEMPERATURE_S": 
			temperatureSensors.add(id);
			break;
		case "LIGHT_S": 
			lightSensors.add(id);
			break;
		case "CO2_S": 
			co2Sensors.add(id);
			break;
		case "GAZ_S": 
			gazSensors.add(id);
			break;
		case "STUDENT_C": 
			studentCounters.add(id);
			break;
		case "DOOR_A": 
			doorActuator.add(id);
			break;
		case "WINDOW_A": 
			windowActuator.add(id);
			break;
		case "ALARM_A": 
			alarmActuator.add(id);
			break;
		case "HEATING_A": 
			heatingActuator.add(id);
			break;
		case "COOLING_A":
			climActuator.add(id);
			break;
		case "LIGHT_A":
			lightActuator.add(id);
			break;
		
		default:
			
		}
	}
	
	public boolean removeDevice(String type, int id) {
		switch (type) {
		case "TEMPERATURE_S": 
			return temperatureSensors.remove(Integer.valueOf(id));
		case "LIGHT_S": 
			return lightSensors.remove(Integer.valueOf(id));
		case "CO2_S": 
			return co2Sensors.remove(Integer.valueOf(id));
		case "GAZ_S": 
			return gazSensors.remove(Integer.valueOf(id));
		case "STUDENT_C": 
			return studentCounters.remove(Integer.valueOf(id));
		case "DOOR_A": 
			return doorActuator.remove(Integer.valueOf(id));
		case "WINDOW_A": 
			return windowActuator.remove(Integer.valueOf(id));
		case "ALARM_A": 
			return alarmActuator.remove(Integer.valueOf(id));
		case "HEATING_A": 
			return heatingActuator.remove(Integer.valueOf(id));
		case "COOLING_A":
			return climActuator.remove(Integer.valueOf(id));
		case "LIGHT_A":
			return lightActuator.remove(Integer.valueOf(id));
		
		default:
			return false;
		}
	}
	
	public ArrayList<Integer> getIdsDevice(String type) {
		switch (type) {
		case "TEMPERATURE_S": 
			return temperatureSensors;
		case "LIGHT_S": 
			return lightSensors;
		case "CO2_S": 
			return co2Sensors;
		case "GAZ_S": 
			return gazSensors;
		case "STUDENT_C": 
			return studentCounters;
		case "DOOR_A": 
			return doorActuator;
		case "WINDOW_A": 
			return windowActuator;
		case "ALARM_A": 
			return alarmActuator;
		case "HEATING_A": 
			return heatingActuator;
		case "COOLING_A":
			return climActuator;
		case "LIGHT_A":
			return lightActuator;
		default:
			return null;
		}
	}

	public Integer getPersons() {
		return countPeople;
	}

	public Integer addPersons(int nbPersons) {
		// If there was nobody before => starts the timer
		if (countPeople == 0 && nbPersons > 0) timer.start();
		countPeople = Math.max(countPeople+nbPersons, 0); // Prevent having -1 person in a room
		return countPeople;
	}

	public Integer removePersons(int nbPersons) {
		countPeople = Math.max(countPeople-nbPersons, 0); // Prevent having -1 person in a room
		// If there is nobody left => stops the timer
		if (countPeople == 0) timer.stop();
		return countPeople;
	}

	public long getElapsedTime() {
		if (countPeople > 0) return timer.getElapsedTime();
		else return 0L;
	}

}
