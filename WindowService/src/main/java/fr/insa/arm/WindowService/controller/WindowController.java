package fr.insa.arm.WindowService.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/window")
public class WindowController {

    // True = open | False = closed
    private final Map<Integer, Boolean> windowStatus = new HashMap<>();
    private final Map<Integer, Boolean> shutterStatus = new HashMap<>();
    private int currentMaxId = 0;

    @GetMapping("/{id}")
    public Boolean getWindowStatus(@PathVariable("id") Integer id) {
        return windowStatus.getOrDefault(id, null);
    }

    @GetMapping("/{id}/shutter")
    public Boolean getShutterStatus(@PathVariable("id") Integer id) {
        return shutterStatus.getOrDefault(id, null);
    }


    @PostMapping("/{id}/open")
    public Boolean openWindow(@PathVariable("id") Integer id) {
        return windowStatus.replace(id, true);
    }

    @PostMapping("/{id}/close")
    public Boolean closeWindow(@PathVariable("id") Integer id) {
        return windowStatus.replace(id, false);
    }


    @PostMapping("/{id}/shutter/open")
    public Boolean openShutter(@PathVariable("id") Integer id) {
        return shutterStatus.replace(id, true);
    }

    @PostMapping("/{id}/shutter/close")
    public Boolean closeShutter(@PathVariable("id") Integer id) {
        return shutterStatus.replace(id, false);
    }

    @PostMapping("/")
    public int addWindow() {
        windowStatus.put(currentMaxId, false);
        shutterStatus.put(currentMaxId, false);
        return currentMaxId++;
    }

    @DeleteMapping("/{id}")
    public Boolean removeWindow(@PathVariable("id") Integer id) {
        Boolean b1 = windowStatus.remove(id);
        Boolean b2 = shutterStatus.remove(id);
        return (b1 != null && b2 != null) ? (b1 && b2) : null;
    }

}
