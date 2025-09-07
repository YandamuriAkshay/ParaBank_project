package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class OpenAccountPage {
    private WebDriver driver;

    private By accountTypeDropdown = By.id("type");   // Dropdown for account type
    private By openAccountBtn = By.xpath("//*[@id='openAccountForm']/form/div/input");
    private By successMsg = By.xpath("//*[contains(text(),'Account Opened')]");

    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectAccountType(String type) {
        new Select(driver.findElement(accountTypeDropdown)).selectByVisibleText(type);
    }

    public void clickOpenAccount() {
        driver.findElement(openAccountBtn).click();
    }

    public boolean isAccountOpened() {
        return driver.getPageSource().contains("Account Opened!");
    }
}
