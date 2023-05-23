package com.faimio.fmwrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication

public class FmWrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmWrapperApplication.class, args);
    }
}
