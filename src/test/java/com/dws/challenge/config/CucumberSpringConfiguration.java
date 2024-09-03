package com.dws.challenge.config;

import com.dws.challenge.ChallengeApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@CucumberContextConfiguration
@SpringBootTest(classes = ChallengeApplication.class)
@ComponentScan("com.dws.challenge.*")
public class CucumberSpringConfiguration {
}
