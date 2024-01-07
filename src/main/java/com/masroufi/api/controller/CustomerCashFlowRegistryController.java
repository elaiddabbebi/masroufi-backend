package com.masroufi.api.controller;


import com.masroufi.api.service.CustomerCashFlowRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-cash-flow-registry")
public class CustomerCashFlowRegistryController {

    @Autowired
    private CustomerCashFlowRegistryService customerCashFlowRegistryService;

}
