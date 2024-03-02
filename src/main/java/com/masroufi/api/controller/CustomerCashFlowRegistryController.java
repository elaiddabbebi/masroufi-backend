package com.masroufi.api.controller;


import com.masroufi.api.dto.CustomerCashFlowRegistryDto;
import com.masroufi.api.dto.response.ResultSetResponse;
import com.masroufi.api.search.criteria.impl.CustomerCashFlowRegistrySearchCriteria;
import com.masroufi.api.service.CustomerCashFlowRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @GetMapping("/search")
    ResultSetResponse<CustomerCashFlowRegistryDto> search(@ModelAttribute CustomerCashFlowRegistrySearchCriteria criteria) {
        return this.customerCashFlowRegistryService.search(criteria);
    }

    @GetMapping("/{uuid}")
    CustomerCashFlowRegistryDto findByUuid(@PathVariable String uuid) {
        return this.customerCashFlowRegistryService.findByUuid(uuid);
    }

    @PostMapping
    @Transactional
    public CustomerCashFlowRegistryDto create(@RequestBody CustomerCashFlowRegistryDto dto) {
        return this.customerCashFlowRegistryService.create(dto);
    }

    @PutMapping("/{uuid}")
    @Transactional
    public CustomerCashFlowRegistryDto update(@PathVariable String uuid, @RequestBody CustomerCashFlowRegistryDto dto) {
        return this.customerCashFlowRegistryService.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    @Transactional
    public CustomerCashFlowRegistryDto delete(@PathVariable String uuid) {
        return this.customerCashFlowRegistryService.delete(uuid);
    }

}
