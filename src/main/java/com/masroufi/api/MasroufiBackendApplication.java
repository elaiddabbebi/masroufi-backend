package com.masroufi.api;

import com.masroufi.api.initializer.ApplicationInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MasroufiBackendApplication {

    @Autowired
    private ApplicationInitializer initializer;

    @PostConstruct
    public void init() {
        this.initializer.initApplication();
    }

    public static void main(String[] args) {
        SpringApplication.run(MasroufiBackendApplication.class, args);
    }
}
