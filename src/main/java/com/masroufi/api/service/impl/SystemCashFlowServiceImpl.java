package com.masroufi.api.service.impl;

import com.masroufi.api.repository.SystemCashFlowRepository;
import com.masroufi.api.service.SystemCashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemCashFlowServiceImpl implements SystemCashFlowService {

    @Autowired
    private SystemCashFlowRepository cashFlowRepository;
}
