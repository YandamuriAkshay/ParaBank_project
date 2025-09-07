package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TransferFundsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By amountField = By.xpath("//*[@id='amount']");
    private By fromAccountDropdown = By.xpath("//*[@id='fromAccountId']");
    private By toAccountDropdown = By.xpath("//*[@id='toAccountId']");
    private By transferButton = By.xpath("//input[@value='Transfer']");
    private By successMessage = By.xpath("//h1[contains(text(),'Transfer Complete!')]");
    private By errorMessage = By.xpath("//*[contains(text(),'error') or contains(text(),'unsuccessful')]");

    // Constructor
    public TransferFundsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Enter transfer amount
    public void enterAmount(String amount) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(amountField));
        driver.findElement(amountField).clear();
        driver.findElement(amountField).sendKeys(amount);
    }

    // Select first account as "From Account"
    public void selectFromAccount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(fromAccountDropdown));
        WebElement dropdown = driver.findElement(fromAccountDropdown);

        wait.until(d -> new Select(dropdown).getOptions().size() > 0);

        Select fromSelect = new Select(dropdown);
        fromSelect.selectByIndex(0);  // Always pick the first account
//        System.out.println("✅ From Account selected: " + fromSelect.getFirstSelectedOption().getText());
    }

    // Select second account as "To Account" (with fallback if only one exists)
    public void selectToAccount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(toAccountDropdown));
        WebElement dropdown = driver.findElement(toAccountDropdown);

        wait.until(d -> new Select(dropdown).getOptions().size() > 0);

        Select toSelect = new Select(dropdown);
        List<WebElement> options = toSelect.getOptions();

        if (options.size() > 1) {
            toSelect.selectByIndex(1); // Select the 2nd account
//            System.out.println("✅ To Account selected: " + toSelect.getFirstSelectedOption().getText());
        } else {
            System.out.println("Only one account available, selecting the same account (transfer may fail).");
            toSelect.selectByIndex(0); // Select the only available account
        }
    }

    // Click Transfer
    public void clickTransfer() {
        wait.until(ExpectedConditions.elementToBeClickable(transferButton));
        driver.findElement(transferButton).click();
    }

    // Check if transfer completed successfully
    public boolean isTransferComplete() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Check if transfer failed
    public boolean isErrorShown() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
