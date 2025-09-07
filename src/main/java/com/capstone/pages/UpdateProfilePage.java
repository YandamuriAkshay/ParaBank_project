package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UpdateProfilePage {
    private WebDriver driver;

    private By stateField = By.id("customer.address.state");
    private By zipField = By.id("customer.address.zipCode");
    private By updateBtn = By.xpath("//*[@id='updateProfileForm']/form/table/tbody/tr[8]/td[2]/input");
    private By successMsg = By.xpath("//*[contains(text(),'Profile Updated')]");

    public UpdateProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void updateProfile(String state, String zip) {
        driver.findElement(stateField).clear();
        driver.findElement(stateField).sendKeys(state);
        driver.findElement(zipField).clear();
        driver.findElement(zipField).sendKeys(zip);
        driver.findElement(updateBtn).click();
    }

    public boolean isProfileUpdated() {
        return driver.findElements(successMsg).size() > 0;
    }
}
