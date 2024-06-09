package com.masroufi.api.controller;

import com.masroufi.api.service.KpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kpi")
public class KpiController {

    @Autowired
    private KpiService kpiService;
}
