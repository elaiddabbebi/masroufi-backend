package com.masroufi.api.controller;


import com.masroufi.api.dto.CashFlowCategoryDto;
import com.masroufi.api.dto.request.ValidityModel;
import com.masroufi.api.service.CashFlowCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cash-flow-category")
public class CashFlowCategoryController {

    @Autowired
    private CashFlowCategoryService cashFlowCategoryService;

    @GetMapping
    List<CashFlowCategoryDto> findAll() {
        return this.cashFlowCategoryService.findAll();
    }

    @GetMapping("/{uuid}")
    CashFlowCategoryDto findCategory(@PathVariable String uuid) {
        return this.cashFlowCategoryService.findCashFlowCategory(uuid);
    }

    @PostMapping
    CashFlowCategoryDto createCategory(@RequestBody CashFlowCategoryDto categoryDto) {
        return this.cashFlowCategoryService.createCashFlowCategory(categoryDto);
    }

    @PutMapping("/{uuid}")
    CashFlowCategoryDto updateCategory(@PathVariable String uuid, @RequestBody CashFlowCategoryDto categoryDto) {
        return this.cashFlowCategoryService.updateCashFlowCategory(uuid, categoryDto);
    }

    @PatchMapping("/{uuid}")
    CashFlowCategoryDto updateCategoryValidity(@PathVariable String uuid, @RequestBody ValidityModel validityModel) {
        return this.cashFlowCategoryService.updateCashFlowCategoryValidity(uuid, validityModel.isValid());
    }

    @DeleteMapping("/{uuid}")
    CashFlowCategoryDto deleteCategory(@PathVariable String uuid) {
        return this.cashFlowCategoryService.deleteCashFlowCategory(uuid);
    }

    @GetMapping("/check-exist")
    boolean checkIfCategoryExist(@RequestParam String categoryName) {
        return this.cashFlowCategoryService.checkIfCategoryExist(categoryName);
    }

    @GetMapping("/top-10-by-name")
    List<CashFlowCategoryDto> findTop10ByNameLike(@RequestParam String categoryName) {
        return this.cashFlowCategoryService.findTop10ByNameLike(categoryName);
    }

    @GetMapping("/name-list")
    List<String> findAllCategoryNames() {
        return this.cashFlowCategoryService.findAllCategoryNames();
    }
}
