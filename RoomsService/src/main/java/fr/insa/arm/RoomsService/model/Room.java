package fr.insa.arm.RoomsService.model;

import java.util.ArrayList;

public class Room {
	
	static int compteur = 0;
	private int id;
	private String name;
	private ArrayList<Integer> temperatureSensors;
	private ArrayList<Integer> lightSensors;
	private ArrayList<Integer> co2Sensors;
	private ArrayList<Integer> gazSensors;
	private ArrayList<Integer> studentCounters;
	private ArrayList<Integer> doorActuator;
	private ArrayList<Integer> windowActuator;
	private ArrayList<Integer> alarmActuator;
	private ArrayList<Integer> heatingActuator;
	private ArrayList<Integer> climActuator;
	private ArrayList<Integer> lightActuator;
	
	public Room(String name) {
		this.id = Room.compteur;
		Room.compteur++;
		this.name = name;
		temperatureSensors = new ArrayList<Integer>();
		lightSensors = new ArrayList<Integer>();
		co2Sensors = new ArrayList<Integer>();
		gazSensors = new ArrayList<Integer>();
		studentCounters = new ArrayList<Integer>();
		doorActuator = new ArrayList<Integer>();
		windowActuator = new ArrayList<Integer>();
		alarmActuator = new ArrayList<Integer>();
		heatingActuator = new ArrayList<Integer>();
		climActuator = new ArrayList<Integer>();
		lightActuator = new ArrayList<Integer>();
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
		case "CLIM_A": 
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
		case "CLIM_A": 
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
		case "CLIM_A": 
			return climActuator;
		case "LIGHT_A":
			return lightActuator;
		
		default:
			return null;
		}
	}
	

}
