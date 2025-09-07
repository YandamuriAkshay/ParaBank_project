package com.capstone.junit;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SanityJUnitTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        // Path to chromedriver already managed by Jenkins/DriverFactory
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    @Order(1)
    public void testHomePageLoads() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        Assertions.assertTrue(driver.getTitle().contains("ParaBank"));
    }

    @Test
    @Order(2)
    public void testLoginPanelExists() {
        Assertions.assertFalse(driver.findElements(By.name("username")).isEmpty(),
                "Username field not found");
        Assertions.assertFalse(driver.findElements(By.name("password")).isEmpty(),
                "Password field not found");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
