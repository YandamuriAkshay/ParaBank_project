package com.capstone.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/features",
	    glue = {"com.capstone.stepdefinitions"},
	    plugin = {
	        "pretty",
	        "json:target/cucumber.json", 
	        "html:target/cucumber-reports.html",
	        "junit:target/cucumber.xml"
	    },
	    monochrome = true
	)

public class TestNGCucumberRunnerTest extends AbstractTestNGCucumberTests {
}
