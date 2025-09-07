package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AccountsOverviewPage {
    private WebDriver driver;

    // ✅ Locate header cells inside the account table
    private By accountTableHeaders = By.xpath("//*[@id='accountTable']//th");

    public AccountsOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Check if the Accounts Overview table has required headers
     */
    public boolean hasOverviewHeaders() {
        List<WebElement> headers = driver.findElements(accountTableHeaders);

        boolean hasAccount = headers.stream().anyMatch(h -> h.getText().trim().equalsIgnoreCase("Account"));
        boolean hasBalance = headers.stream().anyMatch(h -> h.getText().trim().equalsIgnoreCase("Balance*"));
        boolean hasAvailable = headers.stream().anyMatch(h -> h.getText().trim().equalsIgnoreCase("Available Amount"));

        return hasAccount && hasBalance && hasAvailable;
    }
}
