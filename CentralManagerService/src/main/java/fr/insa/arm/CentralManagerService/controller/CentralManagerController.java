package fr.insa.arm.CentralManagerService.controller;

import fr.insa.arm.CentralManagerService.model.ManagerProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/centralManager")
public class CentralManagerController {

    @Autowired
    private RestTemplate restTemplate;

    public final ManagerProcess managerProcess = new ManagerProcess();

    public CentralManagerController() {
        int timerDelay = 5000;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(managerProcess, 0, timerDelay);
    }

    @PostMapping("/start")
    public String start() {
        managerProcess.restTemplate = restTemplate;
        if (!ManagerProcess.running) {
            ManagerProcess.running = true;
            return "success";
        } else {
            return "already running";
        }
    }

    @PostMapping("/stop")
    public String stop() {
        managerProcess.restTemplate = restTemplate;
        if (ManagerProcess.running) {
            ManagerProcess.running = false;
            return "success";
        } else {
            return "not running";
        }
    }

    @GetMapping("/")
    public String getStatus() {
        managerProcess.restTemplate = restTemplate;
        return ManagerProcess.running ? "central manager is running" : "central manager is not running";
    }

    @GetMapping("/test")
    public String test() {
        managerProcess.restTemplate = restTemplate;
        managerProcess.test();
        return "BRAVO";
    }
}
