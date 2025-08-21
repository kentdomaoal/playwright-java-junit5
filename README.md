# Playwright-Java-junit5


---
UI Testing using Playwright Java + Junit5

For this test automation project Playwright Java Framework was used.

Junit5 was used for the TestRunner, Parameterized Test, Parallel Execution, Assertion.

Test Data are stored in CSV files.

In addition to the built-in Playwright and Junit5 Assertions, AssertJ was also used for SoftAssertions.

Page Object Model Pattern was applied.

Common configuration properties are externalized via property file(s)

Allure Report was integrated for the Reporting.


---
## How to run in local machine

## 💻 *Pre-requisites*
- [![Git](https://img.shields.io/badge/-Git-F05032?style=flat&logo=git&logoColor=FFFFFF)](https://git-scm.com/downloads)
- [![Java 8](https://img.shields.io/badge/-Java%208-red?style=flat&logo=java&logoColor=FFFFFF)](https://www.oracle.com/ph/java/technologies/downloads) or higher
- [![Maven](https://img.shields.io/badge/-Maven-C71A36?style=flat&logo=apache-maven&logoColor=FFFFFF)](https://maven.apache.org/download.cgi)
---

### Clone the project
```
git clone https://github.com/kentdomaoal/playwright-java-junit5.git
```
``` 
cd playwright-java-junit5.git
```

### Install Dependencies
``` 
mvn clean install
``` 

### Running the test
``` 
mvn clean test
``` 

**Optional Parameters** when running the test:

**browserName** - the desired browser to run the test (default value = 'chrome')

**headless** - choice to run the test on headless or headed mode (default value = 'false')

``` 
mvn clean test -Dbrowser=firefox -Dheadless=true
``` 


### Generate and Open Allure report
``` 
allure generate --clean target/allure-results && allure open
```
---