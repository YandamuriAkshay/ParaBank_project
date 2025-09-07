package com.capstone.junit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SanityJUnitTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        // Automatically downloads & sets up ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    @Order(1)
    public void testHomePageLoads() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        Assertions.assertTrue(driver.getTitle().contains("ParaBank"),
                "Home page title does not contain 'ParaBank'");
    }

    @Test
    @Order(2)
    public void testLoginPanelExists() {
        Assertions.assertFalse(driver.findElements(By.name("username")).isEmpty(),
                "Login panel (username field) not found");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
