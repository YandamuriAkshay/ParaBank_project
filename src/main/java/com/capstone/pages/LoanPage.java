package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoanPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By requestLoanLink = By.xpath("//*[@id='leftPanel']/ul/li[7]/a");
    private By loanAmountField = By.xpath("//*[@id='amount']");
    private By downPaymentField = By.xpath("//*[@id='downPayment']");
    private By fromAccountDropdown = By.xpath("//*[@id='fromAccountId']");
    private By applyNowButton = By.xpath("//*[@id='requestLoanForm']/form/table/tbody/tr[4]/td[2]/input");

    private By loanStatus = By.xpath("//*[@id='loanStatus']");

    public LoanPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Navigate to Loan Page
    public void openLoanPage() {
        wait.until(ExpectedConditions.elementToBeClickable(requestLoanLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loanAmountField));
//        System.out.println("Navigated to Request Loan page");
    }

    // Enter loan amount
    public void enterLoanAmount(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            throw new IllegalArgumentException("Loan amount is null or empty in Excel");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(loanAmountField)).clear();
        driver.findElement(loanAmountField).sendKeys(amount);
    }

    // Enter down payment
    public void enterDownPayment(String downPayment) {
        if (downPayment == null || downPayment.trim().isEmpty()) {
            throw new IllegalArgumentException("Down Payment is null or empty in Excel");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(downPaymentField)).clear();
        driver.findElement(downPaymentField).sendKeys(downPayment);
    }

    // Select From Account
    public void selectFromAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(fromAccountDropdown));
        Select select = new Select(driver.findElement(fromAccountDropdown));
        if (select.getOptions().size() > 0) {
            select.selectByIndex(0); // pick first account
//            System.out.println("From Account selected: " + select.getFirstSelectedOption().getText());
        } else {
            throw new RuntimeException("No accounts available in dropdown!");
        }
    }

    // Click Apply Now
    public void clickApplyNow() {
        wait.until(ExpectedConditions.elementToBeClickable(applyNowButton)).click();
    }

    // Apply Loan full flow
    public void applyLoan(String loanAmount, String downPayment) {
        openLoanPage(); // must navigate first
        enterLoanAmount(loanAmount);
        enterDownPayment(downPayment);
        selectFromAccount();
        clickApplyNow();
    }

    // Get loan status
    public String getLoanStatus() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loanStatus)).getText().trim();
    }
}
