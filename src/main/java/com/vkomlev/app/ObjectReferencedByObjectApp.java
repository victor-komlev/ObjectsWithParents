package com.vkomlev.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ObjectReferencedByObjectApp {

    private static final Logger LOG = LoggerFactory.getLogger(ObjectReferencedByObjectApp.class);
    public static void main(String[] args) {
        LOG.info("Let's rock");
        SpringApplication.run(ObjectReferencedByObjectApp.class, args);
    }
}
