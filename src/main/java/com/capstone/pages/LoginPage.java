package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Better locators (by name instead of brittle XPath)
    private By username = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//*[@id='loginPanel']//input[@value='Log In']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // increase timeout to 20s
    }

    public void open(String url) {
        driver.get(url);
    }

    public void login(String user, String pass) {
        // wait until username field is visible and enabled
        WebElement userField = wait.until(ExpectedConditions.elementToBeClickable(username));
        userField.clear();
        userField.sendKeys(user);

        WebElement passField = driver.findElement(password);
        passField.clear();
        passField.sendKeys(pass);

        driver.findElement(loginBtn).click();
    }
}
