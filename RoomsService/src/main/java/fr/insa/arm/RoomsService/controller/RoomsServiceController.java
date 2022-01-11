package fr.insa.arm.RoomsService.controller;

import fr.insa.arm.RoomsService.model.*;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomsServiceController {

    private final INSA rooms = new INSA();
    
    @GetMapping("/")
    public ArrayList<Integer> getIds() {
        return rooms.getIds();
    }

    @PostMapping("/")
    public int addRoom(@RequestBody String name) {
        return rooms.addRoom(name);
    }
    
    @DeleteMapping("/{id}")
    public Room removeRoom(@PathVariable("id") int id) {
        return rooms.removeRoom(id);
    }
    
    @GetMapping("/{id}")
    public String getRoomName(@PathVariable("id") int id) {
        return rooms.getRoomName(id);
    }

    @PostMapping("/{id}/devices")
    public boolean affectDevice(@PathVariable("id") int idRoom, @RequestParam String type, @RequestBody Integer idDevice) {
        return rooms.affectDevice(idRoom, type, idDevice);
    }
    
    @DeleteMapping("/{id}/devices")
    public boolean removeDevice(@PathVariable("id") int idRoom, @RequestParam String type, @RequestBody Integer idDevice) {
        return rooms.removeDevice(idRoom, type, idDevice);
    }

    @GetMapping("/{id}/devices")
    public ArrayList<Integer> getIdsDevice(@PathVariable("id") int idRoom, @RequestParam String type) {
        return rooms.getIdsDevice(idRoom, type);
    }

}