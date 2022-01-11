package fr.insa.arm.GazSensorService.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gazSensor")
public class GazSensorController {

    private final Map<Integer, Float> sensorValues = new HashMap<>();
    private int currentMaxId = 0;

    @GetMapping("/{id}")
    public Float getSensorValue(@PathVariable("id") Integer id) {
        return sensorValues.getOrDefault(id, null);
    }

    @PutMapping("/{id}")
    public Float setSensorValue(@PathVariable("id") Integer id, @RequestBody Float value) {
        return sensorValues.replace(id, value);
    }

    @PostMapping("/")
    public int addSensor() {
        sensorValues.put(currentMaxId, 0.0F);
        return currentMaxId++;
    }

    @DeleteMapping("/{id}")
    public Float removeSensor(@PathVariable("id") Integer id) {
        return sensorValues.remove(id);
    }

}