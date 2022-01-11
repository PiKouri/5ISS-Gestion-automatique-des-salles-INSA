package fr.insa.arm.HeatingService.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heating")
public class HeatingController {

    // True = heating system on | False = heating system off
    private final Map<Integer, Boolean> heatingStatus = new HashMap<>();
    private int currentMaxId = 0;

    @GetMapping("/{id}")
    public Boolean getHeatingStatus(@PathVariable("id") Integer id) {
        return heatingStatus.getOrDefault(id, null);
    }

    @PostMapping("/{id}/turnOn")
    public Boolean turnOn(@PathVariable("id") Integer id) {
        return heatingStatus.replace(id, true);
    }

    @PostMapping("/{id}/turnOff")
    public Boolean turnOff(@PathVariable("id") Integer id) {
        return heatingStatus.replace(id, false);
    }

    @PostMapping("/")
    public int addHeatingSystem() {
        heatingStatus.put(currentMaxId, false);
        return currentMaxId++;
    }

    @DeleteMapping("/{id}")
    public Boolean removeHeatingSystem(@PathVariable("id") Integer id) {
        return heatingStatus.remove(id);
    }

}