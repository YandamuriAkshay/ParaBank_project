# 🏦 Parabank Automation Suite

This project automates **Parabank** demo banking website (`https://parabank.parasoft.com/parabank`) using:

- **Java 21**
- **Maven**
- **Selenium WebDriver**
- **Cucumber (BDD)**
- **TestNG & JUnit**
- **Excel (Apache POI) for test data**
- **Jenkins CI/CD**

It covers **end-to-end banking workflows** like:
- User Registration
- Login
- Accounts Overview
- Open Account
- Transfer Funds
- Update Profile
- Request Loan
- Bill Payment (separate test)

---

## 📂 Project Structure

```
src
 ├── main/java/com/capstone/
 │    ├── driver/DriverFactory.java        # Driver management
 │    ├── pages/                           # Page Object Models
 │    └── utils/                           # Config, ExcelUtil, SessionData
 │
 └── test/java/com/capstone/
      ├── runners/TestNGCucumberRunnerTest.java
      ├── stepdefinitions/EndToEndSteps.java
      ├── stepdefinitions/BillPaySteps.java
      ├── stepdefinitions/Hooks.java
      └── junit/SanityJUnitTest.java
resources
 ├── features/
 │    ├── end_to_end.feature               # End-to-End flow
 │    ├── login.feature                    # Login test
 │    └── billpay.feature                  # Bill Pay test
 └── testdata/
      ├── demo_testdata.xlsx               # Registration, Transfer, Profile, Loan
      └── billpay.xlsx                     # Bill payment test data
```

---

## ⚡ Prerequisites

- Java 21+
- Maven 3.8+
- Chrome / Firefox installed
- Git
- (Optional) Jenkins for CI/CD

---

## 🚀 Running Tests Locally

### Run all scenarios
```bash
mvn clean test
```

### Run with TestNG suite
```bash
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml
```

### Run with specific browser
```bash
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
```

### Run JUnit sanity test
```bash
mvn -Dtest=com.capstone.junit.SanityJUnitTest test
```

---

## 📊 Reports

After execution, reports will be generated in:

- **JUnit/TestNG XML reports** → `target/surefire-reports/`
- **Cucumber JSON/HTML reports** → `target/cucumber-html-reports/`

Open the HTML report:
```
target/cucumber-html-reports/overview-features.html
```

---

## 🔄 Jenkins Integration

1. **Install plugins**: Git, Maven, Pipeline, HTML Publisher, JUnit, (optional) Cucumber Reports.
2. **Configure tools**:
   - JDK (e.g., `jdk11` or `jdk21`)
   - Maven (e.g., `Maven 3.x`)
3. **Create Pipeline Job** → point to repo → use this `Jenkinsfile`.

### Example `Jenkinsfile`
```groovy
pipeline {
  agent any
  parameters {
    string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser to run tests')
  }
  tools {
    jdk 'jdk21'
    maven 'Maven 3.x'
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build & Test') {
      steps {
        sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dbrowser=${params.BROWSER}"
      }
    }
    stage('Publish Reports') {
      steps {
        junit 'target/surefire-reports/*.xml'
        publishHTML([reportDir: 'target/cucumber-html-reports',
                     reportFiles: 'overview-features.html',
                     reportName: 'Cucumber Report'])
      }
    }
  }
}
```

---

## 🧪 Test Scenarios

### ✅ Test 1: End-to-End Flow
- Register new user
- Login
- Accounts Overview validation
- Open Savings Account
- Transfer Funds
- Update Profile
- Request Loan

### ✅ Test 2: Login
- Positive login (valid creds)
- Negative login (invalid creds)

### ✅ Test 3: Bill Pay
- Navigate to Bill Pay
- Enter Payee details from `billpay.xlsx`
- Send Payment
- Validate "Bill Payment Complete"

---

## 🛠 Utilities

- **ExcelUtil** → Reads test data from `.xlsx` sheets.
- **SessionData** → Stores runtime data (username, password, account IDs).
- **DriverFactory** → Thread-safe WebDriver management.

---

## 🎨 Console Output

Colored test steps:
- **Green** for Passed
- **Red** for Failed

---

## 📌 Next Improvements
- Run tests in **parallel** across browsers.
- Add **Allure Reports** for richer reporting.
- Dockerize for cross-platform CI.

---

## 👨‍💻 Maintainers

- **Yandamuri Akshay**  
- Contributions welcome 🎉
