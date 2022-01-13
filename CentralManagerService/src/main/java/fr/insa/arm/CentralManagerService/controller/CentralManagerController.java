package fr.insa.arm.CentralManagerService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/centralManager")
public class CentralManagerController {

    @Autowired
    private RestTemplate restTemplate;

    // Urls
    final private static String rooms = "http://RoomsService/rooms/";
    final private static String lightSensor = "http://LightSensorService/lightSensor/";
    final private static String temperatureSensor = "http://TemperatureSensorService/temperatureSensor/";
    final private static String gazSensor = "http://GazSensorService/gazSensor/";
    final private static String co2Sensor = "http://GazSensorService/co2Sensor/";
    final private static String window = "http://WindowService/window/";
    final private static String door = "http://DoorService/door/";
    final private static String light = "http://LightService/light/";
    final private static String heating = "http://HeatingService/heating/";
    final private static String cooling = "http://CoolingService/cooling/";
    final private static String alarm = "http://AlarmService/alarm/";

    @GetMapping("/")
    public String test() {
        String result = "";
//        Integer i = restTemplate.postForObject(lightSensor, "", Integer.class);
//        restTemplate.put(lightSensor + i, 12.0, Float.class);
//        Integer id = restTemplate.getForObject(lightSensor + i, Integer.class);
//        if (id!=null) result += id +"\n";

        Integer i1 = restTemplate.postForObject(rooms, "Room 1", Integer.class);
        Integer i2 = restTemplate.postForObject(rooms, "Room 2", Integer.class);
//        restTemplate.put(lightSensor + i, 12.0, Float.class);
        ArrayList<Integer> ids = restTemplate.getForObject(rooms , ArrayList.class);
        for (int i : Objects.requireNonNull(ids)) {
            String name = restTemplate.getForObject(rooms+i , String.class);
            result += i + " : " + name + "\n";
        }
        return result;
    }
}
