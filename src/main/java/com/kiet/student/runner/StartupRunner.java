package com.kiet.student.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Override
    public void run(String... args) {
        logger.info("KIET Student Management System Started");
        logger.info("Loading Student Data...");
        logger.info("Student Management REST API is Ready");
    }
}
