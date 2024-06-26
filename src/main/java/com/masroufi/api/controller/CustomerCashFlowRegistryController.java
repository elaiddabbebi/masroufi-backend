package com.masroufi.api.controller;


import com.masroufi.api.dto.CustomerCashFlowRegistryDto;
import com.masroufi.api.dto.response.ResultSetResponse;
import com.masroufi.api.search.criteria.impl.CustomerCashFlowRegistrySearchCriteria;
import com.masroufi.api.service.CustomerCashFlowRegistryService;
import com.masroufi.api.shared.constants.http.ContentDisposition;
import com.masroufi.api.shared.constants.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/customer-cash-flow-registry")
public class CustomerCashFlowRegistryController {

    @Autowired
    private CustomerCashFlowRegistryService customerCashFlowRegistryService;

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

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadInExcel(@ModelAttribute CustomerCashFlowRegistrySearchCriteria criteria) {
        byte[] excelFile = this.customerCashFlowRegistryService.exportInExcel(criteria);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.ATTACHMENT_FILENAME + "cash-flow-registry.xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, ContentType.EXCEL_FILE);

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }

}
