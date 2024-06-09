package com.masroufi.api.service.impl;

import com.masroufi.api.repository.KpiRepository;
import com.masroufi.api.service.KpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KpiServiceImpl implements KpiService {

    @Autowired
    private KpiRepository kpiRepository;
}
