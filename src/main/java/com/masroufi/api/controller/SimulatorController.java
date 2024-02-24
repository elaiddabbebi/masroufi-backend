package com.masroufi.api.controller;

import com.masroufi.api.service.SimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulator")
public class SimulatorController {

    @Autowired
    private SimulatorService simulatorService;
}
