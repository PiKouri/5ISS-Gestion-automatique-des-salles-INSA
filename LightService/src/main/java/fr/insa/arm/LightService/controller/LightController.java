package fr.insa.arm.LightService.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/light")
public class LightController {

    // True = light on | False = light off
    private final Map<Integer, Boolean> lightStatus = new HashMap<>();
    private int currentMaxId = 0;

    @GetMapping("/{id}")
    public Boolean getLightStatus(@PathVariable("id") Integer id) {
        return lightStatus.getOrDefault(id, null);
    }

    @PostMapping("/{id}/turnOn")
    public Boolean turnOn(@PathVariable("id") Integer id) {
        return lightStatus.replace(id, true);
    }

    @PostMapping("/{id}/turnOff")
    public Boolean turnOff(@PathVariable("id") Integer id) {
        return lightStatus.replace(id, false);
    }

    @PostMapping("/")
    public int addLight() {
        lightStatus.put(currentMaxId, false);
        return currentMaxId++;
    }

    @DeleteMapping("/{id}")
    public Boolean removeLight(@PathVariable("id") Integer id) {
        return lightStatus.remove(id);
    }

}