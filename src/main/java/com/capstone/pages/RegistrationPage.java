package com.capstone.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By firstName = By.xpath("//*[@id='customerForm']/table/tbody/tr[1]/td[2]/input");
    private By lastName = By.xpath("//*[@id='customerForm']/table/tbody/tr[2]/td[2]/input");
    private By address = By.xpath("//*[@id='customer.address.street']");
    private By city = By.xpath("//*[@id='customer.address.city']");
    private By state = By.xpath("//*[@id='customer.address.state']");
    private By zip = By.xpath("//*[@id='customer.address.zipCode']");
    private By phone = By.xpath("//*[@id='customer.phoneNumber']");
    private By ssn = By.xpath("//*[@id='customer.ssn']");
    private By username = By.xpath("//*[@id='customer.username']");
    private By password = By.xpath("//*[@id='customer.password']");
    private By confirm = By.xpath("//*[@id='repeatedPassword']");
    private By registerBtn = By.xpath("//*[@id='customerForm']/table/tbody/tr[13]/td[2]/input");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openRegisterPage() {
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
    }

    public void register(Map<String, String> data) {
        // Fill form
        driver.findElement(firstName).sendKeys(data.get("firstName"));
        driver.findElement(lastName).sendKeys(data.get("lastName"));
        driver.findElement(address).sendKeys(data.get("address"));
        driver.findElement(city).sendKeys(data.get("city"));
        driver.findElement(state).sendKeys(data.get("state"));
        driver.findElement(zip).sendKeys(data.get("zipCode"));
        driver.findElement(phone).sendKeys(data.get("phone"));
        driver.findElement(ssn).sendKeys(data.get("ssn"));
        driver.findElement(username).sendKeys(data.get("username"));
        driver.findElement(password).sendKeys(data.get("password"));
        driver.findElement(confirm).sendKeys(data.get("password"));

        // Submit
        driver.findElement(registerBtn).click();
    }
}
