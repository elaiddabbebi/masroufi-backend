package com.masroufi.api.controller;


import com.masroufi.api.dto.CashFlowDto;
import com.masroufi.api.dto.request.ValidityModel;
import com.masroufi.api.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cash-flow")
public class CashFlowController {

    @Autowired
    private CashFlowService cashFlowService;

    @GetMapping
    List<CashFlowDto> findAll() {
        return this.cashFlowService.findAll();
    }

    @GetMapping("/search")
    List<String> searchByCategory(@RequestParam(required = false) String category) {
        return this.cashFlowService.searchByCategory(category);
    }

    @GetMapping("/{uuid}")
    CashFlowDto findCashFlow(@PathVariable String uuid) {
        return this.cashFlowService.findCashFlowByUuid(uuid);
    }

    @PostMapping
    CashFlowDto createCashFlow(@RequestBody CashFlowDto dto) {
        return this.cashFlowService.createCashFlow(dto);
    }

    @PutMapping("/{uuid}")
    CashFlowDto updateCashFlow(@PathVariable String uuid, @RequestBody CashFlowDto dto) {
        return this.cashFlowService.updateCashFlow(uuid, dto);
    }

    @PatchMapping("/{uuid}")
    CashFlowDto updateCashFlowValidity(@PathVariable String uuid, @RequestBody ValidityModel validityModel) {
        return this.cashFlowService.updateCashFlowValidity(uuid, validityModel.isValid());
    }

    @DeleteMapping("/{uuid}")
    CashFlowDto deleteCashFlow(@PathVariable String uuid) {
        return this.cashFlowService.deleteCashFlow(uuid);
    }

    @GetMapping("/name-list")
    List<String> getAllCashFlowNameList() {
        return this.cashFlowService.getAllCashFlowNameList();
    }

}
