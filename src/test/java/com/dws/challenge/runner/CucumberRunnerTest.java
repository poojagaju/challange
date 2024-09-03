package com.dws.challenge.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/transfer.feature",
        glue = "com.dws.challenge.stepdefinitions",
        plugin = {"pretty", "html:build/cucumber-reports.html"}
)
public class CucumberRunnerTest {
}
