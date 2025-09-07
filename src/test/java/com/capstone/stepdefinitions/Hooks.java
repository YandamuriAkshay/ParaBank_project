package com.capstone.stepdefinitions;

import com.capstone.driver.DriverFactory;
import com.capstone.utils.ConfigReader;
import com.capstone.utils.ConsoleColors;
import io.cucumber.java.*;

import org.openqa.selenium.WebDriver;

public class Hooks {
    private WebDriver driver;

    @Before
    public void setUp() {
        String browser = ConfigReader.get("browser", "chrome");
        driver = DriverFactory.initDriver(browser);
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
//            System.out.println(ConsoleColors.RED + "Step Failed: " 
//                + scenario.getName() + ConsoleColors.RESET);
        } else {
//            System.out.println(ConsoleColors.GREEN + "Step Passed: " 
//                + scenario.getName() + ConsoleColors.RESET);
        }
    }
}
