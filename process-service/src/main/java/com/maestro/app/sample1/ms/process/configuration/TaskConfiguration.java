package com.maestro.app.sample1.ms.process.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "com.maestro.app.sample1.ms.process.task" })
public class TaskConfiguration {
}
