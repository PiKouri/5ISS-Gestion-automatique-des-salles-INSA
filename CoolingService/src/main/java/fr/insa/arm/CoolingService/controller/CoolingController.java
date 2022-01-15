package fr.insa.arm.CoolingService.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/cooling")
public class CoolingController {

    // True = cooling system on | False = cooling system off
    private final Map<Integer, Boolean> coolingStatus = new HashMap<>();
    private int currentMaxId = 0;

    @GetMapping("/{id}")
    public Boolean getCoolingStatus(@PathVariable("id") Integer id) {
        return coolingStatus.getOrDefault(id, null);
    }

    @PostMapping("/{id}/turnOn")
    public Boolean turnOn(@PathVariable("id") Integer id) {
        return coolingStatus.replace(id, true);
    }

    @PostMapping("/{id}/turnOff")
    public Boolean turnOff(@PathVariable("id") Integer id) {
        return coolingStatus.replace(id, false);
    }

    @PostMapping("/")
    public int addCoolingSystem() {
        coolingStatus.put(currentMaxId, false);
        return currentMaxId++;
    }

    @DeleteMapping("/{id}")
    public Boolean removeCoolingSystem(@PathVariable("id") Integer id) {
        return coolingStatus.remove(id);
    }

}