package fr.insa.arm.AlarmService.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    // True = alarm on | False = alarm off
    private final Map<Integer, Boolean> alarmStatus = new HashMap<>();
    private int currentMaxId = 0;

    @GetMapping("/{id}")
    public Boolean getAlarmStatus(@PathVariable("id") Integer id) {
        return alarmStatus.getOrDefault(id, null);
    }

    @PostMapping("/{id}/turnOn")
    public Boolean turnOn(@PathVariable("id") Integer id) {
        return alarmStatus.replace(id, true);
    }

    @PostMapping("/{id}/turnOff")
    public Boolean turnOff(@PathVariable("id") Integer id) {
        return alarmStatus.replace(id, false);
    }

    @PostMapping("/")
    public int addAlarm() {
        alarmStatus.put(currentMaxId, false);
        return currentMaxId++;
    }

    @DeleteMapping("/{id}")
    public Boolean removeAlarm(@PathVariable("id") Integer id) {
        return alarmStatus.remove(id);
    }

}