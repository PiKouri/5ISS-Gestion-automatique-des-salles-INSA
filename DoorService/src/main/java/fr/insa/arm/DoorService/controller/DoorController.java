package fr.insa.arm.DoorService.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/door")
public class DoorController {

    // True = open | False = closed
    private final Map<Integer, Boolean> doorStatus = new HashMap<>();
    private int currentMaxId = 0;

    @GetMapping("/{id}")
    public Boolean getDoorStatus(@PathVariable("id") Integer id) {
        return doorStatus.getOrDefault(id, null);
    }

    @PostMapping("/{id}/open")
    public Boolean openDoor(@PathVariable("id") Integer id) {
        return doorStatus.replace(id, true);
    }

    @PostMapping("/{id}/close")
    public Boolean closeDoor(@PathVariable("id") Integer id) {
        return doorStatus.replace(id, false);
    }

    @PostMapping("/")
    public int addDoor() {
        doorStatus.put(currentMaxId, false);
        return currentMaxId++;
    }

    @DeleteMapping("/{id}")
    public Boolean removeDoor(@PathVariable("id") Integer id) {
        return doorStatus.remove(id);
    }

}
