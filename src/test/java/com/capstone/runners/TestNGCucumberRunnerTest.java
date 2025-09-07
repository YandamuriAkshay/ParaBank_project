package com.capstone.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepdefinitions"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber.json",  
        "junit:target/cucumber-reports/cucumber.xml"
    },
    monochrome = true,
    tags = "",
    dryRun = false
)
public class TestNGCucumberRunnerTest extends AbstractTestNGCucumberTests {
}
