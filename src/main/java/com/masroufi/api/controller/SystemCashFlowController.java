package com.masroufi.api.controller;


import com.masroufi.api.service.SystemCashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system-cash-flow")
public class SystemCashFlowController {

    @Autowired
    private SystemCashFlowService systemCashFlowService;

}
