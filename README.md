# Selenium_WebDriver_with_Java_DEMO
Demonstration of material learnt from "Selenium WebDriver with Java -Basics to Advanced+Frameworks" Udemy course

## SETUP
This repoistory requires a .env file with the email address and password for a legitimate account for www.amazon.com.
Please see `.env-example` file for an example of the variables required

Maven version: Apache Maven 3.9.4
Java version: "17.0.8" 2023-07-18 LTS
Eclipse IDE for Java Developers Version: 2023-06 (4.28.0)

## Maven commands
- Run smoke test suite:`mvn test -DsuiteXmlFile="src/test/resources/smoke.xml"`
- Run profiles: `mvn test -P<profile-id> e.g. ErrorHandling` e.g `mvn test -PErrorHandling`

## Functionality covered
Automation Tests built:
1. E2E Test (Suite)
	- Login
	- Search for product
	- Add product to cart
	- Remove product from cart
	- Logout
2. Smoke Test Suite
	- Login Failure Test
	- Login Success Test
	- Review Orders Test
3. Error Handling Test Suite
	- Login Failure Test (including capturing screenshots on failure)
	
## Evidence supplied
1. Inheritance vs. Abstraction
	- Inhertiance uses extends (can only be used once) e.g. pageObjects extends AbstractComponents
	- Abstraction uses implements (can be used multiple times but the abstract class can not be instantiated) e.g. E2ECreateOrderTest implements IHelper and it must define the method defined in IHelper
2. Performance considerations
	- wait times
	- SQL queries
3. Framework - see repoistory
4. ExtentReports ***TO BE COMPLETED***
5. Cross Browser Testing ***TO BE COMPLETED***	
6. Selenium Grid  ***TO BE COMPLETED***