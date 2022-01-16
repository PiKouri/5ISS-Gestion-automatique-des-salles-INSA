package fr.insa.arm.CentralManagerService.model;

import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class ManagerProcess extends TimerTask {

    public RestTemplate restTemplate;

    public static Boolean running = false;

    // Urls
    final private static String rooms = "http://RoomsService/rooms/";
    final private static String lightSensor = "http://LightSensorService/lightSensor/";
    final private static String temperatureSensor = "http://TemperatureSensorService/temperatureSensor/";
    final private static String gazSensor = "http://GazSensorService/gazSensor/";
    final private static String co2Sensor = "http://Co2SensorService/co2Sensor/";
    final private static String window = "http://WindowService/window/";
    final private static String door = "http://DoorService/door/";
    final private static String light = "http://LightService/light/";
    final private static String heating = "http://HeatingService/heating/";
    final private static String cooling = "http://CoolingService/cooling/";
    final private static String alarm = "http://AlarmService/alarm/";

    private static String devicesUrl(Integer roomId) {
        return rooms + roomId + "/devices?type=";
    }

    private ArrayList<Integer> getRoomIds() {
        return restTemplate.getForObject(rooms, ArrayList.class);
    }

    public boolean isWorkingHours(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        return (hourOfDay >= 7 && hourOfDay <= 22);
    }

    public boolean isWorkingHours() {
        return isWorkingHours(new Date());
    }

    public Float getMean(ArrayList<Float> values) {
        Float mean = 0f;
        if (values.isEmpty()) {
            return null;
        } else {
            for (Float value : values ) {
                mean += value;
            }
            return mean / values.size();
        }
    }

    private void openWindows(ArrayList<Integer> windowIds) {
        for (Integer windowId : windowIds) {
            restTemplate.postForEntity(window + windowId + "/open", "", Boolean.class);
        }
    }

    private void closeWindows(ArrayList<Integer> windowIds) {
        for (Integer windowId : windowIds) {
            restTemplate.postForEntity(window + windowId + "/close", "", Boolean.class);
        }
    }

    private void manageWindows(Integer roomId, ArrayList<Integer> co2Ids, ArrayList<Integer> temperatureIds) {
        ArrayList<Integer> windowIds = restTemplate.getForObject(devicesUrl(roomId) + "WINDOW_A", ArrayList.class);
        ArrayList<Float> co2Values = co2Ids.stream().map(id -> restTemplate.getForObject(co2Sensor + id, Float.class)).collect(Collectors.toCollection(ArrayList::new));
        if ( !co2Values.isEmpty() && Collections.max(co2Values)  >= 1000f) {
            openWindows(windowIds);
        } else {
            ArrayList<Float> temperatureValues = temperatureIds.stream().map(id -> restTemplate.getForObject(temperatureSensor + id, Float.class)).collect(Collectors.toCollection(ArrayList::new));
            Float temperatureMean = getMean(temperatureValues);
            if (!temperatureValues.isEmpty()) {
                if (temperatureMean < 19.5f) {
                    closeWindows(windowIds);
                } else if (temperatureMean > 23f) {
                    openWindows(windowIds);
                }
            }
        }
    }

    private void manageDoors(Integer roomId) {
        ArrayList<Integer> doorIds = restTemplate.getForObject(devicesUrl(roomId) + "DOOR_A", ArrayList.class);
        Integer nbPersons = restTemplate.getForObject(rooms + roomId + "/persons", Integer.class);
        if (nbPersons == 0 && !isWorkingHours()) {
            for (Integer doorId : doorIds) {
                restTemplate.postForEntity(door + doorId + "/close", "", Boolean.class);
            }
        }
    }

    private void turnOffLights(ArrayList<Integer> lightIds) {
        for (Integer lightId : lightIds) {
            restTemplate.postForEntity(light + lightId + "/turnOff", "", Boolean.class);
        }
    }

    private void turnOnLights(ArrayList<Integer> lightIds) {
        for (Integer lightId : lightIds) {
            restTemplate.postForEntity(light + lightId + "/turnOn", "", Boolean.class);
        }
    }

    private void manageLights(Integer roomId, ArrayList<Integer> lightSensorIds) {
        ArrayList<Integer> lightIds = restTemplate.getForObject(devicesUrl(roomId) + "LIGHT_A", ArrayList.class);
        ArrayList<Float> lightValues = lightSensorIds.stream().map(id -> restTemplate.getForObject(lightSensor + id, Float.class)).collect(Collectors.toCollection(ArrayList::new));
        Integer nbPersons = restTemplate.getForObject(rooms + roomId + "/persons", Integer.class);
        if (nbPersons == 0) {
            turnOffLights(lightIds);
        } else {
            Float lightMean = getMean(lightValues);
            if (!lightValues.isEmpty()) {
                if (lightMean < 15) {
                    turnOnLights(lightIds);
                } else {
                    turnOffLights(lightIds);
                }
            }
        }
    }

    private void turnOnAlarms(ArrayList<Integer> alarmIds) {
        for (Integer alarmId : alarmIds) {
            restTemplate.postForEntity(alarm + alarmId + "/turnOn", "", Boolean.class);
        }
    }

    private void manageAlarms(Integer roomId, ArrayList<Integer> gazIds) {
        ArrayList<Integer> alarmIds = restTemplate.getForObject(devicesUrl(roomId) + "ALARM_A", ArrayList.class);
        Integer nbPersons = restTemplate.getForObject(rooms + roomId + "/persons", Integer.class);
        ArrayList<Float> gazValues = gazIds.stream().map(id -> restTemplate.getForObject(gazSensor + id, Float.class)).collect(Collectors.toCollection(ArrayList::new));
        if (!gazValues.isEmpty() && Collections.max(gazValues) >= 100) {
            turnOnAlarms(alarmIds);
        } else if (nbPersons > 20) {
            turnOnAlarms(alarmIds);
        } else if (nbPersons > 0) {
            if (isWorkingHours()) {
                turnOnAlarms(alarmIds);
            }
        }
    }

    private void turnOffHeatingCooling(ArrayList<Integer> heatingIds, ArrayList<Integer> coolingIds) {
        for (Integer heatingId : heatingIds) {
            restTemplate.postForEntity(heating + heatingId + "/turnOff", "", Boolean.class);
        }
        for (Integer coolingId : coolingIds) {
            restTemplate.postForEntity(cooling + coolingId + "/turnOff", "", Boolean.class);
        }
    }

    private void manageHeatingCooling(Integer roomId) {
        ArrayList<Integer> heatingIds = restTemplate.getForObject(devicesUrl(roomId) + "HEATING_A", ArrayList.class);
        ArrayList<Integer> coolingIds = restTemplate.getForObject(devicesUrl(roomId) + "COOLING_A", ArrayList.class);
        ArrayList<Integer> windowIds = restTemplate.getForObject(devicesUrl(roomId) + "WINDOW_A", ArrayList.class);
        ArrayList<Boolean> windowStatus = windowIds.stream().map(id -> restTemplate.getForObject(window + id, Boolean.class)).collect(Collectors.toCollection(ArrayList::new));
        boolean atLeastOneWindowIsOpened = windowStatus.stream().anyMatch(status -> status);
        if (atLeastOneWindowIsOpened) {
            turnOffHeatingCooling(heatingIds, coolingIds);
        }
    }

    private void manageRoom(Integer roomId) {
        ArrayList<Integer> lightSensorIds = restTemplate.getForObject(devicesUrl(roomId) + "LIGHT_S", ArrayList.class);
        ArrayList<Integer> temperatureSensorIds = restTemplate.getForObject(devicesUrl(roomId) + "TEMPERATURE_S", ArrayList.class);
        ArrayList<Integer> gazSensorIds = restTemplate.getForObject(devicesUrl(roomId) + "GAZ_S", ArrayList.class);
        ArrayList<Integer> co2SensorIds = restTemplate.getForObject(devicesUrl(roomId) + "CO2_S", ArrayList.class);
        this.manageWindows(roomId, co2SensorIds, temperatureSensorIds);
        this.manageDoors(roomId);
        this.manageLights(roomId, lightSensorIds);
        this.manageAlarms(roomId, gazSensorIds);
        this.manageHeatingCooling(roomId);
    }

    @Override
    public void run() {
        if (running) {
            ArrayList<Integer> roomIds = this.getRoomIds();
            for (Integer roomId : roomIds) {
                this.manageRoom(roomId);
            }
        }
    }

    public void test() {
        Integer roomId = restTemplate.postForObject(rooms, "Room 1", Integer.class);

        Integer co2SensorId = restTemplate.postForObject(co2Sensor, "", Integer.class);
        restTemplate.put(co2Sensor + co2SensorId, 12.0, Float.class);
        Integer co2SensorId2 = restTemplate.postForObject(co2Sensor, "", Integer.class);
        restTemplate.put(co2Sensor + co2SensorId2, 100.0, Float.class);
        Integer co2SensorId3 = restTemplate.postForObject(co2Sensor, "", Integer.class);
        restTemplate.put(co2Sensor + co2SensorId3, 1252.0, Float.class);
        restTemplate.postForObject(devicesUrl(roomId) + "CO2_S", co2SensorId, boolean.class);
        restTemplate.postForObject(devicesUrl(roomId) + "CO2_S", co2SensorId2, boolean.class);
        restTemplate.postForObject(devicesUrl(roomId) + "CO2_S", co2SensorId3, boolean.class);

        Integer windowId = restTemplate.postForObject(window, "", Integer.class);
        restTemplate.postForObject(window + windowId + "/close", "", Boolean.class);
        Integer windowId2 = restTemplate.postForObject(window, "", Integer.class);
        restTemplate.postForObject(window + windowId2 + "/close", "", Boolean.class);
        restTemplate.postForObject(devicesUrl(roomId) + "WINDOW_A", windowId, boolean.class);
        restTemplate.postForObject(devicesUrl(roomId) + "WINDOW_A", windowId2, boolean.class);

    }
}