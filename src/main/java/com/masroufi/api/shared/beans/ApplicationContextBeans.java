package com.masroufi.api.shared.beans;

import com.masroufi.api.shared.context.SpringApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextBeans {
    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }
}
