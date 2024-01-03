package com.masroufi.api.service.impl;

import com.masroufi.api.repository.CashFlowRepository;
import com.masroufi.api.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CashFlowServiceImpl implements CashFlowService {

    @Autowired
    private CashFlowRepository cashFlowRepository;
}
