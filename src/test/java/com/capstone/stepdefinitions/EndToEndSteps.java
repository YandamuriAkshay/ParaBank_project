package com.capstone.stepdefinitions;

import com.capstone.driver.DriverFactory;
import com.capstone.pages.*;
import com.capstone.utils.ExcelUtil;
import com.capstone.utils.SessionData;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.InputStream;
import java.time.Duration;
import java.util.Map;
import java.util.Random;

public class EndToEndSteps {
    private LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    private RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());
    private AccountsOverviewPage overviewPage = new AccountsOverviewPage(DriverFactory.getDriver());
    private OpenAccountPage openAccountPage = new OpenAccountPage(DriverFactory.getDriver());
    private TransferFundsPage transferPage = new TransferFundsPage(DriverFactory.getDriver());
    private UpdateProfilePage profilePage = new UpdateProfilePage(DriverFactory.getDriver());
    private LoanPage loanPage = new LoanPage(DriverFactory.getDriver());

    private static String baseUrl = "https://parabank.parasoft.com/parabank";

    // Utility: read Excel test data
    private Map<String, String> getData(String sheet, int row) {
        try {
            String resource = "testdata/demo_testdata.xlsx";
            try (InputStream is = Thread.currentThread()
                                        .getContextClassLoader()
                                        .getResourceAsStream(resource)) {
                if (is == null) {
                    throw new RuntimeException("Test data file not found: " + resource);
                }
                ExcelUtil excel = new ExcelUtil(is);
                return excel.getSheetData(sheet).get(row - 1); // row starts from 1
            }
        } catch (Exception e) {
            throw new RuntimeException("Excel data not found for sheet " + sheet + " row " + row, e);
        }
    }

    // ===================== Application =====================

    @Given("the application is opened")
    public void openApp() {
        loginPage.open(baseUrl);
    }

    // ===================== Registration =====================

    @When("user opens registration page and registers with data from sheet {string} row {int}")
    public void openRegisterAndRegister(String sheet, Integer row) {
        Map<String, String> data = getData(sheet, row);

        String uniqueUser = data.get("username") + new Random().nextInt(1000);
        data.put("username", uniqueUser);

        SessionData.setRegisteredUser(uniqueUser);
        SessionData.setRegisteredPass(data.get("password"));

        registrationPage.openRegisterPage();
        registrationPage.register(data);
    }


    @Then("registration should be successful")
    public void checkRegistration() {
        Assert.assertTrue(
            DriverFactory.getDriver().getPageSource().contains("Your account was created"),
            "Registration failed"
        );
    }

    // ===================== Login =====================

    @When("user logs in with username {string} and password {string}")
    public void loginUser(String user, String pass) {
        if (user.equals("{registered}")) {
            user = SessionData.getRegisteredUser();
            pass = SessionData.getRegisteredPass();
        }

        // Close old browser after registration
        DriverFactory.quitDriver();

        // Start fresh browser
        DriverFactory.restartDriver();

        // Recreate page objects with new driver
        loginPage = new LoginPage(DriverFactory.getDriver());
        overviewPage = new AccountsOverviewPage(DriverFactory.getDriver());
        openAccountPage = new OpenAccountPage(DriverFactory.getDriver());
        transferPage = new TransferFundsPage(DriverFactory.getDriver());
        profilePage = new UpdateProfilePage(DriverFactory.getDriver());
        loanPage = new LoanPage(DriverFactory.getDriver());

        // Navigate to login page
        DriverFactory.getDriver().get("https://parabank.parasoft.com/parabank/index.htm");

        // Perform login
        loginPage.login(user, pass);
    }



    @Then("login result should be {string}")
    public void loginResult(String expected) {
        String pageSource = DriverFactory.getDriver().getPageSource();
        boolean loggedIn = pageSource.contains("Welcome");

        if (expected.equals("success")) {
            Assert.assertTrue(loggedIn, "Expected login success but no Welcome message found");

            // ✅ Extract Welcome text
            String welcomeMsg = DriverFactory.getDriver()
                    .findElement(By.xpath("//*[@id=\"leftPanel\"]/p/b"))
                    .getText();
//            System.out.println("Login Success: " + welcomeMsg);

            // ✅ Navigate to overview to capture primary account
            DriverFactory.getDriver().get("https://parabank.parasoft.com/parabank/overview.htm");

            try {
                WebElement acc = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[@id='accountTable']//tr[1]/td[1]/a")
                        ));
                String accountNumber = acc.getText().trim();
                SessionData.setPrimaryAccount(accountNumber);
            } catch (Exception e) {
                throw new AssertionError("Could not capture primary account number after login", e);
            }

        } else {
            Assert.assertFalse(loggedIn, "Expected failure but found Welcome message");
        }
    }


    // ===================== Accounts Overview =====================

    @When("user navigates to Accounts Overview")
    public void goOverview() {
        DriverFactory.getDriver().get(baseUrl + "/overview.htm");
    }

    @Then("Accounts table should show Account, Balance, Available Amount")
    public void accountsTableCheck() {
        Assert.assertTrue(
            overviewPage.hasOverviewHeaders(),
            "Expected headers [Account, Balance*, Available Amount] not found in table"
        );
    }


    // ===================== Open Account =====================

    @When("user opens a {string} account")
    public void openAccount(String type) {
        DriverFactory.getDriver().get(baseUrl + "/openaccount.htm");
        openAccountPage.selectAccountType(type);
        openAccountPage.clickOpenAccount();
    }

    @Then("{string} message should appear")
    public void accountOpened(String msg) {
        Assert.assertTrue(openAccountPage.isAccountOpened(), msg + " not shown");
    }

    // ===================== Transfer Funds =====================

    @When("user transfers data from sheet {string} row {int}")
    public void transferFunds(String sheet, Integer row) {
        Map<String, String> data = getData(sheet, row);
        DriverFactory.getDriver().get(baseUrl + "/transfer.htm");

        transferPage.enterAmount(data.get("amount"));
        transferPage.selectFromAccount();   // always option[1]
        transferPage.selectToAccount();     // always option[2]
        transferPage.clickTransfer();
    }

    @Then("transfer result should be {string}")
    public void transferResult(String expected) {
        if (expected.equalsIgnoreCase("success")) {
            Assert.assertTrue(transferPage.isTransferComplete(), "Expected transfer success but failed");
        } else {
            Assert.assertTrue(transferPage.isErrorShown(), "Expected error but no error shown");
        }
    }


    // ===================== Update Profile =====================

    @When("user updates profile from sheet {string} row {int}")
    public void updateProfile(String sheet, Integer row) {
        Map<String, String> data = getData(sheet, row);
        DriverFactory.getDriver().get(baseUrl + "/updateprofile.htm");
        profilePage.updateProfile(data.get("state"), data.get("zipCode"));
    }

    @Then("profile update message should be shown")
    public void profileUpdateCheck() {
        Assert.assertTrue(profilePage.isProfileUpdated(), "Profile update failed");
    }

    // ===================== Loan =====================

    @When("user applies loan from sheet {string} row {int}")
    public void applyLoan(String sheet, Integer row) {
        Map<String, String> data = getData(sheet, row);

        String loanAmount = data.get("loanAmount");   // Excel column A
        String downPayment = data.get("downPayment"); // Excel column B

        loanPage.applyLoan(loanAmount, downPayment);
    }

    @Then("loan status should be displayed")
    public void loan_status_should_be_displayed() {
        String actualStatus = loanPage.getLoanStatus(); // "Approved" or "Denied"
        System.out.println("Loan Status: " + actualStatus);
        Assert.assertTrue(
            actualStatus.equalsIgnoreCase("Approved") || actualStatus.equalsIgnoreCase("Denied"),
            "Loan status is not valid. Found: " + actualStatus
        );
    }





}
