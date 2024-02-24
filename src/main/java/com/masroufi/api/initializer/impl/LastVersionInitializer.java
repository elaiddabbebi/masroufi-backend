package com.masroufi.api.initializer.impl;


import com.masroufi.api.initializer.ApplicationInitializer;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class LastVersionInitializer implements ApplicationInitializer {


    @Override
    public void initApplication() {

    }
}
