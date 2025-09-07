Feature: Parabank End-to-End Flow
  Verify that a user can register, login, open account, transfer funds, update profile, and request a loan in order.

  Scenario: Run all functions in order
    Given the application is opened
    When user opens registration page and registers with data from sheet "Registration" row 1
    Then registration should be successful

    When user logs in with username "{registered}" and password "{registered}"
    Then login result should be "success"

    When user navigates to Accounts Overview
    Then Accounts table should show Account, Balance, Available Amount

    When user opens a "SAVINGS" account
    Then "Account Opened" message should appear

    When user transfers data from sheet "Transfer" row 1
    Then transfer result should be "success"

    When user updates profile from sheet "UpdateProfile" row 1
    Then profile update message should be shown

    When user applies loan from sheet "Loan" row 1
    Then loan status should be displayed
