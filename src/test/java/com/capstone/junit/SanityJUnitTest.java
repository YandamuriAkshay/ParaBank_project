package com.capstone.junit;

import com.capstone.driver.DriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SanityJUnitTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        driver = DriverFactory.getDriver();
    }

    @Test
    @Order(1)
    public void testHomePageLoads() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        String title = driver.getTitle();
        System.out.println("Page title = " + title);
        assertTrue(title.contains("ParaBank"), "Homepage did not load correctly");
    }

    @Test
    @Order(2)
    public void testLoginPanelExists() {
        boolean loginPanelPresent = driver.findElements(By.id("loginPanel")).size() > 0;
        assertTrue(loginPanelPresent, "Login panel not found on homepage");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
