package com.capstone.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static String currentBrowser = "chrome"; // default

    /**
     * Initialize driver based on browser name
     */
    public static WebDriver initDriver(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = "chrome"; // fallback
        }
        currentBrowser = browser.toLowerCase();

        switch (currentBrowser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        getDriver().manage().window().maximize();
        return getDriver();
    }

    /**
     * Get active driver
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quit current driver
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    /**
     * Restart driver with same browser (useful between Registration → Login)
     */
    public static WebDriver restartDriver() {
        quitDriver();
        return initDriver(currentBrowser);
    }
}
