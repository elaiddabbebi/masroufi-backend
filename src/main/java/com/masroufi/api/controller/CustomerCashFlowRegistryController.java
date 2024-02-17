package com.masroufi.api.controller;


import com.masroufi.api.dto.CustomerCashFlowRegistryDto;
import com.masroufi.api.service.CustomerCashFlowRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-cash-flow-registry")
public class CustomerCashFlowRegistryController {

    @Autowired
    private CustomerCashFlowRegistryService customerCashFlowRegistryService;

    @GetMapping
    List<CustomerCashFlowRegistryDto> findAll() {
        return this.customerCashFlowRegistryService.findAll();
    }

    @GetMapping("/{uuid}")
    CustomerCashFlowRegistryDto findByUuid(@PathVariable String uuid) {
        return this.customerCashFlowRegistryService.findByUuid(uuid);
    }

    @PostMapping
    CustomerCashFlowRegistryDto create(@RequestBody CustomerCashFlowRegistryDto dto) {
        return this.customerCashFlowRegistryService.create(dto);
    }

    @PutMapping("/{uuid}")
    CustomerCashFlowRegistryDto update(@PathVariable String uuid, @RequestBody CustomerCashFlowRegistryDto dto) {
        return this.customerCashFlowRegistryService.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    CustomerCashFlowRegistryDto delete(@PathVariable String uuid) {
        return this.customerCashFlowRegistryService.delete(uuid);
    }

}
